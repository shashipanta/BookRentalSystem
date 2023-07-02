package com.brs.bookrentalsystem.repo;

import com.brs.bookrentalsystem.enums.RentStatus;
import com.brs.bookrentalsystem.model.BookTransaction;
import com.brs.bookrentalsystem.projections.BookTransactionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookTransactionRepo extends JpaRepository<BookTransaction, Long> {


    @Query(nativeQuery = true,
            name = "getAll",
            value = "SELECT bt, b.id as bookId FROM tbl_book_transaction bt INNER JOIN book b ON bt.book_id = b.id")
    List<BookTransaction> allTransactions();

    @Query(nativeQuery = true,
            value = "select\n" +
                    "    bt.transaction_id as bookTransactionId,\n" +
                    "    bt.code as transactionCode,\n" +
                    "    bt.rent_from as rentFrom,\n" +
                    "    bt.rent_expiry as rentTo,\n" +
                    "    bt.rent_status as rentStatus,\n" +
                    "    tb.id as bookId,\n" +
                    "    tb.name as bookName,\n" +
                    "    tb.isbn_no as isbn,\n" +
                    "    tb.stock_count as stockCount,\n" +
                    "    tb.published_date as publishedDate,\n" +
                    "    tb.category_id as categoryId,\n" +
                    "    tc.category_name as categoryName,\n" +
                    "    tm.id as memberId,\n" +
                    "    tm.name as memberName,\n" +
                    "    tm.mobile_number as memberMobileNumber" +
                    "\n" +
                    "from tbl_book_transaction bt\n" +
                    "    inner join tbl_book tb on bt.book_id = tb.id\n" +
                    "    inner join tbl_member tm on bt.member_id = tm.id\n" +
                    "    inner join tbl_category tc on tb.category_id = tc.id;")
    List<BookTransactionProjection> getAllTransactions();

    BookTransaction findBookTransactionByCode(String bookCode);

    List<BookTransaction> findBookTransactionByRentStatus(RentStatus rentStatus);

    @Query("SELECT bookTransaction FROM BookTransaction bookTransaction WHERE " +
            "bookTransaction.code LIKE CONCAT('%',:filterValue, '%') AND " +
            "bookTransaction.rentStatus = 'RENTED'")
    List<BookTransaction> filterByTransactionCode(String filterValue);

    @Query("SELECT LAST_INSERT_ID()")
    Long getLastInsertedId();
}
