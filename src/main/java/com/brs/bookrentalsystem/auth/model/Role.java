package com.brs.bookrentalsystem.auth.model;

import com.brs.bookrentalsystem.auth.enums.RoleNames;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_role",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_roleCode", columnNames = "role_code"),
                @UniqueConstraint(name = "uk_roleName", columnNames = "role_name")
        }
)
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @NotNull
    @Column(name = "role_code", length = 100, nullable = false)
    private String roleCode;

    @NotNull
    @Column(name = "role_name", length = 100, nullable = false)
    private RoleNames roleName;

    public Role(String roleCode, RoleNames roleName){
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

}
