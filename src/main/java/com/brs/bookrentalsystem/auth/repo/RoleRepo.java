package com.brs.bookrentalsystem.auth.repo;

import com.brs.bookrentalsystem.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Short> {
}
