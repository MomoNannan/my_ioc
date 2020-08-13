package com.github.limo.myioc.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射 Method 对象工具类
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/13
 * @time 12:12
 */
@UtilityClass
public class ReflectMethodUtils {

    public static void invokeNoArgMethod(Method method, Object instance) {
        if (!containsNoParams(method)) {
            throw new RuntimeException("Method '" + method.getName() + "' should contain no args");
        }
        invoke(method, instance);
    }

    public static void invoke(Method method, Object instance, Object ... args) {
        try {
            method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean containsNoParams(Method method) {
        return method.getParameterCount() == 0;
    }
}
