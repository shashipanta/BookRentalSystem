package com.brs.bookrentalsystem.controller.thymeleaf.book;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.author.AuthorResponse;
import com.brs.bookrentalsystem.dto.book.BookRequest;
import com.brs.bookrentalsystem.dto.book.BookResponse;
import com.brs.bookrentalsystem.dto.category.CategoryResponse;
import com.brs.bookrentalsystem.service.AuthorService;
import com.brs.bookrentalsystem.service.BookService;
import com.brs.bookrentalsystem.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/brs/admin/book")
public class BookViewController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    @GetMapping(value = "/")
    public String openBookRegistrationPage(Model model) {

        List<AuthorResponse> registeredAuthors = authorService.getRegisteredAuthors();
        List<CategoryResponse> registeredCategories = categoryService.getAllCategories();

        model.addAttribute("authorList", registeredAuthors);
        model.addAttribute("categoryList", registeredCategories);

        if(!model.containsAttribute("bookRequest")){
            model.addAttribute("bookRequest", new BookRequest());
        }
        return "/book/book-page";
    }


    @RequestMapping(value = "/save")
    public String registerPassedBook(
            @Valid @ModelAttribute("bookRequest") BookRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        // check if errors
        if(bindingResult.hasErrors()){
            model.addAttribute("categoryList", categoryService.getAllCategories());
            model.addAttribute("authorList", authorService.getRegisteredAuthors());
            return "book/book-page";
        }

        BookResponse bookResponse = bookService.saveBook(request);

        Message createdMessage = new Message("CREATED", "Book saved successfully");
        model.addAttribute("message", createdMessage);
        model.addAttribute("messageType", "create");

        return "redirect:/brs/admin/book/";
    }
}
