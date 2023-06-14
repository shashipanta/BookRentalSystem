package com.brs.bookrentalsystem.model;

import jakarta.persistence.*;

import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tbl_book",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_book_name", columnNames = {"name"}),
                @UniqueConstraint(name = "uk_book_isbn", columnNames = "isbn_no")
        }
)
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "total_pages")
    private Short totalPages;

    @Column(name = "isbn_no", length = 30, nullable = false)
    private String isbn;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "stock_count", nullable = false)
    private Integer stockCount = 10;

    @Column(name = "photo", length = 200, nullable = false)
    private String photo;

    @ManyToOne(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "fk_book_categoryId")
    )
    private Category category;


    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_book_authorId"),
            inverseForeignKey = @ForeignKey(name = "fk_author_bookId")
    )
    Set<Author> author;


}
