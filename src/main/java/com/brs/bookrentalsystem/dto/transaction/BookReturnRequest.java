package com.brs.bookrentalsystem.dto.transaction;

import lombok.Data;

@Data
public class BookReturnRequest {

    private Long id;
    private String code;

}
