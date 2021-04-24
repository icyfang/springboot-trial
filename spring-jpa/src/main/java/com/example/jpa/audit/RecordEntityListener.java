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
 * @since 2021-01-06
 */
@Component
@Slf4j
public class RecordEntityListener {

    private static RecordRepository recordRepository;

    @Autowired
    public void init(RecordRepository recordRepository) {
        RecordEntityListener.recordRepository = recordRepository;
        log.info("Initializing with dependency [" + recordRepository + "]");
    }

    @PrePersist
    public void prePersist(Object t) {
        Record record = new Record();
        record.setMethod("persist");
        record.setContent(t.toString());

        recordRepository.save(record);
    }

    @PostPersist
    public void postPersist(Object t) {

    }

    @PostLoad
    public void postLoad(Object t) {

        Record record = new Record();
        record.setMethod("query");
        record.setContent(t.toString());
        recordRepository.save(record);
    }

    @PreUpdate
    public void preUpdate(Object t) {
        Record record = new Record();
        record.setMethod("update");
        record.setContent(t.toString());

        recordRepository.save(record);
    }

    @PostUpdate
    public void postUpdate(Object t) {

    }

    @PreRemove
    public void preRemove(Object t) {
        Record record = new Record();
        record.setMethod("remove");
        record.setContent(t.toString());

        recordRepository.save(record);
    }

    @PostRemove
    public void postRemove(Object t) {

    }
}
