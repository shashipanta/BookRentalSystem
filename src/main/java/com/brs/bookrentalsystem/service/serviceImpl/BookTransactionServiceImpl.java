package com.brs.bookrentalsystem.service.serviceImpl;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.book.BookMessage;
import com.brs.bookrentalsystem.dto.transaction.*;
import com.brs.bookrentalsystem.enums.RentStatus;
import com.brs.bookrentalsystem.model.Book;
import com.brs.bookrentalsystem.model.BookTransaction;
import com.brs.bookrentalsystem.model.Member;
import com.brs.bookrentalsystem.projections.BookTransactionProjection;
import com.brs.bookrentalsystem.repo.BookRepo;
import com.brs.bookrentalsystem.repo.BookTransactionRepo;
import com.brs.bookrentalsystem.repo.CategoryRepo;
import com.brs.bookrentalsystem.repo.MemberRepo;
import com.brs.bookrentalsystem.service.BookService;
import com.brs.bookrentalsystem.service.BookTransactionService;
import com.brs.bookrentalsystem.service.MemberService;
import com.brs.bookrentalsystem.util.DateUtil;
import com.brs.bookrentalsystem.util.RandomAlphaNumericString;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class BookTransactionServiceImpl implements BookTransactionService {


    private final BookService bookService;
    private final MemberService memberService;
    private final BookTransactionRepo bookTransactionRepo;
    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;
    private final MemberRepo memberRepo;
    private final RandomAlphaNumericString randomAlphaNumericString;

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

    // TODO:: Handle exception
    @Override
    public BookTransaction getTransactionById(Long transactionId) {
        return bookTransactionRepo.findById(transactionId).orElseThrow();
    }

    @Override
    public Message returnBook(BookReturnRequest request) {

        BookTransaction transactionById = getTransactionById(request.getId());
//        BookTransactionRequest build = BookTransactionRequest.builder()
//                .transactionId(request.getId())
//                .memberId(transactionById.getMember().getId())
//                .bookId(transactionById.getBook().getId())
//                .totalDays(null)
//                .code(request.getCode())
//                .build();

        BookTransaction bookTransaction = new BookTransaction();
        bookTransaction.setRentStatus(RentStatus.RETURNED);
        bookTransaction.setMember(transactionById.getMember());
        bookTransaction.setBook(transactionById.getBook());
        bookTransaction.setCode(transactionById.getCode());
        bookTransaction.setRentTo(transactionById.getRentTo());
        bookTransaction.setRentFrom(transactionById.getRentFrom());

//        transactionById.setRentStatus(RentStatus.RETURNED);
//        // create new transaction instead of
//        transactionById.setTransactionId(null);

        BookTransaction save = bookTransactionRepo.save(bookTransaction);

        // update stock
        bookService.updateStock(save.getBook().getId(), 1);

        // give return operation to

        return new Message("S100", "Book returned successfully");


    }

    // when returning
    public BookTransactionResponse returnBook(BookTransactionRequest request) {

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
        List<Book> allBooks = bookRepo.findAllBooks();
        List<BookTransactionProjection> allTransactions = bookTransactionRepo.getAllTransactions();
        return allTransactions.stream()
                .map(this::toBookTransactionResponse)
                .collect(Collectors.toList());
    }

    // get book transactions that has not been returned yet
    @Override
    public List<BookTransactionResponse> getNotReturnedBookTransactions() {
        List<BookTransaction> rentedBookTransactions = bookTransactionRepo.findBookTransactionByRentStatus(RentStatus.RENTED);

//        List<BookTransaction> distinct = rentedBookTransactions.stream()
//                .collect(Collectors.toList());
//        rentedBookTransactions.sort(Comparator.comparing(BookTransaction::getCode));
//
//        int count = 0;
//        List<BookTransaction> actualRented =
//        for(int i=0; i<rentedBookTransactions.size() -1; i++){
//            if(rentedBookTransactions.get(i).getCode().equals(rentedBookTransactions.get(i+1))){
//                count++
//            } else {
//                (count % 2) == 0 ?
//            }
//        }
        return rentedBookTransactions.stream()
                .map(this::toBookTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> filterTransactionByTransactionCode(TransactionFilterRequest filterRequest) {
        List<BookTransaction> bookTransactions = bookTransactionRepo.filterByTransactionCode(filterRequest.getCode());

        List<BookTransaction> filteredBookTransactions = bookTransactions.stream()
                .filter(b -> isBookRented(b.getBook().getId()))
                .toList();
        return filteredBookTransactions.stream()
                .map(BookTransaction::getCode)
                .collect(Collectors.toList());
    }

    // generate transaction code for client
    @Override
    public String generateTransactionCode(String bookName) {

        String paddingString = "BOOK-";
        String upperCase = bookName.replace(" ", "").substring(0, 5).toUpperCase();

        String randomString = randomAlphaNumericString.generateRandomString(5);
        return upperCase + randomString;
    }

    @Override
    public String getBookReturnDate(Integer days) {
        LocalDate expiryDate = dateUtil.addDayToDate(days);
        return expiryDate.toString();
    }

    @Override
    public TransactionResponse getTransaction(String transactionCode) {
        BookTransaction bookTransactionByCode = bookTransactionRepo.findBookTransactionByCode(transactionCode);
        return TransactionResponse.builder()
                .id(bookTransactionByCode.getTransactionId())
                .code(bookTransactionByCode.getCode())
                .memberName(bookTransactionByCode.getMember().getName())
                .bookName(bookTransactionByCode.getBook().getName())
                .rentedDate(dateUtil.dateToString(bookTransactionByCode.getRentFrom()))
                .expiryDate(dateUtil.dateToString(bookTransactionByCode.getRentTo()))
                .build();

    }

    @Override
    public List<BookMessage> getTopRatedBooks() {
        List<BookTransaction> bookTransactionByRentStatus = bookTransactionRepo.findBookTransactionByRentStatus(RentStatus.RENTED);
        bookTransactionByRentStatus.sort(Comparator.comparing(o -> o.getBook().getRating()));

        List<BookTransaction> list = bookTransactionByRentStatus.stream().distinct().toList();

        List<BookTransaction> limit = list.stream().distinct().limit(5).collect(Collectors.toList());
//        return limit.stream()
//                .map(bookTransaction -> new BookMessage(bookTransaction.getBook().getName(), ))
        return null;
    }

    @Override
    public List<TransactionExcelResponse> getAllTransactionsForExcel() {
        List<BookTransactionProjection> all = bookTransactionRepo.getAllTransactions();
        return all.stream()
                .map(this::toTransactionExcelResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isBookRented(Integer bookId) {
        List<BookTransactionProjection> allTransactions = bookTransactionRepo.getAllTransactions();
        long rentedCount = allTransactions.stream()
                .filter(p -> p.getRentStatus().equals("RENTED") && p.getBookId().equals(bookId))
                .count();
        long returnedCount = allTransactions.stream()
                .filter(p -> p.getRentStatus().equals("RETURNED") && p.getBookId().equals(bookId))
                .count();

        return returnedCount != rentedCount;
    }

    @Override
    public Page<BookTransactionResponse> getPaginatedTransaction(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<BookTransaction> allTransactions = this.bookTransactionRepo.findAll(pageable);

        List<BookTransactionResponse> collect = allTransactions.stream()
                .map(this::toBookTransactionResponse)
                .collect(Collectors.toList());

        Page<BookTransactionResponse> map = allTransactions.map(this::toBookTransactionResponse);

        return map;
    }

    private TransactionExcelResponse toTransactionExcelResponse(BookTransaction bookTransaction) {
        Book book = bookTransaction.getBook();
        Member member = bookTransaction.getMember();
        String categoryName = bookTransaction.getBook().getCategory().getName();
        String publishedDate = dateUtil.dateToString(book.getPublishedDate());
        String rentedDate = dateUtil.dateToString(bookTransaction.getRentFrom());
        String expiryDate = dateUtil.dateToString(bookTransaction.getRentTo());
        return TransactionExcelResponse.builder()
                .transactionId(bookTransaction.getTransactionId())
                .transactionCode(bookTransaction.getCode())
                .bookName(book.getName())
                .publishedDate(publishedDate)
                .isbn(book.getIsbn())
                .categoryName(categoryName)
                .stockCount(book.getStockCount())
                .rentFrom(rentedDate)
                .rentTo(expiryDate)
                .rentStatus(bookTransaction.getRentStatus().name())
                .memberName(member.getName())
                .memberMobileNumber(member.getMobileNumber())
                .build();
    }

    // for exporting into excel
    private TransactionExcelResponse toTransactionExcelResponse(BookTransactionProjection projection) {

        return TransactionExcelResponse.builder()
                .transactionId(projection.getBookTransactionId())
                .transactionCode(projection.getTransactionCode())
                .bookName(projection.getBookName())
                .publishedDate(projection.getPublishedDate())
                .isbn(projection.getIsbn())
                .categoryName(projection.getCategoryName())
                .stockCount(projection.getStockCount())
                .rentFrom(projection.getRentFrom())
                .rentTo(projection.getRentTo())
                .rentStatus(projection.getRentStatus())
                .memberName(projection.getMemberName())
                .memberMobileNumber(projection.getMemberMobileNumber())
                .build();
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
                .bookName(bookTransaction.getBook().getName())
                .memberName(bookTransaction.getMember().getName())
                .rentStatus(bookTransaction.getRentStatus())
                .build();
    }


    // BookTransactionProjection to BookTransactionResponse
    private BookTransactionResponse toBookTransactionResponse(BookTransactionProjection projection) {
        LocalDate rentedFrom = dateUtil.stringToDate(projection.getRentFrom());
        LocalDate rentExpiry = dateUtil.stringToDate(projection.getRentTo());
        LocalDate bookPublishedOn = dateUtil.stringToDate(projection.getPublishedDate());
//        Book book = bookRepo.findById(projection.getBookId()).get();                    // TODO: check this
//        Category category = categoryRepo.findById(projection.getCategoryId()).get();
//        Member member = memberRepo.findById(projection.getMemberId()).get();
        return BookTransactionResponse.builder()
                .transactionId(projection.getBookTransactionId())
                .code(projection.getTransactionCode())
                .rentFrom(rentedFrom)
                .rentTo(rentExpiry)
                .bookName(projection.getBookName())
                .memberName(projection.getMemberName())
                .rentStatus(RentStatus.valueOf(projection.getRentStatus()))
                .build();
    }
}