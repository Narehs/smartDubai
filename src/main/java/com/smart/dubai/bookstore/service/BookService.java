package com.smart.dubai.bookstore.service;

import com.smart.dubai.bookstore.dto.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDTO get(Long id);
    List<BookDTO> getAll();
    BookDTO create(BookDTO book);
    BookDTO update(Long id, BookDTO book);
    BookDTO getBookByIsbn(String isbn);
    void delete(Long id);
    double checkout(List<Long> books, String promotionCode);

}
