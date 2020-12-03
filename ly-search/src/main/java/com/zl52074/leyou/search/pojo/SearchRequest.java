package com.zl52074.leyou.search.pojo;


import java.util.Map;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/18 17:51
 */

public class SearchRequest {
    private String key;
    private Integer page;
    private Map<String,String> filter;

    private static final Integer DEFAULT_SIZE = 20;
    private static final Integer DEFAULT_PAGE = 1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if(page==null){
            return DEFAULT_PAGE;
        }
        return Math.max(DEFAULT_PAGE,page);//如果页码小于默认，则返回默认页码
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize(){
        return DEFAULT_SIZE;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }
}
