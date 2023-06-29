package com.brs.bookrentalsystem.dto.book;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ExcelBookUploadRequest {

    @NotNull(message = "File cannot be empty")
    private MultipartFile excelFile;
}
