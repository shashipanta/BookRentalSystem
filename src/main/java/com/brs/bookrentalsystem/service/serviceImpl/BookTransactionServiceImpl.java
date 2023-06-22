package com.brs.bookrentalsystem.service.serviceImpl;

import com.brs.bookrentalsystem.dto.transaction.BookTransactionRequest;
import com.brs.bookrentalsystem.dto.transaction.BookTransactionResponse;
import com.brs.bookrentalsystem.dto.transaction.TransactionFilterRequest;
import com.brs.bookrentalsystem.enums.RentStatus;
import com.brs.bookrentalsystem.model.Book;
import com.brs.bookrentalsystem.model.BookTransaction;
import com.brs.bookrentalsystem.model.Member;
import com.brs.bookrentalsystem.repo.BookTransactionRepo;
import com.brs.bookrentalsystem.service.BookService;
import com.brs.bookrentalsystem.service.BookTransactionService;
import com.brs.bookrentalsystem.service.MemberService;
import com.brs.bookrentalsystem.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookTransactionServiceImpl implements BookTransactionService {


    private final BookService bookService;
    private final MemberService memberService;
    private final BookTransactionRepo bookTransactionRepo;

    private final DateUtil dateUtil;

    @Override
    public BookTransactionResponse rentBook(BookTransactionRequest request) {

        BookTransaction bookTransaction = toBookTransaction(request);

        BookTransaction bookTransactionByCode = bookTransactionRepo.findBookTransactionByCode(request.getCode());

        bookTransaction = bookTransactionRepo.save(bookTransaction);

        // stock maintenance
        bookService.updateStock(request.getBookId(), -1);

        return toBookTransactionResponse(bookTransaction);
    }

    // when returning
    public BookTransactionResponse returnBook(BookTransactionRequest request){

        BookTransaction bookTransaction = toBookTransaction(request);

        // update status to RETURNED
        bookTransaction.setRentStatus(RentStatus.RETURNED);

        bookTransaction = bookTransactionRepo.save(bookTransaction);

        // update stock info
        bookService.updateStock(request.getBookId(), 1);

        // TODO :: penalty if return date has been exceeded

        return toBookTransactionResponse(bookTransaction);

    }

    @Override
    public List<BookTransactionResponse> getAllTransactions() {

        List<BookTransaction> all = bookTransactionRepo.findAll();
        return all.stream()
                .map(this::toBookTransactionResponse)
                .collect(Collectors.toList());
    }

    // get book transactions that has not been returned yet
    @Override
    public List<BookTransactionResponse> getNotReturnedBookTransactions() {
        List<BookTransaction> rentedBookTransactions = bookTransactionRepo.findBookTransactionByRentStatus(RentStatus.RENTED);

        return rentedBookTransactions.stream()
                .map(this::toBookTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookTransactionResponse> filterTransactionByTransactionCode(TransactionFilterRequest filterRequest) {
        List<BookTransaction> bookTransactions = bookTransactionRepo.filterByTransactionCode(filterRequest.getCode());
        return bookTransactions.stream()
                .map(this::toBookTransactionResponse)
                .collect(Collectors.toList());
    }

    private BookTransaction toBookTransaction(BookTransactionRequest request) {

        BookTransaction bookTransaction = new BookTransaction();

        Book book = bookService.getBookEntityById(request.getBookId());
        Member member = memberService.getMemberEntityById(request.getMemberId());

        bookTransaction.setTransactionId(request.getTransactionId());
        bookTransaction.setCode(request.getCode());
        bookTransaction.setBook(book);
        bookTransaction.setRentStatus(RentStatus.RENTED);
        bookTransaction.setMember(member);
        bookTransaction.setRentTo(dateUtil.addDayToDate(request.getTotalDays()));

        return bookTransaction;
    }

    private BookTransactionResponse toBookTransactionResponse(BookTransaction bookTransaction) {

        return BookTransactionResponse.builder()
                .transactionId(bookTransaction.getTransactionId())
                .code(bookTransaction.getCode())
                .rentFrom(bookTransaction.getRentFrom())
                .rentTo(bookTransaction.getRentTo())
                .book(bookTransaction.getBook())
                .member(bookTransaction.getMember())
                .rentStatus(bookTransaction.getRentStatus())
                .build();
    }
}
