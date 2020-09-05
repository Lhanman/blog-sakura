package com.Lhan.personal_blog.dto;

public class UserDto {
    /**
     * tbl_uer表基础字段
     */
    private Long id;

    private String username;

    private String phone;

    private String gender;

    private String birthday;

    private String email;

    private String personal_brief;

    private String avatarImg_url;

    private String recently_landed;

    private Boolean is_locked;


    /**
     * tbl_role表的基础字段
     */
    private String role;

    public Boolean getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(Boolean is_locked) {
        this.is_locked = is_locked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonal_brief() {
        return personal_brief;
    }

    public void setPersonal_brief(String personal_brief) {
        this.personal_brief = personal_brief;
    }

    public String getAvatarImg_url() {
        return avatarImg_url;
    }

    public void setAvatarImg_url(String avatarImg_url) {
        this.avatarImg_url = avatarImg_url;
    }

    public String getRecently_landed() {
        return recently_landed;
    }

    public void setRecently_landed(String recently_landed) {
        this.recently_landed = recently_landed;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
