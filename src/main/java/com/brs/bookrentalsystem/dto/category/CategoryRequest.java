package com.brs.bookrentalsystem.dto.category;

import com.brs.bookrentalsystem.model.audit.Auditable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest extends Auditable {

    private Short id;

    @NotBlank(message = "Category name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z].*[\\s\\.]*$", message = "Category name is not valid")
    private String name;

    @Length(min = 20, max = 50, message = "Description must be within range 20 - 50")
    private String description;

}
