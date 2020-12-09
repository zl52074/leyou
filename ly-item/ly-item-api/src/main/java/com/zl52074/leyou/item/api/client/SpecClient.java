package com.zl52074.leyou.item.api.client;

import com.zl52074.leyou.item.bo.SpecGroupBO;
import com.zl52074.leyou.item.po.SpecGroup;
import com.zl52074.leyou.item.po.SpecParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("item-service")
@RequestMapping("spec")
public interface SpecClient {
    /**
     * @description 根据条件查询规格参数
     * @param gid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.zl52074.leyou.item.pojo.SpecParam>>
     * @author zl52074
     * @time 2020/11/5 16:54
     */
    @GetMapping("params")
    public List<SpecParam> querySpecParamList(@RequestParam(value = "gid",required = false) Long gid,
                                              @RequestParam(value = "cid",required = false) Long cid,
                                              @RequestParam(value = "searching",required = false)Boolean searching);

    /**
     * @description 根据分类查询规格组和规格组参数
     * @param cid 分类id
     * @return org.springframework.http.ResponseEntity<java.util.List<com.zl52074.leyou.item.bo.SpecGroupBO>>
     * @author zl52074
     * @time 2020/12/3 17:53
     */
    @GetMapping("{cid}")
    public List<SpecGroupBO> querySpecsByCid(@PathVariable("cid") Long cid);
}
