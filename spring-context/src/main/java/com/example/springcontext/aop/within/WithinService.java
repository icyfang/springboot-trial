package com.example.springcontext.aop.within;

import org.springframework.stereotype.Service;

/**
 * @author Hodur
 * @since 2021-05-23
 */
@Service
public class WithinService {

    public void act() {
        System.out.println("WithinService act");
    }
}
