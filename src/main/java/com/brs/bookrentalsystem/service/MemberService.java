package com.brs.bookrentalsystem.service;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.member.MemberRequest;
import com.brs.bookrentalsystem.dto.member.MemberResponse;
import com.brs.bookrentalsystem.model.Member;

import java.util.List;

public interface MemberService {

    MemberResponse registerNewMember(MemberRequest request);

    MemberResponse updateMember(MemberRequest request, Integer memberId);

    MemberResponse getMemberById(Integer memberId);
    Member getMemberEntityById(Integer memberId);

    Message deleteMember(Integer memberId);

    List<MemberResponse> getRegisteredMembers();
}
