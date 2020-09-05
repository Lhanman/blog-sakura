package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.dto.CommentDto;
import com.Lhan.personal_blog.mapper.ArticleCommentMapper;
import com.Lhan.personal_blog.mapper.ArticleInfoMapper;
import com.Lhan.personal_blog.mapper.CommentMapper;
import com.Lhan.personal_blog.mapper.UserMapper;
import com.Lhan.personal_blog.pojo.*;
import com.Lhan.personal_blog.redis.StringRedisServiceImpl;
import com.Lhan.personal_blog.service.CommentService;
import com.Lhan.personal_blog.service.UserService;
import com.Lhan.personal_blog.util.*;
import com.Lhan.personal_blog.vo.CommentVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {


    @Resource
    CommentMapper commentMapper;

    @Resource
    ArticleCommentMapper articleCommentMapper;

    @Resource
    UserService userService;

    @Resource
    UserMapper userMapper;

    @Resource
    ArticleInfoMapper articleInfoMapper;

    @Autowired
    StringRedisServiceImpl stringRedisService;

    private static IpLocationUtil ipUtil;
    static {
        ipUtil = new IpLocationUtil();
        ipUtil.init();

    }

    @Override
    @Transactional
    public void addComment(Comment comment,Long articleId) {
        if (comment.getAnswerId().equals(comment.getRespondentId()))
        {
            comment.setIsRead(true);
        }
        else
        {
            comment.setIsRead(false);
        }
        commentMapper.insert(comment);
        //redis中存入该用户评论未读数
        addNotReadComment(comment);


        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticleId(articleId);
        articleComment.setCreateBy(DateFormatUtil.dateFormat(new Date()));
        articleComment.setCommentId(comment.getId());
        articleComment.setIsEffective(true);
        articleCommentMapper.insert(articleComment);

    }

    /**
     * 通过文章id,负责填充文章的评论
     * @param articleId
     * @return
     */
    @Override
    public DataMap findCommentByArticleId(Long articleId) {
        ArticleCommentExample example = new ArticleCommentExample();
        example.or().andArticleIdEqualTo(articleId);
        example.setOrderByClause("id desc");
        List<ArticleComment> articleCommentList = articleCommentMapper.selectByExample(example);
        List<Comment> commentList = new ArrayList<>();
        for (ArticleComment articleComment : articleCommentList)
        {
            Comment comment = new Comment();
            comment = commentMapper.selectByPrimaryKey(articleComment.getCommentId());
            if (comment.getPid() == 0)
            {
                commentList.add(comment);
            }
        }
        if (commentList.isEmpty())
        {
            return DataMap.success().setData(new JSONArray());
        }
        JSONArray commentJsonArray = new JSONArray();
        JSONArray replyJsonArray;
        JSONObject commentJsonObject;
        JSONObject replyJsonObject;
        List<Comment> replyList;

        for (Comment comment : commentList)
        {
            commentJsonObject = new JSONObject();

            replyList = commentMapper.findCommentByPid(comment.getId());

            replyJsonArray = new JSONArray();

            //封装所有评论中的回复
            for (Comment reply : replyList)
            {
                replyJsonObject = new JSONObject();
                replyJsonObject.put("id",reply.getId());
                replyJsonObject.put("answerer",userService.findUsernameByUserId(reply.getAnswerId()));
                replyJsonObject.put("commentDate",reply.getCommentDate());
                replyJsonObject.put("commentContent",reply.getContent());
                replyJsonObject.put("respondent",userService.findUsernameByUserId(reply.getRespondentId()));
                replyJsonObject.put("avatarImgUrl",userService.findAvatarImgUrlByUserId(reply.getAnswerId()));
                replyJsonArray.add(replyJsonObject);
            }

            //封装评论
            commentJsonObject.put("id",comment.getId());
            commentJsonObject.put("answerer",userService.findUsernameByUserId(comment.getAnswerId()));
            commentJsonObject.put("commentDate",comment.getCommentDate());
            commentJsonObject.put("commentContent",comment.getContent());
            commentJsonObject.put("replies",replyJsonArray);
            commentJsonObject.put("avatarImgUrl",userService.findAvatarImgUrlByUserId(comment.getAnswerId()));

            commentJsonArray.add(commentJsonObject);
        }
        return DataMap.success().setData(commentJsonArray);
    }

    /**
     *  回复的回复 的填充功能
     * @param comment
     * @param answerer
     * @param respondent
     * @return
     */
    @Override
    public DataMap replyReplyReturn(Comment comment,String answerer,String respondent) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",comment.getId());
        jsonObject.put("answerer",answerer);
        jsonObject.put("respondent",respondent);
        jsonObject.put("commentContent",comment.getContent());
        jsonObject.put("commentDate",comment.getCommentDate());
        jsonObject.put("avatarImgUrl",userService.findAvatarImgUrlByUserId(comment.getAnswerId()));

        return DataMap.success().setData(jsonObject);
    }

    @Override
    public int commentNum() {
        return commentMapper.commentCount();
    }

    @Override
    public int findCommentNumByArticleId(Long article_id) {
        return articleCommentMapper.findCommentNumByArticleId(article_id);
    }

    @Override
    public DataMap findAllCommentByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        //创建一个List<包装类>，包装评论详细信息
        List<CommentDto> commentDtoList = new ArrayList<>();

        //查询所以评论的基础信息
        CommentExample example = new CommentExample();
        example.or();
        List<Comment> commentList = commentMapper.selectByExample(example);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);

        for (Comment comment : commentList)
        {
            CommentDto commentDto = new CommentDto();
            commentDto.setId(comment.getId());
            commentDto.setIp(comment.getIp());
            commentDto.setComment_date(comment.getCommentDate());
            commentDto.setContent(comment.getContent());
            //填充博客标题信息
            ArticleCommentExample example1 = new ArticleCommentExample();
            example1.or().andCommentIdEqualTo(comment.getId());
            ArticleComment articleComment = articleCommentMapper.selectByExample(example1).get(0);
            ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articleComment.getArticleId());
            commentDto.setArticle_id(articleInfo.getId());
            commentDto.setTitle(articleInfo.getTitle());

            //填充评论者用户名信息
            commentDto.setUsername(userService.findUsernameByUserId(comment.getAnswerId()));

            commentDtoList.add(commentDto);
        }

        JSONArray commentJsonArray = JSONArray.fromObject(commentDtoList);

        return DataMap.success().setData(commentJsonArray);

    }

    @Override
    public JsonResultAdmin.JsonData findCommentLikeUserName(int pageNum, int pageSize, String username) {
        User user = userMapper.findUserLikeUserName(username);
        PageHelper.startPage(pageNum,pageSize);

        //创建一个List<包装类>，包装评论详细信息
        List<CommentDto> commentDtoList = new ArrayList<>();

        CommentExample example = new CommentExample();
        example.or().andAnswerIdEqualTo(user.getId());
        List<Comment> commentList = commentMapper.selectByExample(example);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);

        for (Comment comment : commentList)
        {
            CommentDto commentDto = new CommentDto();
            commentDto.setId(comment.getId());
            commentDto.setIp(comment.getIp());
            commentDto.setComment_date(comment.getCommentDate());
            commentDto.setContent(comment.getContent());
            //填充博客标题信息
            ArticleCommentExample example1 = new ArticleCommentExample();
            example1.or().andCommentIdEqualTo(comment.getId());
            ArticleComment articleComment = articleCommentMapper.selectByExample(example1).get(0);
            ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articleComment.getArticleId());
            commentDto.setTitle(articleInfo.getTitle());

            //填充评论者用户名信息
            commentDto.setUsername(userService.findUsernameByUserId(comment.getAnswerId()));

            commentDtoList.add(commentDto);
        }
        JSONArray commentJsonArray = JSONArray.fromObject(commentDtoList);
        DataMap data = DataMap.success().setData(commentJsonArray);

        JsonResultAdmin.JsonData jsonData = JsonResultAdmin.build(data);
        jsonData.put("count",pageInfo.getTotal());
        return jsonData;
    }

    @Override
    @Transactional
    public void deleteCommentByComment_id(Long comment_id) {
        //删除tbl_comment表
        commentMapper.deleteByPrimaryKey(comment_id);
        //删除tbl_article_comment
        ArticleCommentExample example = new ArticleCommentExample();
        example.or().andCommentIdEqualTo(comment_id);
        ArticleComment articleComment = articleCommentMapper.selectByExample(example).get(0);
        articleCommentMapper.deleteByPrimaryKey(articleComment.getId());
    }

    @Override
    public Result getUserComment(int pageNum, int pageSize, String username) {
        Long userId = userService.findUserIdByUsername(username);
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> commentList = commentMapper.getUserCommentByRespondentId(userId);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);

        List<CommentVo> commentVoList = new ArrayList<>();

        for(Comment comment : commentList)
        {
            ArticleCommentExample example1 = new ArticleCommentExample();
            example1.or().andCommentIdEqualTo(comment.getId());
            ArticleComment articleComment = articleCommentMapper.selectByExample(example1).get(0);
            User answer = userService.findUserByUserId(comment.getAnswerId());
            CommentVo commentVo = new CommentVo();
            commentVo.setAuthorAvatar(answer.getAvatarimgUrl());
            commentVo.setPostsTitle(articleInfoMapper.selectByPrimaryKey(articleComment.getArticleId()).getTitle());
            commentVo.setPostsId(articleComment.getArticleId());
            commentVo.setCreateTime(DateFormatUtil.getTimeStampByString(comment.getCommentDate()));
            commentVo.setIsRead(comment.getIsRead());
            commentVo.setAuthorName(answer.getUsername());
            commentVo.setId(comment.getId());
            commentVo.setPid(comment.getPid());
            commentVoList.add(commentVo);
        }

        com.Lhan.personal_blog.common.base.PageInfo page = PageUtil.initPageInfo(pageNum,pageSize
                ,(int)pageInfo.getTotal());

        return Result.createWithPaging(commentVoList,page)
                .setExtra(commentMapper.findCountIsReadByRespondentId(userId));

    }

    @Override
    public DataMap readOneComment(Long id) {
        Comment comment = commentMapper.selectByPrimaryKey(id);
        comment.setIsRead(true);
        commentMapper.updateByPrimaryKeySelective(comment);
        return DataMap.success();
    }

    @Override
    public DataMap readAllComment(String username) {
        Long respondentId = userService.findUserIdByUsername(username);
        if (stringRedisService.get(String.valueOf(respondentId)) == null)
        {
            return DataMap.success(CodeType.ALREADY_READ_ALL);
        }
        commentMapper.readCommentByRespondentId(respondentId);
        return DataMap.success();
    }

    @Override
    public DataMap getUserNews(String username) {
        Long user_id = userService.findUserIdByUsername(username);

        int commentNum = commentMapper.findCountIsReadByRespondentId(user_id);
        return DataMap.success().setData(commentNum);
    }

    @Override
    public Result getPostsCommentsByPostsIdList(CommentVo commentVo) {
        commentVo = Optional.ofNullable(commentVo).orElse(new CommentVo());
        int pageNum = Optional.ofNullable(commentVo.getPage()).orElse(1);
        int pageSize = Optional.ofNullable(commentVo.getSize()).orElse(5);
        PageHelper.startPage(pageNum,pageSize);
        ArticleCommentExample example = new ArticleCommentExample();
        example.or().andArticleIdEqualTo(commentVo.getPostsId());
        example.setOrderByClause("id desc");
        List<ArticleComment> articleCommentList = articleCommentMapper.selectByExample(example);

        PageInfo<ArticleComment> pageInfo = new PageInfo<>(articleCommentList);

        List<CommentVo> commentVoList = new ArrayList<>();
        List<Comment> commentList = new ArrayList<>();
        //获取评论需要的信息
        for (ArticleComment articleComment : articleCommentList)
        {
            Comment comment = new Comment();
            comment = commentMapper.selectByPrimaryKey(articleComment.getCommentId());
            if (comment.getPid() == 0)
            {
                commentList.add(comment);
            }
        }
        if (commentList.isEmpty())
        {
            return Result.createWithPaging(commentVoList,PageUtil.initPageInfo(pageNum,pageSize
                    ,(int) pageInfo.getTotal()));
        }
        List<Comment> replyList;

        for (Comment comment : commentList) {
            CommentVo commentVo2 = new CommentVo();
            replyList = commentMapper.findCommentByPid(comment.getId());
            Map commentBrowserAndOS = BrowserStatisticsUtil.getBrowserAndOSByUA(comment.getUserAgent());
            String p_operateSystem = "";
            String p_browserName = "";
            String p_browserVersion = "";
            if (commentBrowserAndOS != null)
            {
                p_operateSystem = (String) commentBrowserAndOS.get("operatingSystem");
                p_browserName = (String) commentBrowserAndOS.get("browser_name");
                p_browserVersion = (String) commentBrowserAndOS.get("browser_version");
            }
            String p_city = ipUtil.getIpLocation(comment.getIp()).getCountry();
            String p_area = ipUtil.getArea(comment.getIp());

            List<CommentVo> replies = new ArrayList<>();
            //封装评论中的回复
            replyList.forEach(reply -> {
                Map replyBrowserAndOS = BrowserStatisticsUtil.getBrowserAndOSByUA(reply.getUserAgent());
                String operateSystem = "";
                String browserName = "";
                String browserVersion = "";

                if (replyBrowserAndOS != null)
                {
                    operateSystem = (String) replyBrowserAndOS.get("operatingSystem");
                    browserName = (String) replyBrowserAndOS.get("browser_name");
                    browserVersion = (String) replyBrowserAndOS.get("browser_version");
                }
                String city = ipUtil.getIpLocation(reply.getIp()).getCountry();
                String area = ipUtil.getArea(reply.getIp());
                CommentVo commentVo1 = new CommentVo();
                commentVo1.setAuthorName(userService.findUsernameByUserId(reply.getAnswerId()));
                commentVo1.setContent(reply.getContent());
                commentVo1.setParentUserName(userService.findUsernameByUserId(reply.getRespondentId()));
                commentVo1.setAuthorAvatar(userService.findAvatarImgUrlByUserId(reply.getAnswerId()));
                commentVo1.setCreateTime(DateFormatUtil.getTimeStampByString(reply.getCommentDate()));
                commentVo1.setBrowser_name(browserName);
                commentVo1.setBrowser_icon(getBrowserIcon(browserName));
                commentVo1.setBrowser_version(browserVersion);
                commentVo1.setOperating_system(operateSystem);
                commentVo1.setOperation_icon(getOperateIcon(operateSystem));
                commentVo1.setCity(city);
                commentVo1.setArea(area);
                commentVo1.setId(reply.getId());
                replies.add(commentVo1);
            });
            commentVo2.setId(comment.getId());
            commentVo2.setAuthorName(userService.findUsernameByUserId(comment.getAnswerId()));
            commentVo2.setContent(comment.getContent());
            commentVo2.setAuthorAvatar(userService.findAvatarImgUrlByUserId(comment.getAnswerId()));
            commentVo2.setCreateTime(DateFormatUtil.getTimeStampByString(comment.getCommentDate()));
            commentVo2.setBrowser_name(p_browserName);
            commentVo2.setBrowser_icon(getBrowserIcon(p_browserName));
            commentVo2.setBrowser_version(p_browserVersion);
            commentVo2.setOperating_system(p_operateSystem);
            commentVo2.setOperation_icon(getOperateIcon(p_operateSystem));
            commentVo2.setCity(p_city);
            commentVo2.setArea(p_area);
            commentVo2.setReplies(replies);
            commentVoList.add(commentVo2);
        }
        com.Lhan.personal_blog.common.base.PageInfo page = PageUtil.initPageInfo(pageNum,pageSize
                ,(int)pageInfo.getTotal());
        return Result.createWithPaging(commentVoList,page);
    }

    @Override
    public Result savePostsComment(CommentVo commentVo) {

        return null;
    }


    /**
     * 发送评论成功后，往redis中加入一个未读评论数
     * @param comment
     */

    private void addNotReadComment(Comment comment)
    {
        if (comment.getRespondentId() != comment.getAnswerId())
        {
            boolean isExistKey = stringRedisService.hasKey(comment.getRespondentId()+StringUtil.BLANK);
            if (!isExistKey)
            {
                stringRedisService.set(String.valueOf(comment.getRespondentId()),1);
            }
            else
            {
                stringRedisService.stringIncrement(String.valueOf(comment.getRespondentId()),1);
            }
        }
    }

    /**
     * 获取浏览器图标路径
     * @param browserName
     * @return
     */

    private String getBrowserIcon(String browserName)
    {
        if (browserName.contains("Chrome"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/chrome.svg";
        }
        else if (browserName.contains("QQ"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/QQBrowser.svg";
        }
        else if (browserName.contains("Safari"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/safari.svg";
        }
        else if (browserName.contains("Firefox"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/firefox.svg";
        }
        else if (browserName.contains("Opera"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/opera15.svg";
        }
        else if (browserName.contains("Edge"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/edge.svg";
        }
        else if (browserName.contains("SouGou"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/sogou.svg";
        }
        else
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/unknow.svg";
        }
    }


    /**
     * 获取操作系统图标路径
     * @param operateSystem
     * @return
     */
    private String getOperateIcon(String operateSystem)
    {
        if (operateSystem.contains("Windows 10"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/windows_win10.svg";
        }
        else if (operateSystem.contains("Android"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/android.svg";
        }
        else if (operateSystem.contains("Iphone"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/iphone.svg";
        }
        else if (operateSystem.contains("Linux"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/linux.svg";
        }

        else if (operateSystem.contains("Windows 7"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/windows_win7.svg";
        }

        else if (operateSystem.contains("Mac"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/iphone.svg";
        }

        else if (operateSystem.contains("Windows Server 2003"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/windows_win7.svg";
        }

        else if (operateSystem.contains("iPad"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/iphone.svg";
        }

        else if (operateSystem.toLowerCase().contains("ubuntu"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/ubuntu.svg";
        }
        else
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/unknow.svg";
        }
    }
}
