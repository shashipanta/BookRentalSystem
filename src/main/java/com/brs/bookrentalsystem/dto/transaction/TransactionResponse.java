package com.brs.bookrentalsystem.dto.transaction;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {

    private Long id;
    private String code;
    private String rentedDate;
    private String expiryDate;
    private String memberName;
    private String bookName;
}
