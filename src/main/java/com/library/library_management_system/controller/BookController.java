package com.library.library_management_system.controller;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedBook);
    }
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
    @PutMapping("/{id}")
    public Book updateBook(
            @PathVariable Long id,
            @RequestBody Book book) {

        return bookService.updateBook(id, book);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }
}
