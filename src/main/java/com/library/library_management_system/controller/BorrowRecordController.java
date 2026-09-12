package com.library.library_management_system.controller;
import com.library.library_management_system.dto.BorrowRecordResponseDTO;
import com.library.library_management_system.dto.IssueBookRequestDTO;
import com.library.library_management_system.entity.BorrowRecord;
import com.library.library_management_system.service.BorrowRecordService;
import com.library.library_management_system.mapper.BorrowRecordMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/borrow-records")
public class BorrowRecordController {
    private final BorrowRecordService borrowRecordService;
    private final BorrowRecordMapper borrowRecordMapper;
    public BorrowRecordController(BorrowRecordService borrowRecordService, BorrowRecordMapper borrowRecordMapper) {
        this.borrowRecordService = borrowRecordService;
        this.borrowRecordMapper = borrowRecordMapper;
    }
    @PostMapping("/issue")
    public ResponseEntity<BorrowRecordResponseDTO> issueBook(
            @Valid @RequestBody IssueBookRequestDTO dto) {

        BorrowRecord borrowRecord =
                borrowRecordService.issueBook(
                        dto.getMemberId(),
                        dto.getBookId()
                );

        BorrowRecordResponseDTO response =
                borrowRecordMapper.toResponseDTO(borrowRecord);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PutMapping("/{id}/return")
    public ResponseEntity<BorrowRecordResponseDTO> returnBook(
            @PathVariable Long id) {

        BorrowRecord borrowRecord = borrowRecordService.returnBook(id);

        BorrowRecordResponseDTO response =
                borrowRecordMapper.toResponseDTO(borrowRecord);

        return ResponseEntity.ok(response);
    }

}
