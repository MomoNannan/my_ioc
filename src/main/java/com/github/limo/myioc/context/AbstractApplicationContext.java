package com.github.limo.myioc.context;

import com.github.limo.myioc.core.impl.DefaultListableBeanFactory;
import com.github.limo.myioc.exception.IOCRuntimeException;
import com.github.limo.myioc.model.BeanDefinition;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/12
 * @time 21:09
 */
public abstract class AbstractApplicationContext extends DefaultListableBeanFactory implements ApplicationContext {

    @Override
    public String getApplicationName() {
        return "An app powered by my_ioc";
    }

    protected void init() throws IOCRuntimeException {
        // 1. Build bean definitions from json or etc.
        List<? extends BeanDefinition> beanDefinitions = buildBeanDefinitions();

        // 2. Register bean definitions
        registerBeanDefinitions(beanDefinitions);
        
        // 3. Register shutdown hook.
        registerShutdownHook();
    }

    /**
     * 注册关闭钩子方法
     */
    protected void registerShutdownHook() {
        // 如我们所见, 关闭钩子其实就是一个初始化但未启动的线程. 它启动之时, 也就是容器的销毁工作开始之时 -- 程序正常退出或 JVM 退出时, 这
        // 由 JVM 来管理, 不需要我们来操心. new Thread(...) 里面是一个方法引用, 也就是说, 这是个以 Lambda 表达式方式创建的 Thread.
        // 里面的代码就是 run() 方法的逻辑. AbstractApplicationContext.this 引用的是 AbstractApplicationContext 的实例, 而它自身并没有,
        // 所以会沿着继承体系, 找到 DefaultBeanFactory 中的 destroy().
        // 如此, 我们就将销毁工作嵌入到容器的生命周期中了.
        Runtime.getRuntime()
               .addShutdownHook(new Thread(AbstractApplicationContext.this::destroy));
    }

    protected void registerBeanDefinitions(List<? extends BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            super.registerBeanDefinition(beanDefinition.getName(), beanDefinition);
            fillDefaultProperties(beanDefinition);
        }
    }

    /**
     * 为 BeanDefinition 填充默认属性.
     * @param beanDefinition
     */
    protected void fillDefaultProperties(BeanDefinition beanDefinition) {
        if (StringUtils.isBlank(beanDefinition.getScope())) {
            beanDefinition.setDefaultScope();
        }
    }

    /**
     * 构建 BeanDefinition 列表, 例如从配置文件中解析.
     * @return
     */
    protected abstract List<? extends BeanDefinition> buildBeanDefinitions() throws IOCRuntimeException;
}
