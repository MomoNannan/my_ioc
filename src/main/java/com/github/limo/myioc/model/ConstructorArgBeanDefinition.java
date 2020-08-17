package com.github.limo.myioc.model;

/**
 * Bean 构造器参数的定义.
 * 为了方便, 所有的参数都是引用类型, 对于基本类型, 使用其包装类型.
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/16
 * @time 14:17
 */
public interface ConstructorArgBeanDefinition {

    /**
     * 获取参数对应的字段名称
     * @return
     */
    public String getName();

    public void setName(String name);
    /**
     * 获取字段引用的 Bean 的名称
     * @return
     */
    public String getRef();

    public void setRef(String ref);
    /**
     * 获取参数类型
     * @return
     */
    public String getType();

    public void setType(String type);

    /**
     * 获取参数的值
     * @return
     */
    public String getVal();

    public void setVal(String val);

    boolean hasRef();
}
