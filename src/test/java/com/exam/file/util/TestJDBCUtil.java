package com.exam.file.util;

import org.junit.Test;

import java.sql.Connection;

public class TestJDBCUtil {
    @Test
    public void testConnection() {
        Connection connection = JDBCUtil.getConnection();
        System.out.println("数据库连接成功");
        JDBCUtil.close(connection);
    }

}
