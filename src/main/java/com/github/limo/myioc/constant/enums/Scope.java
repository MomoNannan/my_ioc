package com.github.limo.myioc.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/8
 * @time 12:22
 */
@Getter
@AllArgsConstructor
public enum Scope {

    /** 单例 */
    SINGLETON("singleton"),

    /** 多例 */
    PROTOTYPE("prototype"),
    ;

    private String scope;

    public static boolean isSingleton(String scope) {
        return StringUtils.equals(SINGLETON.getScope(), scope);
    }

    public static boolean isPrototype(String scope) {
        return StringUtils.equals(PROTOTYPE.getScope(), scope);
    }}
