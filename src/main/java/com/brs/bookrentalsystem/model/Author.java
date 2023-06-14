package com.brs.bookrentalsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "tbl_author",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_author_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_author_phoneNo", columnNames = "mobile_no")
        }
)
public class Author {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "mobile_no", length = 15, nullable = false)
    private String mobileNumber;

}
