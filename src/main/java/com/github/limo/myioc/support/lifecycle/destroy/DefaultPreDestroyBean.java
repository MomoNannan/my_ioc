package com.github.limo.myioc.support.lifecycle.destroy;

import com.github.limo.myioc.exception.IOCRuntimeException;
import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.support.lifecycle.DisposableBean;
import com.github.limo.myioc.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PreDestroy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/12
 * @time 22:10
 */
public class DefaultPreDestroyBean implements DisposableBean {

    private Object instance;

    private BeanDefinition beanDefinition;

    public DefaultPreDestroyBean(Object instance, BeanDefinition beanDefinition) {
        this.instance = instance;
        this.beanDefinition = beanDefinition;
    }

    @Override
    public void destroy() {
        // 1. 若存在在 PreDestroy 节点注册了事件的 Bean, 则执行.
        preDestroy();

        // 2. 若 instance 自身实现了 DisposableBean, 则执行.
        disposableBean();

        // 3. 若 instance 存在自定义的销毁方法, 则执行.
        customDestroy();
    }

    private void preDestroy() {
        // 1. Find the method annotated with @PreDestory.
        Optional<Method> optionalMethod =
                ClassUtils.findFirstMethodAnnotatedWith(instance.getClass(), PreDestroy.class);
        if (!optionalMethod.isPresent()) {
            return;
        }

        // 2. Check whether the method has no params
        if (!ClassUtils.containsNoParams(optionalMethod.get())) {
            throw new IOCRuntimeException(methodAnnotatedWithPreDestroyIllegalArgumentsExceptionMsg(optionalMethod.get()));
        }

        // 3. If exists, invoke it.
        try {
            optionalMethod.get().invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IOCRuntimeException(methodAnnotatedWithPreDestroyInvocationExceptionMsg(optionalMethod.get(), e));
        }
    }

    private String methodAnnotatedWithPreDestroyInvocationExceptionMsg(Method method, ReflectiveOperationException e) {
        return String.format("The method %s annotated with @PreDestroy invocation exception: %s", method, e);
    }

    private String methodAnnotatedWithPreDestroyIllegalArgumentsExceptionMsg(Method method) {
        return String.format("The method %s of instance %s annotated with @PreDestroy should contain no params", method, instance);
    }

    private void disposableBean() {
        if (instance instanceof DisposableBean) {
            ((DisposableBean) instance).destroy();
        }
    }

    private void customDestroy() {
        // 1. Check whether destroy-method has been defined in beanDefinition.
        if (StringUtils.isBlank(beanDefinition.getDestroyMethod())) {
            return;
        }

        // 2. Check whether instance has defined the method
        Optional<Method> optionalMethod = ClassUtils.findMethodByName(instance.getClass(), beanDefinition.getDestroyMethod());

        // 3. Invoke it
        optionalMethod.ifPresent(method -> {
            try {
                method.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IOCRuntimeException(e);
            }
        });
    }
}
