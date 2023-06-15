package com.brs.bookrentalsystem.dto.member;

import com.brs.bookrentalsystem.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberRequest {

    private Integer id;

    @NotBlank(message = "Email field cannot be blank")
    private String email;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Mobile number should not be blank")
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
