package com.example.springcontext.aop.args;

import com.example.springcontext.aop.CustomAnn;

/**
 * @author Hodur
 * @since 2021-05-26
 */
@CustomAnn("AnnotatedClass")
public class AnnotatedClass {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
