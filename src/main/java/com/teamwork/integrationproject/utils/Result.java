package com.teamwork.integrationproject.utils;

import lombok.Data;
import org.springframework.boot.configurationprocessor.json.JSONObject;

/**
 * 封装结果集<p>
 * <p>
 * 代码描述<p>
 * <p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author haochaojie
 * @since 2020/8/5 16:10
 */
@Data
public class Result<T> {

    private String message;

    private Integer retCode;

    private Integer retStatus;

    private T data;

    private  T page;

    private Result() {
        this.retCode = 200;
        this.retStatus = 0;
        this.message = "ok";
    }
    private Result(T data) {
        this.retCode = 200;
        this.retStatus = 0;
        this.message = "ok";
        this.data = data;
    }
    private Result(T data, T page) {
        this.retCode = 200;
        this.retStatus = 0;
        this.message = "ok";
        this.data = data;
        this.page = page;
    }
    public static Result success() {
        return new Result();
    }
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }
    public static <T> Result<T> success(T data, T page) {
        return new Result<T>(data, page);
    }

    private Result(String mes) {
        this.retCode = 500;
        this.retStatus = 1;
        this.message = mes;
    }
    public static <T> Result<T> error(String message) {
        return new Result<T>(message);
    }
}
