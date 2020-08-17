package com.github.limo.myioc.model.impl;

import com.github.limo.myioc.model.ConstructorArgBeanDefinition;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/16
 * @time 14:23
 */
public class DefaultConstructorArgBeanDefinition implements ConstructorArgBeanDefinition {

    /**
     * Bean 字段名称
     */
    private String name;

    /**
     * Bean 中引用作为字段的其它 Bean 的名称
     */
    private String ref;

    /**
     * 参数类型. 都是使用引用类型, 基本类型使用其包装类型.
     */
    private String type;

    /**
     * 参数值
     */
    private String val;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getRef() {
        return ref;
    }

    @Override
    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getVal() {
        return val;
    }

    @Override
    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public boolean hasRef() {
        return StringUtils.isNotBlank(ref);
    }
}
