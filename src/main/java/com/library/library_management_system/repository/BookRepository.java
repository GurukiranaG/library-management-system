package com.library.library_management_system.repository;
import com.library.library_management_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
boolean existsByIsbn(String isbn);
}
