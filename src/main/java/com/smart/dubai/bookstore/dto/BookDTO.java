package com.smart.dubai.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {

    private Long id;

    private String name;

    private String description;

    private double price;

    private String isbn;

    private String author;

    private String type;
}
