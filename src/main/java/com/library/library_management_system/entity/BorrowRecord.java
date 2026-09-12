package com.library.library_management_system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    @ManyToOne
    @JoinColumn(name="member_id",nullable=false)
    private Member member;
    @ManyToOne
    @JoinColumn(name="book_id",nullable=false)
    private Book book;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BorrowStatus status;
    public BorrowRecord() {

    }
    public Long getId() {
        return id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public BorrowStatus getStatus() {
        return status;
    }


    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }
}
