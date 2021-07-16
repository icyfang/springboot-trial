package com.example.jpa.async;

import com.example.jpa.base.ForumPO;
import com.example.jpa.base.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hodur
 * @date 2020-12-02
 */
@Repository
public class AsyncRepository {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ForumRepository forumRepository;

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void saveForum(long id) {

        ForumPO entity = new ForumPO();
        entity.setId(id);
        entity.setUsername("username");
        forumRepository.save(entity);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void saveForumWithException(long id) {

        ForumPO entity = new ForumPO();
        entity.setId(id);
        entity.setUsername("username");
        forumRepository.save(entity);
        throw new RuntimeException();
    }
}
