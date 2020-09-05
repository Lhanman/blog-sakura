/*
    为show接发请求的JavaScript
*/
var articleId ="";



// function getQueryVariable(variable) {
//     var query = window.location.search.substring(1);
//     var vars = query.split("&");
//     for (var i=0; i<vars.length; i++)
//     {
//         var pair = vars[i].split("=");
//         if (pair[0] == variable)
//         {
//             return pair[1];
//         }
//     }
//     return (false);
// }

//填充博客内容
function putInArticle(data) {
        $('.Lhan-article-footer').html('');
        $('#ArticleTitle').html(data['articleTitle']);
        $('.title').html(data['articleTitle']);
        $('#Category').html(data['articleCategory']);
        $('#articleCover').attr('src',data.articlePictureUrl);
        $(".commentNumber").html("&nbsp;"+data['commentNumber']);
        var tags=$('.cats');
        for(var i=0; i<data['articleTags'].length; i++)
        {
            var tag = $('<a href="/tags?tag='+ data['articleTags'][i].tagName +'">' +data['articleTags'][i].tagName + '</a>');
            tags.append(tag);
        }
        $('#PostTime').html(data.publishDate);
        $('.articleTraffic').html("&nbsp;"+data.articleTraffic);

        $('#mdText').text(data.articleContent);
        var wordsView;
        wordsView = editormd.markdownToHTML("wordsView",{
            htmlDecode:"true",
            emoji:true,
            taskList:true,
            tex:true,
            flowChart:true,
            sequenceDiagram:true
        });
        var articleFooter = $('<div class="end-logo">' +
            '<i class="am-icon-btn am-success am-icon-lg">End</i>' +
            '</div>' +
            '<hr class="layui-bg-orange">' +
            '<div class="two-article">' +
            '<span class="article-last">' +
            '</span>' +
            '<span class="article-next">' +
            '</span> ' +
            '</div>');
        $('.Lhan-article-footer').append(articleFooter);

        if (data.lastStatus === "200")
        {
            var articleLast200 =$('<i class="am-icon-angle-left am-icon-sm"></i>&nbsp;&nbsp;<a class="lastAndNext" ' +
                'href="/article/' + data.lastArticleId +'">' + data.lastArticleTitle + '</a>');
            $('.article-last').append(articleLast200);
        }
        else
        {
            var articleLast404 = $('<i class="am-icon-angle-left am-icon-sm"></i>&nbsp;&nbsp;<a  class="lastAndNext">' + data.lastInfo + '</a>');
            $('.article-last').append(articleLast404);
        }
        if(data.nextStatus == "200")
        {
            var articleNext200 = $('<a class="lastAndNext" href="/article/' + data.nextArticleId +'">' + data.nextArticleTitle + '</a>' + '&nbsp;&nbsp;<i class="am-icon-angle-right am-icon-sm"></i>');
            $('.article-next').append(articleNext200);
        }
        else
            {
                var articleNext404 = $('<a  class="lastAndNext">' + data.nextInfo + '</a>' + '&nbsp;&nbsp;<i class="am-icon-angle-right am-icon-sm"></i>');
                $('.article-next').append(articleNext404);
            }



        //选中所有需要放大的图片加入data-src属性
        $('#wordsView img').each(function (index) {
            if (!$(this).hasClass("emoji"))
            {
                var a = $(this).attr('src');
                $(this).attr("data-src",a);
                $(this).addClass("enlargePicture");
            }
        });

        //放大图片框架
        lightGallery(document.getElementById('wordsView'));
}

//填充文章评论信息和回复
function putInComment(data) {
    var comments = $("#comments").html("");

    if (data.length === 0) {
        var comment = $('<div class="comment">' +
            '<span class="noComment" style="color: black">还没有评论，快来抢沙发咯！</span>' +
            '</div>');
        comments.append(comment);
    } else {
        var allComments = $('<div class="all-comments"></div>');
        $.each(data, function (index, obj) {
            var visitorReplies = $('<div class="visitorReplies"></div>');
            $.each(obj['replies'], function (index1, obj1) {
                var visitorReply = $('<li class="single-comment-wrapper" id="p' + obj1['id'] + '"></li>');
                var visitorReplyWords = $('<div class="single-post-comment visitorReplyWords">' +
                    '<div class="layui-inline"><img src="' + obj1['avatarImgUrl'] + ' " class="layui-circle am-comment-avatar"> </div>' +
                    '<div class="comment-content">' +
                    '<div class="comment-author-name"> <h6 class="answerer">' + obj1['answerer'] + '</h6> <br><h6>&nbsp;&nbsp;&nbsp;&nbsp;回复了</h6>' +
                    '<h6 class="respondent">@'+obj1['respondent']+':</h6>' +
                    '' + obj1['commentContent'] + '</div>' +
                    '<a class="reply-btn replyPointer">' +
                    '<i class="replyReply fa fa-comment-o" onclick="clickReplyReply(this)" style="font-size: 120%">&nbsp;回复</i> ' +
                    '</a>' +
                    '</div>' +
                    '</div>')
                var visitorReplyTime = $('<div class="visitorReplyTime">' +
                    '<span class="visitorReplyTime2"><b>' + obj1['commentDate'] + '</b></span>' +
                    '</div>')
                visitorReply.append(visitorReplyWords);
                visitorReply.append(visitorReplyTime);
                visitorReplies.append(visitorReply);
            });
            var subCommentList = $('<div class="sub-comment-list"></div>')
            var moreComment = $('<div class="more-comment">' +
                '<a class="replyPointer">' +
                '<i class="moreComment fa fa-edit" onclick="clickMoreComment(this)"> 添加新评论</i>' +
                '</a>' +
                '</div>')
            subCommentList.append(visitorReplies);
            subCommentList.append(moreComment);
            var subComment = $('<ul class="children"></ul>');
            if (obj['replies'].length !== 0) {
                subComment.append(subCommentList);
            }
            subComment.append($('<div class="reply-sub-comment-list am-animation-slide-bottom" style="display: none">' +
                '<div class="replyWord">' +
                '<div class="replyWordBtn">' +
                '<textarea class="replyWordTextarea am-form-field am-round" placeholder="写下你的评论..."></textarea> <br>' +
                '<button type="button" class="sendReplyWordBtn am-btn am-btn-success am-round" onclick="sendReplyWord(this)">发送</button> ' +
                '<button type="button" class="quitReplyWordBtn am-btn am-btn-sm" onclick="quitReply(this)">取消</button> ' +
                '</div>' +
                '</div>' +
                '</div>'));
            var comment = $('<li class="single-comment-wrapper" ></li>')
            var commentInfo = $('<div class="single-post-comment"><div class="commentInfo">' +
                '<div class="layui-inline"><img src="' + obj['avatarImgUrl'] + '" alt="" class="layui-circle am-comment-avatar"></div> ' +
                '</div>' +
                '<div class="comment-content">' +
                '<div class="comment-author-name"><h6 class="visitorName">' + obj['answerer'] + '</h6><span class="commentFloor"><h1>#' + (data.length - index) + '楼</h1></span>' +
                '<span class="commentDate"><b>' + obj['commentDate'] + '</b></span>' +
                '</div>' +
                '<div class="visitorSay">' + obj['commentContent']+' ' +
                '</div>' +
                '<a class="reply reply-btn replyPointer" onclick="clickReply(this)"><i class="am-icon-comment-o" style="font-size: 120%">&nbsp;回复</i>' +
                '</a>' +
                '</div>' +
                '</div>');
            comment.append(commentInfo);
            comment.append(subComment);
            var visitComment = $('<div class="visitorComment" id="p' + obj['id'] + '"></div>')
            visitComment.append(comment);
            visitComment.append($('<hr class="layui-bg-green">'))
            allComments.append(visitComment);
        });

        comments.append(allComments);

        $('#CommentNumber').html("&nbsp;"+data.length);
    }
}




    var respondent;

    //点击回复显示评论框
function clickReply(obj) {
        var $this = $(obj);
        $.ajax({
            type: 'get',
            url: '/isLogin',
            dataType: 'json',
            async: false,
            data: {},
            success: function (data) {
                if (data['status'] === 101) {
                    $.get("/toLogin", function (data, status, xhr) {
                        window.location.replace("/login");
                    });
                } else {
                    $this.parent().parent().next().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).val('');
                    $this.parent().parent().next().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).focus();
                    $this.parent().parent().next().find($('.reply-sub-comment-list')).css("display", "block");
                    // $this.parent().parent().parent().parent().find($('.reply-sub-comment-list')).find($('.YourName')).val('');
                    // $this.parent().parent().parent().parent().find($('.reply-sub-comment-list')).find($('.YourName')).focus();

                    respondent = $this.parent().find($('.visitorName')).html();
                    }
                },
                    error: function () {

                    }

                });
            }

        //添加新评论显示评论框
        function clickMoreComment(obj) {
            var $this = $(obj);
            $.ajax({
                type: 'get',
                url: '/isLogin',
                dataType: 'json',
                async: false,
                data: {},
                success: function (data) {
                    if (data['status'] === 101) {
                        $.get("/toLogin", function (data, status, xhr) {
                            window.location.replace("/login");
                        });
                    } else {
                        $this.parent().parent().parent().parent().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).val('');
                        $this.parent().parent().parent().parent().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).focus();
                        $this.parent().parent().parent().parent().find($('.reply-sub-comment-list')).css("display", "block");

                        respondent = $this.parent().parent().parent().parent().parent().find($('.visitorName')).html();
                    }
                },
                error: function () {

                }
            });
        }

        //评论中的回复的回复按钮显示评论框
        function clickReplyReply(obj) {
            var $this = $(obj);

            $.ajax({
                type: 'get',
                url: '/isLogin',
                dataType: 'json',
                async: false,
                data: {},
                success: function (data) {
                    if (data['status'] === 101) {
                        $.get("/toLogin", function (data, status, xhr) {
                            window.location.replace("/login");
                        });
                    }else {
                        respondent = $this.parent().parent().parent().find($('.answerer')).html();
                        respondent = respondent.replace(/\s/ig,'');
                        $this.parent().parent().parent().parent().parent().parent().parent().find($('.reply-sub-comment-list')).css("display", "block");
                        $this.parent().parent().parent().parent().parent().parent().parent().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).focus();
                        $this.parent().parent().parent().parent().parent().parent().parent().find($('.reply-sub-comment-list')).find($('.replyWordTextarea'))
                            .val('@'+respondent+' ');

                    }
                },
                error: function () {

                }
            });
        }

        //点击取消隐藏评论框
        function quitReply(obj) {
            var $this = $(obj);
            $this.parent().find($('.replyWordTextarea')).val('');
            $this.parent().parent().parent().css("display", "none");
        }


        //发送评论中的回复
       function sendReplyWord(obj) {
            var $this = $(obj);
            var replyContent = $this.parent().find($('.replyWordTextarea')).val();
            var pid = $this.parent().parent().parent().parent().parent().parent().attr("id");
            if (replyContent === "") {
                layer.msg('我没看清楚你回复了什么鸭！',{
                    icon : 5
                })
            } else {
                respondent = respondent.replace(/\s/ig,'');
                $.ajax({
                    type: 'POST',
                    url: 'comment/publishReply',
                    dataType: 'json',
                    data: {
                        replyContent: replyContent,
                        pId: pid,
                        respondent: respondent,
                        articleId: articleId
                    },
                    success: function (data) {
                        if (data['status'] === 101) {
                            $.get("/toLogin", function (data, status, xhr) {

                                layer.alert("未登录，登录才可以评论", {
                                    icon: 5
                                }, function () {
                                    window.location.replace("/login");
                                });
                            });
                        }
                        else if (data['status'] === 501)
                        {

                            layer.msg("评论内容不能为空",{
                                icon:7
                            })
                        }
                        else {
                            var sub_comment = $this.parent().parent().parent().parent();
                            var visitorReply = $('<li class="single-comment-wrapper" id="p' + data['data']['id'] + ' "></li>');
                            var visitorReplyWord = $('<div class="single-post-comment visitorReplyWords">' +
                                '<div class="layui-inline"> ' +
                                '<img src="'+data['data']['avatarImgUrl']+'" alt="" class="layui-circle am-comment-avatar">' +
                                '</div>' +
                                '<div class="comment-content">' +
                                '<div class="comment-author-name"> <h6 class="answerer">'+data['data']['answerer']+'</h6><br><h6>&nbsp;&nbsp;&nbsp;&nbsp;回复了</h6>' +
                                '<h6 class="respondent">@'+data['data']['respondent']+'</h6>:' +
                                '' + data['data']['commentContent'] + '</div>' +
                                '<a class="reply-btn replyPointer">' +
                                '<i class="replyReply fa fa-comment-o" onclick="clickReplyReply(this)">&nbsp;回复</i> ' +
                                '</a>' +
                                '</div>' +
                                '</div>');
                            var visitorReplyTime = $('<div class="visitorReplyTime">' +
                                '<span class="visitorReplyTime2">' + data['data']['commentDate'] + '</span>' +
                                '</div>');

                            visitorReply.append(visitorReplyWord);
                            visitorReply.append(visitorReplyTime);

                            if (sub_comment.find($('.visitorReplies')).length > 0) {
                                sub_comment.find($('.visitorReplies')).append(visitorReply);
                            } else {
                                var visitorReplies = $('<div class="visitorReplies"></div>');
                                var sub_comment_list = $('<div class="sub-comment-list"></div>');
                                visitorReplies.append(visitorReply);
                                sub_comment_list.append(visitorReplies);
                                sub_comment_list.append($('<div class="more-comment">' +
                                    '<a class="replyPointer">' +
                                    '<i class="moreComment fa fa-edit" onclick="clickMoreComment(this)"> 添加新评论</i> ' +
                                    '</a>' +
                                    '</div>'));
                                sub_comment.prepend(sub_comment_list);
                            }

                            $this.parent().find($('.replyWordTextarea')).val();
                            $this.parent().parent().parent().css("display", "none");

                        }
                    },
                    error: function () {
                        alert("回复失败");
                    }

                })
            }
        }
window.onload = function () {
    $.ajax({
        type: 'HEAD',//获取头信息
        url: window.location.href,
        async: false,
        success: function (data, status, xhr) {
            articleId = xhr.getResponseHeader("articleId");
        }
    });

    $.ajax({
        type: 'get',
        url: 'comment/getAllComment',
        dataType: 'json',
        data: {
            articleId: articleId
        },
        success: function (data) {
            putInComment(data['data']);

        },
        error: function () {
            alert("错误啦!");
        }
    });

    $.ajax({
        type: 'GET',
        url: '/getArticleById',
        dataType: 'json',
        data: {
            articleId: articleId
        },
        success: function (data) {

            if (data['status'] === 0) {
                putInArticle(data['data'])
            } else {
                $('.content').html('');
                var error = $('<div class="article"><div class="Lhan-article-top"><div class="error">' +
                    '<img src="assets/images/blogNotFind.jpg">' +
                    '<p>没有找到这篇文章哦</p>' +
                    '<p>可能不小心被博主手残删掉了吧</p>' +
                    '<div class="row">' +
                    '</div>' +
                    '</div></div></div>');
                $('.content').append(error);
                $("#comments").html("");

            }

        },
        error: function () {
            window.location.href = "/404";
        }
    });
};




