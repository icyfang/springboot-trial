package com.example.springcontext.aop.jdk;

import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-23
 */
@Component
public class JdkComponent implements JdkInterface {

    @Override
    public Integer act(Integer... a) {
        return doAct(a);
    }

    @Override
    public Integer doAct(Integer... a) {
        if (a.length > 10) {
            throw new RuntimeException("length exceeds limit");
        }
        return a.length;
    }

}
