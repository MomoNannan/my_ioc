package com.github.limo.myioc.model.impl;

import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.model.ConstructorArgBeanDefinition;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 11:21
 */
public class DefaultBeanDefinition implements BeanDefinition {

    /** bean 名称 */
    private String name;

    /** bean 所属类的类路径 */
    private String className;

    /** bean 范围*/
    private String scope;

    /** bean 是否是延迟加载 */
    private boolean lazyInit;

    /** 自定义的初始化方法名称 */
    private String initMethod;

    /** 自定义的销毁方法名称 */
    private String destroyMethod;

    /** 自定义的用于为 Bean 设置初值的 factoryMethod 的名称*/
    private String factoryMethod;

    /** 构造器参数列表 */
    private List<ConstructorArgBeanDefinition> constructorArgs;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public void setDefaultScope() {
        this.scope = DEFAULT_SCOPE;
    }

    @Override
    public boolean isLazyInit() {
        return lazyInit;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    @Override
    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    @Override
    public String getInitMethod() {
        return initMethod;
    }

    @Override
    public void setDestroyMethod(String destroyMethod) {
        this.destroyMethod = destroyMethod;
    }

    @Override
    public String getDestroyMethod() {
        return destroyMethod;
    }

    @Override
    public void setFactoryMethod(String factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

    @Override
    public String getFactoryMethod() {
        return factoryMethod;
    }

    @Override
    public List<ConstructorArgBeanDefinition> getConstructorArgs() {
        return constructorArgs;
    }

    @Override
    public void setConstructorArgs(List<ConstructorArgBeanDefinition> constructorArgs) {
        this.constructorArgs = constructorArgs;
    }


    @Override
    public boolean constructorArgsExist() {
        return CollectionUtils.isNotEmpty(getConstructorArgs());
    }

}
