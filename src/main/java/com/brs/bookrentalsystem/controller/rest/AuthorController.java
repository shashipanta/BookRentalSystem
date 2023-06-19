package com.brs.bookrentalsystem.controller.rest;

import com.brs.bookrentalsystem.dto.author.AuthorRequest;
import com.brs.bookrentalsystem.dto.author.AuthorResponse;
import com.brs.bookrentalsystem.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;


    @PostMapping(value = "/")
    public AuthorResponse registerNewAuthor(@RequestBody @Valid AuthorRequest authorRequest){
        return authorService.registerAuthor(authorRequest);
    }

    @GetMapping(value = "/{id}")
    public AuthorResponse getAuthorById(@PathVariable("id") Integer authorId){
        return authorService.findAuthorById(authorId);
    }

}
