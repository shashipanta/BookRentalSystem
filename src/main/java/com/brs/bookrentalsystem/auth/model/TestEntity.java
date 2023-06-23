package com.brs.bookrentalsystem.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_test",
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_name", columnNames = {"name", "deleted"})
        }
)
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String desc;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

}
