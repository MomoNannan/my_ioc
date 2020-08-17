package com.github.limo.myioc.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/16
 * @time 23:48
 */
@UtilityClass
public class ReflectConstructorUtils {

    public static Optional<Constructor> getOptionalConstructor(Class aClass, Class<?>...types) {
        try {
            return Optional.of(aClass.getConstructor(types));
        } catch (NoSuchMethodException e) {
        }
        return Optional.empty();
    }

    public static Constructor getConstructor(Class aClass, Class<?>...types) {
        try {
            return aClass.getConstructor(types);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object newInstance(Constructor constructor, Object[] initVals) {
        try {
            return constructor.newInstance(initVals);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(newInstanceFailureMsg(constructor, initVals));
        }
    }

    private static String newInstanceFailureMsg(Constructor constructor, Object[] initVals) {
        return String.format("Failed to new instance for constructor ' %s ' with vals: %s",
                constructor.getName(), ArrayUtils.toString(initVals));
    }

    public static Constructor getAllDeclaredArgsConstructor(Class clazz) {
        Class<?>[] typeClasses = Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getType).toArray(Class<?>[]::new);
        try {
            return clazz.getConstructor(typeClasses);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Class '" + clazz.getName() +"' has no all-args constructor.");
        }
    }
}
