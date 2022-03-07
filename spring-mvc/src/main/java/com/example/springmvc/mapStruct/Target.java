package com.example.springmvc.mapStruct;

import lombok.Data;

import java.util.List;

/**
 * @author Hodur
 * @date 2022/02/10
 */
@Data
public class Target {

    private Integer id;

    private String targetName;

    private InnerTarget innerTarget;

    private List<InnerTarget> targets;

    @Data
    public static class InnerTarget {

        private Boolean isDeleted;

        private String name;
    }
}