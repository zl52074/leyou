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
import com.zl52074.leyou.search.repository.GoodsRepository;
import com.zl52074.leyou.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public PageResult<Goods> search(SearchRequest searchRequest) {
        int page = searchRequest.getPage()-1; //ES分页从下标0开始
        int size = searchRequest.getSize();
        //创建查询构造器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //过滤要查询的字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"}, null));
        //分页
        queryBuilder.withPageable(PageRequest.of(page, size));
        //过滤
        queryBuilder.withQuery(QueryBuilders.matchQuery("all",searchRequest.getKey()));
        //查询
        Page<Goods> goodsPage = goodsRepository.search(queryBuilder.build());

        Long total = goodsPage.getTotalElements();
        int totalPages = goodsPage.getTotalPages();
        List<Goods> goods = goodsPage.getContent();

        return new PageResult<Goods>(total,totalPages,goods);
    }

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
