package com.zl52074.leyou.page.service;

import java.util.Map;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/3 18:37
 */
public interface PageService {
    /**
     * @description 封装页面模型数据
     * @param id spuId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author zl52074
     * @time 2020/12/3 18:41
     */
    Map<String, Object> loadModel(Long id);

    /**
     * @description 生成页面到文件
     * @param spuId
     * @return void
     * @author zl52074
     * @time 2020/12/8 17:33
     */
    void createHtml(Long spuId);

    /**
     * @description 删除页面
     * @param spuId
     * @return void
     * @author zl52074
     * @time 2020/12/8 17:33
     */
    void deleteHtml(long spuId);
}
