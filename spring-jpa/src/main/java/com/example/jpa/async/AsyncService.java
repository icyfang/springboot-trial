package com.example.jpa.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hodur
 * @date 2020-12-01
 */
@Service
public class AsyncService {

    @Autowired
    private AsyncRepository asyncRepository;

    @Transactional(rollbackFor = Exception.class)
    public void trans(long id) throws InterruptedException {
        asyncRepository.saveForum(id);
        Thread.sleep(1000);
        throw new RuntimeException();
    }

    @Transactional(rollbackFor = Exception.class)
    public void trans2(long id) {
        asyncRepository.saveForumWithException(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void trans3(long id1, long id2) {
        asyncRepository.saveForum(id1);
        asyncRepository.saveForumWithException(id2);
    }

}
