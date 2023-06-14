package com.brs.bookrentalsystem.dto.category;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {

    private Short id;

    private String name;

    private String description;
}
