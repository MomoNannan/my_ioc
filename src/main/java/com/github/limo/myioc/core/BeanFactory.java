package com.github.limo.myioc.core;

/**
 * 该接口用于获取 Bean,
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 10:42
 */
public interface BeanFactory {

    /**
     * 根据名称获取 Bean
     * @param beanName bean 名称
     * @return
     */
    Object getBean(String beanName);


    /**
     * 根据名称和类型获取 Bean
     * @param beanName
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> T getBean(String beanName, final Class<T> requiredType);
}
