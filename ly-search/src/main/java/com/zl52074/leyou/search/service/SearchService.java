package com.zl52074.leyou.search.service;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.po.Spu;
import com.zl52074.leyou.search.pojo.Goods;
import com.zl52074.leyou.search.pojo.SearchRequest;
import com.zl52074.leyou.search.pojo.SearchResult;
import org.springframework.http.ResponseEntity;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/19 9:51
 */
public interface SearchService {
    /**
     * @description 构建商品实体类
     * @param spu
     * @return com.zl52074.leyou.search.pojo.Goods
     * @author zl52074
     * @time 2020/11/19 9:53
     */
    Goods buildGoods(Spu spu);

    /**
     * @description 商品es条件分页查询
     * @param searchRequest
     * @return org.springframework.http.ResponseEntity<com.zl52074.leyou.common.pojo.PageResult<com.zl52074.leyou.search.pojo.Goods>>
     * @author zl52074
     * @time 2020/11/19 9:54
     */
    SearchResult search(SearchRequest searchRequest);
}
