package com.brs.bookrentalsystem.service;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.book.BookMessage;
import com.brs.bookrentalsystem.dto.transaction.*;
import com.brs.bookrentalsystem.model.BookTransaction;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

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

    Optional<TransactionResponse> getTransaction(String transactionCode);

    // get top 5 rented books
    List<BookMessage> getTopRentedBooks();

    // excel report
    List<TransactionExcelResponse> getAllTransactionsForExcel();

    // check if book is rented
    Boolean isBookRented(Integer bookId);

    Page<BookTransactionResponse> getPaginatedTransaction(Integer pageNo, Integer pageSize);


    // filter transaction by date range
    Page<BookTransactionResponse> getPaginatedAndFilteredTransaction(FilterTransaction filterTransaction, Integer pageNumber);



}