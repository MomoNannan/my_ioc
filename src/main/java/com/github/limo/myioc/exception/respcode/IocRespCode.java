package com.github.limo.myioc.exception.respcode;

import lombok.Getter;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/13
 * @time 11:49
 */
@Getter
public class IocRespCode {

    private String code;

    private String msg;

    private String advice;

    public IocRespCode(String code, String advice) {
        this.code = code;
        this.advice = advice;
    }

    public IocRespCode(String code, String msg, String advice) {
        this.code = code;
        this.msg = msg;
        this.advice = advice;
    }

    @Override
    public String toString() {
        return "IocRespCode{" +
               "code='" + code + "\', " +
               "msg='" + msg + "\', " +
               "advice='" + advice + "\'" +
               "}";
    }

}
