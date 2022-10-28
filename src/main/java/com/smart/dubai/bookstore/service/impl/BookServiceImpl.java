package com.smart.dubai.bookstore.service.impl;

import com.smart.dubai.bookstore.constants.BookType;
import com.smart.dubai.bookstore.domain.Book;
import com.smart.dubai.bookstore.dto.BookDTO;
import com.smart.dubai.bookstore.mapper.BookMapper;
import com.smart.dubai.bookstore.repo.BookRepository;
import com.smart.dubai.bookstore.service.BookService;
import com.smart.dubai.bookstore.exception.BookStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper mapper;

    @Override
    public BookDTO get(Long id) {
        return bookRepository.findById(id).map(b->mapper.toDto(b)).orElseThrow(()-> new BookStoreException("Book with id = " + id + "not found"));
    }

    @Override
    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream().map(b->mapper.toDto(b)).collect(Collectors.toList());
    }

    @Override
    public BookDTO create(BookDTO book) {
        return mapper.toDto(bookRepository.save(mapper.toEntity(book)));
    }
    @Override
    public BookDTO update(Long id, BookDTO book) {
        BookDTO foundLoad = mapper.toDto(bookRepository.findById(id).orElseThrow(()-> new BookStoreException("Book with id = " + id + "not found")));
        foundLoad.setName(book.getName());
        foundLoad.setDescription(book.getDescription());
        foundLoad.setIsbn(book.getIsbn());
        foundLoad.setPrice(book.getPrice());
        foundLoad.setAuthor(book.getAuthor());
        foundLoad.setType(book.getType());
        return mapper.toDto(bookRepository.save(mapper.toEntity(foundLoad)));
    }

    @Override
    public BookDTO getBookByIsbn(String isbn) {
        return mapper.toDto(bookRepository.findOneByIsbn(isbn)
                .orElseThrow(() -> new BookStoreException("Book with isbn:" + isbn + " not found.")));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public double checkout(List<Long> books, String promotionCode) {
        double finalPrice = 0.0;
        for (Long id: books) {
            double discount = 0;
            Optional<Book> book = bookRepository.findById(id);
            if (Objects.nonNull(promotionCode) && promotionCode.length()>0){
                if (book.isPresent()){
                    discount = book.get().getPrice() * BookType.discountPercentage(book.get().getType())/100;
                    finalPrice = finalPrice + book.get().getPrice() - discount;
                }
            } else {
                finalPrice = finalPrice + book.get().getPrice();
            }
        }
        return finalPrice;
    }
}
