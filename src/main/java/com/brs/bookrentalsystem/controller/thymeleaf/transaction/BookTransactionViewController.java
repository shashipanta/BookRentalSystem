package com.brs.bookrentalsystem.controller.thymeleaf.transaction;


import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.book.BookMessage;
import com.brs.bookrentalsystem.dto.book.BookResponse;
import com.brs.bookrentalsystem.dto.transaction.BookReturnRequest;
import com.brs.bookrentalsystem.dto.member.MemberResponse;
import com.brs.bookrentalsystem.dto.transaction.BookTransactionRequest;
import com.brs.bookrentalsystem.dto.transaction.BookTransactionResponse;
import com.brs.bookrentalsystem.dto.transaction.TransactionFilterRequest;
import com.brs.bookrentalsystem.dto.transaction.TransactionResponse;
import com.brs.bookrentalsystem.service.BookService;
import com.brs.bookrentalsystem.service.BookTransactionService;
import com.brs.bookrentalsystem.service.CategoryService;
import com.brs.bookrentalsystem.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brs/library/book")
public class BookTransactionViewController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final MemberService memberService;

    private final BookTransactionService bookTransactionService;

    @GetMapping("/")
    public String getTransactionViewPage(Model model){
        List<BookTransactionResponse> allTransactions = bookTransactionService.getAllTransactions();
        model.addAttribute("transactionList", allTransactions);
        return "/bookTransaction/transactions-view-page";

    }


    @GetMapping("/rent")
    public String openRentBookPage(Model model) {

        List<BookResponse> savedBooks = bookService.getBooksAvailableOnStock();
        List<MemberResponse> registeredMembers = memberService.getRegisteredMembers();

        model.addAttribute("transactionRequest", new BookTransactionRequest());
        model.addAttribute("bookList", savedBooks);
        model.addAttribute("memberList", registeredMembers);

        return "/bookTransaction/rentbook-page";
    }

    @GetMapping(value = "/rent/submit")
    public String rentBook(
            @Valid @ModelAttribute("transactionRequest") BookTransactionRequest request,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            List<BookResponse> savedBooks = bookService.getBooksAvailableOnStock();
            List<MemberResponse> registeredMembers = memberService.getRegisteredMembers();

//            model.addAttribute("transactionRequest", new BookTransactionRequest());
            model.addAttribute("bookList", savedBooks);
            model.addAttribute("memberList", registeredMembers);

            return "/bookTransaction/rentbook-page";
        }

        BookTransactionResponse bookTransaction = bookTransactionService.rentBook(request);
        model.addAttribute("transactionResponse", bookTransaction);
        model.addAttribute("message", bookTransaction);
        model.addAttribute("type", "create");

        return "redirect:/brs/library/book/rent";
    }


    // RETURN RENTED BOOKS
    @GetMapping(value = "/return")
    public String openBookReturnPage(Model model) {
        List<BookTransactionResponse> allTransactions = bookTransactionService.getNotReturnedBookTransactions();

        model.addAttribute("bookReturnRequest", new BookReturnRequest());

        return "/bookTransaction/returnbook-page";
    }


    // http://localhost:8080/brs/library/book/return/verify?code={codepassed}
    @GetMapping(value = "/return/verify")
    @ResponseBody
    public ResponseEntity<TransactionResponse> getTransactionDetails(@RequestParam("code") String code){
        TransactionResponse response = bookTransactionService.getTransaction(code);

        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/return/submit")
    public String returnBookByCode(
            @Valid @ModelAttribute("bookReturnRequest") BookReturnRequest request,
            Model model
    ) {
        Message bookTransactionResponse = bookTransactionService.returnBook(request);

        model.addAttribute("message", bookTransactionResponse);

        return "/bookTransaction/returnbook-page";
    }

    @GetMapping(value = "/return/filter")
    @ResponseBody
    public List<String> filterBookTransactionByTransactionCode(
            @RequestParam("filterValue") String filterRequest
    ) {
        TransactionFilterRequest transactionFilterRequest = new TransactionFilterRequest(filterRequest);
        List<String> codeList = bookTransactionService.filterTransactionByTransactionCode(transactionFilterRequest);
        return codeList;
    }


    // generate transaction code
    @GetMapping("/generate-code")
    @ResponseBody
    public String generateTransactionCode(@RequestParam("bookName") String bookName){
        String s = bookTransactionService.generateTransactionCode(bookName);
        return s;
    }

    // get book return date
    @GetMapping("/get-return-date")
    @ResponseBody
    public String generateBookReturnDate(@RequestParam("totalDays") Integer totalDays){
        return bookTransactionService.getBookReturnDate(totalDays);
    }


    // top 5 rented books
    @GetMapping("/top-rented")
    @ResponseBody
    public ResponseEntity<List<BookMessage>> getTopRentedBooks(){

        List<BookMessage> topRentedBooks = bookTransactionService.getTopRatedBooks();

        return ResponseEntity.ok(topRentedBooks);

    }

}
