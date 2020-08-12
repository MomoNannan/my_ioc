package com.github.limo.myioc.support.lifecycle.init;

import com.github.limo.myioc.exception.IOCRuntimeException;
import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.support.lifecycle.InitializingBean;
import com.github.limo.myioc.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

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
     * 执行在 beanDefinition 中声明的 Bean 实例的自定义初始化方法
     */
    private void customInit() {
        // 1. 判断 beanDefinition 是否声明了初始化方法. 如果有, 获取其名称.
        if (StringUtils.isBlank(beanDefinition.getInitMethod())) {
            return;
        }

        // 2. 根据名称查找 Bean 中的 Method.
        Optional<Method> optionalMethod = ClassUtils.findMethodByName(instance.getClass(), beanDefinition.getInitMethod());

        // 3. 反射方式执行
        optionalMethod.ifPresent(method -> {
            try {
                method.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IOCRuntimeException(customMethodInvocationExceptionMsg(method, e));
            }
        });
    }

    private String customMethodInvocationExceptionMsg(Method method, Exception e) {
        return String.format("Custom method %s invocation exception: %s", method, e);
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
     * 以反射方式执行指定 Bean 中注解了 @PostConstruct 的方法
     */
    private void postConstruct() {
        // 1. 筛选出 instance 中包含了 @PostConstruct 注解的方法.
        Optional<Method> optionalMethod = ClassUtils.findFirstMethodAnnotatedWith(instance.getClass(), PostConstruct.class);
        if (!optionalMethod.isPresent()) {
            return;
        }

        // 2. 校验 -- @PostConstruct 注明的方法必须是无参的.
        if (!ClassUtils.containsNoParams(optionalMethod.get())) {
            throw new IOCRuntimeException("Methods annotated with @PostConstruct should contain no params");
        }

        // 2. 反射方式执行.
        try {
            optionalMethod.get().invoke(instance);
        } catch (IllegalAccessException  | InvocationTargetException e) {
            throw new IOCRuntimeException(postConstructMethodInvocationExceptionMsg(optionalMethod.get(), e));
        }
    }

    private String postConstructMethodInvocationExceptionMsg(Method method, Exception e) {
        return String.format("Method %s invoking exception: %s", method, e);
    }
}
