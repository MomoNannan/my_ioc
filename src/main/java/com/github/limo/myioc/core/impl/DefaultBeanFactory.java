package com.github.limo.myioc.core.impl;

import com.github.limo.myioc.core.BeanFactory;
import com.github.limo.myioc.exception.BeanDefinitionNotFoundException;
import com.github.limo.myioc.exception.IOCRuntimeException;
import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.util.ClassUtils;

import java.util.Map;
import java.util.Objects;
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
     * 注册 BeanDefinition
     * @param beanName
     * @param beanDefinition
     */
    protected void registerBeanDefinition(String beanName, final BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
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

    private <T> String classTypeNotMatchMsg(Class<T> requiredType, Object bean) {
        return String.format("Class type not match: requiredType: %s, actually: %s",
                requiredType, bean.getClass());
    }
}
