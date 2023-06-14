package com.brs.bookrentalsystem.repo;

import com.brs.bookrentalsystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
}
