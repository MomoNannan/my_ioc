package com.github.limo.myioc.core.impl;

import com.github.limo.myioc.core.BeanFactory;
import com.github.limo.myioc.exception.BeanDefinitionNotFoundException;
import com.github.limo.myioc.exception.IOCRuntimeException;
import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.util.ArgUtils;
import com.github.limo.myioc.util.ClassUtils;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的 BeanFactory
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 11:15
 */
public class DefaultBeanFactory implements BeanFactory {

    /**
     * 用来存储 bean 定义的 Map. key: bean name; val: bean definition
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 用来存储 bean 实例的 Map. key: bean name; val: bean instance.
     */
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 以类型 ( class 对象 ) 为键, 存储其对应的全部 bean 名称的 Map. 有了名称的全集, 通过 beanMap 即可获取全部的 bean 实例.
     */
    private Map<Class<?>, Set<String>> allBeanNamesByType = new ConcurrentHashMap<>();


    /**
     * 注册 BeanDefinition
     * @param beanName
     * @param beanDefinition
     */
    protected void registerBeanDefinition(String beanName, final BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);

        // 缓存 type - all bean names
        // 1. get Class by bean definition.
        final Class type = getType(beanDefinition);

        // 2. Cache class and all of its bean instances
        Set<String> allBeanNames = allBeanNamesByType.get(type);
        if (CollectionUtils.isEmpty(allBeanNames)) {
            allBeanNames = Sets.newHashSet();
            allBeanNamesByType.put(type, allBeanNames);
        }
        allBeanNames.add(beanName);
    }

    private Class getType(BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        return ClassUtils.getClass(className);
    }

    /**
     * 获取类型对应的全部 bean 名称.
     * 不暴露 field -- allBeanNamesByType, 而是以方法的形式对外暴露. 是一种更加安全的手法.
     * @param type
     * @param <T>
     * @return
     */
    public <T> Set<String> getBeanNames(Class<T> type) {
        ArgUtils.nonNull("type", type);
        return allBeanNamesByType.get(type);
    }

    @Override
    public Object getBean(String beanName) {
        // 本版本中, 默认以单例模式创建. 若是多例模式, 则直接创建对象, 而不缓存.
        // 1. 如果已创建过, 直接返回.
        if (beanMap.containsKey(beanName)) {
            return beanMap.get(beanName);
        }

        // 2. 若未创建过, 获取其 BeanDefinition.
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (Objects.isNull(beanDefinition)) {
            throw new BeanDefinitionNotFoundException();
        }

        // 3. 根据 BeanDefinition 创建 Bean 实例.
        Object result = createBean(beanDefinition);

        // 4. 缓存到 beanMap 中
        beanMap.put(beanName, result);
        return result;
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class clazz = ClassUtils.getClass(beanDefinition.getClassName());
        return ClassUtils.newInstance(clazz);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) {
        Object bean = getBean(beanName);
        // 判断类型是否匹配
        if (bean.getClass() != requiredType) {
            throw new IOCRuntimeException(classTypeNotMatchMsg(requiredType, bean));
        }
        T result = (T) getBean(beanName);
        return result;
    }

    @Override
    public boolean containsBean(String beanName) {
        return beanMap.containsKey(beanName);
    }

    @Override
    public boolean isTypeMatch(String beanName, Class<?> type) {
        Class<?> typeCached = getType(beanName);
        return type == typeCached;
    }

    @Override
    public Class<?> getType(String beanName) {
        ArgUtils.notBlank("beanName", beanName);
        Object bean = getBean(beanName);
        return bean.getClass();
    }

    private <T> String classTypeNotMatchMsg(Class<T> requiredType, Object bean) {
        return String.format("Class type not match: requiredType: %s, actually: %s",
                requiredType, bean.getClass());
    }
}
