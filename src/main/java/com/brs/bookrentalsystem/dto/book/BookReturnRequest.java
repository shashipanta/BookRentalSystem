package com.brs.bookrentalsystem.dto.book;

import lombok.Data;

@Data
public class BookReturnRequest {

    private String code;

    private Integer memberId;

    private String dateOfReturn;

}
