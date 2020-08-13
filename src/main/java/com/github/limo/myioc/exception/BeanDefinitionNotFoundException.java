package com.github.limo.myioc.exception;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 11:30
 */
public class BeanDefinitionNotFoundException extends IOCRuntimeException {

    public BeanDefinitionNotFoundException() {
    }

    public BeanDefinitionNotFoundException(String message) {
        super(message);
    }

    public BeanDefinitionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanDefinitionNotFoundException(Throwable cause) {
        super(cause);
    }

}
