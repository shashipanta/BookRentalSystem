package com.brs.bookrentalsystem.controller.thymeleaf.book;

import com.brs.bookrentalsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/brs/admin/book")
public class BookViewController {

    private final BookService bookService;

    @GetMapping(value = "/")
    public String openBookPage(){
        return "/book/book-page";
    }
}
