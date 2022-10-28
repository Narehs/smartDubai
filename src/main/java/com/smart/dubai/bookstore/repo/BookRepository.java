package com.smart.dubai.bookstore.repo;

import com.smart.dubai.bookstore.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findOneByIsbn(String isbn);
}
