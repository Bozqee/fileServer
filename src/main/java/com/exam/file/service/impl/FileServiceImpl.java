package com.exam.file.service.impl;

import com.exam.file.dao.FileDao;
import com.exam.file.domain.FileInfo;
import com.exam.file.exception.BadRequestException;
import com.exam.file.service.FileService;
import com.exam.file.util.AESUtil;
import com.exam.file.util.FileUtil;
import com.exam.file.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author luqy
 * @date 2020-02-16
 */

@Service
public class FileServiceImpl implements FileService {

    private FileDao fileDao = new FileDao();

    @Override
    public Object create(MultipartFile file) {
        //通过年月日存储
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String timePath = sdf.format(new Date());

        //获取长度
        Long fileLength = file.getSize();

        //文件原始名字
        String originalFileName = file.getOriginalFilename();

        //文件后缀  .txt
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        //获取uuid作为加密后文件名
        String uuidName = UUIDUtil.getUuid();

        //获取密钥
        String privateKey = UUIDUtil.getUuid();

        String fileOnlyName = uuidName + suffix;
        File sourceFile = null;
        try {
            sourceFile = FileUtil.multipartFileToFile(file);
        } catch (Exception e) {
            throw new BadRequestException("文件转换失败！");
        }
        //文件加密
        File encryptFile = AESUtil.encrypt(sourceFile,fileOnlyName,suffix,privateKey);
        FileUtil.deleteTempFile(sourceFile);
        // 读取文件存储目录
        InputStream in = FileServiceImpl.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new BadRequestException("读取配置文件失败！");
        }
        String uploadPath = properties.getProperty("file.uploadPath");
        //创建文件目录
        String dirPath = uploadPath + timePath;
        File dir = new File(dirPath);
        if  (!dir.exists()) {
            dir.mkdirs();
        }

        File destFile = new File(dirPath + fileOnlyName);
        encryptFile.renameTo(destFile);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setLength(fileLength);
        fileInfo.setUuid(uuidName);
        fileInfo.setCreateTime(new Date());
        fileInfo.setType(suffix);
        fileInfo.setPath(dirPath);
        fileInfo.setEnvelope(privateKey);
        fileInfo.setOriginalName(originalFileName.substring(0,originalFileName.lastIndexOf(".")));
        fileDao.create(fileInfo);
        return fileInfo.getUuid();
    }

    @Override
    public Object findByUuid(String uuid) {
        return fileDao.get(uuid);
    }

    @Override
    public Object downloadByUuid(String uuid) {
        FileInfo fileInfo = fileDao.get(uuid);
        //加密后的文件
        File sourceFile = new File(fileInfo.getPath()+fileInfo.getUuid()+fileInfo.getType());
        //解密结果
        File decryptFile = AESUtil.decrypt(sourceFile,fileInfo.getOriginalName(),fileInfo.getType(),fileInfo.getEnvelope());
        return decryptFile;
    }
}
