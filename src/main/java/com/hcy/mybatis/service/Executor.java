package com.hcy.mybatis.service;

import com.hcy.mybatis.config.HcyMapperRegistory;

/**
 * @auther hcy
 * @create 2020-08-03 14:23
 * @Description 执行器接口，后面会通过代理 将参数置换到sql语句中
 */
public interface Executor {
    <T> T query(HcyMapperRegistory.MapperDate mapperDate,Object params) throws Exception;
}
