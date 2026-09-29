package com.library.library_management_system.controller;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDTO>> searchBooks(@RequestParam String keyword,@RequestParam int size,@RequestParam int page){
        List<Book> books=bookService.searchBooks(keyword,size,page);
        List<BookResponseDTO> response = books.stream().map(bookMapper::toResponseDTO).toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("filter")
    public ResponseEntity<List<BookResponseDTO>> filterBooks(@RequestParam boolean available){
        List<Book>books=bookService.searchBooksByAvailability(available);
        List<BookResponseDTO> response = books.stream().map(bookMapper::toResponseDTO).toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/page")
    public ResponseEntity<Page<BookResponseDTO>> getBooksByPage(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String direction) {

        Page<Book> books =
                bookService.getBooksWithPagination(
                        page,
                        size,
                        sortBy,
                        direction
                );

        Page<BookResponseDTO> response =
                books.map(bookMapper::toResponseDTO);

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
