package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.aspect.annotation.AdminCheck;
import com.Lhan.personal_blog.aspect.annotation.PermissionCheck;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.dto.ArticleInfoDto;
import com.Lhan.personal_blog.mapper.MessageMapper;
import com.Lhan.personal_blog.pojo.*;
import com.Lhan.personal_blog.util.*;
import com.Lhan.personal_blog.validator.InsertBook;
import com.Lhan.personal_blog.validator.InsertLink;
import com.Lhan.personal_blog.vo.BookVo;
import com.Lhan.personal_blog.vo.FriendshipLinkVo;
import com.Lhan.personal_blog.vo.MangaVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 后台管理系统
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController  extends BaseController {

    private static String messageCoverImg_url;

    /**
     * 首页显示统计信息
     */
    @GetMapping(value = "/admin/index")
    @ResponseBody
    public String index()
    {
        DataMap data = adminIndexService.findNumForIndex();
        return JsonResult.build(data).toJSON();
    }



    /**
     * 博客信息
     *      --查找所有博客信息放入包装类ArticleInfoDto
     *
     * @return
     */
    @GetMapping(value = "/admin/findAllArticle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findAllArticleByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize) {
        int article_count = articleService.findArticleCount()-6;
        DataMap data = articleService.findAllArticleByPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", article_count);
        return jsonData.toJSON();
    }

    /**
     * 博客编辑
     *      --博客删除
     */
    @PostMapping(value = "/admin/deleteArticle/{article_id}")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String deleteArticleById(@PathVariable("article_id") Long article_id) {
        articleService.deleteArticleById(article_id);
        return JsonResult.success().toJSON();
    }

    /**
     * 博客编辑
     *      --批量删除
     */
    @PostMapping(value = "/admin/deleteArticleBatch")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String deleteArticleBatch(@RequestParam("ids") Long[] ids) {
        //我比较懒就不写批量删除sql语句了
        for (Long id : ids) {
            deleteArticleById(id);
        }
        return JsonResult.success().toJSON();
    }

    /**
     * 博客信息
     *      --博客搜索
     *      --需要用到全文索引
     *      --之后再进行开发
     */
    @GetMapping(value = "/admin/articleSearch", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String articleSearch(@RequestParam("searchParams") String searchParams, @RequestParam("page") String pageNum,
                                @RequestParam("limit") String pageSize) {
        int article_count = articleService.findArticleCount();
        JSONObject jsonObject = (JSONObject) JSONObject.parse(searchParams);
        String title = (String) jsonObject.get("title");
        String tagName = (String) jsonObject.get("tag");
        String categoryName = (String) jsonObject.get("category");
        DataMap data = articleService.findAllArticleByPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", article_count);
        return jsonData.toJSON();
    }


    /**
     * 评论管理
     *      --分页查询所有评论相关信息
     */

    @GetMapping(value = "/admin/findAllComment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findAllCommentByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize) {
        int commentNum = commentService.commentNum();
        DataMap data = commentService.findAllCommentByPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", commentNum);
        return jsonData.toJSON();
    }

    /**
     * 评论管理
     *      --通过评论者名称模糊查询
     */
    @GetMapping(value = "/admin/findCommentLikeUserName", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findCommentLikeUserName(@RequestParam("searchParams") String searchParams,
                                          @RequestParam("page") String pageNum, @RequestParam("limit") String pageSize) {
        JSONObject jsonObject = (JSONObject) JSONObject.parse(searchParams);
        String username = (String) jsonObject.get("username");
        JsonResultAdmin.JsonData jsonData = commentService.findCommentLikeUserName(Integer.parseInt(pageNum), Integer.parseInt(pageSize), username);
        return jsonData.toJSON();
    }

    /**
     * 评论管理
     *      --删除评论
     */
    @PostMapping(value = "/admin/deleteComment/{comment_id}")
    @AdminCheck(value="ROLE_ADMIN")
    @ResponseBody
    public String deleteCommentByCommentId(@PathVariable("comment_id") Long comment_id) {
        commentService.deleteCommentByComment_id(comment_id);
        return JsonResult.success().toJSON();
    }

    /**
     * 评论管理
     *      --批量删除
     */
    @PostMapping(value = "/admin/deleteCommentBatch")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String deleteCommentBatch(@RequestParam("ids") Long[] ids) {
        //我比较懒就不写批量删除sql语句了
        for (Long id : ids) {
            deleteCommentByCommentId(id);
        }
        return JsonResult.success().toJSON();
    }


    /**
     * 评论管理
     *      --评论内容回显
     */
    @RequestMapping(value = "/admin/editComment/{comment_id}")
    public String editComment(@PathVariable("comment_id") Long comment_id, Model model) {
        Comment comment = commentMapper.selectByPrimaryKey(comment_id);
        model.addAttribute("content", comment.getContent());
        model.addAttribute("id", comment_id);
        return "admin/comment-update";
    }

    /**
     * 评论管理
     *      --评论内容修改
     */
    @PostMapping(value = "/admin/updateComment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String updateComment(@RequestParam("content") String content, @RequestParam("id") Long comment_id) {
        Comment comment = commentMapper.selectByPrimaryKey(comment_id);
        comment.setContent(content);
        commentMapper.updateByPrimaryKeySelective(comment);
        return JsonResult.success().toJSON();
    }

    /**
     * 标签管理
     *      --分页显示所有标签相关信息
     */
    @GetMapping(value = "/admin/findAllTag", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findAllTagByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize) {
        int tagNum = tagService.tagNum();
        DataMap data = tagService.findAllTagByPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", tagNum);
        return jsonData.toJSON();
    }


    /**
     * 标签管理
     *      --标签名回显
     */
    @GetMapping(value = "/admin/editTag/{tag_id}")
    public String editTag(@PathVariable("tag_id") Long tag_id, Model model) {
        ArticleTag articleTag = articleTagMapper.selectByPrimaryKey(tag_id);
        model.addAttribute("tag_name", articleTag.getTagName());
        model.addAttribute("id", tag_id);
        return "admin/tag-update";
    }

    /**
     * 标签管理
     *      --标签名修改
     */
    @PostMapping(value = "/admin/updateTag", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String updateTag(@RequestParam("tag_name") String tag_name, @RequestParam("id") Long tag_id) {
        ArticleTag articleTag = articleTagMapper.selectByPrimaryKey(tag_id);
        tag_name = tag_name.replaceAll("&nbsp;", StringUtil.BLANK)
                .replaceAll("\\s", StringUtil.BLANK).trim();
        articleTag.setTagName(tag_name);
        articleTag.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
        tagService.updateTag(articleTag);
        return JsonResult.success().toJSON();
    }

    /**
     * 标签管理
     *      --删除标签
     */
    @PostMapping(value = "/admin/deleteTag/{tag_id}")
    @AdminCheck(value="ROLE_ADMIN")
    @ResponseBody
    public String deleteTagByTagId(@PathVariable("tag_id") Long tag_id) {
        ArticleTag articleTag = articleTagMapper.selectByPrimaryKey(tag_id);
        tagService.deleteTagByTag_id(articleTag);

        return JsonResult.success().toJSON();
    }

    /**
     * 标签管理
     *      --批量删除
     */
    @PostMapping(value = "/admin/deleteTagBatch")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String deleteTagBatch(@RequestParam("ids") Long[] ids) {
        //我比较懒就不写批量删除sql语句了
        for (Long id : ids) {
            deleteTagByTagId(id);
        }
        return JsonResult.success().toJSON();
    }

    /**
     * 标签管理
     *      --跳转至添加表单
     */
    @GetMapping(value = "/admin/addTag/{article_id}")
    public String addTag(@PathVariable("article_id") Long article_id, Model model) {

        model.addAttribute("article_id", article_id);
        return "admin/tag-add";
    }

    /**
     * 标签管理
     *      --添加标签
     */
    @PostMapping(value = "/admin/insertTag")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String insertTag(@RequestParam("tag_name") String tag_name, @RequestParam("id") Long article_id) {
        ArticleTag articleTag = new ArticleTag();
        tag_name = tag_name.replaceAll("&nbsp;", StringUtil.BLANK)
                .replaceAll("\\s", StringUtil.BLANK).trim();
        articleTag.setTagName(tag_name);
        articleTag.setIsEffective(true);
        articleTag.setCreateBy(DateFormatUtil.dateFormat(new Date()));
        articleTag.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
        articleTag.setArticleId(article_id);
        tagService.insertTag(articleTag);
        return JsonResult.success().toJSON();
    }


    /**
     * 分类管理
     *      --分页获取所有分类信息
     */
    @GetMapping(value = "/admin/findAllCategory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findAllCategoryByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize) {
        int categoryNum = categoryService.findCategoryNum();
        DataMap data = categoryService.findAllCategoryByPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", categoryNum);
        return jsonData.toJSON();
    }

    /**
     * 分类管理
     *      --跳转至添加表单
     */
    @GetMapping(value = "/admin/addCategory")
    public String addCategory() {
        return "admin/category-add";
    }

    /**
     * 分类管理
     *      --添加分类
     */
    @PostMapping(value = "/admin/insertCategory")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String insertCategory(@RequestParam("category_name") String category_name) {
        CategoryInfo categoryInfo = new CategoryInfo();
        category_name = category_name.replaceAll("&nbsp;", StringUtil.BLANK)
                .replaceAll("\\s", StringUtil.BLANK).trim();
        categoryInfo.setName(category_name);
        categoryInfo.setNumber(0);
        categoryInfo.setCreateBy(DateFormatUtil.dateFormat(new Date()));
        categoryInfo.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
        categoryService.addCategoryInfo(categoryInfo);
        return JsonResult.success().toJSON();
    }


    /**
     * 分类管理
     *      --删除分类
     */
    @PostMapping(value = "/admin/deleteCategory/{category_id}")
    @AdminCheck(value="ROLE_ADMIN")
    @ResponseBody
    public String deleteCategoryByCategoryId(@PathVariable("category_id") Long category_id) {
        categoryService.deleteCategoryById(category_id);

        return JsonResult.success().toJSON();
    }

    /**
     * 分类管理
     *      --批量删除分类
     */
    @PostMapping(value = "/admin/deleteCategoryBatch")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String deleteCategoryBatch(@RequestParam("ids") Long[] ids) {
        //我比较懒就不写批量删除sql语句了
        for (Long id : ids) {
            deleteCategoryByCategoryId(id);
        }
        return JsonResult.success().toJSON();
    }


    /**
     * 分类管理
     *      --分类名的回显
     */
    @GetMapping(value = "/admin/editCategory/{category_id}")
    public String editCategory(@PathVariable("category_id") Long category_id, Model model) {
        CategoryInfo categoryInfo = categoryInfoMapper.selectByPrimaryKey(category_id);
        model.addAttribute("category_name", categoryInfo.getName());
        model.addAttribute("id", category_id);
        return "admin/category-update";
    }


    /**
     * 分类管理
     *      --分类名修改
     */
    @PostMapping(value = "/admin/updateCategory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String updateCategory(@RequestParam("category_name") String category_name, @RequestParam("id") Long category_id) {
        CategoryInfo categoryInfo = categoryInfoMapper.selectByPrimaryKey(category_id);
        category_name = category_name.replaceAll("&nbsp;", StringUtil.BLANK)
                .replaceAll("\\s", StringUtil.BLANK).trim();
        categoryInfo.setName(category_name);
        categoryInfo.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
        categoryService.updateCategory(categoryInfo);
        return JsonResult.success().toJSON();
    }


    /**
     * 图片管理
     *      --分页显示所有博客图片相关信息
     */
    @GetMapping(value = "/admin/findAllPicture", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @AdminCheck(value="ROLE_ADMIN")
    @ResponseBody
    public String findAllPictureByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize) {

        int pictureNum = pictureService.findPictureNum();
        DataMap data = pictureService.findAllPictureByPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", pictureNum);
        return jsonData.toJSON();
    }

    /**
     * 图片管理
     *      --删除封面
     */
    @PostMapping(value = "/admin/deletePicture/{picture_id}")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String deletePictureByPictureId(@PathVariable("picture_id") Long picture_id)
    {
        ArticlePicture articlePicture = articlePictureMapper.selectByPrimaryKey(picture_id);
        DataMap data = pictureService.deletePictureByPictureId(articlePicture);
        return JsonResult.build(data).toJSON();
    }

    /**
     * 图片管理
     *      --批量删除图片
     */
    @PostMapping(value = "/admin/deletePictureBatch")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String deletePictureBatch(@RequestParam("ids") Long[] ids) {
        //我比较懒就不写批量删除sql语句了
        for (Long id : ids) {
            String str = deletePictureByPictureId(id);
            if (str.contains("1"))
            {
                return JsonResult.fail().toJSON();
            }
        }
        return JsonResult.success().toJSON();
    }


    /**
     * 图片管理
     *      --跳转到图片上传页面
     */
    @GetMapping(value = "/admin/editPicture/{picture_id}")
    public String editPicture(@PathVariable("picture_id") Long picture_id, Model model,HttpServletRequest request) {

        model.addAttribute("id",picture_id);
        return "admin/picture-update";
    }


    /**
     * 图片管理
     *      --图片更新
     */
    @PostMapping(value = "/admin/updatePicture/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String updatePicture(MultipartFile file, @PathVariable("id") Long picture_id) {
        if (file.isEmpty())
        {
            return JsonResult.fail(CodeType.UPLOAD_BLOG_PICTURE_ERROR).toJSON();
        }
        ArticlePicture articlePicture = articlePictureMapper.selectByPrimaryKey(picture_id);
        pictureService.updatePictureByPictureId(articlePicture,file);

        return JsonResult.success().toJSON();
    }


    /**
     * 用户管理
     *      --分页显示所有用户信息
     */
    @GetMapping(value = "/admin/findAllUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findAllUserByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize) {

        int userNum = userService.findUserNum();
        DataMap data = userService.findAllUserByPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", userNum);
        return jsonData.toJSON();
    }


    /**
     * 用户管理
     *      --封锁用户功能
     */
    @PostMapping(value = "/admin/lockOrUnlockUser")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String lockOrUnlockUser(@RequestParam("user_id") Long user_id,
                               @RequestParam("is_locked") boolean is_locked)
    {
        if (is_locked)
        {
            userService.lockUser(user_id);
        }
        else
        {
            userService.unlockUser(user_id);
            return JsonResult.fail().toJSON();
        }
        return JsonResult.success().toJSON();
    }

    /**
     * 用户管理
     *      --跳转至用户编辑页面
     */
    @GetMapping(value = "/admin/editUser/{user_id}")
    public String editUser(@PathVariable("user_id") Long user_id, Model model) {
        User user = userService.findUserByUserId(user_id);
        model.addAttribute("username",user.getUsername());
        model.addAttribute("gender",user.getGender());
        model.addAttribute("phone",user.getPhone());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("avatarImgUrl",user.getAvatarimgUrl());
        model.addAttribute("personal_brief",user.getPersonalBrief());
        model.addAttribute("birthday",user.getBirthday());
        model.addAttribute("id",user_id);
        return "admin/user-update";
    }

    /**
     * 用户管理
     *      --用户编辑
     */
    @PostMapping(value = "/admin/updateUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String updateUser(User user) {
        userService.updateUser(user);
        return JsonResult.success().toJSON();
    }

    /**
     * 用户管理
     *      --用户修改头像
     */
    @PostMapping(value = "/admin/uploadHead/{user_id}")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String uploadHead(@PathVariable("user_id") Long user_id,MultipartFile file)
    {
        if (file.isEmpty())
        {
            return JsonResult.fail(CodeType.UPLOAD_BLOG_PICTURE_ERROR).toJSON();
        }
        DataMap data = userService.updateAvatarImgByUserId(user_id,file);
        return JsonResult.build(data).toJSON();
    }


    /**
     * 用户管理
     *      --分页显示用户权限信息
     */
    @GetMapping(value = "/admin/findAllUserRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findAllUserRoleByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize) {

        int userNum = userService.findUserNum();
        DataMap data = userService.findAllUserRoleByPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", userNum);
        return jsonData.toJSON();
    }


    /**
     * 用户管理
     *      --用户权限
     *      --提权
     */
    @PostMapping(value = "/admin/upUserRight")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String upUserRight(@RequestParam("user_id")String user_id)
    {
        DataMap data = userService.upUserRight(Long.parseLong(user_id));
        return JsonResult.build(data).toJSON();
    }



    /**
     * 用户管理
     *      --用户权限
     *      --降权
     */
    @PostMapping(value = "/admin/downUserRight")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String downUserRight(@RequestParam("user_id")String user_id)
    {
        DataMap data = userService.downUserRight(Long.parseLong(user_id));
        return JsonResult.build(data).toJSON();
    }


    /**
     * 留言管理
     *      --留言列表
     */
    @GetMapping(value = "/admin/findAllMessage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findAllMessageByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize) {

        int messageNum = messageService.findMessageNum();
        DataMap data = messageService.findAllMessageByPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", messageNum);
        return jsonData.toJSON();
    }


    /**
     * 留言管理
     *      --留言回复
     *      --email回复
     */
    @PostMapping(value = "/admin/replyMessage",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String replyMessage(@RequestParam("to")String to,
                               @RequestParam("title")String title,
                               @RequestParam("content") String content,
                               @RequestParam("name")String name,
                               @RequestParam("id") String id)
    {

        if (messageCoverImg_url == null)
        {
            return JsonResult.fail().toJSON();
        }
        Map<String,Object> valueMap = new HashMap<>();
        valueMap.put("to",to);
        valueMap.put("title",title);
        valueMap.put("content",content);
        valueMap.put("name","您好！"+name+"：");
//        valueMap.put("logoImage","static/assets/images/logo.jpg");
        valueMap.put("coverImage",messageCoverImg_url);
        mailService.sendThymeleafMail(valueMap);
        messageService.replyMessage(Integer.parseInt(id));
        messageCoverImg_url = null;

        return JsonResult.success().toJSON();
    }

    /**
     * 留言管理
     *      --跳转至编辑页面
     */
    @GetMapping(value = "/admin/editEmail/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        Message message = messageMapper.selectByPrimaryKey(id);
        model.addAttribute("email",message.getEmail());
        model.addAttribute("name",message.getName());
        model.addAttribute("id",message.getId());
        return "admin/message-edit";
    }

    /**
     * 留言管理
     *      --留言回复
     *      --上传封面
     */
    @PostMapping(value = "/admin/uploadMessageCover",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String  uploadMessageCover(MultipartFile file)
    {
        messageCoverImg_url = messageService.updateMessageCoverUrl(file);
        return JsonResult.success().toJSON();
    }


    /**
     * 留言管理
     *      --留言详情内容
     */
    @GetMapping(value = "/admin/findMessageDetails/{id}")
    public String findMessageDetails(@PathVariable("id") int id, Model model) {
        Message message = messageMapper.selectByPrimaryKey(id);
        model.addAttribute("content",message.getContent());
        return "admin/message-detail";
    }


    /**
     * 书单管理
     *     --书单列表
     */
    @GetMapping(value = "/admin/findAllBooks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findAllBooksByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize)
    {

        int bookNum = bookService.findBookNum();
        DataMap data = bookService.getBookListByPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", bookNum);
        return jsonData.toJSON();
    }

    /**
     * 书单管理
     *      --跳转至书单添加表单页面
     * @return
     */
    @GetMapping(value = "/admin/addBook")
    public String addBook() {
        return "admin/booklist-add";
    }

    /**
     * 书单管理
     *      --添加书单
     */
    @PostMapping(value = "/admin/insertBook")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String insertBook(@Validated({InsertBook.class}) @RequestBody BookVo bookVo) {
        bookService.addBook(bookVo);
        return JsonResult.success().toJSON();
    }


    /**
     * 书单管理
     *      --书单内容回显
     */
    @RequestMapping(value = "/admin/editBook/{book_id}")
    public String editBook(@PathVariable("book_id") Long book_id, Model model) {
        Book book = bookService.findBookById(book_id);
        model.addAttribute("name", book.getName());
        model.addAttribute("description",book.getDescription());
        model.addAttribute("author",book.getAuthor());
        model.addAttribute("progress",book.getProgress());
        model.addAttribute("douban_link",book.getDoubanLink());
        model.addAttribute("purchase_link",book.getPurchaseLink());
        model.addAttribute("pdf_link",book.getPdfLink());
        model.addAttribute("id", book_id);
        return "admin/booklist-update";
    }

    /**
     * 书单管理
     *      --更新书单内容
     */
    @PostMapping(value = "/admin/updateBook", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String updateBook(BookVo bookVo)
    {
        bookService.updateBook(bookVo);
        return JsonResult.success().toJSON();
    }

    /**
     * 书单管理
     *      --删除书籍
     */
    @PostMapping(value = "/admin/deleteBook/{book_id}")
    @AdminCheck(value="ROLE_ADMIN")
    @ResponseBody
    public String deleteBookByBookId(@PathVariable("book_id") Long book_id) {
        bookService.deleteBook(book_id);
        return JsonResult.success().toJSON();
    }

    /**
     * 书单管理
     *      --批量删除
     */
    @PostMapping(value = "/admin/deleteBookBatch")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String deleteBookBatch(@RequestParam("ids") Long[] ids) {
        //我比较懒就不写批量删除sql语句了
        for (Long id : ids) {
            deleteBookByBookId(id);
        }
        return JsonResult.success().toJSON();
    }

    /**
     * 友链管理
     *      --分页显示友链列表
     */
    @GetMapping(value = "/admin/findAllFriendLink", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findAllFriendLinkByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize)
    {

        int friendLinkNum = friendshipLinkService.findFriendLinkNum();
        DataMap data = friendshipLinkService.findFriendLinkListByPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", friendLinkNum);
        return jsonData.toJSON();
    }

    /**
     * 友链管理
     *      --跳转到添加友链界面
     */
    @GetMapping(value = "/admin/addFriendshipLink")
    public String addFriendshipLink() {
        return "admin/friendshipLink-add";
    }

    /**
     * 友链管理
     *      --添加友链
     */
    @PostMapping(value = "/admin/insertFriendshipLink")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String insertFriendshipLink(@Validated({InsertLink.class})@RequestBody FriendshipLinkVo friendshipLinkVo) {
        friendshipLinkService.addFriendLink(friendshipLinkVo);
        return JsonResult.success().toJSON();
    }

    /**
     * 友链管理
     *      --内容回显
     */
    @RequestMapping(value = "/admin/editFriendshipLink/{link_id}")
    public String editFriendshipLink(@PathVariable("link_id") Long link_id, Model model) {
        FriendshipLink friendshipLink = friendshipLinkService.findFriendLinkById(link_id);
        model.addAttribute("name",friendshipLink.getName());
        model.addAttribute("title",friendshipLink.getTitle());
        model.addAttribute("logo",friendshipLink.getLogo());
        model.addAttribute("description",friendshipLink.getDescription());
        model.addAttribute("href",friendshipLink.getHref());
        model.addAttribute("id", link_id);
        return "admin/friendshipLink-update";
    }

    /**
     * 友链管理
     *      --更新友链内容
     */
    @PostMapping(value = "/admin/updateFriendshipLink", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String updateFriendshipLink(FriendshipLinkVo friendshipLinkVo)
    {
        friendshipLinkService.updateFriendLink(friendshipLinkVo);
        return JsonResult.success().toJSON();
    }

    /**
     * 友链管理
     *      --删除友链
     */
    @PostMapping(value = "/admin/deleteFriendshipLink/{link_id}")
    @ResponseBody
    @AdminCheck(value = "ROLE_ADMIN")
    public String deleteFriendshipLinkById(@PathVariable("link_id") Long link_id)
    {
        friendshipLinkService.deleteFriendLink(link_id);
        return JsonResult.success().toJSON();
    }

    /**
     * 友链管理
     *      --批量删除
     */
    @PostMapping(value = "/admin/deleteFriendshipLinkBatch")
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String deleteFirendshipLinkBatch(@RequestParam("ids") Long[] ids) {
        //我比较懒就不写批量删除sql语句了
        for (Long id : ids) {
            deleteFriendshipLinkById(id);
        }
        return JsonResult.success().toJSON();
    }

    /**
     * 漫画管理
     *      --分页显示漫画列表
     */
    @GetMapping(value = "/admin/findAllManga", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findAllMangaByPage(@RequestParam("page") String pageNum, @RequestParam("limit") String pageSize)
    {

        int mangaNum = mangaService.findMangaNum();
        DataMap data = mangaService.findMangaListByPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count", mangaNum);
        return jsonData.toJSON();
    }

    /**
     * 漫画管理
     *      --内容回显
     */
    @RequestMapping(value = "/admin/editManga/{manga_id}")
    public String editManga(@PathVariable("manga_id") Long manga_id, Model model) {
        Manga manga = mangaService.findMangaByMangaId(manga_id);
        model.addAttribute("cnTitle",manga.getCntitle());
        model.addAttribute("url",manga.getUrl());
        model.addAttribute("id", manga_id);
        return "admin/manga-update";
    }

    /**
     * 漫画管理
     *      --更新漫画内容
     */
    @PostMapping(value = "/admin/updateManga", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @AdminCheck(value="ROLE_ADMIN")
    public String updateManga(MangaVo mangaVo)throws Exception
    {
        mangaService.updateManga(mangaVo);
        mangaService.refreshCache();
        mangaService.getMangaListRedis("updateByDataBase");
        return JsonResult.success().toJSON();
    }


    /**
     * 缓存管理
     *      --漫画缓存管理
     */
    @GetMapping(value = "/admin/refreshMangaCache")
    @ResponseBody
    @AdminCheck(value = "ROLE_ADMIN")
    public String refreshMangaCache()
    {
        mangaService.refreshCache();
        return JsonResult.success().toJSON();
    }


    /**
     * 缓存管理
     *      --动漫缓存管理
     */
    @GetMapping(value = "/admin/refreshAnimeCache")
    @ResponseBody
    @AdminCheck(value = "ROLE_ADMIN")
    public String refreshAnimeCache()
    {
        animeService.refreshAnimeCache();
        return JsonResult.success().toJSON();
    }

    /**
     * 缓存管理
     *      --音乐缓存管理
     */
    @GetMapping(value = "/admin/refreshMusicCache")
    @ResponseBody
    @AdminCheck(value = "ROLE_ADMIN")
    public String refreshMusicCache()
    {
        musicService.refreshMusicCache();
        return JsonResult.success().toJSON();
    }

    /**
     * 缓存管理
     *      --博客缓存管理
     * @return
     */
    @GetMapping(value = "/admin/refreshBlogCache")
    @ResponseBody
    @AdminCheck(value = "ROLE_ADMIN")
    public String refreshBlogCache()
    {
        articleService.refreshBlogCache();
        return JsonResult.success().toJSON();
    }

    /**
     * 缓存管理
     *      --书单缓存管理
     * @return
     */
    @GetMapping(value = "/admin/refreshBookListCache")
    @ResponseBody
    @AdminCheck(value = "ROLE_ADMIN")
    public String refreshBookListCache()
    {
        bookService.refreshBookCache();
        return JsonResult.success().toJSON();
    }

    /**
     * 缓存管理
     *      --清楚所有缓存
     */
    @GetMapping(value = "/admin/refreshAllCache")
    @ResponseBody
    @AdminCheck(value = "ROLE_ADMIN")
    public String refreshAllCache()
    {
        authService.refreshCache();
        return JsonResult.success().toJSON();
    }
}
