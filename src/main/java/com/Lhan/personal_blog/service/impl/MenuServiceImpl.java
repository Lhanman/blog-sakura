package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.mapper.MenuMapper;
import com.Lhan.personal_blog.pojo.Menu;
import com.Lhan.personal_blog.pojo.MenuExample;
import com.Lhan.personal_blog.service.MenuService;
import com.Lhan.personal_blog.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    MenuMapper menuMapper;

    @Override
    public Result saveMenu(MenuVo menuVo) {
        return null;
    }

    @Override
    public Result findMenu(Long id) {
        return null;
    }

    @Override
    public Result getMenuList(MenuVo menuVo) {
        return null;
    }

    @Override
    public Result updateMenu(MenuVo menuVo) {
        return null;
    }

    @Override
    public Result deleteMenu(Long id) {
        return null;
    }

    @Override
    public Result getFrontMenuList(MenuVo menuVo) {
        MenuExample example = new MenuExample();
        example.or().andParentIdEqualTo(0L);
        List<Menu> menuList = menuMapper.selectByExample(example);
        List<MenuVo> menuVoList = new ArrayList<>();
        menuList.forEach(menu -> {
            menuVoList.add(new MenuVo()
            .setId(menu.getId())
            .setIcon(menu.getIcon())
            .setTitle(menu.getTitle())
            .setParentId(menu.getParentId())
            .setSort(menu.getSort())
            .setUrl(menu.getUrl())
            .setChild(menuMapper.findMenuListByParentId(menu.getId()))
                            .setAnimation(menu.getAnimation())
            );
        });

        return Result.createWithModels(menuVoList);
    }
}
