package com.brs.bookrentalsystem.dto.book;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookMessage {

    private String bookName;

    private String rentedQuantity;

    private double rating;

}
