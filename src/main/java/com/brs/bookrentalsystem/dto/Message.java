package com.brs.bookrentalsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class Message {

    private String code;
    private String message;
}
