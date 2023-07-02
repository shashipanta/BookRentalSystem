package com.brs.bookrentalsystem.auth.model;

import com.brs.bookrentalsystem.auth.enums.RoleNames;
import com.brs.bookrentalsystem.model.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor( )
@Table(name = "Users",
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

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleNames role;

//    @ManyToMany(fetch = LAZY, cascade = CascadeType.PERSIST)
//    @JoinTable(
//            name = "user_role",
//            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
//            foreignKey = @ForeignKey(name = "fk_user_roleId"),
//            inverseForeignKey = @ForeignKey(name = "fk_role_userId")
//    )
//    Set<Role> roles;

    @Column(name = "remaining_logins", columnDefinition = "integer default 5")
    private Short remainingLogins = 5;

    public UserAccount(String userName, String email, String password, RoleNames role){
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }


}
