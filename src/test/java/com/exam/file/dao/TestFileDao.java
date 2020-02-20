package com.exam.file.dao;

import com.exam.file.domain.FileInfo;
import com.exam.file.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class TestFileDao {

    @Test
    public void testInsert() {
        FileDao fileDao = new FileDao();
        FileInfo fileInfo = new FileInfo("1111",11L,"11","111",new Date(),"11","11");
        fileDao.create(fileInfo);
    }

    @Test
    public void testCreateTable() {
        FileDao fileDao = new FileDao();
        fileDao.createTable();
    }

    @Test
    public void testFindAll() {
        FileDao fileDao = new FileDao();
        List<FileInfo> fileInfoList = fileDao.findAll();
        fileInfoList.forEach( item -> {
            System.out.println(item);
        });
    }

    @Test
    public void testGet() {
        FileDao fileDao = new FileDao();
        FileInfo fileInfo = fileDao.get("81bab8ec4e4245968695e6128ac8bbb2");
        System.out.println(fileInfo);
    }
}
