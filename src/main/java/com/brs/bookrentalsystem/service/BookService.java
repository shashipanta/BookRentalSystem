package com.brs.bookrentalsystem.service;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.book.BookRequest;
import com.brs.bookrentalsystem.dto.book.BookResponse;

import java.util.List;

public interface BookService {

        BookResponse saveBook(BookRequest request);

        List<BookResponse> getSavedBooks();

        BookResponse getBookById(Integer bookId);

        BookResponse updateBook(BookRequest request, Integer bookId);

        Message deleteBookById(Integer bookId);
}
