package com.github.limo.myioc.model;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 11:21
 */
public class DefaultBeanDefinition implements BeanDefinition {

    private String name;

    private String className;


    /** bean 范围*/
    private String scope;

    /** bean 是否是延迟加载 */
    private boolean lazyInit;

    /** 自定义的初始化方法名称 */
    private String initMethod;

    /** 自定义的销毁方法名称 */
    private String destroyMethod;

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
}
