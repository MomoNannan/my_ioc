package com.github.limo.myioc.support.lifecycle;

import com.github.limo.myioc.context.JsonApplicationContext;
import com.github.limo.myioc.core.BeanFactory;
import com.github.limo.myioc.test.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/12
 * @time 15:54
 */
public class LifeCycleTest {

    static final BeanFactory BEAN_FACTORY = new JsonApplicationContext("beans.json");

    @Test
    public void testInitAndDestory() {
        User user = BEAN_FACTORY.getBean("user", User.class);
        Assertions.assertEquals(1, user.getSex());
    }

}
