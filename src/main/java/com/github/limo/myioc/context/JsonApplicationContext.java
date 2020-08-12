package com.github.limo.myioc.context;

import com.github.limo.myioc.exception.BeanConfFileNotFoundException;
import com.github.limo.myioc.exception.IOCRuntimeException;
import com.github.limo.myioc.model.BeanDefinition;
import com.github.limo.myioc.model.DefaultBeanDefinition;
import com.github.limo.myioc.util.FileUtils;
import com.github.limo.myioc.util.JsonUtils;

import java.io.InputStream;
import java.util.List;

/**
 * 比 BeanFactory 更方便的接口, 以解析 json 格式的 bean 配置文件的方式加载依赖关系.
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 12:33
 */
public class JsonApplicationContext extends AbstractApplicationContext {

    /** bean 配置文件路径 */
    private String confFilePath;

    public JsonApplicationContext(String confFilePath) {
        this.confFilePath = confFilePath;
        super.init();
    }


    @Override
    protected List<? extends BeanDefinition> buildBeanDefinitions() throws IOCRuntimeException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(confFilePath);
        // 1. 载入 json 配置文件
        String content = null;
        try {
            content = FileUtils.getFileContent(in);
        } catch (RuntimeException e) {
            throw new BeanConfFileNotFoundException(beanConfFileNotFoundMsg());
        }

        // 2. 对每个 bean 配置都解析成 bean definition 并缓存
        return JsonUtils.deserializeArray(content, DefaultBeanDefinition.class);
    }


    private String beanConfFileNotFoundMsg() {
        return String.format("bean conf file not found by path: %s", confFilePath);
    }

}
