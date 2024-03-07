package com.andrelopes.transaction_api.models;

import com.andrelopes.transaction_api.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserModel implements UserDetails {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    private Long userId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "USER_EMAIL")
    private String userEmail;
    @Column(name = "USER_PASSWORD")
    private String userPassword;
    @Column(name = "USER_REGISTER")
    private String userRegister;
    @Column(name = "USER_TYPE")
    @Enumerated(EnumType.STRING)
    private RoleEnum userType;
    @Column(name = "USER_BALANCE")
    private Float userBalance;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.userType == RoleEnum.COMUM) return List.of(new SimpleGrantedAuthority("ROLE_COMUM"));
        else if(this.userType == RoleEnum.LOJISTA) return List.of(new SimpleGrantedAuthority("ROLE_LOJISTA"));
        else if(this.userType == RoleEnum.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        else return null;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
