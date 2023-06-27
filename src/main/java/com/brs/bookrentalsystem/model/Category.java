package com.brs.bookrentalsystem.model;

import com.brs.bookrentalsystem.model.audit.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "tbl_category",
        uniqueConstraints = @UniqueConstraint(name = "uk_category_categoryName", columnNames = "category_name")
)
@SQLDelete(sql = "UPDATE tbl_category SET is_deleted=true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Category extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Short id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", length = 500)
    private String description;

    @Column
    private Boolean isDeleted = Boolean.FALSE;
}
