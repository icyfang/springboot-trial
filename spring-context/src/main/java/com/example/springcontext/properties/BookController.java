package com.example.springcontext.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    @Qualifier("bookProperty")
    private BookProperty bookProperty;

    @Autowired
    @Qualifier("bookProperty2")
    private BookProperty bookProperty2;

    @Value("${book.editDate}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date editDate;

    @GetMapping("/bookProperty")
    public BookProperty getBook() {
        return bookProperty;
    }

    @GetMapping("/bookProperty2")
    public BookProperty getBook2() {
        return bookProperty2;
    }
}
