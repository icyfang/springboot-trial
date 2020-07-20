package com.example.springcontext.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "book")
public class BookProperty {
    private String name;
    private String author;
    private List<String> chapters;

}
