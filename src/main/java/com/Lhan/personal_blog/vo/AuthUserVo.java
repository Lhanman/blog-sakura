package com.Lhan.personal_blog.vo;

import com.Lhan.personal_blog.common.validator.annotion.IntegerNotNull;
import com.Lhan.personal_blog.validator.UpdateUser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AuthUserVo extends BaseVo<AuthUserVo>{

    @IntegerNotNull(groups = {UpdateUser.class})
    private Long id;

    private String name;

    private String introduction;

    private String avatar;

    private String token;

    private Long roleId;

    private List<String> roles;

}
