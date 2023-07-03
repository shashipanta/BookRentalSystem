package com.brs.bookrentalsystem.dto.transaction;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilterTransaction {

    @NotNull(message = "From date should be selected")
    private String from;

    private String to;
}
