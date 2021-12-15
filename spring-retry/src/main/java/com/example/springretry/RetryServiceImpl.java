package com.example.springretry;

import com.example.basic.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

/**
 * @author Hodur
 * @date 2021/12/15
 */
@Service
@Slf4j
public class RetryServiceImpl implements RetryService {

    private static int count = 1;

    @Override
    public String retry() throws ApiException {
        log.info("retry {}, throw CustomRetryException in method retry", count);
        count++;
        throw new ApiException("throw custom exception");
    }

    @Recover
    public String recover(Throwable throwable) {
        log.info("Default Retry service test");
        return "Error Class :: " + throwable.getClass().getName();
    }

}

