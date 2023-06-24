package com.brs.bookrentalsystem.repo;

import com.brs.bookrentalsystem.enums.RentStatus;
import com.brs.bookrentalsystem.model.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookTransactionRepo extends JpaRepository<BookTransaction, Long> {

    BookTransaction findBookTransactionByCode(String bookCode);

    List<BookTransaction> findBookTransactionByRentStatus(RentStatus rentStatus);

    @Query("SELECT bookTransaction FROM BookTransaction bookTransaction WHERE " +
            "bookTransaction.code LIKE CONCAT('%',:filterValue, '%')" )
    List<BookTransaction> filterByTransactionCode(String filterValue);

    @Query("SELECT LAST_INSERT_ID()")
    Long getLastInsertedId();
}
