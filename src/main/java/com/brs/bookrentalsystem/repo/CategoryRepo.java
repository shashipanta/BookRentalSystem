package com.brs.bookrentalsystem.repo;

import com.brs.bookrentalsystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Short> {
}
