package com.github.limo.myioc.core;

import com.github.limo.myioc.context.JsonApplicationContext;
import com.github.limo.myioc.test.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/6
 * @time 12:16
 */
public class ListableBeanFactoryTest {

    public static final ListableBeanFactory BEAN_FACTORY = new JsonApplicationContext("beans-for-listable.json");

    @Test
    public void testGetBeans() {
        Class<User> userClass = User.class;
        List<User> beans = BEAN_FACTORY.getBeans(userClass);
        Assertions.assertEquals(2, beans.size());
    }
}
