package com.github.limo.myioc.support.lifecycle.init;

import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.support.lifecycle.InitializingBean;
import com.github.limo.myioc.util.ArgUtils;
import com.github.limo.myioc.util.ClassUtils;
import com.github.limo.myioc.util.ReflectMethodUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * 该类用于执行一些对象创建后的初始化动作.
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/12
 * @time 14:17
 */
public class DefaultPostConstructBean implements InitializingBean {

    /** 注册了初始化事件的 Bean 实例 */
    private Object instance;

    /** 实例 instance 对应的 BeanDefinition */
    private BeanDefinition beanDefinition;

    public DefaultPostConstructBean(Object instance, BeanDefinition beanDefinition) {
        ArgUtils.nonNull("instance", instance);
        ArgUtils.nonNull("beanDefinition", beanDefinition);
        this.instance = instance;
        this.beanDefinition = beanDefinition;
    }

    @Override
    public void initialize() {

        // 1. 若 instance 包含注解了 @PostConstruct 的方法, 则执行.
        postConstruct();

        // 2. 若 instance 本身是 InitializingBean 的实现类, 那么执行其 initialize() 方法
        initializingBean();

        // 3. 若 instance 的 beanDefinition 设置了自定义的初始化方法, 则执行
        customInit();
    }

    /**
     * 以反射方式执行指定 Bean 中注解了 @PostConstruct 的方法
     */
    private void postConstruct() {
        // 1. 筛选出 instance 中包含了 @PostConstruct 注解的方法.
        // 2. 校验 -- @PostConstruct 注明的方法必须是无参的.
        // 3. 执行
        ClassUtils.findFirstOptionalMethodAnnotatedWith(instance.getClass(), PostConstruct.class)
                  .ifPresent(method -> ReflectMethodUtils.invokeNoArgsMethod(method, instance));
    }

    /**
     * 若该 Bean 实例实现了 InitializingBean, 则执行其重写的方法.
     */
    private void initializingBean() {
        if (instance instanceof InitializingBean) {
            ((InitializingBean) instance).initialize();
        }
    }

    /**
     * 执行在 beanDefinition 中声明的 Bean 实例的自定义初始化方法
     */
    private void customInit() {
        // 1. Check whether destroy-method has been defined in beanDefinition.
        if (StringUtils.isBlank(beanDefinition.getDestroyMethod())) {
            return;
        }
        // 2. Find the method by name.
        Method method = ClassUtils.findMethodByName(instance.getClass(), beanDefinition.getInitMethod());

        // 3. Invoke it
        ReflectMethodUtils.invokeNoArgsMethod(method, instance);

    }

}
