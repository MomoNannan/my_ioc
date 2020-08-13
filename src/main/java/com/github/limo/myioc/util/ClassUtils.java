package com.github.limo.myioc.util;

import com.github.limo.myioc.exception.IOCRuntimeException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public static Object newInstance(final Class clazz) {
        Object result = null;
        try {
            result = clazz.newInstance();
        } catch (Exception e) {
            throw new IOCRuntimeException(e);
        }
        return result;
    }

    /**
     * 获取 aClass 类型中第一个声明了 annotationClass 注解的方法.
     * @param aClass
     * @param annotationClass
     * @return
     */
    public static Optional<Method> findFirstOptionalMethodAnnotatedWith(final Class<?> aClass,
                                                                        final Class<? extends Annotation> annotationClass) {
        List<Method> methods = findMethodsAnnotatedWith(aClass, annotationClass);
        if (!methods.isEmpty()) {
            return Optional.ofNullable(methods.get(0));
        }
        return Optional.empty();
    }

    /**
     * 找出某个类中所有声明了 annotationClass 注解的方法, 不包括继承来的.
     * @param aClass
     * @param annotationClass
     * @return
     */
    public static List<Method> findMethodsAnnotatedWith(final Class<?> aClass,
                                                        final Class<? extends Annotation> annotationClass) {
        return Arrays.stream(aClass.getDeclaredMethods())
                     // 判断某个方法/类 ( Method/Class ) 是否具有指定的注解 -- Method/CLass#isAnnotationPresent()
                     .filter(method -> method.isAnnotationPresent(annotationClass))
                     .collect(Collectors.toList());
    }

    /**
     * 该方法认为 aClass 中可能存在名为 methodName 的方法, 所以返回值是 Optional 类型的.
     * @param aClass
     * @param methodName
     * @return
     */
    public static Optional<Method> findOptionalMethodByName(Class<?> aClass, String methodName) {
        try {
            return Optional.of(aClass.getMethod(methodName));
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    /**
     * 该方法认为 aClass 中一定存在名为 methodName 的方法, 如果不存在, 则属于使用错误, 抛出异常.
     * @param aClass
     * @param destroyMethod
     * @param paramTypes
     * @return
     */
    public static Method findMethodByName(Class<?> aClass, String destroyMethod, Class<?> ... paramTypes) {
        try {
            return aClass.getMethod(destroyMethod, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
