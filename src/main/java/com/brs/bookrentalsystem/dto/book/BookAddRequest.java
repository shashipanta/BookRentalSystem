package com.brs.bookrentalsystem.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

// register new book via thymeleaf

@Data
@Builder
public class BookAddRequest {

    private Integer bookId;

    @NotBlank(message = "Book Name cannot be blank")
    private String bookName;

    private Short totalPages;

    @Size(min = 13, max = 13, message = "ISBN should be 13 digits")
    private String isbn;

    private Double rating;

    @NotNull(message = "StockCount should be provided")
    private Integer stockCount;

    @NotBlank(message = "Published date cannot be empty")
    private String publishedDate;

    @NotBlank(message = "Photo should be provided")
    private String photoPath;

    @NotNull(message = "Select one category")
    private Short category;

    @NotNull(message = "Select at least one author")
    private List<Integer> authors;

}
