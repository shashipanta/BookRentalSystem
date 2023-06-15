package com.brs.bookrentalsystem.repo;

import com.brs.bookrentalsystem.model.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTransactionRepo extends JpaRepository<BookTransaction, Long> {
}
