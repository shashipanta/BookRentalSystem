package com.brs.bookrentalsystem.auth.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_token")
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Short id;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "otp", nullable = false)
    private String otp;
}
