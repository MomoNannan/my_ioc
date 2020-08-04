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

}
