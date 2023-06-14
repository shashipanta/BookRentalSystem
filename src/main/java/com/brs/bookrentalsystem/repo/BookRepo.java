package com.brs.bookrentalsystem.repo;

import com.brs.bookrentalsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Integer> {

}
