package com.brs.bookrentalsystem.controller.thymeleaf.transaction;


import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.book.BookMessage;
import com.brs.bookrentalsystem.dto.book.BookResponse;
import com.brs.bookrentalsystem.dto.transaction.*;
import com.brs.bookrentalsystem.dto.member.MemberResponse;
import com.brs.bookrentalsystem.error.ErrorResponse;
import com.brs.bookrentalsystem.service.BookService;
import com.brs.bookrentalsystem.service.BookTransactionService;
import com.brs.bookrentalsystem.service.CategoryService;
import com.brs.bookrentalsystem.service.MemberService;
import com.brs.bookrentalsystem.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brs/library/book")
public class BookTransactionViewController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final DateUtil dateUtil;

    private final BookTransactionService bookTransactionService;

    @GetMapping("/")
    public String getTransactionViewPage(Model model) {
//        List<BookTransactionResponse> allTransactions = bookTransactionService.getAllTransactions();
//        model.addAttribute("transactionList", allTransactions);
//        return "/bookTransaction/transactions-view-page";

        model.addAttribute("filterTransaction", new FilterTransaction());

        return findPaginated(1, model, null, null);

    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model,
                                @RequestParam(required = false) String fromDate,
                                @RequestParam(required = false) String toDate
    ) {
        int pageSize = 5;
        Page<BookTransactionResponse> page;
        FilterTransaction filterTransaction = new FilterTransaction();

        if(fromDate != null){
            toDate = (toDate == null)? dateUtil.dateToString(LocalDate.now()) : toDate;
            filterTransaction.setFrom(fromDate);
            filterTransaction.setTo(toDate);
            page = bookTransactionService.getPaginatedAndFilteredTransaction(filterTransaction, pageNo);
        } else {
            page = bookTransactionService.getPaginatedTransaction(pageNo, pageSize);
        }

        List<BookTransactionResponse> transactionList = page.getContent();

        if(!model.containsAttribute("transactionList")){
            model.addAttribute("transactionList", transactionList);
        }

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        if (!model.containsAttribute("filterTransaction")) {
            model.addAttribute("filterTransaction", filterTransaction);
        } else {
            filterTransaction.setTo(toDate);
            filterTransaction.setFrom(fromDate);
            model.addAttribute("filterTransaction", filterTransaction);
        }

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

    @PostMapping(value = "/rent/submit")
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
    public ResponseEntity<TransactionResponse> getTransactionDetails(@RequestParam("code") String code) {
        TransactionResponse response = bookTransactionService.getTransaction(code);

        return ResponseEntity.ok(response);
    }


    @RequestMapping(value = "/return/submit")
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
    public String generateTransactionCode(@RequestParam("bookName") String bookName) {
        String s = bookTransactionService.generateTransactionCode(bookName);
        return s;
    }

    // get book return date
    @GetMapping("/get-return-date")
    @ResponseBody
    public String generateBookReturnDate(@RequestParam("totalDays") Integer totalDays) {
        return bookTransactionService.getBookReturnDate(totalDays);
    }


    // top 5 rented books
    @GetMapping("/top-rented")
    @ResponseBody
    public ResponseEntity<List<BookMessage>> getTopRentedBooks() {

        List<BookMessage> topRentedBooks = bookTransactionService.getTopRatedBooks();

        return ResponseEntity.ok(topRentedBooks);

    }

    @PostMapping("/filter/filter-by-date")
    public String filterTransactionByDateRange(
            @ModelAttribute("filterTransaction") FilterTransaction filterTransaction,
            Model model,
            RedirectAttributes ra
    ) {
//        if(bindingResult.hasErrors()){
//            System.out.println(bindingResult);
//            return "bookTransaction/transactions-view-page";
//        }

        if(filterTransaction.getTo().isEmpty() || filterTransaction.getTo().isBlank()){
           ra.addFlashAttribute("errorResponse", new ErrorResponse("FAILED", "Date cannot be blank"));
           return "redirect:/brs/library/book/";
        }



        Integer pageNumber = 1;

        Page<BookTransactionResponse> paginatedAndFilteredTransaction = bookTransactionService.getPaginatedAndFilteredTransaction(filterTransaction, pageNumber);
//        model.addAttribute("")

        List<BookTransactionResponse> content = paginatedAndFilteredTransaction.getContent();
        if (!model.containsAttribute("filterTransaction")) {
            model.addAttribute("filterTransaction", new FilterTransaction());
        }
        model.addAttribute("transactionList", content);

        return findPaginated(1, model, filterTransaction.getFrom(), filterTransaction.getTo());
    }

}
