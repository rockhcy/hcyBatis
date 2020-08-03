package com.hcy.mybatis.service;

import com.hcy.mybatis.config.HcyBaseConfig;
import com.hcy.mybatis.config.HcyMapperRegistory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @auther hcy
 * @create 2020-08-03 14:53
 * @Description 参数处理器，将接口中的参数和sql中的？做置换
 * getConnection() 这个地方应该是写成活的
 */
public class StatementHandler {

    private final HcyBaseConfig configuration;

    private final ResultSetHandler resultSetHandler;

    public StatementHandler(HcyBaseConfig configuration) {
        this.configuration = configuration;
        resultSetHandler = new ResultSetHandler(configuration);
    }
    public <E> E query(HcyMapperRegistory.MapperDate mapperDate, Object parameter) throws Exception {
        try {
            //JDBC
            Connection conn = getConnection();
            // 在这个地方做参数转换
            PreparedStatement pstmt = conn.prepareStatement(String.format(mapperDate.getSql(), Integer.parseInt(String.valueOf(parameter))));
            pstmt.execute();
            // 在这个地方做结果转换
            return (E)resultSetHandler.handle(pstmt,mapperDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Connection getConnection() throws SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/mybatis_demo?useAffectedRows=true&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=false&useServerPrepStmts=false&rewriteBatchedStatements=true&serverTimezone=UTC";
        String username = "root";
        String password = "3.14";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


}
