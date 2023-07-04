package com.brs.bookrentalsystem.dto.transaction;


import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FilterTransaction {

    private String from;

    private String to;

}
