package com.brs.bookrentalsystem.dto.author;

import jakarta.validation.constraints.Email;
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
public class AuthorRequest {

    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    @Length(max = 100, message = "Name's length cannot exceed length of 100")
    private String name;

    @Email(message = "Email format is wrong")
    @Length(max = 150, message = "Email length exceeded : 150")
    private String email;

    @NotBlank(message = "Mobile number cannot be blank")
    @Length(min = 10, max = 15, message = "Phone number cannot exceed 10")
    private String mobileNumber;

}
