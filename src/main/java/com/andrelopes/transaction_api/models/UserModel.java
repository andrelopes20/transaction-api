package com.andrelopes.transaction_api.models;

import com.andrelopes.transaction_api.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserModel {
    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_register")
    private String userRegister;
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private RoleEnum userType;
    @Column(name = "user_balance")
    private Float userBalance;
}
