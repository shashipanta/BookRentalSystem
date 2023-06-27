package com.brs.bookrentalsystem.dto.member;

import com.brs.bookrentalsystem.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {

    private Integer id;

    @NotEmpty(message = "Email field cannot be blank")
    private String email;

    @NotBlank(message = "Name cannot be blank")
    private String name;

//    @NotNull(message = "Mobile number should not be blank")
//    @Size(min = 10, max = 10)
    @Digits(fraction = 0, integer = 10, message = "Mobile number should be 10 digits long")
    private String mobileNumber;

    private String address;

    public static Member toMember(MemberRequest request){

        Member member = new Member();
        member.setId(request.getId());
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setAddress(request.getAddress());
        member.setMobileNumber(request.getMobileNumber());

        return member;
    }

}
