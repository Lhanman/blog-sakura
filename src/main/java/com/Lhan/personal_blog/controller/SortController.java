package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.pojo.CategoryInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sort")
public class SortController {

    /**
     * 获取所有分类信息
     */
    @ApiOperation("获取所有分类信息")
    @GetMapping("/list")
    public List<CategoryInfo> listAllCategoryInfo()
    {
        return null;
    }

    /**
     * 通过id获取某一条分类信息
     *
     * @param id
     * @return
     */
    @ApiOperation("获取某一条分类信息")
    @ApiImplicitParam(name = "id",value = "分类ID",required = true,dataType = "Long")
    @GetMapping("/{id}")
    public CategoryInfo getCategoryInfoById(@PathVariable Long id)
    {
        return null;
    }

    /**
     *增加一条分类信息数据
     *
     */
    @ApiOperation("增加分类信息")
    @ApiImplicitParam(name = "name",value = "分类名称",required = true,dataType = "String")
    @PostMapping("")
    public String addCategoryInfo()
    {
        return null;
    }

    /**
     * 更新/编辑一条数据
     */

    @ApiOperation("更新/编辑一条数据")
    @ApiImplicitParam(name = "id",value = "分类id",required = true,dataType = "Long")
    @PutMapping("/{id}")
    public String updateCategoryInfo(@PathVariable Long id)
    {
        return null;
    }

    /**
     * 根据id删除一个分类信息
     *
     */
    @ApiOperation("删除分类信息")
    @ApiImplicitParam(name = "id",value = "分类ID",required = true,dataType = "Long")
    @DeleteMapping("/{id}")
    public String deleteCategoryInfo(@PathVariable Long id)
    {
        return null;
    }

}
