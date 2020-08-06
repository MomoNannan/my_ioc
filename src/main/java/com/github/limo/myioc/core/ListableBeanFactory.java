package com.github.limo.myioc.core;

import java.util.List;

/**
 * LitableBeanFactory 是 BeanFactory 的扩展, 可根据类型获取对应的全部 bean 实例, 而不用在被客户端请求时, 逐个
 * 根据名称查找.
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/6
 * @time 11:21
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 根据 Class 对象获取其对应的全部 bean 实例.
     * @param type
     * @param <T>
     * @return
     */
    <T> List<T> getBeans(Class<T> type);
}
