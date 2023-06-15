package com.brs.bookrentalsystem.dto.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BookTransactionRequest {

    private Long transactionId;

    @NotBlank(message = "Code cannot be blank")
    private String code;

    @NotNull(message = "Book Id cannot be blank")
    private Integer bookId;

    @NotNull(message = "Member must be selected")
    private Integer memberId;

    @NotNull(message = "Days must be selected")
    private Integer totalDays;

}
