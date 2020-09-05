package com.Lhan.personal_blog.vo;

import com.Lhan.personal_blog.pojo.Menu;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MenuVo extends BaseVo<MenuVo>{


    private static final long serialVersionUID = 1;

    private Long id;

    private Long parentId;

    private String title;

    private String icon;

    private String url;

    private Integer sort;

    private String animation;

    private List<Menu> child;

}
