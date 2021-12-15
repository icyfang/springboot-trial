package com.example.springretry;

import com.example.basic.exception.ApiException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

/**
 * @author Hodur
 * @date 2021/12/15
 */
public interface RetryService {
    /**
     * 指定异常CustomRetryException重试，重试最大次数为4（默认是3）,重试补偿机制间隔200毫秒
     * 还可以配置exclude,指定异常不充实，默认为空
     *
     * @return result
     * @throws CustomRetryException 指定异常
     */
    @Retryable(value = {ApiException.class}, maxAttempts = 4, backoff = @Backoff(200))
    String retry() throws ApiException;
}
