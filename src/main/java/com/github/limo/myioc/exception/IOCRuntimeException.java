package com.github.limo.myioc.exception;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 11:42
 */
public class IOCRuntimeException extends RuntimeException {

    public IOCRuntimeException() {
    }

    public IOCRuntimeException(Throwable cause) {
        super(cause);
    }

    public IOCRuntimeException(String cause) {
        super(cause);
    }

    public IOCRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
