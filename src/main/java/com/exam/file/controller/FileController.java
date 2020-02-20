package com.exam.file.controller;

import com.exam.file.domain.FileInfo;
import com.exam.file.service.FileService;
import com.exam.file.util.AESUtil;
import com.exam.file.util.FileUtil;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SAAJResult;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping("/file")
public class FileController {

    private static Logger log = Logger.getLogger(Test.class.getClass());

    @Autowired
    private FileService fileService;

    @RequestMapping(value="/download/{uuid}",method = RequestMethod.GET)
    @ResponseBody
    public void download(@PathVariable String uuid, HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        FileInfo fileInfo = (FileInfo) fileService.findByUuid(uuid);
        File sourceFile = new File(fileInfo.getPath()+fileInfo.getUuid()+fileInfo.getType());
        //解密结果
        File file = AESUtil.decrypt(sourceFile,fileInfo.getOriginalName(),fileInfo.getType(),fileInfo.getEnvelope());
        String fileName = fileInfo.getOriginalName()+fileInfo.getType();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName="+ URLEncoder.encode(fileName, "UTF-8"));
        InputStream input=new FileInputStream(file);
        OutputStream out = response.getOutputStream();
        byte[] buff =new byte[1024];
        int index=0;
        while((index= input.read(buff))!= -1){
            out.write(buff, 0, index);
            out.flush();
        }
        out.close();
        input.close();
        log.info(fileName+"下载成功");
    }

    @RequestMapping(value="/upload",method = RequestMethod.POST)
    @ResponseBody
    public Object create(MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = (String) fileService.create(file);
        log.info("上传成功");
        return uuid;
    }

    @RequestMapping(value="/get/{uuid}",method = RequestMethod.GET)
    @ResponseBody
    public FileInfo get(@PathVariable String uuid, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return (FileInfo) fileService.findByUuid(uuid);
    }

}
