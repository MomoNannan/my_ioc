package com.github.limo.myioc.support.lifecycle.destroy;

import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.support.lifecycle.DisposableBean;
import com.github.limo.myioc.util.ArgUtils;
import com.github.limo.myioc.util.ClassUtils;
import com.github.limo.myioc.util.ReflectMethodUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;

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
        ArgUtils.nonNull("instance", instance);
        ArgUtils.nonNull("beanDefinition", beanDefinition);
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
        // 1. Check whether there is a method annotated with @PreDestroy.
        // 2. If yes, invoke it as a no-arg method.
        ClassUtils.findFirstOptionalMethodAnnotatedWith(instance.getClass(), PreDestroy.class)
                  .ifPresent(method -> ReflectMethodUtils.invokeNoArgMethod(method, instance));
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
        // 2. Find the method by name.
        Method method = ClassUtils.findMethodByName(instance.getClass(), beanDefinition.getDestroyMethod());

        // 3. Invoke it
        ReflectMethodUtils.invokeNoArgMethod(method, instance);
    }
}
