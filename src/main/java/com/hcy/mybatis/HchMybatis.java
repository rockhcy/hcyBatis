package com.hcy.mybatis;

import com.hcy.bean.User;
import com.hcy.mybatis.config.HcyBaseConfig;
import com.hcy.mybatis.mappers.UserMapper;
import com.hcy.mybatis.service.ExecutorFactory;
import com.hcy.mybatis.sqlSession.HcySqlSession;

import java.io.FileNotFoundException;

/**
 * @auther hcy
 * @create 2020-08-03 13:32
 * @Description 该demo只是模拟了mybatis中的SqlSession初始化的过程，并通过读取配置文件和动态代理
 * 将带符号的sql和参数拼接，然后执行sql，在通过反射将查询结果设置到对象中。
 * StatementHandler 中的数据库链接没有使用线程池，这部分还有很大的优化空间。
 */
public class HchMybatis {
    public static void main(String[] args) throws FileNotFoundException {
        //1.加载配置文件
        HcyBaseConfig hcyBaseConfig = new HcyBaseConfig();
        hcyBaseConfig.setScanPath("com.hcy.mybatis.mappers");//设置要扫描的接口路径
        hcyBaseConfig.build();
        //2.创建sqlSession对象，通过它就能操作数据库了。ExecutorFactory使用的抽象工厂模式，运行普通查询和带缓存的查询两种模式
        HcySqlSession hcySqlSession = new HcySqlSession( hcyBaseConfig,
                ExecutorFactory.get(ExecutorFactory.ExecutorType.CACHING.name(),hcyBaseConfig));
        //获取到代理对象，类似spring中的@Autowired获取到接口对象
        UserMapper userMapper = hcySqlSession.getMapper(UserMapper.class);
        long t1 = System.currentTimeMillis();
        //执行接口中定义的方法，方法对应的sql已经写死在HcyMapperRegistory中了。原版mybatis是支持直接和xml写入
        User user = userMapper.selectUserById(1);
        System.out.println( user );
        long t2 = System.currentTimeMillis();
        System.out.println( "第一次查询耗时："+ (t2-t1) +"毫秒");//第一次查询耗时：3939毫秒
        user = userMapper.selectUserById(1);
        System.out.println( user );
        long t3 = System.currentTimeMillis();
        System.out.println( "第二次查询耗时："+ (t3-t2) +"毫秒");//第二次查询耗时：0毫秒。带缓存的查询速度明显高于不带缓存的
    }


}
