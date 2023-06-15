package com.brs.bookrentalsystem.repo;

import com.brs.bookrentalsystem.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Integer> {
}
