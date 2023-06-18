package com.brs.bookrentalsystem.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    private Short id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    @Length(min = 20, max = 500, message = "Message must be within range 20 - 50")
    private String description;
}
