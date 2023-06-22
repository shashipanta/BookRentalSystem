package com.brs.bookrentalsystem.service;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.book.BookRequest;
import com.brs.bookrentalsystem.dto.book.BookResponse;
import com.brs.bookrentalsystem.model.Book;

import java.util.List;

public interface BookService {

        BookResponse saveBook(BookRequest request);

        List<BookResponse> getSavedBooks();

        BookResponse getBookById(Integer bookId);

        BookRequest getBookRequestById(Integer bookId);

        Book getBookEntityById(Integer bookId);

        BookResponse updateBook(BookRequest request);

        Message deleteBookById(Integer bookId);

        void updateStock(Integer bookId, Integer stockUpdateNumber);

        List<BookResponse> getBooksAvailableOnStock();
}
