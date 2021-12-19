package com.example.springcontext.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * @author Hodur
 * @date 2020-10-26
 */
@Slf4j
@Component
public class AsyncTask {

    public static final Random RANDOM = new Random();

    @Async("taskExecutor")
    public void doTaskOne() throws Exception {
        log.info("start task 1");
        long start = System.currentTimeMillis();
        Thread.sleep(RANDOM.nextInt(10000));
        log.info("task 1 completed, takes：" + (System.currentTimeMillis() - start) + "ms");
    }

    @Async("taskExecutor")
    public void doTaskTwo() throws Exception {
        log.info("start task 2");
        long start = System.currentTimeMillis();
        Thread.sleep(RANDOM.nextInt(10000));
        log.info("task 2 completed, takes：" + (System.currentTimeMillis() - start) + "ms");
    }

    @Async("taskExecutor")
    public void doTaskThree() throws Exception {
        log.info("start task 3");
        long start = System.currentTimeMillis();
        Thread.sleep(RANDOM.nextInt(10000));
        log.info("task 3 completed, takes：" + (System.currentTimeMillis() - start) + "ms");
    }

    @Async
    public Future<String> doCallableTaskOne() throws Exception {
        log.info("start callable task 1");
        long start = System.currentTimeMillis();
        Thread.sleep(RANDOM.nextInt(10000));
        log.info("callable task 1 completed, takes：" + (System.currentTimeMillis() - start) + "ms");
        return new AsyncResult<>("callable task 1 completed");
    }

    @Async
    public Future<String> doCallableTaskTwo() throws Exception {
        log.info("start callable task 1");
        long start = System.currentTimeMillis();
        Thread.sleep(RANDOM.nextInt(10000));
        log.info("callable task 2 completed, takes：" + (System.currentTimeMillis() - start) + "ms");
        return new AsyncResult<>("callable task 2 completed");
    }

    @Async
    public Future<String> doCallableTaskThree() throws Exception {
        log.info("start callable task 1");
        long start = System.currentTimeMillis();
        Thread.sleep(RANDOM.nextInt(10000));
        log.info("callable task 3 completed, takes：" + (System.currentTimeMillis() - start) + "ms");
        return new AsyncResult<>("callable task 3 completed");
    }
}
