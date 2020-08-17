package com.github.limo.myioc.support.lifecycle;

import com.github.limo.myioc.core.BeanFactory;
import com.github.limo.myioc.model.BeanDefinition;

/**
 * 给 Bean 实现的, 用于为字段设置初始值. 使用方式如下:
 * 1. 为 Bean 设置 constrctorArgs 属性, 并设置相应的值.
 * 2. 在 Bean 中提供设置初始值的方法, 并以 @FactoryMethod 注解.
 * 方法 2 具有更高的优先级, 即使两种都使用了, 仅方法 2 会生效.
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/16
 * @time 10:39
 */
public interface NewInstanceBean {

    /**
     * 创建 Bean 实例并设置初始值.
     * @param beanFactory
     * @param beanDefinition
     * @return
     */
    Object newInstance(BeanFactory beanFactory, BeanDefinition beanDefinition);
}
