package com.hcy.mybatis.service;

import com.hcy.mybatis.config.HcyBaseConfig;
import com.hcy.mybatis.config.HcyMapperRegistory;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther hcy
 * @create 2020-08-03 14:51
 * @Description 缓存执行器
 */
public class ExecutorCaching implements Executor{

    private HcyBaseConfig configuration;

    private ExecutorSimple executorSimple;

    private Map<String,Object> localCache = new HashMap();

    public ExecutorCaching(ExecutorSimple executorSimple) {
        this.executorSimple = executorSimple;
    }

    public ExecutorCaching(HcyBaseConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T query(HcyMapperRegistory.MapperDate mapperDate, Object params) throws Exception {
        //先查缓存，缓存中没有时才执行简单查询（executorSimple）
        Object result = localCache.get(mapperDate.getSql());
        if( null != result){
            System.out.println("缓存命中");
            return (T)result;
        }
        result =  (T) executorSimple.query(mapperDate,params);
        localCache.put(mapperDate.getSql(),result);
        return (T)result;
    }
}
