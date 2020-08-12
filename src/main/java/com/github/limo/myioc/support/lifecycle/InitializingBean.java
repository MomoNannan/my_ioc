package com.github.limo.myioc.support.lifecycle;

/**
 * 提供给 Bean 实现的接口, 用于在初始化时执行一些操作. 调用方式如下:
 * 1. Bean 中包含 @PostConstruct 声明的方法.
 * 2. Bean 实现了该接口的 initialize() 方法.
 * 3. Bean 提供了自定义的初始化方法 -- Bean 为 init-Method 属性设置自定义方法的名称.
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/12
 * @time 13:47
 */
public interface InitializingBean {

    /**
     * Bean 实例创建后调用此方法.
     */
    void initialize();
}
