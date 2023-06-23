package com.brs.bookrentalsystem.auth.model;

import com.brs.bookrentalsystem.auth.enums.RoleNames;
import com.brs.bookrentalsystem.model.audit.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Data
@Table(name = "tbl_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_email", columnNames = "email")
        }
)
public class UserAccount extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", length = 100, nullable = false)
    private String userName;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "ip")
    private String ip;

    @ManyToMany(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_user_roleId"),
            inverseForeignKey = @ForeignKey(name = "fk_role_userId")
    )
    Set<Role> roles;

    @Column(name = "remaining_logins", columnDefinition = "integer default 5", insertable = false)
    private Short remainingLogins;


}
