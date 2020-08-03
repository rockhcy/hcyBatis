package com.hcy.mybatis.service;

import com.hcy.mybatis.config.HcyBaseConfig;
import com.hcy.mybatis.config.HcyMapperRegistory;
import lombok.Data;

/**
 * @auther hcy
 * @create 2020-08-03 15:11
 * @Description 一个简单的执行器
 */
@Data
public class ExecutorSimple implements Executor{
    private HcyBaseConfig configuration;

    public ExecutorSimple(HcyBaseConfig configuration){
        this.configuration = configuration;
    }

    @Override
    public <T> T query(HcyMapperRegistory.MapperDate mapperDate, Object params) throws Exception {
        //初始化StatementHandler --> ParameterHandler --> ResultSetHandler
        StatementHandler handler = new StatementHandler(configuration);
        return (T) handler.query(mapperDate, params);
    }
}
