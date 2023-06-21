package com.brs.bookrentalsystem.dto.book;

import com.brs.bookrentalsystem.annotation.ValidMultipartFile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    private Integer id;

    @NotBlank(message = "Book Name cannot be blank")
    private String bookName;

    private Short totalPages;

    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;

    private Double rating;

    @NotNull(message = "StockCount should be provided")
    private Integer stockCount;

    @NotBlank(message = "Published date cannot be empty")
    private String publishedDate;

//    @NotBlank(message = "Photo should be provided")
    private String photoPath;

    @ValidMultipartFile(message = "Please provide book cover image")
    private MultipartFile multipartFile;

//    @NotNull(message = "Select one category")
//    private Short category;

//    @NotNull(message = "Select at least one author")
//    private List<Integer> authors;

//    for thymeleaf request

    @NotNull(message = "Select a category")
    private Short categoryId;

    @NotNull(message = "Select at least one author")
    @Size(min = 1, message = "Select at least one author")
    private List<Integer> authorId;

}
