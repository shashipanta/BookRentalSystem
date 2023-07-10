package com.brs.bookrentalsystem.repo;

import com.brs.bookrentalsystem.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookRepoTest {

    @Mock
    BookRepo bookRepo;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findBookByStockCountIsGreaterThan() {
    }

    @Test
    void findAllBooks() {

        List<Book> allBooks = bookRepo.findAllBooks();
        Assertions.assertThat(allBooks).hasSize(12);
    }

    @Test
    void reviveDeletedBookById() {
    }
}