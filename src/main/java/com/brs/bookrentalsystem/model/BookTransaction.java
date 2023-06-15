package com.brs.bookrentalsystem.model;

import com.brs.bookrentalsystem.enums.RentStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;


@Data
@Entity
@Table(name = "tbl_book_transaction",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_bookTransaction_code", columnNames = "code"),
        }
)
public class BookTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "code", length = 15, nullable = false)
    private String code;

    @CreationTimestamp
    @Column(name = "rent_from")
    private LocalDate rentFrom;

    @Column(name = "rent_expiry")
    private LocalDate rentTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "rent_status", nullable = false)
    private RentStatus rentStatus;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(
            name = "book_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_bookTransaction_bookId")
    )
    private Book book;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(
            name = "member_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_bookTransaction_memberId")
    )
    private Member member;

}
