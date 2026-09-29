package com.library.library_management_system.service;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.exception.DuplicateIsbnException;
import com.library.library_management_system.repository.BookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.library.library_management_system.exception.BookNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
@Service
public class BookService {
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }
    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }
    public Book addBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DuplicateIsbnException(
                    "Book with ISBN " + book.getIsbn() + " already exists"
            );
        }

        book.setAvailable(true);
        return bookRepository.save(book);
    }
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book not found with id: " + id));
    }
    public Book updateBook(Long id, Book updatedBook) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException(
                                "Book not found with id: " + id));

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setCategory(updatedBook.getCategory());

        return bookRepository.save(existingBook);
    }
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException(
                                "Book not found with id: " + id));

        bookRepository.delete(book);
    }
     public List<Book>searchBooks(String keyword,int page, int size) {
         Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword,keyword,keyword,pageable);
     }
     public List<Book> searchBooksByAvailability(boolean available) {
        return bookRepository.findByAvailable(available);
     }
    public Page<Book> getBooksWithPagination(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        return bookRepository.findAll(pageable);
    }
}
