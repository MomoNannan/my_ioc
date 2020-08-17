package com.github.limo.myioc.support.lifecycle.create;

import com.github.limo.myioc.core.BeanFactory;
import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.model.ConstructorArgBeanDefinition;
import com.github.limo.myioc.support.lifecycle.NewInstanceBean;
import com.github.limo.myioc.util.ClassUtils;
import com.github.limo.myioc.util.JsonUtils;
import com.github.limo.myioc.util.ReflectConstructorUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/16
 * @time 14:14
 */
public class ConstructorNewInstanceBean implements NewInstanceBean {

    private static final NewInstanceBean INSTANCE = new ConstructorNewInstanceBean();

    private ConstructorNewInstanceBean() {}

    public static NewInstanceBean getInstance() {
        return INSTANCE;
    }

    @Override
    public Object newInstance(BeanFactory beanFactory, BeanDefinition beanDefinition) {
        if (CollectionUtils.isEmpty(beanDefinition.getConstructorArgs())) {
            return ClassUtils.newInstance(ClassUtils.getClass(beanDefinition.getClassName()));
        } else {
            return newInstanceByConstructor(beanFactory, beanDefinition);
        }
    }

    private Object newInstanceByConstructor(BeanFactory beanFactory, BeanDefinition beanDefinition) {
        // 1. 获取 beanDefinition 中的类型列表.
        Pair<Class<?>[], Object[]> paramPairs = getParamPairs(beanFactory, beanDefinition);

        // 2. 在类中寻找符合类型列表的构造器.
        Class clazz = ClassUtils.getClass(beanDefinition.getClassName());
        Constructor constructor = ReflectConstructorUtils.getConstructor(clazz, paramPairs.getLeft());

        // 3. 使用上述构造器创建实例并以预设的构造器参数值来初始化.
        return ReflectConstructorUtils.newInstance(constructor, paramPairs.getRight());
    }

    private Pair<Class<?>[], Object[]> getParamPairs(BeanFactory beanFactory,
                                                     BeanDefinition beanDefinition) {
        List<ConstructorArgBeanDefinition> constructorArgBeanDefinitions =
                beanDefinition.getConstructorArgs();
        Class<?>[] types = new Class[constructorArgBeanDefinitions.size()];
        Object[] vals = new Object[constructorArgBeanDefinitions.size()];
        for (int i = 0; i < constructorArgBeanDefinitions.size(); i++) {
            ConstructorArgBeanDefinition item = constructorArgBeanDefinitions.get(i);
            if (StringUtils.isNotBlank(item.getRef())) {
                types[i] = beanFactory.getType(item.getRef());
                vals[i] = beanFactory.getBean(item.getRef());
            } else {
                Class typeClass = ClassUtils.getClass(item.getType());
                types[i] = typeClass;
                vals[i] = JsonUtils.deserialize(item.getVal(), typeClass);
            }
        }
        return Pair.of(types, vals);
    }


}
