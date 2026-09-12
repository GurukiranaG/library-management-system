package com.library.library_management_system.mapper;
import com.library.library_management_system.dto.BorrowRecordResponseDTO;
import com.library.library_management_system.entity.BorrowRecord;
import org.springframework.stereotype.Component;

@Component
public class BorrowRecordMapper {
    public BorrowRecordResponseDTO toResponseDTO(BorrowRecord borrowRecord) {
    BorrowRecordResponseDTO dto = new BorrowRecordResponseDTO();
        dto.setId(borrowRecord.getId());

        dto.setMemberId(borrowRecord.getMember().

    getId());
        dto.setMemberName(borrowRecord.getMember().

    getName());

        dto.setBookId(borrowRecord.getBook().

    getId());
        dto.setBookTitle(borrowRecord.getBook().

    getTitle());

        dto.setIssueDate(borrowRecord.getIssueDate());
        dto.setDueDate(borrowRecord.getDueDate());
        dto.setReturnDate(borrowRecord.getReturnDate());

        dto.setStatus(borrowRecord.getStatus());

        return dto;
}
}
