package com.example.springretry;

import com.example.basic.exception.ApiException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RetryServiceImplTest {

    @Autowired
    private RetryService retryService;

    @Test
    void retry() {
        try {
            String message = retryService.retry();
            System.out.println(("message = " + message));
        } catch (ApiException e) {
            System.out.println("Error while executing test");
            e.printStackTrace();
        }
    }

}
