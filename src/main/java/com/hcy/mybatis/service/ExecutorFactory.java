package com.hcy.mybatis.service;

import com.hcy.mybatis.config.HcyBaseConfig;

/**
 * @auther hcy
 * @create 2020-08-03 14:49
 * @Description 执行器工厂，支持简单查询和带缓存的查询方式
 */
public class ExecutorFactory {
    private static final String SIMPLE = "SIMPLE";
    private static final String CACHING = "CACHING";

    public static Executor DEFAULT(HcyBaseConfig configuration) {
        return get(SIMPLE, configuration);
    }
    public static Executor get(String key, HcyBaseConfig configuration) {
        if (SIMPLE.equalsIgnoreCase(key)) {
            return new ExecutorSimple(configuration);
        }
        if (CACHING.equalsIgnoreCase(key)) {
            return new ExecutorCaching(new ExecutorSimple(configuration));
        }
        throw new RuntimeException("no executor found");
    }

    public enum ExecutorType {
        SIMPLE,CACHING
    }

}
