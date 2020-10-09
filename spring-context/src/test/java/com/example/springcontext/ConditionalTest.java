package com.example.springcontext;

import com.example.springcontext.conditional.SystemBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Shanghong Cai
 * @since 2020-08-06
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ConditionalTest {

    @Autowired(required = false)
    @Qualifier("mac")
    private SystemBean mac;

    @Autowired
    @Qualifier("windows")
    private SystemBean windows;

    @Test
    public void test() {
        if (windows != null) {
            System.out.println("windows = " + windows);
        }
        if (mac != null) {
            System.out.println("linux = " + mac);
        }
    }
}
