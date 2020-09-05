package com.Lhan.personal_blog.constant;

public enum CodeType {

    /**
     * 状态码
     *
     */

    SUCCESS_STATUS(0,"成功"),
    USER_NOT_LOGIN(101,"用户未登录"),
    PERMISSION_VERIFY_FAIL(102,"权限验证失败"),
    SERVER_EXCEPTION(103,"服务器异常"),
    ARTICLE_NOT_EXIST(204,"文章不存在"),
    PUBLISH_ARTICLE_NO_PERMISSION(205,"发表文章没有权限"),
    PUBLISH_ARTICLE_EXCEPTION(206,"服务器异常"),
    UPLOAD_BLOG_PICTURE_ERROR(207,"上传博客封面异常"),
    BLOG_PICTURE_NULL(208,"未上传博客封面"),
    BLOG_UPDATE_SUCCESS(209,"博客更新成功"),
    FIND_TAG_CLOUD(301,"获得标签云成功"),
    FIND_ARTICLE_BY_TAG(302,"通过标签获得文章成功"),
    CATEGORY_NOT_EXIST(404,"分类不存在"),
    COMMENT_BLANK(501,"评论内容为空"),
    MESSAGE_HAS_THUMBS_UP(502,"已经点赞过了"),
    USERNAME_IS_NULL(503,"用户名为空"),
    USERNAME_UPDATE_SUCCESS(504,"用户信息修改成功"),
    USERNAME_EXIST(505,"用户名已存在"),
    USERNAME_NOT_EXIST(508,"用户名不存在"),
    USERNAME_FORMAT_ERROR(509,"用户名长度过长或格式不正确"),
    ALREADY_READ_ALL(601,"已经全部标记已读了"),
    PHONE_ERROR(901,"手机号错误"),
    AUTH_CODE_ERROR(902,"验证码错误"),
    PHONE_EXIST(903,"手机号已存在");

    private int code;

    private String message;

    CodeType(int code,String message)
    {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
