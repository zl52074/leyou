package com.zl52074.leyou.common.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description: 通用mapper接口
 * @author: zl52074
 * @time: 2020/11/10 14:19
 */
@RegisterMapper
public interface LyBaseMapper<T,V> extends Mapper<T>, IdListMapper<T,V>, InsertListMapper<T> {
}
