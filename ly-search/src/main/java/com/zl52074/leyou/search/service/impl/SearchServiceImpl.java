package com.zl52074.leyou.search.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zl52074.leyou.common.enums.ExceptionEnum;
import com.zl52074.leyou.common.exception.LyException;
import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.common.utils.JsonUtils;
import com.zl52074.leyou.item.api.client.BrandClient;
import com.zl52074.leyou.item.api.client.CategoryClient;
import com.zl52074.leyou.item.api.client.GoodsClient;
import com.zl52074.leyou.item.api.client.SpecClient;
import com.zl52074.leyou.item.po.*;
import com.zl52074.leyou.search.pojo.Goods;
import com.zl52074.leyou.search.pojo.SearchRequest;
import com.zl52074.leyou.search.pojo.SearchResult;
import com.zl52074.leyou.search.repository.GoodsRepository;
import com.zl52074.leyou.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Native;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/17 13:51
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private SpecClient specClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * @description 构建goods对象
     * @param spu
     * @return com.zl52074.leyou.search.pojo.Goods
     * @author zl52074
     * @time 2020/12/1 15:40
     */
    public Goods buildGoods(Spu spu){
        Long spuId = spu.getId();
        Goods goods = new Goods();
        //查询分类
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
        if(CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());
        //查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        if(brand==null){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        String all = spu.getTitle()+" "+StringUtils.join(names," ")+" "+brand.getName();

        //查询sku
        List<Sku> skus = goodsClient.querySkuOnlyBySpuId(spuId);
        if(CollectionUtils.isEmpty(skus)){
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        List<Map<String,Object>> skuMaps = new ArrayList<>();
        List<Long> priceList = new ArrayList<>();
        for(Sku sku:skus){
            Map<String,Object> skuMap = new HashMap();
            skuMap.put("id",sku.getId());
            skuMap.put("title",sku.getTitle());
            skuMap.put("price",sku.getPrice());
            skuMap.put("image", StringUtils.substringBefore(sku.getImages(), ","));
            skuMaps.add(skuMap);
            priceList.add(sku.getPrice());
        }
        //根据分类获取参数列表
        List<SpecParam> specParams = specClient.querySpecParamList(null,spu.getCid3(), true);
        if(CollectionUtils.isEmpty(specParams)){
            throw new LyException(ExceptionEnum.SPEC_PARAMS_NOT_FOUND);
        }
        //获取spuDetail
        SpuDetail spuDetail = goodsClient.querySpuDetailBySpuId(spuId);
        Map<String,String> genericSpec = JsonUtils.parseMap(spuDetail.getGenericSpec(),String.class,String.class);
        Map<String,List<String>> specialSpec = JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<String, List<String>>>() {});
        //规格参数，key是规格参数名称，value是规格参数的值
        Map<String,Object> specs = new HashMap<>();
        for(SpecParam param:specParams){
            String name = param.getName();
            Object value = "";
            //判断是否为通用参数
            if(param.getGeneric()){
                value = genericSpec.get(param.getId().toString());
                //判断是否为数值型
                if(param.getNumeric()){
                    value = chooseSegment(value.toString(), param);
                }
            }else{
                value = specialSpec.get(param.getId().toString());
            }
            specs.put(name, value);
        }

        //构建对象
        goods.setId(spuId);
        goods.setSubTitle(spu.getSubTitle());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setAll(all); // 所有需要被搜索的信息，包含标题，分类，甚至品牌
        goods.setSkus(JsonUtils.serialize(skuMaps));// sku信息的json结构
        goods.setPrice(priceList); //所有价格集合
        goods.setSpecs(specs);//可搜索的规格参数，key是参数名，值是参数值
        return goods;
    }

    /**
     * @description 分页查询并构造查询结果
     * @param searchRequest
     * @return com.zl52074.leyou.search.pojo.SearchResult
     * @author zl52074
     * @time 2020/12/1 15:40
     */
    @Override
    public SearchResult search(SearchRequest searchRequest) {
        int page = searchRequest.getPage()-1; //ES分页从下标0开始
        int size = searchRequest.getSize();
        //创建查询构造器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //过滤要查询的字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"}, null));
        //分页
        queryBuilder.withPageable(PageRequest.of(page, size));
        //过滤
        QueryBuilder basicQueryBuilder = buildBasicQuery(searchRequest);
        queryBuilder.withQuery(basicQueryBuilder);
        //查询
        Page<Goods> goodsPage = goodsRepository.search(queryBuilder.build());
        //聚合分类
        String categoryAggName = "category_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        //聚合品牌
        String brandAggName = "brand_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));
        //查询
        AggregatedPage<Goods> result = elasticsearchTemplate.queryForPage(queryBuilder.build(), Goods.class);
        //解析page
        Long total = goodsPage.getTotalElements();
        int totalPages = goodsPage.getTotalPages();
        List<Goods> goods = goodsPage.getContent();
        //解析result
        Aggregations aggregations = result.getAggregations();
        List<Category> categories = parseCategoryAgg(aggregations.get(categoryAggName));
        List<Brand> brands = parseBrandAgg(aggregations.get(brandAggName));
        //根据分类聚合，当只有一个分类，才聚合其他参数
        List<Map<String,Object>> specs = null;
        if(categories!=null&&categories.size()==1){
            specs = buildSpecAgg(categories.get(0).getId(),basicQueryBuilder);
        }
        return new SearchResult(total,totalPages,goods,categories,brands,specs);
    }

    /**
     * @description 根据spuId重建索引
     * @param spuId
     * @return void
     * @author zl52074
     * @time 2020/12/8 16:23
     */
    @Override
    public void createOrUpdateIndex(Long spuId) {
        Spu spu =goodsClient.querySpuOnlyById(spuId);
        //当状态为上架 构建索引库,索引重复会自动覆盖
        if(spu.getSaleable()){
            Goods goods = buildGoods(spu);
            goodsRepository.save(goods);
        }
    }

    /**
     * @description 根据id删除索引
     * @param spuId
     * @return void
     * @author zl52074
     * @time 2020/12/8 17:12
     */
    @Override
    public void deleteIndex(Long spuId) {
        goodsRepository.deleteById(spuId);
    }

    private QueryBuilder buildBasicQuery(SearchRequest searchRequest) {
        //创建布尔查询
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //查询条件
        queryBuilder.must(QueryBuilders.matchQuery("all", searchRequest.getKey()));
        //过滤条件
        Map<String,String> filterMap = searchRequest.getFilter();
        for(Map.Entry<String,String> entry : filterMap.entrySet()){
            String key = entry.getKey();
            //处理key
            if(!"cid3".equals(key)&&!"brandId".equals(key)){
                key = "specs."+key+".keyword";
            }
            String value = entry.getValue();
            queryBuilder.filter(QueryBuilders.termQuery(key, value));
        }
        return queryBuilder;
    }


    /**
     * @description 根据查询结果聚合其他参数
     * @param cid 分类id
     * @param basicQueryBuilder 查询条件
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author zl52074
     * @time 2020/12/2 11:15
     */
    public List<Map<String,Object>> buildSpecAgg(Long cid,QueryBuilder basicQueryBuilder){
        //创建查询构造器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //过滤
        queryBuilder.withQuery(basicQueryBuilder);
        //获取该分类下的可作为检索的商品参数
        List<SpecParam> specParams = specClient.querySpecParamList(null, cid, true);
        for(SpecParam param:specParams){
            //添加聚合条件 keyword 不分词
//            "specs": {
//                "贴膜尺寸": "5.5-6.0英寸",
//                "材质": "钢化玻璃",
//                "类型": "高透膜"
//            }
            // key:specs下具体参数名称，value:聚合后的候选值
            queryBuilder.addAggregation(AggregationBuilders.terms(param.getName()).field("specs."+param.getName()+".keyword"));
        }
        //查询
        AggregatedPage<Goods> result = elasticsearchTemplate.queryForPage(queryBuilder.build(), Goods.class);
        //解析result
        Aggregations aggregations = result.getAggregations();
        List<Map<String,Object>> specs = new ArrayList<>();
        for(SpecParam param:specParams){
            StringTerms terms = aggregations.get(param.getName());
            List<String> options = terms.getBuckets().stream()
                    .map(b -> b.getKeyAsString()).collect(Collectors.toList());
            Map<String,Object> spec = new HashMap<>();
            spec.put("k", param.getName());
            spec.put("options",options);
            specs.add(spec);
        }
        return specs;
    }

    /**
     * @description 将cid聚合转换成category集合
     * @param terms 提前用LongTerms接收，避免后期转型
     * @return java.util.List<com.zl52074.leyou.item.po.Category>
     * @author zl52074
     * @time 2020/12/1 14:12
     */
    private List<Category> parseCategoryAgg(LongTerms terms) {
        try {
            List<Long> cids = terms.getBuckets().stream()
                    .map(b -> b.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Category> categories = categoryClient.queryCategoryByIds(cids);
            return categories;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @description 将brandId聚合转换成brand集合
     * @param terms 提前用LongTerms接收，避免后期转型
     * @return java.util.List<com.zl52074.leyou.item.po.Category>
     * @author zl52074
     * @time 2020/12/1 14:12
     */
    private List<Brand> parseBrandAgg(LongTerms terms) {
        try {
            List<Long> ids = terms.getBuckets().stream()
                    .map(b -> b.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Brand> brands = brandClient.queryBrandByIds(ids);
            return brands;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @description 根据参数区间标记数字
     * @param value
     * @param p
     * @return java.lang.String
     * @author zl52074
     * @time 2020/12/1 15:23
     */
    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }
}
