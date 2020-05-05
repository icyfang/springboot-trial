package com.example.demo.context.controller;

import com.example.demo.context.properties.BookBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookBean bookBean;

    @RequestMapping("/getBook")
    @ResponseBody
    public BookBean getBook() {
        return bookBean;
    }
}
