package com.library.library_management_system.service;

import com.library.library_management_system.entity.Book;
import com.library.library_management_system.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    // 1. Create a fake (mock) repository so it doesn't touch MySQL
    @Mock
    private BookRepository bookRepository;

    // 2. Inject the fake repository into our BookService
    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetBookById_Success() {
        // Arrange: Create a mock book and tell Mockito what to return when queried
        Book mockBook = new Book();
        mockBook.setId(1L);
        mockBook.setTitle("Spring Boot in Action");
        mockBook.setIsbn("1234567890");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));

        // Act: Call the service method we want to test
        Book foundBook = bookService.getBookById(1L);

        // Assert: Verify that the result matches what we expect
        assertNotNull(foundBook);
        assertEquals("Spring Boot in Action", foundBook.getTitle());
        assertEquals("1234567890", foundBook.getIsbn());

        // Verify that the repository method was actually called once
        verify(bookRepository, times(1)).findById(1L);
    }
    @Test
    public void testGetBookById_NotFound() {
        // Arrange: Tell Mockito that looking up ID 99 will return empty (not found)
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert: Verify that calling the service throws our custom exception (or RuntimeException)
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.getBookById(99L);
        });

        assertNotNull(exception);

        // Verify repository was called once
        verify(bookRepository, times(1)).findById(99L);
    }
}