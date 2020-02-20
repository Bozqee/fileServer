package com.exam.file.domain;

import java.util.Date;


public class FileInfo {
    private String uuid;
    /**
     * 文件大小
     */
    private Long length;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 原始姓名
     */
    private String originalName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 文件保存目录地址
     */
    private String path;

    /**
     * 数字信封
     */
    private String envelope;

    public FileInfo() {
    }

    public FileInfo(String uuid, Long length, String type, String originalName, Date createTime, String path, String envelope) {
        this.uuid = uuid;
        this.length = length;
        this.type = type;
        this.originalName = originalName;
        this.createTime = createTime;
        this.path = path;
        this.envelope = envelope;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEnvelope() {
        return envelope;
    }

    public void setEnvelope(String envelope) {
        this.envelope = envelope;
    }

    @Override
    public String toString() {
        return "FileDO{" +
                "uuid='" + uuid + '\'' +
                ", length=" + length +
                ", type='" + type + '\'' +
                ", originalName='" + originalName + '\'' +
                ", createTime=" + createTime +
                ", path='" + path + '\'' +
                ", envelope='" + envelope + '\'' +
                '}';
    }
}
