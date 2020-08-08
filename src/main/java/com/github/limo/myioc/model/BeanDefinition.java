package com.github.limo.myioc.model;

/**
 * 该接口用来描述 Bean 的定义
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 11:07
 */
public interface BeanDefinition {

    /**
     * 获取 bean 配置的名称
     * @return 配置的 bean 的名称
     */
    String getName();

    /**
     * 设置 bean 名称
     * @param name bean 名称
     */
    void setName(String name);

    /**
     * 获取 bean 的 Class 对象的名称
     * @return
     */
    String getClassName();

    /**
     * 设置 bean 的 Class 对象名称
     */
    void setClassName(String className);

    /**
     * 获取 bean 范围
     * @return
     */
    String getScope();

    void setScope(String scope);

    /**
     * Bean 是否是延迟加载
     * @return
     */
    boolean isLazyInit();

    void setLazyInit(boolean lazyInit);
}
