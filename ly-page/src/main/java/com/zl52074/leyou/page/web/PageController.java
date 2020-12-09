package com.zl52074.leyou.page.web;

import com.zl52074.leyou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.jar.Attributes;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/3 16:24
 */
@Controller
public class PageController {

    @Autowired
    private PageService pageService;

    /**
     * @description 根据spuId封装视图模型
     * @param id spuId
     * @return java.lang.String
     * @author zl52074
     * @time 2020/12/3 16:27
     */
    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id") Long id, Model model){
        Map<String,Object> attributes = pageService.loadModel(id);
        model.addAllAttributes(attributes);
        return "item";
    }
}
