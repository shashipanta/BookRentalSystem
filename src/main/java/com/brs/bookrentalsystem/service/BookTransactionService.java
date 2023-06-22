package com.brs.bookrentalsystem.service;

import com.brs.bookrentalsystem.dto.transaction.BookTransactionRequest;
import com.brs.bookrentalsystem.dto.transaction.BookTransactionResponse;
import com.brs.bookrentalsystem.dto.transaction.TransactionFilterRequest;

import java.util.List;

public interface BookTransactionService {

    BookTransactionResponse rentBook(BookTransactionRequest request);

    BookTransactionResponse returnBook(BookTransactionRequest request);

    List<BookTransactionResponse> getAllTransactions();

    List<BookTransactionResponse> getNotReturnedBookTransactions();

    // filter transaction
    List<BookTransactionResponse> filterTransactionByTransactionCode(TransactionFilterRequest filterRequest);



}
