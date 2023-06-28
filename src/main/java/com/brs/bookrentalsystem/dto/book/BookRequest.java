package com.brs.bookrentalsystem.dto.book;

import com.brs.bookrentalsystem.annotation.ValidMultipartFile;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    private Integer id;

    @NotBlank(message = "Book Name cannot be blank")
    private String bookName;

    @Min(value = 1, message = "Book should have at least 1 page")
    private Short totalPages;

    @Pattern(regexp = "^[0-9]{13}", message = "ISBN should be 13 digits")
    private String isbn;

    @Min(value = 0, message = "Min rating should be greater than 0")
    @Max(value = 10, message = "Max rating should not be greater than 10")
    private Double rating;

    @NotNull(message = "StockCount should be provided")
    @Min(value = 1, message = "Min stock should be 1")
    @Max(value = 1000, message = "Stock should not be greater than 1000")
    private Integer stockCount;

    @NotNull(message = "Published date cannot be empty")
    @PastOrPresent(message = "Published date should be either present or past one")
    private LocalDate publishedDate;

//    @NotBlank(message = "Photo should be provided")
    private String photoPath;

    @ValidMultipartFile(message = "Please provide book cover image")
    private MultipartFile multipartFile;

//    for thymeleaf request

    @NotNull(message = "Select a category")
    private Short categoryId;

    @NotNull(message = "Select at least one author")
    @Size(min = 1, message = "Select at least one author")
    private List<Integer> authorId;

}
