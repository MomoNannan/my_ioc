package com.github.limo.myioc.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射 Method 对象工具类
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/13
 * @time 12:12
 */
@UtilityClass
public class ReflectMethodUtils {

    public static Object invokeNoArgsMethod(Method method, Object instance) {
        return invoke(method, instance, false, true);
    }

    public static Object invokeStaticNoArgMethod(Class aClass,
                                                 Method method) {
        return invoke(method, null, true, true);
    }

    /**
     * 反射方式执行 instance 的 method 方法.
     * @param method 方法对应的 Method 对象
     * @param instance method 所属的类实例
     * @param mustBeStatic 是否必须是静态, 校验用
     * @param mustBeNoArg 是否必须是无参方法, 校验用
     * @param args 参数值
     * @return
     */
    public static Object invoke(Method method,
                                Object instance,
                                boolean mustBeStatic,
                                boolean mustBeNoArg,
                                Object ... args) {
        if (mustBeStatic && !Modifier.isStatic(method.getModifiers())) {
            throw new RuntimeException("Method '" + method.getName() + "' should be static");
        }
        if (mustBeNoArg && !containsNoParams(method)) {
            throw new RuntimeException("Method '" + method.getName() + "' should contain no args");
        }
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean containsNoParams(Method method) {
        return method.getParameterCount() == 0;
    }

}
