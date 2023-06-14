package com.brs.bookrentalsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "tbl_category")
public class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Short id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", length = 500)
    private String description;
}
