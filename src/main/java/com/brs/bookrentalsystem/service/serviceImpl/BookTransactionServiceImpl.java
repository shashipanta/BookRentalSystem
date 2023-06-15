package com.brs.bookrentalsystem.service.serviceImpl;

import com.brs.bookrentalsystem.dto.book.BookResponse;
import com.brs.bookrentalsystem.dto.transaction.BookTransactionRequest;
import com.brs.bookrentalsystem.dto.transaction.BookTransactionResponse;
import com.brs.bookrentalsystem.enums.RentStatus;
import com.brs.bookrentalsystem.model.Book;
import com.brs.bookrentalsystem.model.BookTransaction;
import com.brs.bookrentalsystem.model.Member;
import com.brs.bookrentalsystem.repo.BookRepo;
import com.brs.bookrentalsystem.repo.BookTransactionRepo;
import com.brs.bookrentalsystem.service.BookService;
import com.brs.bookrentalsystem.service.BookTransactionService;
import com.brs.bookrentalsystem.service.MemberService;
import com.brs.bookrentalsystem.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookTransactionServiceImpl  implements BookTransactionService {


    private final BookService bookService;
    private final MemberService memberService;
    private final BookTransactionRepo bookTransactionRepo;

    private final DateUtil dateUtil;

    @Override
    public BookTransactionResponse createBookTransaction(BookTransactionRequest request) {

        BookTransaction bookTransaction = toBookTransaction(request);

        bookTransaction = bookTransactionRepo.save(bookTransaction);

        return toBookTransactionResponse(bookTransaction);

    }


    private BookTransaction toBookTransaction(BookTransactionRequest request){

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

    private BookTransactionResponse toBookTransactionResponse(BookTransaction bookTransaction){
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
