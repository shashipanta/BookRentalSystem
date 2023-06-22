package com.brs.bookrentalsystem.dto.transaction;


import com.brs.bookrentalsystem.enums.RentStatus;
import com.brs.bookrentalsystem.model.Book;
import com.brs.bookrentalsystem.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private Book book;

    private String expiryDate;

    private Member member;
}
