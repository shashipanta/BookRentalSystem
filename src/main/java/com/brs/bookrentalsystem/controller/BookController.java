package com.brs.bookrentalsystem.controller;

import com.brs.bookrentalsystem.dto.book.BookRequest;
import com.brs.bookrentalsystem.dto.book.BookResponse;
import com.brs.bookrentalsystem.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/book")
public class BookController {

    private final BookService bookService;


    @PostMapping(value = "/")
    public BookResponse registerNewBook(@RequestBody @Valid BookRequest bookRequest){
        return bookService.saveBook(bookRequest);
    }

    public List<BookResponse> getAllRegisteredBooks(){
        return bookService.getSavedBooks();
    }

}
