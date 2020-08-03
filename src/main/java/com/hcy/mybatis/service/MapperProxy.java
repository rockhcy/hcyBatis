package com.hcy.mybatis.service;

import com.hcy.mybatis.config.HcyMapperRegistory;
import com.hcy.mybatis.sqlSession.HcySqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @auther hcy
 * @create 2020-08-03 14:33
 * @Description 通过动态代理将sql语句中的字符串替换为对应的参数
 *  HcySqlSession 中包含了mybatis的配置，而配置中的HcyMapperRegistory包含了命名空间+方法名 和返回值的类型
 */
public class MapperProxy<T> implements InvocationHandler {

    private final HcySqlSession hcySqlSession;
    private final Class<T> mappperInterface;

    public MapperProxy(HcySqlSession hcySqlSession,Class<T> tClass){
        this.hcySqlSession = hcySqlSession;
        this.mappperInterface = tClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HcyMapperRegistory.MapperDate mapperData =
                hcySqlSession.getHcyBaseConfig()
                        .getMapperRegistory()
                        .get(method.getDeclaringClass().getName() + "." + method.getName());
        if (null != mapperData) {
            // 对sql语句和参数进行置换
            System.out.println(String.format("SQL [ %s ], parameter [%s] ", mapperData.getSql(), args[0]));
            return hcySqlSession.selectOne(mapperData, String.valueOf(args[0]));
        }
        return method.invoke(proxy, args);
    }
}
