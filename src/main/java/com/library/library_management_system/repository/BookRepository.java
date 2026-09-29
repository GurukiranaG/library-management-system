package com.library.library_management_system.repository;
import com.library.library_management_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
boolean existsByIsbn(String isbn);
List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrCategoryContainingIgnoreCase(
            String title,
            String author,
            String category,
            Pageable pageable
    );
    List<Book>findByAvailable(boolean available);
}
