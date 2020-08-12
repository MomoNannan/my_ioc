package com.github.limo.myioc.context;

/**
 * 应用上下文. 提供给外界使用的方便接口.
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/12
 * @time 21:07
 */
public interface ApplicationContext {

    /**
     * 获取应用名称
     * @return
     */
    String getApplicationName();
}
