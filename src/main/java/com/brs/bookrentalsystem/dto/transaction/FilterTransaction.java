package com.brs.bookrentalsystem.dto.transaction;


import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FilterTransaction {

    @NotBlank(message = "From value should be provided")
    private String from;

    private String to;

}
