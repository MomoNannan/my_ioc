package com.github.limo.myioc.context;

import com.github.limo.myioc.core.impl.DefaultListableBeanFactory;
import com.github.limo.myioc.exception.BeanConfFileNotFoundException;
import com.github.limo.myioc.exception.IOCRuntimeException;
import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.model.DefaultBeanDefinition;
import com.github.limo.myioc.util.FileUtils;
import com.github.limo.myioc.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.List;

/**
 * 比 BeanFactory 更方便的接口, 以解析 json 格式的 bean 配置文件的方式加载依赖关系.
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 12:33
 */
public class JsonApplicationContext extends DefaultListableBeanFactory {

    /** bean 配置文件路径 */
    private String confFilePath;

    public JsonApplicationContext(String confFilePath) {
        this.confFilePath = confFilePath;
        try {
            init();
        } catch (BeanConfFileNotFoundException e) {
            throw new IOCRuntimeException(e);
        }
    }

    /**
     * 初始化相关配置
     * @throws BeanConfFileNotFoundException
     */
    private void init() throws BeanConfFileNotFoundException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(confFilePath);
        // 1. 载入 json 配置文件
        String content = null;
        try {
            content = FileUtils.getFileContent(in);
        } catch (RuntimeException e) {
            throw new BeanConfFileNotFoundException(beanConfFileNotFoundMsg());
        }

        // 2. 对每个 bean 配置都解析成 bean definition 并缓存
        List<DefaultBeanDefinition> beanDefinitions = JsonUtils.deserializeArray(content, DefaultBeanDefinition.class);
        for (DefaultBeanDefinition item : beanDefinitions) {
            fillDefaultValue(item);
            registerBeanDefinition(item.getName(), item);
        }
    }

    /**
     * 填充默认的属性. 如: 设置默认 scope 为 singleton
     * @param beanDefinition
     */
    private void fillDefaultValue(BeanDefinition beanDefinition) {
        if (StringUtils.isBlank(beanDefinition.getScope())) {
            beanDefinition.setDefaultScope();
        }
    }

    private String beanConfFileNotFoundMsg() {
        return String.format("bean conf file not found by path: %s", confFilePath);
    }

}
