package com.zl52074.leyou.item.web;

import com.zl52074.leyou.item.po.SpecGroup;
import com.zl52074.leyou.item.po.SpecParam;
import com.zl52074.leyou.item.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 商品规格controller
 * @author: zl52074
 * @time: 2020/11/5 11:22
 */
@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    /**
     * @description 根据分类id查询该分类下的规格组
     * @param
     * @return org.springframework.http.ResponseEntity<java.util.List<com.zl52074.leyou.item.pojo.SpecGroup>>
     * @author zl52074
     * @time 2020/11/5 13:10
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroupByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(specService.querySpecGroupByCid(cid));
    }

    /**
     * @description
     * @param specGroup 更新规格组
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/5 15:39
     */
    @PutMapping("group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroup specGroup){
        specService.UpdateSpecGroup(specGroup);
        return ResponseEntity.ok().build();
    }

    /**
     * @description 保存规格组
     * @param specGroup
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/5 15:40
     */
    @PostMapping("group")
    public ResponseEntity<Void> insertSpecGroup(@RequestBody SpecGroup specGroup){
        specService.insertSpecGroup(specGroup);
        return ResponseEntity.ok().build();
    }

    /**
     * @description 删除规格组
     * @param id
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/5 15:40
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteSpecGroup(@PathVariable("id") Long id){
        specService.deleteSpecGroup(id);
        return ResponseEntity.ok().build();
    }

    /**
     * @description 根据规格组id查询规格参数
     * @param gid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.zl52074.leyou.item.pojo.SpecParam>>
     * @author zl52074
     * @time 2020/11/5 16:54
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParamList(@RequestParam(value = "gid",required = false) Long gid,
                                                          @RequestParam(value = "cid",required = false) Long cid,
                                                          @RequestParam(value = "searching",required = false)Boolean searching
    ){
        return ResponseEntity.ok(specService.querySpecParamList(gid,cid,searching));
    }

    /**
     * @description 保存规格参数
     * @param specParam
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/5 17:11
     */
    @PostMapping("param")
    public ResponseEntity<Void> insertSpecParam(@RequestBody SpecParam specParam){
        specService.insertSpecParam(specParam);
        return ResponseEntity.ok().build();
    }

    /**
     * @description 更新规格参数
     * @param specParam
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/5 17:15
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam specParam){
        specService.updateSpecParam(specParam);
        return ResponseEntity.ok().build();
    }

    /**
     * @description 根据规格参数id删除规格
     * @param id
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/5 17:32
     */
    @DeleteMapping("param/{pid}")
    public ResponseEntity<Void> deleteSpecParam(@PathVariable("pid") Long id){
        specService.deleteSpecParam(id);
        return ResponseEntity.ok().build();
    }
}
