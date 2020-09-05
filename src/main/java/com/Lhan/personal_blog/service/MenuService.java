package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.vo.MenuVo;

public interface MenuService {

    /**
     * 新增菜单表
     */

    Result saveMenu(MenuVo menuVo);

    /**
     * 查询菜单表
     */
    Result findMenu(Long id);

    /**
     * 分页查询菜单表
     */
    Result getMenuList(MenuVo menuVo);

    /**
     * 更新菜单表
     */
    Result updateMenu(MenuVo menuVo);

    /**
     * 删除菜单表
     */
    Result deleteMenu(Long id);

    Result getFrontMenuList(MenuVo menuVo);


}
