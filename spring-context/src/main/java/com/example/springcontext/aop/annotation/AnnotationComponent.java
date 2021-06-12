package com.example.springcontext.aop.annotation;

import com.example.springcontext.aop.CustomAnn;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-25
 */
@Component
public class AnnotationComponent {

    public void act() {
        System.out.println("AnnotationComponent act");
    }

    @CustomAnn("doAct")
    public void doAct() {
        System.out.println("AnnotationComponent doAct");
    }
}
