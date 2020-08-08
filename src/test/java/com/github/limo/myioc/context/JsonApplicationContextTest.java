package com.github.limo.myioc.context;

import com.github.limo.myioc.test.model.User;
import com.github.limo.myioc.util.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author 顾慎为
 * @version 1.0
 * @date 2020/8/2
 * @time 13:09
 */
public class JsonApplicationContextTest {

    @Test
    public void testReadContent() throws FileNotFoundException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("singleton/singleton-beans.json");
        String fileContent = FileUtils.getFileContent(in);
        Assertions.assertEquals(true, StringUtils.isNotBlank(fileContent));
    }

    @Test
    public void testJsonApplicationContext() {
        // 1. 根据文件名称创建 JsonApplicationContext 对象.
        String confFileName = "singleton/singleton-beans.json";
        JsonApplicationContext jsonApplicationContext = new JsonApplicationContext(confFileName);

        // 2. get bean by name.
        User user = jsonApplicationContext.getBean("user", User.class);

        // 3. test
        Assertions.assertNotNull(user);
    }


}
