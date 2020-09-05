package com.Lhan.personal_blog.vo;

import com.Lhan.personal_blog.common.validator.annotion.NotBlank;
import com.Lhan.personal_blog.validator.InsertLink;
import com.Lhan.personal_blog.validator.UpdateLink;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FriendshipLinkVo extends BaseVo<FriendshipLinkVo>{

    @NotBlank(groups = {UpdateLink.class})
    private Long id;

    private String title;

    @NotBlank(groups = {InsertLink.class,UpdateLink.class})
    private String name;

    @NotBlank(groups = {InsertLink.class,UpdateLink.class})
    private String href;

    private String logo;

    private String sort;

    private String description;

}
