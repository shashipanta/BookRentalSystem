package com.brs.bookrentalsystem.controller.rest;

import com.brs.bookrentalsystem.dto.transaction.BookTransactionRequest;
import com.brs.bookrentalsystem.dto.transaction.BookTransactionResponse;
import com.brs.bookrentalsystem.service.BookTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/book-transactions")
public class BookTransactionController {

    private final BookTransactionService bookTransactionService;


    @PostMapping(value = "/")
    public BookTransactionResponse addNewBookTransaction(@RequestBody @Valid BookTransactionRequest request){
        return bookTransactionService.createBookTransaction(request);
    }
}
