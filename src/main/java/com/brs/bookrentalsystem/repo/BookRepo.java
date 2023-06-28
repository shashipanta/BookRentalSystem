package com.brs.bookrentalsystem.repo;

import com.brs.bookrentalsystem.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Integer> {

    List<Book> findBookByStockCountIsGreaterThan(Integer stockCount);

    @Query(nativeQuery = true, value = "select  * from tbl_book")
    List<Book> findAllBooks();

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "update tbl_book b set b.is_active = true where b.id = ?1")
    void reviveDeletedBookById(Integer bookId);

}
