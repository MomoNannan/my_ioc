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

    /**
     * 是否包含指定名称的 bean
     * @param beanName
     * @return
     */
    boolean containsBean(String beanName);

    /**
     * bean 名称对应的类型与指定的类型是否一致
     * @param beanName
     * @param type
     * @return
     */
    boolean isTypeMatch(String beanName, Class<?> type);

    /**
     * 根据 bean 名称获取类型 -- Class 对象
     * @param beanName
     * @return
     */
    Class<?> getType(String beanName);

}
