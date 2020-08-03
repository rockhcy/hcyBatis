package com.hcy.mybatis.mappers;

import com.hcy.bean.User;

/**
 * @auther hcy
 * @create 2020-08-03 14:06
 * @Description
 */
public interface UserMapper {

    User selectUserById(Integer userId);
}
