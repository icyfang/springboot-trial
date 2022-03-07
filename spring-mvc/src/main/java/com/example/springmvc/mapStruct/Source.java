package com.example.springmvc.mapStruct;

import lombok.Data;

import java.util.List;

/**
 * @author Hodur
 * @date 2022/02/10
 */
@Data
public class Source {

    private Integer id;

    private String sourceName;

    private InnerSource innerSource;

    private List<InnerSource> sources;

    @Data
    public static class InnerSource {

        private Integer deleted;

        private String name;
    }
}
