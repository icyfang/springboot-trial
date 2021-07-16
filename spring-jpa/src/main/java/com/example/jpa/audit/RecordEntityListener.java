package com.example.jpa.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * @author Hodur
 * @date 2021-01-06
 */
@Component
@Slf4j
public class RecordEntityListener {

    private static RecordRepository recordRepository;

    @Autowired
    public void init(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") RecordRepository recordRepository) {
        RecordEntityListener.recordRepository = recordRepository;
        log.info("Initializing with dependency [" + recordRepository + "]");
    }

    @PrePersist
    public void prePersist(Object t) {
        RecordPO recordPO = new RecordPO();
        recordPO.setMethod("persist");
        recordPO.setContent(t.toString());

        recordRepository.save(recordPO);
    }

    @PostPersist
    public void postPersist(Object t) {

    }

    @PostLoad
    public void postLoad(Object t) {

        RecordPO recordPO = new RecordPO();
        recordPO.setMethod("query");
        recordPO.setContent(t.toString());
        recordRepository.save(recordPO);
    }

    @PreUpdate
    public void preUpdate(Object t) {
        RecordPO recordPO = new RecordPO();
        recordPO.setMethod("update");
        recordPO.setContent(t.toString());

        recordRepository.save(recordPO);
    }

    @PostUpdate
    public void postUpdate(Object t) {

    }

    @PreRemove
    public void preRemove(Object t) {
        RecordPO recordPO = new RecordPO();
        recordPO.setMethod("remove");
        recordPO.setContent(t.toString());

        recordRepository.save(recordPO);
    }

    @PostRemove
    public void postRemove(Object t) {

    }
}
