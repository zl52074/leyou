package com.zl52074.leyou.user.mapper;

import com.zl52074.leyou.common.mapper.LyBaseMapper;
import com.zl52074.leyou.user.po.UserPO;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/18 10:15
 */
@Repository
public interface UserMapper extends LyBaseMapper<UserPO,Long> {
}
