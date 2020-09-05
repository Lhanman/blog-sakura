/*
为index.html写的JavaScript

 */
window.onload = function () {

    $.ajax({
        type: "get",
        url: "articles/listGeekForIndex",
        datatype: "json",
        success:function (json) {
            $.each(json,function (i, item) {
                //填充极客博客的文章信息
                var articleInfo = document.querySelector("#GeekArticleInfo"+i);
                if (articleInfo == null)
                {
                    alert("JavaScript无法取出元素!!!")
                }
                if (i === 0)
                {
                    var GeekBigImg = document.getElementById("GeekBigImg0");
                    // var GeekBigImg = document.querySelector("#GeekBigImg");
                    jQuery(GeekBigImg).attr("style","background-image:url("+item.pictureUrl+");");
                    articleInfo.querySelector("#title0").innerHTML = item.title;
                    articleInfo.querySelector("#PostTime0").innerHTML = "&nbsp;"+item.createBy;
                    articleInfo.querySelector("h6").innerHTML = item.id;
                    articleInfo.querySelector("#commentNum0").innerHTML = "&nbsp;"+item.commentNum + "&nbsp;条评论";
                    articleInfo.querySelector(".traffic0").innerHTML = "&nbsp;"+item.traffic +"&nbsp;热度";
                    $.each(item.articleTagNames,function (i,tagName) {
                        jQuery(articleInfo.querySelector(".cats")).append($('<i class="fa fa-tag"></i><a href="/tags?tag='+ tagName+ '" class="tag"'+i+'>'+tagName+'</a>'))
                    })
                }
                else
                {
                    articleInfo.querySelector("#title"+i).innerHTML = item.title;
                    articleInfo.querySelector("#PostTime"+i).innerHTML = "&nbsp;"+item.createBy;
                    articleInfo.querySelector("h6").innerHTML = item.id;
                    articleInfo.querySelector("#desc"+i).innerHTML = item.summary;
                    articleInfo.querySelector("#commentNum"+i).innerHTML = "&nbsp;" + item.commentNum +"&nbsp;条评论";
                    articleInfo.querySelector(".traffic"+i).innerHTML = "&nbsp;" +item.traffic +"&nbsp;热度";
                    $.each(item.articleTagNames,function (i,tagName) {
                        jQuery(articleInfo.querySelector(".cats")).append($('<i class="fa fa-tag"></i><a href="/tags?tag=' +tagName+ '" class="tag"'+i+'>'+tagName+'</a>'))
                    })
                }
            })
        }
    });

    $.ajax({
        type: "get",
        url: "articles/listAnimeForIndex",
        dataType: "json",
        success: function (json) {
            $.each(json,function (i, item) {
                //填充番剧博客的文章信息
                var articleInfo = document.querySelector("#AnimeArticleInfo"+i);
                if (articleInfo == null)
                {
                    alert("JavaScript无法取出元素!!!")
                }
                    jQuery(document.querySelector("#AnimeBigImg"+i)).attr("style","background-image:url("+item.pictureUrl+");");
                    articleInfo.querySelector(".title"+i).innerHTML = item.title;
                    articleInfo.querySelector(".PostTime"+i).innerHTML = "&nbsp;"+ item.createBy;
                    articleInfo.querySelector("h6").innerHTML = item.id;
                    articleInfo.querySelector(".desc"+i).innerHTML = item.summary;
                    articleInfo.querySelector(".commentNum"+i).innerHTML = "&nbsp;" + item.commentNum +"&nbsp;条评论";
                    articleInfo.querySelector(".traffic"+i).innerHTML = "&nbsp;"+item.traffic;
                    $.each(item.articleTagNames,function (i,tagName) {
                        jQuery(articleInfo.querySelector(".cats")).append($('<i class="fa fa-tag"></i><a href="/tags?tag=' +tagName+ '" class="tag"'+i+'>'+tagName+'</a>'))
                    })
            })
        }
    });

    $.ajax({
        type: "get",
        url: "articles/listMovieForIndex",
        dataType: "json",
        success: function (json) {
            $.each(json,function (i, item) {
                //填充影评博客的文章信息
                var articleInfo = document.querySelector("#MovieArticleInfo"+i);
                if (articleInfo == null)
                {
                    alert("JavaScript无法取出元素!!!")
                }
                if (i === 0)
                {
                    document.querySelector("#MovieBigImg"+i).src = item.pictureUrl;
                    articleInfo.querySelector(".title"+i).innerHTML = item.title;
                    articleInfo.querySelector("h6").innerHTML = item.id;
                    $.each(item.articleTagNames,function (i,tagName) {
                        jQuery(articleInfo.querySelector(".cats")).append($('<i class="fa fa-tag"></i><a href="/tags?tag=' +tagName+ '" class="tag"'+i+'>'+tagName+'</a>'))
                    })
                }
                else
                {
                    document.querySelector("#MovieBigImg"+i).src = item.pictureUrl;
                    articleInfo.querySelector(".title"+i).innerHTML = item.title;
                    articleInfo.querySelector("h6").innerHTML = item.id;
                    articleInfo.querySelector(".desc"+i).innerHTML = item.summary;
                    $.each(item.articleTagNames,function (i,tagName) {
                        jQuery(articleInfo.querySelector(".cats")).append($('<i class="fa fa-tag"></i><a href="/tags?tag=' +tagName+ '" class="tag"'+i+'>'+tagName+'</a>'))
                    })
                }

            })
        }
    });

    $.ajax({
        type: "get",
        url: "articles/listMoodForIndex",
        datatype: "json",
        success: function (json) {
            $.each(json,function (i, item) {
                //填充随想博客的文章信息
                var articleInfo = document.querySelector("#MoodArticleInfo"+i);
                if (articleInfo == null)
                {
                    alert("JavaScript无法取出元素!!!")
                }
                jQuery(document.querySelector("#MoodBigImg"+i)).attr("style","background-image:url("+item.pictureUrl+");");
                articleInfo.querySelector(".title"+i).innerHTML = item.title;
                articleInfo.querySelector(".PostTime"+i).innerHTML = "&nbsp;"+  item.createBy;
                articleInfo.querySelector("h6").innerHTML = item.id;
                articleInfo.querySelector(".desc"+i).innerHTML = item.summary;
                articleInfo.querySelector(".commentNum"+i).innerHTML = "&nbsp;" + item.commentNum +"&nbsp;条评论";
                articleInfo.querySelector(".traffic"+i).innerHTML = "&nbsp;" +item.traffic;
                $.each(item.articleTagNames,function (i,tagName) {
                    jQuery(articleInfo.querySelector(".cats")).append($('<i class="fa fa-tag"></i><a href="/tags?tag=' +tagName+ '" class="tag"'+i+'>'+tagName+'</a>'))
                })

            })
        }
    });

    /**
     * 下面这个是用js实现的标签云回显，后面想了想还是用thymeleaf实现比较好
     *
     */
    // $.ajax({
    //     type:'get',
    //     url:'/getTagsNameForIndex',
    //     dataType:'json',
    //     success : function (data) {
    //
    //         putInTagInTagCloud(data['data']);
    //     },
    //     error :function () {
    //
    //     }
    // });

    //layui的日历嵌套显示
    layui.use('laydate',function () {
        var laydate = layui.laydate;

        laydate.render({
            elem:'#date2',
            position:'static',
            theme: '#393D49',
            calendar: true,
            btns: ['now'],
            mark:{
                '0-12-22':'生日',
                '0-12-31':'跨年咯'
            },
            change:function (value, date) {//监听日期被更换
                if (date.month===12&&date.date===22)
                {
                    alert("今天是my生日!")
                }
                lay('#dateView').html(value);

            }
        });
    });


    try
    {
        var i,et = document.getElementById("tags").childNodes;
        for (i in et)
        {
            et[i].nodeName === 'A' && et[i].addEventListener('click',function (e) {
                window.location.href = et[i].attr('href');
                e.preventDefault();

            });
        }

        TagCanvas.Start('myCanvas','tags',{
            textColour: '#991',
            outlineColour: '#fff',
            reverse: true,
            depth: 0.8,
            dragControl: true,
            decel:0.95,
            maxSpeed: 0.1,
            initial: [-0.2, 0]
        })

    }
    catch (e) {

    }



};


// function putInTagInTagCloud(data) {
//     var tags = $('#tags');
//     // tags.empty();
//     $.each(data['result'],function (i, obj) {
//         tags.append($('<a href="/tags?tag=' +obj['tag_name']+ '">'+obj['tag_name']+'</a>'));
//     });
//
// }

function showArticle(_this) {
    var articleId = $(_this).children("h6").text();
    var url = "/article/"+articleId;
    window.location.href=url;
}

function showArticleByClickPic(_this) {
    var articleId = $(_this).parent().parent().find($('h6')).text();
    var url = "/article/"+articleId;
    window.location.href = url;
}

