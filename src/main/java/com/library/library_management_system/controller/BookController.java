package com.library.library_management_system.controller;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.service.BookService;
import jakarta.validation.Valid;
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
import  com.library.library_management_system.dto.BookRequestDTO;
import com.library.library_management_system.dto.BookResponseDTO;
import com.library.library_management_system.mapper.BookMapper;
import java.util.List;
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService,BookMapper bookMapper) {

        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {

        List<Book> books = bookService.getAllBooks();

        List<BookResponseDTO> response = books.stream()
                .map(bookMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(
            @PathVariable Long id) {

        Book book = bookService.getBookById(id);

        BookResponseDTO response =
                bookMapper.toResponseDTO(book);

        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(
            @Valid @RequestBody BookRequestDTO dto) {

        // DTO → Entity
        Book book = bookMapper.toEntity(dto);

        // Save Entity
        Book savedBook = bookService.addBook(book);

        // Entity → Response DTO
        BookResponseDTO response = bookMapper.toResponseDTO(savedBook);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDTO dto) {

        Book book = bookMapper.toEntity(dto);

        Book updatedBook = bookService.updateBook(id, book);

        BookResponseDTO response =
                bookMapper.toResponseDTO(updatedBook);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }
}
