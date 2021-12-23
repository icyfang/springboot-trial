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
 * @date 2020/8/19
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

        Person person = factory.getBean("person", Person.class);
        System.out.println(person);
        String beanName = person.getBeanName();
        System.out.println(beanName);
        Person bean = person.getBeanFactory().getBean(beanName, Person.class);
        System.out.println(bean.getName());
        System.out.println("现在开始关闭容器！");
        factory.close();
//        factory.registerShutdownHook();
    }
}
