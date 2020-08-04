package com.github.limo.myioc.util;

import com.github.limo.myioc.exception.IOCRuntimeException;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 11:38
 */
public class ClassUtils {

    /**
     * 根据 class name 获取 Class 对象
     * @param className
     * @return
     */
    public static Class getClass(String className) {
        Class<?> result = null;
        try {
            result = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IOCRuntimeException(e);
        }
        return result;
    }

    /**
     * 根据 Class 对象创建其对应的类实例
     * @param clazz
     * @return
     */
    public static Object newInstance(Class clazz) {
        Object result = null;
        try {
            result = clazz.newInstance();
        } catch (Exception e) {
            throw new IOCRuntimeException(e);
        }
        return result;
    }
}
