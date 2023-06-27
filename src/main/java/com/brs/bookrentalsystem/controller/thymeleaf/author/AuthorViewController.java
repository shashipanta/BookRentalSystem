package com.brs.bookrentalsystem.controller.thymeleaf.author;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.author.AuthorRequest;
import com.brs.bookrentalsystem.dto.author.AuthorResponse;
import com.brs.bookrentalsystem.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//http://localhost:8080/brs/admin/author/;jsessionid=D1653963FF0BD16535DF1982126122D8

@Controller
@RequiredArgsConstructor
@RequestMapping("/brs/admin/author")
public class AuthorViewController {

    private final AuthorService authorService;

    @GetMapping(path = {"/", ""})
    public String openAuthorPage(Model model) {
        if (!model.containsAttribute("authorRequest")) {
            model.addAttribute("authorRequest", new AuthorRequest());
        }

        System.out.println(model.asMap());

        // get registered authors
        List<AuthorResponse> registeredAuthors = authorService.getRegisteredAuthors();
        model.addAttribute("authorList", registeredAuthors);

        return "/author/author-page";
    }

    @PostMapping("/save")
    public String saveNewAuthor(
            @Valid @ModelAttribute("authorRequest") AuthorRequest request,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            System.out.println("binding errors : " + bindingResult);
            model.addAttribute("authorRequest", request);
            model.addAttribute("authorList", authorService.getRegisteredAuthors());
//            ra.addFlashAttribute(bindingResult);

            return "/author/author-page";
        }

        if (request.getName().contains("shashi")) {
            bindingResult.rejectValue("name", "author.name", "Shashi cannot be added as author");
            model.addAttribute("authorRequest", request);
            return "/author/author-form-redirect";
        }

        AuthorResponse authorResponse = authorService.registerAuthor(request);
        Message createdMessage = new Message("CREATED", "Author saved successfully");
        model.addAttribute("message", createdMessage);
        model.addAttribute("messageType", "create");
        return "redirect:/brs/admin/author/";
    }


    @RequestMapping(value = "/{id}/edit")
    public String editAuthor(
            @PathVariable("id") Integer authorId,
            RedirectAttributes ra
    ) {
        AuthorResponse authorResponse = authorService.findAuthorById(authorId);

        ra.addFlashAttribute("authorRequest", authorResponse);

        return "redirect:/brs/admin/author/";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthorById(
            @PathVariable("id") Integer authorId,
            RedirectAttributes ra
    ) {
        Message message = authorService.deleteAuthorById(authorId);
        ra.addFlashAttribute("message", message);
        ra.addFlashAttribute("messageType", "delete");

        return "redirect:/brs/admin/author/";
    }
}
