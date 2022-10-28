package com.smart.dubai.bookstore.mapper;

import com.smart.dubai.bookstore.domain.Book;
import com.smart.dubai.bookstore.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDto(Book book);

    Book toEntity(BookDTO book);

}
