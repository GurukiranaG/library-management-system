package com.library.library_management_system.service;

import com.library.library_management_system.entity.Book;
import com.library.library_management_system.entity.BorrowRecord;
import com.library.library_management_system.entity.BorrowStatus;
import com.library.library_management_system.entity.Member;
import com.library.library_management_system.exception.BookAlreadyReturnedException;
import com.library.library_management_system.exception.BookNotFoundException;
import com.library.library_management_system.exception.BorrowRecordNotFoundException;
import com.library.library_management_system.exception.MemberNotFoundException;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.BorrowRecordRepository;
import com.library.library_management_system.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowRecordService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    public BorrowRecordService(BookRepository bookRepository, MemberRepository memberRepository, BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }
    @Transactional
    public BorrowRecord issueBook(Long memberId,Long bookId){
         Member member=memberRepository.findById(memberId).orElseThrow(
                 ()->new MemberNotFoundException("Member Not Found with id: "+memberId)
         );
         Book book=bookRepository.findById(bookId).orElseThrow(
                 ()-> new BookNotFoundException("Book Not found with book id"+bookId)
         );
         if(!book.isAvailable()){
             throw new BookNotFoundException( "Book with id " + bookId + " is currently not available");
         }
        BorrowRecord borrowRecord = new BorrowRecord();

        borrowRecord.setMember(member);
        borrowRecord.setBook(book);

        LocalDate today = LocalDate.now();

        borrowRecord.setIssueDate(today);
        borrowRecord.setDueDate(today.plusDays(14));
        borrowRecord.setStatus(BorrowStatus.ISSUED);

        book.setAvailable(false);

        bookRepository.save(book);

        return borrowRecordRepository.save(borrowRecord);
    }
    @Transactional
    public BorrowRecord returnBook(Long borrowRecordId){
        BorrowRecord borrowRecord=borrowRecordRepository.findById(borrowRecordId).orElseThrow(
                ()->new BorrowRecordNotFoundException("Borrow Record Not Found with id: "+borrowRecordId)
        );
        if(borrowRecord.getStatus()==BorrowStatus.RETURNED){
            throw new BookAlreadyReturnedException("Book Already Returned");
        }
        borrowRecord.setReturnDate(LocalDate.now());
        borrowRecord.setStatus(BorrowStatus.RETURNED);
        Book book=borrowRecord.getBook();
        book.setAvailable(true);
        bookRepository.save(book);
        return borrowRecordRepository.save(borrowRecord);
    }

}
