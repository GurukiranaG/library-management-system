package com.library.library_management_system.service;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.repository.BookRepository;
import org.springframework.stereotype.Service;
import com.library.library_management_system.exception.BookNotFoundException;
import java.util.List;
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
}
