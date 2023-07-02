package com.brs.bookrentalsystem.auth.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailSignature {

    private String from;
    private String to;
    private String subject;
    private String body;
}
