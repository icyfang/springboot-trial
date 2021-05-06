package com.example.springcontext.lifecycle;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Hodur
 * @date 2020-08-19
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LifeCycleTest {

    @Test
    public void testLifeCycle() {
        System.out.println("现在开始初始化容器");
        AnnotationConfigApplicationContext factory = new AnnotationConfigApplicationContext(PersonConfig.class);
        System.out.println("容器初始化成功");
        //得到Person，并使用
        Person person = factory.getBean("person", Person.class);
        System.out.println(person);
        System.out.println("现在开始关闭容器！");
        factory.registerShutdownHook();
    }
}
