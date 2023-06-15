package com.brs.bookrentalsystem.dto.member;


import com.brs.bookrentalsystem.model.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResponse {

    private Integer id;

    private String email;

    private String name;

    private String mobileNumber;

    private String address;

    public static MemberResponse toMemberResponse(Member member){
        return MemberResponse
                .builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .mobileNumber(member.getMobileNumber())
                .address(member.getAddress())
                .build();
    }

}
