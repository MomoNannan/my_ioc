package com.github.limo.myioc.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 13:24
 */
@UtilityClass
public class ArgUtils {

    public static void notBlank(String argName, String argVal) {
        if (StringUtils.isBlank(argName)) {
            throw new IllegalArgumentException(String.format("%s cannot be empty", argName));
        }
    }

    public static void nonNull(Object argName, Object argVal) {
        if (Objects.isNull(argVal)) {
            throw new IllegalArgumentException(String.format("%s cannot be null", argName));
        }
    }

}
