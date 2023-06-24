package com.brs.bookrentalsystem.dto.transaction;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// rent a book

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookTransactionRequest {

    private Long transactionId;

    @NotBlank(message = "Code cannot be blank")
    private String code;

    @NotNull(message = "Book Id cannot be blank")
    private Integer bookId;

    @NotNull(message = "Member must be selected")
    private Integer memberId;

    @NotNull(message = "Days should be provided")
    @Max(value = 365, message = "Days cannot exceed 1 year")
    @Min(value = 1, message = "Days should be at least 1")
    private Integer totalDays;

}
