package com.example.jpa.async;

import com.example.jpa.base.Forum;
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

    @Autowired
    private ForumRepository forumRepository;

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void saveForum(long id) {
        System.out.println("start");
        Forum entity = new Forum();
        entity.setId(id);
        entity.setUserName("username");
        forumRepository.save(entity);
        System.out.println("end");
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void saveForumWithException(long id) {

        Forum entity = new Forum();
        entity.setId(id);
        entity.setUserName("username");
        forumRepository.save(entity);
        throw new RuntimeException();
    }
}