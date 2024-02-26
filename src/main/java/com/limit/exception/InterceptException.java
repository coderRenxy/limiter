package com.limit.exception;

/**
 * @description:
 * @CreateTime: 2024-02-13 23:14
 */

public class InterceptException extends RuntimeException{


    private String msg;

    public InterceptException(String msg){
        this.msg = msg;
    }
    public InterceptException(){

    }

    public String getMsg() {
        return msg;
    }
}
