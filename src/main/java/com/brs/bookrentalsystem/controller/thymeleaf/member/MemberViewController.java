package com.brs.bookrentalsystem.controller.thymeleaf.member;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.category.CategoryRequest;
import com.brs.bookrentalsystem.dto.category.CategoryResponse;
import com.brs.bookrentalsystem.dto.member.MemberRequest;
import com.brs.bookrentalsystem.dto.member.MemberResponse;
import com.brs.bookrentalsystem.model.Category;
import com.brs.bookrentalsystem.model.Member;
import com.brs.bookrentalsystem.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/brs/admin/member")
public class MemberViewController {

    private final MemberService memberService;


    @GetMapping("/")
    public String openMemberPage(Model model) {
        if(!model.containsAttribute("memberRequest")){
            model.addAttribute("memberRequest", new MemberRequest());

        }

        // else edit response

        List<MemberResponse> allMembers = memberService.getRegisteredMembers();

        model.addAttribute("memberList", allMembers);

        return "/member/member-page";
    }

    @GetMapping(value = "/save")
    public String registerNewMember(
            @Valid @ModelAttribute("memberRequest") MemberRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding errors" + bindingResult.getFieldErrors());
            List<MemberResponse> allMembers = memberService.getRegisteredMembers();
            model.addAttribute("memberList", allMembers);
            model.addAttribute("memberRequest", request);
            return "/member/member-page";
        }

        MemberResponse memberResponse = memberService.registerNewMember(request);

//        if(request.getId() == null){
//            ra.addFlashAttribute("message", new Message("CREATED", "Member created successfully"));
//            ra.addFlashAttribute("messageType", "create");
//        } else {
//            ra.addFlashAttribute("message", new Message("UPDATED", "Member updated successfully"));
//            ra.addFlashAttribute("messageType", "update");
//        }

        return "redirect:/brs/admin/member/";
    }

    @RequestMapping(value = "/{id}/edit")
    public String editMember(
            @PathVariable("id") Integer memberId,
            RedirectAttributes ra
    ) {

        Member memberEntityById = memberService.getMemberEntityById(memberId);

        ra.addFlashAttribute("memberRequest", memberEntityById);

        return "redirect:/brs/admin/member/";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteMember(
            @PathVariable("id") Integer memberId,
            RedirectAttributes ra
    ) {

        Message message = memberService.deleteMember(memberId);

        ra.addFlashAttribute("message", message);

        ra.addFlashAttribute("messageType", "delete");

        return "redirect:/brs/admin/member/";
    }
}
