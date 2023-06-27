package com.brs.bookrentalsystem.dto.book;


import lombok.Data;

@Data
public class BookMessage {

    private String bookName;

    private String rentedQuantity;
}
