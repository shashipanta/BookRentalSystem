package com.brs.bookrentalsystem.dto.transaction;


import com.brs.bookrentalsystem.enums.RentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookTransactionResponse {

    private Long transactionId;

    private String code;

    private LocalDate rentFrom;

    private LocalDate rentTo;

    private RentStatus rentStatus;

    private String bookName;

    private String expiryDate;

    private String memberName;
}
