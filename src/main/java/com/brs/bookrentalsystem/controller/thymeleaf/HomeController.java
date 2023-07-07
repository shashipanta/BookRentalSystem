package com.brs.bookrentalsystem.controller.thymeleaf;


import com.brs.bookrentalsystem.dto.book.BookResponse;
import com.brs.bookrentalsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/brs")
public class HomeController {

    private final BookService bookService;

    //http://localhost:8080/brs/dashboard

    @GetMapping(value = "/")
    public String getDashboard(Model model){
        List<BookResponse> allBooks = bookService.getAllBooks();
        model.addAttribute("bookList", allBooks);
        return "index";
    }
}
