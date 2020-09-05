//标签页的js

var tag="";

//添加所有标签
function putInAllTags(data) {
    var allTags = $('.allTags');
    allTags.empty();
    allTags.append($('<div class="allTagsTitle">' +
        '<h2 style="font-size: 20px">Tags</h2>' +
        '</div>'));
    allTags.append($('<div class="allTagsNum">' +
        '目前共计 <span class="num" style="font-size: 20px;color: red">'+data['tagsNum']+'</span>个标签' +
        '</div>'))
    var allTagCloud = $('<div class="allTagsCloud categories-comment am-animation-slide-top"></div>');
    $.each(data['result'],function (index, obj) {
        allTagCloud.append($('<a href="/tags?tag='+obj['tag_name']+'" style="font-size:' + obj['tag_size'] + 'px">'+obj['tag_name']+'</a>'));
    });
    allTags.append(allTagCloud);
}

function putInArticleInfoByTag(data) {
    var siteInner=$('.site-inner');
    siteInner.empty();
    var timeLine = $('<div class="timeline timeline-wrap"></div>');
    timeLine.append($('<div class="timeline-row">' +
        '<span class="node" style="-webkit-box-sizing: content-box;-moz-box-sizing: content-box;box-sizing: content-box">' +
        '<i class="am-icon-tag"></i> ' +
        '</span>' +
        '<h1 class="title am-animation-slide-top"># '+ data['tag'] +'</h1>' +
        '</div>'));
    $.each(data['result'],function (i, obj) {
        var timelineRowMajor = $('<div class="timeline-row-major"></div>');
        timelineRowMajor.append($('<span class="node am-animation-slide-top am-animation-delay-1"></span>'));
        var content =$('<div class="content am-comment-main am-animation-slide-top am-animation-delay-1"></div>');
        content.append($('<header class="am-comment-hd" style="background: #fff">' +
            '<div class="contentTitle am-comment-meta">' +
            '<a href="/article/'+obj['articleId']+'">'+obj['articleTitle']+'</a> ' +
            '</div>' +
            '</header>'));
        var amCommentBd = $('<div class="am-comment-bd"></div>');
        amCommentBd.append($('<i class="am-icon-calendar">&nbsp;'+obj['publishDate']+'</i>' +
            '<i class="am-icon-folder"><a href="/category/'+obj['articleCategory']+'"> &nbsp;'+obj['articleCategory']+'</a></i>'));
        var amCommentBdTags = $('<i class="am-comment-bd-tags am-icon-tag"></i>');
        for (var i=0; i<obj['articleTags'].length; i++)
        {
            var tag = $('<a href="/tags?tag=' + obj['articleTags'][i] + '">' + obj['articleTags'][i] + '</a>');
            amCommentBdTags.append(tag);
            if (i !== obj['articleTags'].length-1)
            {
                amCommentBdTags.append(",");
            }
        }
        amCommentBd.append(amCommentBdTags);
        amCommentBd.append($('<i class="am-icon-fire">&nbsp;'+obj['traffic']+'热度</i>'));
        content.append(amCommentBd);
        timelineRowMajor.append(content);
        timeLine.append(timelineRowMajor);
    });
    siteInner.append(timeLine);

}

$.ajax({//获取头信息
    type:'HEAD',
    url:window.location.href,
    async:false,
    success : function (data, status, xhr) {
        tag = xhr.getResponseHeader("tag");
    }
});


function ajaxRequest(currentPage) {
    $.ajax({
        type:'POST',
        url:'/getTagArticle',
        dataType:'json',
        data:{
            tag:tag,
            rows:10,
            pageNum:currentPage
        },
        success :function (data) {
            if (data['status'] === 301)
            {
                putInAllTags(data['data']);
            }
            else if (data['status'] === 302 )
            {
                putInArticleInfoByTag(data['data']);
                scrollTo(0,0);
            }

            //分页功能

            var pageNum = data['data']['pageInfo'].pageNum;//当前页码
            var pageSize = data['data']['pageInfo'].pageSize;//每页显示条目
            var total = data['data']['pageInfo'].total;//总记录数

            layui.use('laypage',function () {
                var laypage = layui.laypage;

                //执行一个laypage实例
                laypage.render({
                    elem:'pageInfo',
                    curr:pageNum,
                    count:total,
                    limit:pageSize,
                    prev:"上页",
                    next:"下页",
                    layout:['count','prev','next','skip'],
                    jump:function (obj, first) {
                        //这个方法是在你选择页数后触发执行，在这里完成当你点击页码后需要向服务请求数据的操作
                        if (first)
                        {
                            //第一次不执行
                            return;
                        }
                        var curr_page = obj.curr;
                        ajaxRequest(curr_page);
                    }


                })
            });
        },
        error:function () {
            alert("获取标签或文章失败");
        }
    })
}
ajaxRequest(1);