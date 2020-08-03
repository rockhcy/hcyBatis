package com.hcy.mybatis.sqlSession;

import com.hcy.mybatis.config.HcyBaseConfig;
import com.hcy.mybatis.config.HcyMapperRegistory;
import com.hcy.mybatis.service.Executor;
import com.hcy.mybatis.service.MapperProxy;

import java.lang.reflect.Proxy;


/**
 * @auther hcy
 * @create 2020-08-03 14:18
 * @Description sqlSession对象，mybatis就是通过它来操作数据库的。
 * 类似jdbc的 Connection + PreparedStatement，但是做的事情要比他们多的多。
 *  里面会有两个成员，
 *  HcyBaseConfig mybatis的配置对象，根据配置生成对应的sqlSession
 *  Executor sql执行器，通过代理将sql语句中的?替换为对应的参数
 */
public class HcySqlSession {

    private HcyBaseConfig hcyBaseConfig;
    private Executor executor;

    public HcyBaseConfig getHcyBaseConfig(){
        return hcyBaseConfig;
    }
    //将executor 和 hcyBaseConfig关联起来
    public HcySqlSession(HcyBaseConfig getHcyBaseConfig,Executor executor){
        this.executor = executor;
        this.hcyBaseConfig = getHcyBaseConfig;
    }
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},new MapperProxy(this,clazz));
    }

    public <T> T selectOne(HcyMapperRegistory.MapperDate mapperDate, Object parameter) throws Exception {
        return executor.query(mapperDate, parameter);
    }
}
