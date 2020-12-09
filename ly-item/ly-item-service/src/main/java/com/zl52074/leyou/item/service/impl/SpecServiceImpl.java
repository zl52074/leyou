package com.zl52074.leyou.item.service.impl;

import com.zl52074.leyou.common.enums.ExceptionEnum;
import com.zl52074.leyou.common.exception.LyException;
import com.zl52074.leyou.item.bo.SpecGroupBO;
import com.zl52074.leyou.item.bo.SpuBO;
import com.zl52074.leyou.item.mapper.SpecGroupMapper;
import com.zl52074.leyou.item.mapper.SpecParamMapper;
import com.zl52074.leyou.item.po.SpecGroup;
import com.zl52074.leyou.item.po.SpecParam;
import com.zl52074.leyou.item.service.SpecService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 规格管理service
 * @author: zl52074
 * @time: 2020/11/5 13:11
 */
@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * @description 根据分类idd查询该分类下的规格组
     * @param cid
     * @return java.util.List<com.zl52074.leyou.item.pojo.SpecGroup>
     * @author zl52074
     * @time 2020/11/5 13:11
     */
    @Override
    public List<SpecGroup> querySpecGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> specGroups = specGroupMapper.select(specGroup);
        if(CollectionUtils.isEmpty(specGroups)){
            throw new LyException(ExceptionEnum.SPEC_GROUPS_NOT_FOUND);
        }
        return specGroups;
    }

    /**
     * @description 更新规格组
     * @param specGroup
     * @return void
     * @author zl52074
     * @time 2020/11/5 14:44
     */
    @Override
    public void UpdateSpecGroup(SpecGroup specGroup) {
        int count = specGroupMapper.updateByPrimaryKey(specGroup);
        if(count != 1){
            throw new LyException(ExceptionEnum.SPEC_GROUP_UPDATE_ERROR);
        }
    }

    /**
     * @description 保存规格组
     * @param specGroup
     * @return void
     * @author zl52074
     * @time 2020/11/5 15:13
     */
    @Override
    public void insertSpecGroup(SpecGroup specGroup) {
        int count = specGroupMapper.insert(specGroup);
        if(count != 1){
            throw new LyException(ExceptionEnum.SPEC_GROUP_SAVE_ERROR);
        }
    }

    /**
     * @description 删除规格组
     * @param id
     * @return void
     * @author zl52074
     * @time 2020/11/5 15:38
     */
    @Override
    public void deleteSpecGroup(Long id) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        int count = specGroupMapper.delete(specGroup);
        if(count != 1){
            throw new LyException(ExceptionEnum.SPEC_GROUP_DELETE_ERROR);
        }
    }


    /**
     * @description 根据条件查询规格参数
     * @param gid
     * @param cid
     * @param searching
     * @return java.util.List<com.zl52074.leyou.item.pojo.SpecParam>
     * @author zl52074
     * @time 2020/11/10 15:00
     */
    @Override
    public List<SpecParam> querySpecParamList(Long gid,Long cid,Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> specParams = specParamMapper.select(specParam);
        if(CollectionUtils.isEmpty(specParams)){
            throw new LyException(ExceptionEnum.SPEC_PARAMS_NOT_FOUND);
        }
        return specParams;
    }

    /**
     * @description 保存规格参数
     * @param specParam
     * @return void
     * @author zl52074
     * @time 2020/11/5 17:14
     */
    @Override
    public void insertSpecParam(SpecParam specParam) {
        int count = specParamMapper.insert(specParam);
        if(count != 1){
            throw new LyException(ExceptionEnum.SPEC_PARAM_SAVE_ERROR);
        }
    }

    /**
     * @description 更新规格参数
     * @param specParam
     * @return void
     * @author zl52074
     * @time 2020/11/5 17:17
     */
    @Override
    public void updateSpecParam(SpecParam specParam) {
        int count = specParamMapper.updateByPrimaryKey(specParam);
        if(count != 1){
            throw new LyException(ExceptionEnum.SPEC_PARAM_UPDATE_ERROR);
        }
    }

    /**
     * @description 根据规格参数id删除规格参数
     * @param id
     * @return void
     * @author zl52074
     * @time 2020/11/5 17:38
     */
    @Override
    public void deleteSpecParam(Long id) {
        SpecParam specParam = new SpecParam();
        specParam.setId(id);
        int count = specParamMapper.delete(specParam);
        if(count != 1){
            throw new LyException(ExceptionEnum.SPEC_PARAM_DELETE_ERROR);
        }
    }

    /**
     * @description 根据分类查询规格组和规格组参数
     * @param cid 分类id
     * @return java.util.List<com.zl52074.leyou.item.bo.SpecGroupBO>
     * @author zl52074
     * @time 2020/12/3 17:55
     */
    @Override
    public List<SpecGroupBO> querySpecsByCid(Long cid) {
        //查询当前分类下规格组列表
        List<SpecGroup> specGroups = querySpecGroupByCid(cid);
        //查询当前分类下规格参数列表
        List<SpecParam> specParams = querySpecParamList(null, cid,null);
        //根据gid将参数归类到map
        Map<Long,List<SpecParam>> map = new HashMap<>();
        for(SpecParam specParam:specParams){
            if(!map.containsKey(specParam.getGroupId())){
                //如果当前param的gid不在map中,则map中加入新的gid作为key
                map.put(specParam.getGroupId(),new ArrayList<>());
            }
            //根据gid从map中取值，往值的集合中add新的元素
            map.get(specParam.getGroupId()).add(specParam);
        }
        //转化group至groupBO,填充param
        List<SpecGroupBO> specGroupBOS = new ArrayList<>();
        for(SpecGroup specGroup:specGroups){
            SpecGroupBO specGroupBO = new SpecGroupBO();
            //转换
            BeanUtils.copyProperties(specGroup,specGroupBO);
            //填充
            specGroupBO.setParams(map.get(specGroupBO.getId()));
            specGroupBOS.add(specGroupBO);
        }
        return specGroupBOS;
    }

}
