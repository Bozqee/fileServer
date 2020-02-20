package com.exam.file.util;

import com.exam.file.exception.BadRequestException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {
    private static Connection conn;

    public static Connection getConnection() {
        InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new BadRequestException("读取配置文件失败！");
        }
        try {
            String url = properties.getProperty("jdbc.url");
            String driver = properties.getProperty("jdbc.driver");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);
//            conn = DriverManager.getConnection(url);
            return conn;
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestException("数据库连接失败！");
        }
    }

    public static void close(Connection conn) {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement stm) {
        try {
            if (stm != null) {
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
