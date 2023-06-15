package com.brs.bookrentalsystem.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_member",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_member_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_mobile_number", columnNames = "mobile_number")
        }


)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", length = 150, nullable = false)
    private String email;


    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "mobile_number", length = 15, nullable = false)
    private String mobileNumber;

    @Column(name = "address")
    private String Address;
}
