package com.github.limo.myioc.core.impl;

import com.github.limo.myioc.core.ListableBeanFactory;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/6
 * @time 11:25
 */
public class DefaultListableBeanFactory extends DefaultBeanFactory implements ListableBeanFactory {

    @Override
    public <T> List<T> getBeans(Class<T> type) {
        Set<String> beanNames = super.getBeanNames(type);
        return beanNames.stream()
                        .map(beanName -> super.getBean(beanName, type))
                        .collect(toList());
    }
}
