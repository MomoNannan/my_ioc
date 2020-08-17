package com.github.limo.myioc.support.lifecycle.create;

import com.github.limo.myioc.core.BeanFactory;
import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.support.lifecycle.NewInstanceBean;
import com.github.limo.myioc.util.ClassUtils;
import com.github.limo.myioc.util.ReflectMethodUtils;

import java.lang.reflect.Method;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/16
 * @time 14:14
 */
public class FactoryMethodNewInstanceBean implements NewInstanceBean {

    private static final NewInstanceBean INSTANCE = new FactoryMethodNewInstanceBean();

    private FactoryMethodNewInstanceBean() {}

    public static NewInstanceBean getInstance() {
        return INSTANCE;
    }

    @Override
    public Object newInstance(BeanFactory beanFactory, BeanDefinition beanDefinition) {
        Class aClass = ClassUtils.getClass(beanDefinition.getClassName());
        Method method = ClassUtils.findFirstMethodAnnotatedWith(aClass, FactoryMethod.class);
        // 我们要先创建实例, 再为其设置属性. 但是我们没有实例, 所以只能通过类引用静态方法的方式创建.
        // 因此 FactoryMethod 要求必须是静态的.
        return ReflectMethodUtils.invokeStaticNoArgMethod(aClass, method);
    }
}
