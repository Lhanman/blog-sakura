package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.aspect.annotation.PermissionCheck;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.dto.ArticleTagDto;
import com.Lhan.personal_blog.entity.security.JwtUser;
import com.Lhan.personal_blog.service.security.CustomUserServiceImpl;
import com.Lhan.personal_blog.util.JwtUtil;
import com.Lhan.personal_blog.util.StringUtil;
import com.Lhan.personal_blog.util.TransformCodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责页面跳转
 *
 */
@Controller
public class BackController extends BaseController{


    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomUserServiceImpl customUserService;

//    /**
//     * 跳转首页
//     *
//      */
//    @GetMapping("/")
//    public String index(HttpServletRequest request, HttpServletResponse response,Model model)
//    {
//        //通过thymeleaf给标签云添加信息
//        List<ArticleTagDto> articleTagDtoList = tagService.findTagCloudForIndex();
//        List<String> tagNameList = new ArrayList<>();
//        for (ArticleTagDto articleTagDto : articleTagDtoList)
//        {
//            tagNameList.add(articleTagDto.getTag_name());
//        }
//        model.addAttribute("tagNameList",tagNameList);
//
//
//        response.setHeader("Access-Controller-Allow-Origin","*");
//        response.setHeader("lastUrl",(String)request.getSession().getAttribute("lastUrl"));
//
//        return "index";
//    }
//
//    /**
//     * 跳转到关于界面
//     *
//     */
//    @GetMapping("/aboutme")
//    public String aboutme(HttpServletRequest request)
//    {
//        request.getSession().removeAttribute("lastUrl");
//        return "about";
//    }

    @GetMapping("/toLogin")
    @ResponseBody
    public void toLogin(HttpServletRequest request)
    {
        //保存跳转页面的Url
        String lastUrl = request.getHeader("Referer");
        if (lastUrl != null)
        {
            try {
                URL url = new URL(lastUrl);
                if (!"/".equals(url.getPath()))
                {
                    request.getSession().setAttribute("lastUrl",lastUrl);
                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        }
    }


//    @GetMapping("/register")
//    public String register()
//    {
//        return "register";
//    }
//
    @GetMapping("/login")
    public String login()
    {
        return "admin/login";
    }

    /**
     * 写博客
     * @param request
     * @return
     */

    @GetMapping("/editor")
    public String editor(HttpServletRequest request)
    {
        request.getSession().removeAttribute("lastUrl");
        String id = request.getParameter("id");
        if (!StringUtil.BLANK.equals(id))
        {
            request.getSession().setAttribute("id",id);
        }
        return "editor";

    }
//
//    /**
//     * 跳转到文章显示页
//     *
//     */
//    @GetMapping("/article/{articleId}")
//    public String show(@PathVariable("articleId") Long articleId,
//                       HttpServletResponse response,
//                       Model model,HttpServletRequest request)
//    {
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
//        request.getSession().removeAttribute("lastUrl");
//
//        //将文章id存入响应头
//        response.setHeader("articleId",String.valueOf(articleId));
//        return "show";
//    }

    /**
     * 通过分类查博客
     * @param categoryId
     * @param request
     * @param response
     * @return
     */
//    @GetMapping("/category/{categoryId}")
//    public String CategoryResult(@PathVariable("categoryId") Long categoryId,
//                                 HttpServletRequest request,
//                                 HttpServletResponse response,
//                                 Model model)
//    {
//
//        //通过thymeleaf给标签云添加信息
//        List<ArticleTagDto> articleTagDtoList = tagService.findTagCloudForIndex();
//        List<String> tagNameList = new ArrayList<>();
//        for (ArticleTagDto articleTagDto : articleTagDtoList)
//        {
//            tagNameList.add(articleTagDto.getTag_name());
//        }
//        model.addAttribute("tagNameList",tagNameList);
//
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
//        request.getSession().removeAttribute("lastUrl");
//
//
//        //将分类id存入响应头
//        response.setHeader("categoryId",String.valueOf(categoryId));
//        return "category-result";
//    }
//
//    /**
//     * 分类
//     * @return
//     */
//    @GetMapping("/categories")
//    public String categoryList()
//    {
//        return "categories";
//    }
//
//    /**
//     * 联系我
//     * @return
//     */
//    @GetMapping("/contactme")
//    public String contact()
//    {
//        return "contact";
//    }
//
//    /**
//     * 跳转到用户编辑页
//     */
//    @GetMapping("/user")
//    public String user(HttpServletRequest request)
//    {
//        return "user";
//    }

//    /**
//     * 追番页面
//     *
//     * @return
//     */
//    @GetMapping("/anime")
//    public String anime()
//    {
//        return "anime";
//    }
//
//    /**
//     * 跳转到标签页面
//     */
//    @GetMapping("/tags")
//    public String tags(HttpServletRequest request,HttpServletResponse response)
//    {
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
//        request.getSession().removeAttribute("lastUrl");
//        String tag = request.getParameter("tag");
//
//        if (tag != null && !tag.equals(StringUtil.BLANK))
//        {
//            response.setHeader("tag", TransformCodingUtil.stringToUnicode(tag));
//        }
//        return "tag-cloud";
//    }

    /**
     * 跳转至后台管理系统
     *
     */
    @GetMapping("/setPermission")
    @ResponseBody
    public Result superAdmin(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();

        String token=null;
        for (Cookie cookie : cookies)
        {
            switch (cookie.getName())
            {
                case "Lhan-blog-token":
                    token = cookie.getValue();
                    break;
                default:
                    break;
            }
        }

        String username = jwtUtil.getUsernameFromToken(token);
        String phone = jwtUtil.getPhone(token);
        System.out.println(username);
        if (jwtUtil.containToken(username,token) && username != null
                )
        {

            JwtUser jwtUser = (JwtUser)customUserService.loadUserByUsername(phone);
            if (jwtUtil.validateToken(token,jwtUser))
            {
                System.out.println("已赋予权限");
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(jwtUser,null,jwtUser.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        return Result.createWithSuccessMessage();
    }

    @GetMapping("/superAdmin")
    public String admin(){
        return "admin/index";
    }




//    /**
//     * 测试
//     */
//    @GetMapping("/sakura")
//    public String sakura()
//    {
//        return "sakura/index";
//    }
}
