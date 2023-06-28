package com.brs.bookrentalsystem.dto;


import com.brs.bookrentalsystem.error.codes.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String code;
    private String message;
}
