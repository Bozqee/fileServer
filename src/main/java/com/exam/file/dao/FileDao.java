package com.exam.file.dao;

import com.exam.file.domain.FileInfo;
import com.exam.file.util.JDBCUtil;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class FileDao {
    private static Logger log = Logger.getLogger(Test.class.getClass());
    public boolean create(FileInfo fileInfo) {
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        boolean flag = false;
        try {
            String sql = "insert into file_info (uuid,length,type,original_name,create_time,path,envelope) values (?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1,fileInfo.getUuid());
            ps.setLong(2,fileInfo.getLength());
            ps.setString(3,fileInfo.getType());
            ps.setString(4,fileInfo.getOriginalName());
            ps.setTimestamp(5,new Timestamp(fileInfo.getCreateTime().getTime()));
            ps.setString(6,fileInfo.getPath());
            ps.setString(7,fileInfo.getEnvelope());
            int res = ps.executeUpdate();
            if (res > 0) {
                flag = true;
            }
            log.info(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(connection);
            JDBCUtil.close(ps);
            return flag;
        }
    }

    public List<FileInfo> findAll() {
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<FileInfo> fileInfoList = new ArrayList<>();
        try {
            String sql = "select uuid,length,type,original_name,create_time,path,envelope " +
                    "from file_info";
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setUuid(resultSet.getString("uuid"));
                fileInfo.setLength(resultSet.getLong("length"));
                fileInfo.setType(resultSet.getString("type"));
                fileInfo.setOriginalName(resultSet.getString("original_name"));
                fileInfo.setCreateTime(resultSet.getDate("create_time"));
                fileInfo.setEnvelope(resultSet.getString("envelope"));
                fileInfo.setPath(resultSet.getString("path"));
                fileInfoList.add(fileInfo);
            }
            log.info(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(connection);
            JDBCUtil.close(ps);
            JDBCUtil.close(resultSet);
        }
        return fileInfoList;
    }

    public FileInfo get(String uuid) {
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        FileInfo fileInfo = null;
        try {
            String sql = "select uuid,length,type,original_name,create_time,path,envelope " +
                    "from file_info where uuid=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,uuid);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                fileInfo = new FileInfo();
                fileInfo.setUuid(resultSet.getString("uuid"));
                fileInfo.setLength(resultSet.getLong("length"));
                fileInfo.setType(resultSet.getString("type"));
                fileInfo.setOriginalName(resultSet.getString("original_name"));
                fileInfo.setCreateTime(resultSet.getDate("create_time"));
                fileInfo.setEnvelope(resultSet.getString("envelope"));
                fileInfo.setPath(resultSet.getString("path"));
            }
            log.info(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(connection);
            JDBCUtil.close(ps);
            JDBCUtil.close(resultSet);
        }
        return fileInfo;
    }


    public void createTable() {
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "CREATE TABLE file_info (uuid VARCHAR(32) NOT NULL,length bigint,type VARCHAR(32),original_name VARCHAR(64),create_time TIMESTAMP,path VARCHAR(255),envelope VARCHAR(32),PRIMARY KEY (uuid))";
            ps = connection.prepareStatement(sql);
            ps.execute();
            log.info(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(connection);
            JDBCUtil.close(ps);
        }
    }

}
