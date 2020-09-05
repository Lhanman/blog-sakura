package com.Lhan.personal_blog.entity.security;

import com.Lhan.personal_blog.pojo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String avatar;

    private Role role;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() {
    }

    public JwtUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public JwtUser(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public JwtUser(String username, String password, String avatar, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.authorities = authorities;
    }

    public JwtUser(Long id, String username, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
    }

    public JwtUser(String username, String password, String phone, String avatar, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role=" + role +
                ", authorities=" + authorities +
                '}';
    }
}
