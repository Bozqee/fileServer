package com.exam.file.service;

import com.exam.file.domain.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 增
     * @param file
     * @return
     */
    Object create(MultipartFile file);

    /**
     * 通过uuid查找
     * @param uuid
     * @return
     */
    Object findByUuid(String uuid);

    /**
     * 通过uuid下载
     * @param uuid
     * @return
     */
    Object downloadByUuid(String uuid);
}
