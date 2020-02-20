package com.exam.file.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends RuntimeException{
    private Integer status = BAD_REQUEST.value();

    private Integer code;

    public BadRequestException(String msg){
        super(msg);
    }

    public BadRequestException(Integer code,String msg){
        super(msg);
        this.code = code;
    }

    public BadRequestException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }
}
