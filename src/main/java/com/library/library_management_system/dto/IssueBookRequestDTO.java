package com.library.library_management_system.dto;

import jakarta.validation.constraints.NotNull;

public class IssueBookRequestDTO {
    @NotNull(message="member ID should not be null")
    private Long memberId;
    @NotNull(message = "book ID should not be null")
    private Long bookId;

    public Long getMemberId() {
        return memberId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
