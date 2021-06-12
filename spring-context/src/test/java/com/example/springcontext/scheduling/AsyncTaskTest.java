package com.example.springcontext.scheduling;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.Future;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AsyncTaskTest {

    @Autowired
    private AsyncTask asyncTask;

    @Test
    void test() throws Exception {
        asyncTask.doTaskOne();
        asyncTask.doTaskTwo();
        asyncTask.doTaskThree();
        Thread.sleep(10000);
    }

    @Test
    void testCallable() throws Exception {
        Future<String> task1 = asyncTask.doCallableTaskOne();
        Future<String> task2 = asyncTask.doCallableTaskTwo();
        Future<String> task3 = asyncTask.doCallableTaskThree();

        // 三个任务都调用完成，退出循环等待
        while (!task1.isDone() || !task2.isDone() || !task3.isDone()) {
            Thread.sleep(1000);
        }
    }

}
