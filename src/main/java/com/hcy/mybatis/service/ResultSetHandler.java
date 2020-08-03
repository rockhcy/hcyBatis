package com.hcy.mybatis.service;

import com.hcy.mybatis.config.HcyBaseConfig;
import com.hcy.mybatis.config.HcyMapperRegistory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @auther hcy
 * @create 2020-08-03 14:54
 * @Description 结果处理器，将查询到的结果设置到对象中
 */
public class ResultSetHandler {
    private final HcyBaseConfig configuration;

    public ResultSetHandler(HcyBaseConfig configuration){
        this.configuration = configuration;
    }
    //在这里将sql结果设置到对象中，下面那些方法都是工具类
    public <E>E handle(PreparedStatement pstmt, HcyMapperRegistory.MapperDate mapperDate) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object resultObj = new DefaultObjectFactory().create(mapperDate.getType());
        ResultSet rs = pstmt.getResultSet();
        if (rs.next()) {
            int i = 0;
            for (Field field : resultObj.getClass().getDeclaredFields()) {
                setValue(resultObj, field, rs ,i);
            }
        }
        return (E) resultObj;
    }

    private void setValue(Object resultObj, Field field, ResultSet rs, int i) throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        Method setMethod = resultObj.getClass().getMethod("set" + upperCapital(field.getName()), field.getType());
        //通过反射将结果设置到属性中
        setMethod.invoke(resultObj, getResult(field,rs));
    }
    //检查实体类的字段类型
    private Object getResult(Field field, ResultSet rs) throws SQLException {
        //TODO type handles
        Class<?> type = field.getType();
        if(Integer.class == type){
            return rs.getInt(field.getName());
        }
        if(String.class == type){
            return rs.getString(field.getName());
        }
        return rs.getString(field.getName());
    }
    // 将结果首字母大写
    private String upperCapital(String name) {
        String first = name.substring(0, 1);
        String tail = name.substring(1);
        return first.toUpperCase() + tail;
    }

}
