package com.github.limo.myioc.exception;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 13:01
 */
public class BeanConfFileNotFoundException extends IOCRuntimeException {

    public BeanConfFileNotFoundException() {
    }

    public BeanConfFileNotFoundException(Throwable cause) {
        super(cause);
    }

    public BeanConfFileNotFoundException(String msg) {
        super(msg);
    }

    public BeanConfFileNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
