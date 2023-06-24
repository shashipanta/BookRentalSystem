package com.brs.bookrentalsystem.service;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.transaction.*;
import com.brs.bookrentalsystem.model.BookTransaction;

import java.util.List;

public interface BookTransactionService {

    BookTransactionResponse rentBook(BookTransactionRequest request);

    BookTransaction getTransactionById(Long transactionId);

    Message returnBook(BookReturnRequest request);

    List<BookTransactionResponse> getAllTransactions();

    List<BookTransactionResponse> getNotReturnedBookTransactions();

    // filter transaction
    List<String> filterTransactionByTransactionCode(TransactionFilterRequest filterRequest);

    String generateTransactionCode(String bookName);

    String getBookReturnDate(Integer days);

    TransactionResponse getTransaction(String transactionCode);



}
