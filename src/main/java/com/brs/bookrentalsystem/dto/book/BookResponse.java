package com.brs.bookrentalsystem.dto.book;

import com.brs.bookrentalsystem.model.Author;
import com.brs.bookrentalsystem.model.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookResponse {

    private Integer bookId;

    private String bookName;

    private Short totalPages;

    private String isbn;

    private Double rating;

    private Integer stockCount;

    private String publishedDate;

    private String photoPath;

    private Category category;

    private List<Author> authors;
}