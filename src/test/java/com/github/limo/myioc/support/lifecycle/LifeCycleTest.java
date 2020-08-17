package com.github.limo.myioc.support.lifecycle;

import com.github.limo.myioc.context.JsonApplicationContext;
import com.github.limo.myioc.core.BeanFactory;
import com.github.limo.myioc.test.model.User;
import com.github.limo.myioc.test.model.VipUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/12
 * @time 15:54
 */
public class LifeCycleTest {


    @Test
    public void testInitAndDestory() {
        BeanFactory beanFactory = new JsonApplicationContext("beans.json");
        User user = beanFactory.getBean("user", User.class);
        Assertions.assertEquals(1, user.getSex());
    }

    @Test
    public void testNewInstance() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        BeanFactory beanFactory = new JsonApplicationContext("beans-wih-init-vals.json");
        User user = beanFactory.getBean("user", User.class);
        VipUser vipUser = beanFactory.getBean("vipUser", VipUser.class);
        Assertions.assertEquals(user, vipUser.getUser());
        Assertions.assertEquals(3, vipUser.getLevel());
    }

}
