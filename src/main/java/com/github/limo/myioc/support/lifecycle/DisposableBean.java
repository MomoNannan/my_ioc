package com.github.limo.myioc.support.lifecycle;

/**
 * 提供给想要在容器退出时释放资源的 bean 实现的接口.
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/12
 * @time 22:08
 */
public interface DisposableBean {

    /**
     * 销毁方法
     */
    void destroy();
}
