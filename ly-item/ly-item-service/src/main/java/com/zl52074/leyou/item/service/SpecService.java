package com.zl52074.leyou.item.service;

import com.zl52074.leyou.item.po.SpecGroup;
import com.zl52074.leyou.item.po.SpecParam;

import java.util.List;

/**
 * @description: 规格管理service
 * @author: zl52074
 * @time: 2020/11/5 13:11
 */
public interface SpecService {

    /**
     * @description 根据分类idd查询该分类下的规格组
     * @param cid
     * @return java.util.List<com.zl52074.leyou.item.pojo.SpecGroup>
     * @author zl52074
     * @time 2020/11/5 13:11
     */
    public List<SpecGroup> querySpecGroupByCid(Long cid);

    /**
     * @description 修改规格组
     * @param specGroup
     * @return void
     * @author zl52074
     * @time 2020/11/5 13:32
     */
    public void UpdateSpecGroup(SpecGroup specGroup);

    /**
     * @description 保存规格组
     * @param specGroup
     * @return void
     * @author zl52074
     * @time 2020/11/5 15:12
     */
    public void insertSpecGroup(SpecGroup specGroup);

    /**
     * @description 删除规格组
     * @param cid
     * @return void
     * @author zl52074
     * @time 2020/11/5 15:36
     */
    public void deleteSpecGroup(Long cid);

    /**
     * @description 根据条件查询规格参数
     * @param gid 规格组id
     * @param cid 分类id
     * @param searching 是否用作查询
     * @return java.util.List<com.zl52074.leyou.item.pojo.SpecParam>
     * @author zl52074
     * @time 2020/11/10 15:00
     */
    public List<SpecParam> querySpecParamList(Long gid,Long cid,Boolean searching);

    /**
     * @description 保存规格参数
     * @param specParam
     * @return void
     * @author zl52074
     * @time 2020/11/5 17:11
     */
    public void insertSpecParam(SpecParam specParam);

    /**
     * @description 更新规格参数
     * @param specParam
     * @return void
     * @author zl52074
     * @time 2020/11/5 17:16
     */
    public void updateSpecParam(SpecParam specParam);

    /**
     * @description 根据规格参数id删除规格
     * @param id
     * @return void
     * @author zl52074
     * @time 2020/11/5 17:34
     */
    public void deleteSpecParam(Long id);
}
