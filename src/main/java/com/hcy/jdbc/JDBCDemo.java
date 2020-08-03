package com.hcy.jdbc;

import com.hcy.bean.User;

import java.sql.*;

/**
 * @auther hcy
 * @create 2020-08-03 11:43
 * @Description 使用原生jdbc方式操作数据库。
 *  有对比才能更好的理解mybatis
 */
public class JDBCDemo {

    public static void main(String[] args) {
        System.out.println( insertUser(new User(4,"hcy",29)) );
        System.out.println( getUserById(4) );
    }


    private static int insertUser(User user){
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mybatis_demo?useAffectedRows=true&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=false&useServerPrepStmts=false&rewriteBatchedStatements=true&serverTimezone=UTC",
                    "root","3.14");
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT INTO  hcy_tb (id,username,age) VALUES (?,?,?)");
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setString(2,user.getUsername());
            preparedStatement.setInt( 3,user.getAge() );
            int  n =preparedStatement.executeUpdate();
            //注意：commit() 应该是在 executeUpdate() 后执行，网上很多帖子这个地方是错的。
            connection.commit();
            return n;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if ( connection !=null ){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private static User getUserById(Integer userId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User user = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mybatis_demo?useAffectedRows=true&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=false&useServerPrepStmts=false&rewriteBatchedStatements=true&serverTimezone=UTC",
                    "root","3.14");
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT * FROM `hcy_tb` WHERE id = ?");
            preparedStatement.setInt(1,userId);
            ResultSet rs = preparedStatement.executeQuery();
            while ( rs.next() ){
                user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setAge(rs.getInt(3));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if ( connection !=null ){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }



}
