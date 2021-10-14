package com.example.springcontext.beanfactory;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.ResolvableType;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple implement of Spring BeanFactory, using xml to do DI, only bean injection supported.
 *
 * @author Hodur
 * @date 2021/10/12
 */
public class CustomeBeanFactory implements BeanFactory {

    /**
     * bean容器
     */
    private final Map<String, Object> container = new HashMap<>();

    final String BEAN = "bean";
    final String PROPERTY = "property";

    public void init(String xml) {
        try {
            // 解析配置文件
            Document doc = getDocument(xml);
            Element root = doc.getRootElement();
            Element foo;
            // 遍历bean
            for (Iterator<Element> i = root.elementIterator(BEAN); i.hasNext(); ) {
                foo = i.next();
                Attribute id = foo.attribute("id");
                Attribute cls = foo.attribute("class");
                Class<?> bean = Class.forName(cls.getText());
                Object obj = bean.getDeclaredConstructor().newInstance();

                //得到beanDefinition对应的类的所有属性
                for (Element foo2 : foo.elements(PROPERTY)) {
                    Attribute name = foo2.attribute("name");
                    Attribute ref = foo2.attribute("ref");
                    Field declaredField = bean.getDeclaredField(name.getText());
                    declaredField.setAccessible(true);
                    declaredField.set(obj, container.get(ref.getText()));
                }
                container.put(id.getText(), obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Document getDocument(String xml) throws DocumentException {
        SAXReader reader = new SAXReader();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream ins = classLoader.getResourceAsStream(xml);
        return reader.read(ins);
    }

    @Override
    public Object getBean(String beanName) {
        return container.get(beanName);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return null;
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {
        return null;
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return null;
    }

    @Override
    public Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
        return null;
    }

    @Override
    public String[] getAliases(String name) {
        return new String[0];
    }
}
