package com.example.springcontext.beanfactory;

/**
 * @author Hodur
 * @date 2021/10/12
 */
public class CustomeBeanFactoryDemo {

    public static void main(String[] args) {
        CustomeBeanFactory factory = new CustomeBeanFactory();
        factory.init("custome.xml");
        CourseService courseService = (CourseService) factory.getBean("courseService");
        System.out.println(courseService.findAll());
    }
}
