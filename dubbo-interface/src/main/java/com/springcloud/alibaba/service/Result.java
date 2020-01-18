package com.springcloud.alibaba.service;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/12/31
 * Time: 9:22 AM
 * Description: No Description
 */
public class Result<T> {
    public T data;

    public String msg;

    public int code;

    public T getData() {
        return data;
    }

    public void setData( T data ) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg( String msg ) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode( int code ) {
        this.code = code;
    }
}
