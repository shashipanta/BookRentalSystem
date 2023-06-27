package com.brs.bookrentalsystem.excel;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;


@JsonPropertyOrder({})
public class BookTransactionFormat {

    // format for writing excel

    private Long transactionId;

    private String transactionCode;

    private LocalDate rentFrom;

    private LocalDate rentTo;

    private String rentStatus;

    // book
    private String bookName;

    private String isbn;

    private Integer stockCount;

    private LocalDate publishedDate;

    private String categoryName;

    // member
    private String memberName;

    private String memberMobileNumber;
}
