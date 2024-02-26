package com.limit.exception;

/**
 * @description:
 * @Author: Xhy
 * @gitee: https://gitee.com/XhyQAQ
 * @copyright: B站: https://space.bilibili.com/152686439?spm_id_from=333.1007.0.0
 * @CreateTime: 2023-04-13 23:14
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
