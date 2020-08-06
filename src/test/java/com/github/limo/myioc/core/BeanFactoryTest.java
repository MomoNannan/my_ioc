package com.github.limo.myioc.core;

import com.github.limo.myioc.context.JsonApplicationContext;
import com.github.limo.myioc.test.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/6
 * @time 12:16
 */
public class BeanFactoryTest {

    public static final BeanFactory BEAN_FACTORY = new JsonApplicationContext("beans.json");

    @Test
    public void testGetBean() {
        Object user = BEAN_FACTORY.getBean("user");
        Assertions.assertNotNull(user);
    }

    @Test
    public void testGetBean2() {
        User user = BEAN_FACTORY.getBean("user", User.class);
        Assertions.assertNotNull(user);
    }

    @Test
    public void testGetType() {
        Class<?> clazz = BEAN_FACTORY.getType("user");
        Assertions.assertEquals(User.class, clazz);
    }

    @Test
    public void testContainsBean() {
        boolean containsBean = BEAN_FACTORY.containsBean("user");
        Assertions.assertTrue(containsBean);
    }

    @Test
    public void testIsTypeMatch() {
        Class<?> clazz = BEAN_FACTORY.getType("user");
        Assertions.assertTrue(BEAN_FACTORY.isTypeMatch("user", clazz));
    }
}
