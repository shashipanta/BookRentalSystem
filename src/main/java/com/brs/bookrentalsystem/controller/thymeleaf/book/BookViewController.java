package com.brs.bookrentalsystem.controller.thymeleaf.book;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.author.AuthorResponse;
import com.brs.bookrentalsystem.dto.book.BookRequest;
import com.brs.bookrentalsystem.dto.book.BookResponse;
import com.brs.bookrentalsystem.dto.book.BookUpdateRequest;
import com.brs.bookrentalsystem.dto.book.ExcelBookUploadRequest;
import com.brs.bookrentalsystem.dto.category.CategoryResponse;
import com.brs.bookrentalsystem.excel.BookImportHelper;
import com.brs.bookrentalsystem.service.AuthorService;
import com.brs.bookrentalsystem.service.BookService;
import com.brs.bookrentalsystem.service.BookTransactionService;
import com.brs.bookrentalsystem.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/brs/admin/book")
public class BookViewController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookTransactionService bookTransactionService;
    private final BookImportHelper bookImportHelper;

    @GetMapping(value = {"/", ""})
    public String openBookRegistrationPage(Model model) {

        List<AuthorResponse> registeredAuthors = authorService.getRegisteredAuthors();
        List<CategoryResponse> registeredCategories = categoryService.getAllCategories();

        model.addAttribute("authorList", registeredAuthors);
        model.addAttribute("categoryList", registeredCategories);

        if (!model.containsAttribute("bookRequest")) {
            model.addAttribute("bookRequest", new BookRequest());
        }

//        BookRequest bookRequest =(BookRequest) model.getAttribute("bookRequest");
//        assert bookRequest != null;
//        if(bookRequest.getId()!= null){
//             model.addAttribute("bookRequest", bookRequest);
//        }
        return "/book/book-page";
    }


    @RequestMapping(value = "/save")
    public String registerPassedBook(
            @Valid @ModelAttribute("bookRequest") BookRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        // check if errors
        if (bindingResult.hasErrors()) {
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


    @GetMapping(value = "/inventory")
    public String openBookInventory(Model model) {

//        List<BookResponse> savedBooks = bookService.getSavedBooks();
        List<BookResponse> allBooks = bookService.getAllBooks();

        model.addAttribute("savedBooks", allBooks);
        model.addAttribute("fileUpload", new ExcelBookUploadRequest());

        return "/book/book-list-page";
    }

    // http://localhost:8080/brs/book/admin/{id}/edit
    @RequestMapping(value = "/{id}/edit")
    public String openBookEditPage(
            @PathVariable(value = "id") Integer bookId,
            Model model
    ) {
        // populate category and author field
        List<AuthorResponse> registeredAuthors = authorService.getRegisteredAuthors();
        List<CategoryResponse> registeredCategories = categoryService.getAllCategories();

        model.addAttribute("authorList", registeredAuthors);
        model.addAttribute("categoryList", registeredCategories);

        // bypass multipart validation
        // BookRequest bookById = bookService.getBookRequestById(bookId);

        // get bookUpdateRequest dto for update operation
        BookUpdateRequest bookUpdateRequest = bookService.getBookUpdateRequest(bookId);

        model.addAttribute("bookUpdateRequest", bookUpdateRequest);
        String fileName = bookUpdateRequest.getPhotoPath().substring(bookUpdateRequest.getPhotoPath().lastIndexOf("/") + 1);
        model.addAttribute("fileName", fileName);

        return "/book/book-update-page";
    }

    @RequestMapping(value = "/update")
    public String updateExistingBook(
            @Valid @ModelAttribute("bookUpdateRequest") BookUpdateRequest updateRequest,
            BindingResult bindingResult,
            RedirectAttributes ra,
            Model model
    ) {

        // check if errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryService.getAllCategories());
            model.addAttribute("authorList", authorService.getRegisteredAuthors());
            return "book/book-update-page";
        }

        bookService.updateBook(updateRequest);

//        BookResponse bookResponse = bookService.saveBook(updateRequest);

//        Message createdMessage = new Message("U100", "Book updated successfully");
//        ra.addAttribute("message", createdMessage);
//        ra.addAttribute("messageType", "create");

        return "redirect:/brs/admin/book/inventory";
    }

    // view single book
    @GetMapping(value = "/view/{id}")
    public String viewSingleBook(@PathVariable("id") Integer bookId, Model model) {
        BookResponse bookById = bookService.getBookById(bookId);
        model.addAttribute("bookTitle", bookById.getBookName());
        model.addAttribute("bookResponse", bookById);
        List<Integer> ratings = new ArrayList<>();
        // for rating : temporary solution
        for (int i = 0; i < bookById.getRating(); i++) {
            ratings.add(i);
        }
        model.addAttribute("ratings", ratings);

        return "/book/single-book-page";
    }

    // http://localhost:8080/brs/book/admin/{id}/delete
    @GetMapping(value = "/delete/{id}")
    public String deleteBook(RedirectAttributes ra, @PathVariable("id") Integer bookId) {
        Boolean bookRented = bookTransactionService.isBookRented(bookId);
        Message message = new Message();
        if (bookRented) {
            message.setCode("DEL-BOOK");
            message.setMessage("Book is rented and not returned yet");
        } else {
            message = bookService.deleteBookById(bookId);
        }
        ra.addFlashAttribute("message", message);

        return "redirect:/brs/admin/book/inventory";
    }

    @GetMapping(value = "/revive-deleted/{id}")
    public String reviveDeletedBook(RedirectAttributes ra, @PathVariable("id") Integer bookId) {

        Message message = bookService.reviveDeletedBookById(bookId);

        ra.addFlashAttribute("message", message);

        return "redirect:/brs/admin/book/inventory";
    }

    // import books from excel
    @PostMapping(value = "/import")
    public String importBooksFromExcel(
            @Valid @ModelAttribute("fileUpload") ExcelBookUploadRequest request,
            RedirectAttributes ra
    ) throws IOException {

        Message message = new Message();
        if (BookImportHelper.isUploadedFileValid(request.getExcelFile())) {
            // save
            int importedBooks = bookService.importBooks(request.getExcelFile());
            message.setMessage(importedBooks + " books imported successfully.");
            message.setCode("IMPORTED");
        } else {
            message.setMessage("Invalid Excel format. Please check your file and try again.");
            message.setMessage("FAILLED");
        }
        ra.addFlashAttribute("message", message);

        return "redirect:/brs/admin/book/inventory";
    }

}
