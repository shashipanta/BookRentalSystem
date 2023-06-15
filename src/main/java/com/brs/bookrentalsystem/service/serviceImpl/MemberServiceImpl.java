package com.brs.bookrentalsystem.service.serviceImpl;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.member.MemberRequest;
import com.brs.bookrentalsystem.dto.member.MemberResponse;
import com.brs.bookrentalsystem.model.Member;
import com.brs.bookrentalsystem.repo.MemberRepo;
import com.brs.bookrentalsystem.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberRepo memberRepo;

    @Override
    public MemberResponse registerNewMember(MemberRequest request) {
        Member member = MemberRequest.toMember(request);
        member = memberRepo.save(member);
        return MemberResponse.toMemberResponse(member);
    }

    @Override
    public MemberResponse updateMember(MemberRequest request, Integer memberId) {
        return null;
    }

    @Override
    public MemberResponse getMemberById(Integer memberId) {
        return null;
    }

    // handle exception
    @Override
    public Member getMemberEntityById(Integer memberId) {
        return memberRepo.findById(memberId).orElseThrow();
    }

    @Override
    public Message deleteMember(Integer memberId) {
        return null;
    }

    @Override
    public List<MemberResponse> getRegisteredMembers() {
        return null;
    }
}
