package com.example.springmvc.localdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Hodur
 * @date 2021/8/5
 */
@Data
public class LocalDateItem {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
