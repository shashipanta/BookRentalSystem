package com.brs.bookrentalsystem.auth.repo;


import com.brs.bookrentalsystem.auth.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepo extends JpaRepository<TestEntity, Short> {
}
