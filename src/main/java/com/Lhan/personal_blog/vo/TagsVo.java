package com.Lhan.personal_blog.vo;

import com.Lhan.personal_blog.common.validator.annotion.NotBlank;
import com.Lhan.personal_blog.common.validator.group.Insert;
import com.Lhan.personal_blog.common.validator.group.Update;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TagsVo extends BaseVo<TagsVo>{

    /**
     * 名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;

    /**
     * 文章总数
     */

    private Integer postsTotal;

}
