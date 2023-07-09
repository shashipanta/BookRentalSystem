package com.brs.bookrentalsystem.dto.transaction;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TransactionExcelResponse {

    private Long transactionId;

    private String transactionCode;

    private String rentFrom;

    private String rentTo;

    private String rentStatus;

    // book
    private String bookName;

    private String bookCoverImage;

    private String isbn;

    private Integer stockCount;

    private String publishedDate;

    private String categoryName;

    // member
    private String memberName;

    private String memberMobileNumber;
}
