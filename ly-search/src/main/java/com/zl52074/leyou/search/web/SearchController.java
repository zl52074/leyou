package com.zl52074.leyou.search.web;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.search.pojo.Goods;
import com.zl52074.leyou.search.pojo.SearchRequest;
import com.zl52074.leyou.search.pojo.SearchResult;
import com.zl52074.leyou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/18 17:10
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<SearchResult> search(@RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(searchService.search(searchRequest));
    }
}
