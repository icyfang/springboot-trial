package com.example.jpa.association.onetoone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainSubPO {

    private Long id;
    private String content;
    private Long subId;
    private String name;
}
