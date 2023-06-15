package com.brs.bookrentalsystem.service;

import com.brs.bookrentalsystem.dto.transaction.BookTransactionRequest;
import com.brs.bookrentalsystem.dto.transaction.BookTransactionResponse;

public interface BookTransactionService {

    BookTransactionResponse createBookTransaction(BookTransactionRequest request);




}
