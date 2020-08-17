package com.github.limo.myioc.support.lifecycle.create;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于 Bean 的为字段设置初始值的工厂方法,
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/16
 * @time 15:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FactoryMethod {

}
