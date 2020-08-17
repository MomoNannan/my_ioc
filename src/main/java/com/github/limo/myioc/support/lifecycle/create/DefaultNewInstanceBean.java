package com.github.limo.myioc.support.lifecycle.create;

import com.github.limo.myioc.core.BeanFactory;
import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.support.lifecycle.NewInstanceBean;
import com.github.limo.myioc.util.ClassUtils;

/**
 * 单例模式
 * 因为该 Bean 在 DefaultBeanFactory 中创建对象时可以被所有的 BeanDefinition 共享,
 * 而非为每个 BeanDefinition 都创建一个该对象, 因此设置成了单例.
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/16
 * @time 11:04
 */
public class DefaultNewInstanceBean implements NewInstanceBean {

    public static final NewInstanceBean INSTANCE = new DefaultNewInstanceBean();

    private DefaultNewInstanceBean() {}

    public static NewInstanceBean getInstance() {
        return INSTANCE;
    }

    @Override
    public Object newInstance(BeanFactory beanFactory, BeanDefinition beanDefinition) {
        // 1. 如果 Bean 中定义了 @FactoryMethod 的方法就使用该方法创建实例并设置初始值并返回.
        if (factoryMethodExists(beanDefinition)) {
            return FactoryMethodNewInstanceBean.getInstance()
                                               .newInstance(beanFactory, beanDefinition);
        } else if (beanDefinition.constructorArgsExist()) {
        // 2. 否则, 使用 BeanDefinition 定义的 constructorArgs 属性的值设置初值 ( 如果存在的话 ).
            return ConstructorNewInstanceBean.getInstance()
                                             .newInstance(beanFactory, beanDefinition);
        } else {
        // 3. 如果上述都不存在, 则创建无初始值的 Bean 实例.
            return ClassUtils.newInstance(ClassUtils.getClass(beanDefinition.getClassName()));
        }
    }

    private boolean factoryMethodExists(BeanDefinition beanDefinition) {
        return ClassUtils.hasMethodAnnotatedWith(
                   ClassUtils.getClass(beanDefinition.getClassName()),
                   FactoryMethod.class
               );
    }

}
