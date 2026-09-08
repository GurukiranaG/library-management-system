package com.library.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.library.library_management_system.entity.Member;
public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
}
