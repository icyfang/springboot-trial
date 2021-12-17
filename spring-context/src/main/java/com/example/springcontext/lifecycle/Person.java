package com.example.springcontext.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;

/**
 * @author Hodur
 * @date 2020-08-19
 */
public class Person implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {

    private String name;
    private BeanFactory beanFactory;
    private String beanName;

    public Person() {
        System.out.println("Person constructor");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("Person setProperty Name");
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + "]";
    }

    /**
     * 这是BeanFactoryAware接口方法
     */
    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware#setBeanFactory");
        this.beanFactory = beanFactory;
    }

    /**
     * 这是BeanNameAware接口方法
     */
    @Override
    public void setBeanName(@NonNull String name) {
        System.out.println("BeanNameAware#setBeanName");
        this.beanName = name;
    }

    /**
     * 这是InitializingBean接口方法
     */
    @Override
    public void afterPropertiesSet() {
        System.out.println("InitializingBean#afterPropertiesSet");
    }

    /**
     * 这是DiposibleBean接口方法
     */
    @Override
    public void destroy() {
        System.out.println("DisposableBean#destory");
    }

    /**
     * 通过<bean>的init-method属性指定的初始化方法
     */
    public void myInit() {
        System.out.println("Person init-method");
    }

    /**
     * 通过<bean>的destroy-method属性指定的初始化方法
     */
    public void myDestroy() {
        System.out.println("Person destroy-method");
    }
}
