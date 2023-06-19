package com.brs.bookrentalsystem.controller.thymeleaf.author;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.author.AuthorRequest;
import com.brs.bookrentalsystem.dto.author.AuthorResponse;
import com.brs.bookrentalsystem.model.Author;
import com.brs.bookrentalsystem.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brs/admin/author")
public class AuthorViewController {

    private final AuthorService authorService;

    @GetMapping(value = "/")
    public String openAuthorPage(Model model) {
        if (!model.containsAttribute("authorRequest")) {
            model.addAttribute("authorRequest", new AuthorRequest());
        }
        // get registered authors
        List<AuthorResponse> registeredAuthors = authorService.getRegisteredAuthors();
        model.addAttribute("authorList", registeredAuthors);

        return "/author/author-page";
    }

    @GetMapping("/save")
    public String saveNewAuthor(
           @Valid @ModelAttribute("AuthorRequest") AuthorRequest request,
           RedirectAttributes ra
    ) {
        AuthorResponse authorResponse = authorService.registerAuthor(request);
        Message createdMessage = new Message("CREATED", "Author saved successfully");
        ra.addFlashAttribute("message", createdMessage);
        ra.addFlashAttribute("messageType", "create");
        return "redirect:/brs/admin/author/";
    }


    @RequestMapping(value = "/{id}/edit")
    public String editAuthor(
            @PathVariable("id") Integer authorId,
            RedirectAttributes ra
    ){
        AuthorResponse authorResponse = authorService.findAuthorById(authorId);

        ra.addFlashAttribute("authorRequest", authorResponse);


        return "redirect:/brs/admin/author/";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthorById(
            @PathVariable("id") Integer authorId,
            RedirectAttributes ra
    ){
        Message message = authorService.deleteAuthorById(authorId);
        ra.addFlashAttribute("message",message);
        ra.addFlashAttribute("messageType", "delete");

        return "redirect:/brs/admin/author/";
    }
}
