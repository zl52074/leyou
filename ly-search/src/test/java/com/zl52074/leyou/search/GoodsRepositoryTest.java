package com.zl52074.leyou.search;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.api.client.GoodsClient;
import com.zl52074.leyou.item.po.Spu;
import com.zl52074.leyou.search.pojo.Goods;
import com.zl52074.leyou.search.repository.GoodsRepository;
import com.zl52074.leyou.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/17 10:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class GoodsRepositoryTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Test
    public void testCreate(){
        //创建索引库
        elasticsearchTemplate.createIndex(Goods.class);
        //添加映射
        elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public void testDelete(){
        elasticsearchTemplate.deleteIndex(Goods.class);
    }

    @Test
    public void loadData(){
        int page = 1;
        int rows = 100;
        int size = 0;
        do{
            //查询spu
            PageResult<Spu> spuResult = goodsClient.querySpuOnlyByPage(page,rows,true,null);
            List<Spu> spus = spuResult.getItems();
            if(CollectionUtils.isEmpty(spus)){
                break;
            }
            //构建goods
            List<Goods> goodsList = spus.stream().map(searchService::buildGoods).collect(Collectors.toList());
            //存入索引库
            goodsRepository.saveAll(goodsList);
            //翻页
            page++;
            size = spus.size();
        }while (size==100);
    }
}