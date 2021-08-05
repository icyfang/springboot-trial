package com.example.springmvc.localdate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Hodur
 * @date 2021/8/5
 */
@RestController
@RequestMapping("/localDate")
public class LocalDateController {

    @GetMapping()
    public List<LocalDateItem> listDateItem(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {

        LocalDateItem item1 = new LocalDateItem();
        item1.setId(1L);
        item1.setDate(startDate);
        LocalDateItem item2 = new LocalDateItem();

        item2.setId(2L);
        item2.setDate(endDate);
        return List.of(item1, item2);
    }

    @PostMapping()
    public LocalDateItem listDateItem(@RequestBody LocalDateItem item) {
        return item;
    }
}
