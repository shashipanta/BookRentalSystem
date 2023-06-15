package com.brs.bookrentalsystem.controller.rest;

import com.brs.bookrentalsystem.dto.member.MemberRequest;
import com.brs.bookrentalsystem.dto.member.MemberResponse;
import com.brs.bookrentalsystem.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
public class MemberController {


    private final MemberService memberService;


    @PostMapping(value = "/")
    public MemberResponse registerNewMember(@RequestBody @Valid MemberRequest request){
        return memberService.registerNewMember(request);
    }


}
