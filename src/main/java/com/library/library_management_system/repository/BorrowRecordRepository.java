package com.library.library_management_system.repository;

import com.library.library_management_system.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository  extends JpaRepository<BorrowRecord,Long> {

}
