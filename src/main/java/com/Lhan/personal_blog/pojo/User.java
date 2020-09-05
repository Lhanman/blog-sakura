package com.Lhan.personal_blog.pojo;

import java.util.List;

public class User {
    private Long id;

    private String phone;

    private String username;

    private String password;

    private String gender;

    private String birthday;

    private String email;

    private String personalBrief;

    private String avatarimgUrl;

    private String recentlyLanded;

    private Boolean isLocked;

    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPersonalBrief() {
        return personalBrief;
    }

    public void setPersonalBrief(String personalBrief) {
        this.personalBrief = personalBrief == null ? null : personalBrief.trim();
    }

    public String getAvatarimgUrl() {
        return avatarimgUrl;
    }

    public void setAvatarimgUrl(String avatarimgUrl) {
        this.avatarimgUrl = avatarimgUrl == null ? null : avatarimgUrl.trim();
    }

    public String getRecentlyLanded() {
        return recentlyLanded;
    }

    public void setRecentlyLanded(String recentlyLanded) {
        this.recentlyLanded = recentlyLanded == null ? null : recentlyLanded.trim();
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", personalBrief='" + personalBrief + '\'' +
                ", avatarimgUrl='" + avatarimgUrl + '\'' +
                ", recentlyLanded='" + recentlyLanded + '\'' +
                ", isLocked=" + isLocked +
                ", roles=" + roles +
                '}';
    }
}