package com.example.springcontext.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    @Qualifier("bookProperty")
    private BookProperty bookProperty;

    @Autowired
    @Qualifier("bookProperty2")
    private BookProperty bookProperty2;

    @GetMapping("/bookProperty")
    public BookProperty getBook() {
        return bookProperty;
    }

    @GetMapping("/bookProperty2")
    public BookProperty getBook2() {
        return bookProperty2;
    }
}
