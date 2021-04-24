package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.UserPO;

/**
 * @author Hodur
 * @since 2020-09-03
 */
public interface UserMapper extends BaseMapper<UserPO>, BatchMapper<UserPO> {

}

