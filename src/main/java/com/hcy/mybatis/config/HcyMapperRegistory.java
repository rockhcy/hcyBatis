package com.hcy.mybatis.config;

import com.hcy.bean.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther hcy
 * @create 2020-08-03 13:59
 * @Description mybatis内部维护的一个map，启动时会扫描全部mapper，
 *  key 为xml中的命名空间+方法名，
 *  value为一个对象，对象会有两个属性，分别是sql语句和一个class，class用于标记结果用什么对象接收
 */
public class HcyMapperRegistory {
    public static final Map<String,MapperDate> methodSqlMapping = new ConcurrentHashMap<>();


    // 这个地方应该是可以通过注解或者xml动态写入的，这里图方便先手工写死一条感受下。
    public HcyMapperRegistory(){
        methodSqlMapping.put("com.hcy.mybatis.mappers.UserMapper.selectUserById",
                new MapperDate("SELECT * FROM hcy_tb WHERE id = %d", User.class));
    }

    public class MapperDate<T>{
        private String sql;
        private Class<T> type;
        public MapperDate(String sql, Class<T> type) {
            this.sql = sql;
            this.type = type;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Class<T> getType() {
            return type;
        }

        public void setType(Class<T> type) {
            this.type = type;
        }
    }
    public MapperDate get(String nameSpace) {
        return methodSqlMapping.get(nameSpace);
    }

}
