package com.smart.dubai.bookstore.service;

import com.smart.dubai.bookstore.domain.Book;
import com.smart.dubai.bookstore.dto.BookDTO;
import com.smart.dubai.bookstore.exception.BookStoreException;
import com.smart.dubai.bookstore.mapper.BookMapper;
import com.smart.dubai.bookstore.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookServiceTest {

    private final Long id = 1234L;
    private final double price = 250;
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @MockBean
    BookMapper mapper;

    @Test
    public void canCreateNewBook() {
        //given
        BookDTO bookDto = new BookDTO(id, "test_name", "test_desc", 128, "test_isbn", "test_auther", "test_type");
        Book book = new Book(id, "test_name", "test_desc", "test_auther", "test_type", 128, "test_isbn");
        Mockito.when(mapper.toEntity(bookDto)).thenReturn(book);
        Mockito.when(mapper.toDto(book)).thenReturn(bookDto);
        //when
        bookService.create(bookDto);

        //then
        ArgumentCaptor<Book> bookDTOArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookDTOArgumentCaptor.capture());

        Book capturedBook = bookDTOArgumentCaptor.getValue();

        assertEquals(capturedBook, book);

    }

    @Test
    public void canGetAllBooks() {

        //given
        Book book = mock(Book.class);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        BookDTO bookDto = mock(BookDTO.class);
        List<BookDTO> bookDtoList = new ArrayList<>();
        bookDtoList.add(bookDto);
        Mockito.when(bookRepository.findAll()).thenReturn(bookList);
        Mockito.when(mapper.toDto(book)).thenReturn(bookDto);

        ///when
        List<BookDTO> bookDTOS = bookService.getAll();
        //then
        verify(bookRepository).findAll();
        assertEquals(bookDtoList, bookDTOS);
    }

    @Test
    public void canGetBook() {
        BookDTO bookDto = new BookDTO(id, "test_name", "test_desc", 128, "test_isbn", "test_auther", "test_type");
        Book book = new Book(id, "test_name", "test_desc", "test_auther", "test_type", 128, "test_isbn");
        Mockito.when(bookRepository.findById(id))
                .thenReturn(Optional.of(book));
        Mockito.when(mapper.toDto(book)).thenReturn(bookDto);

        //when
        BookDTO bookDTO2 = bookService.get(id);

        //Assert
        assertEquals(bookDTO2, bookDto);
        verify(bookRepository).findById(id);

    }


    @Test
    public void willThrowWhenBookNotFound() {
        Mockito.when(bookRepository.findById(id))
                .thenThrow(BookStoreException.class);

        assertThatThrownBy(() -> bookService.get(id)).isInstanceOf(BookStoreException.class);

    }


    @Test
    public void canUpdateBook() {

        Book book = mock(Book.class);
        BookDTO bookDto = mock(BookDTO.class);
        Mockito.when(mapper.toEntity(bookDto)).thenReturn(book);
        Mockito.when(mapper.toDto(book)).thenReturn(bookDto);
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.when(book.getPrice()).thenReturn(price);
        //when
        bookService.update(id, bookDto);

        //then
        ArgumentCaptor<Book> bookDTOArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookDTOArgumentCaptor.capture());

        Book capturedBook = bookDTOArgumentCaptor.getValue();

        assertEquals(capturedBook.getPrice(), price);
        verify(bookRepository).save(book);

    }

    @Test
    public void canDeleteBook() {

        //when
        bookService.delete(id);

        //Assert
        verify(bookRepository).deleteById(id);
    }
}
