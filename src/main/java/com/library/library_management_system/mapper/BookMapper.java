package com.library.library_management_system.mapper;
import com.library.library_management_system.dto.BookRequestDTO;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.dto.BookResponseDTO;
import org.springframework.stereotype.Component;
@Component
public class BookMapper {
    // Request DTO → Entity
    public Book toEntity(BookRequestDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setCategory(dto.getCategory());

        return book;
    }

    // Entity → Response DTO
    public BookResponseDTO toResponseDTO(Book book) {

        BookResponseDTO dto = new BookResponseDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setCategory(book.getCategory());

        return dto;
    }


}
