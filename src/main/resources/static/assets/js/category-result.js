/*
为category-result写的JavaScript

 */
var categoryId="";
window.onload = function () {
    // var categoryId = getQueryVariable("category_id");
    // if (categoryId)
    // {
    //     showArticleByCategoryId(categoryId,1);
    // }
    // else
    // {
    //     alert("出现错误！！！");
    // }

    $.ajax({
        type:'HEAD',//获取头信息
        url:window.location.href,
        async:false,
        success : function (data,status,xhr) {
            categoryId = xhr.getResponseHeader("categoryId");
            showArticleByCategoryId(categoryId,1);
        },
        error : function () {
            alert("获取分类id失败");
        }
    });

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

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0; i<vars.length; i++)
    {
        var pair = vars[i].split("=");
        if (pair[0] == variable)
        {
            return pair[1];
        }
    }
    return (false);
}

function showArticleByCategoryId(id,currentPage) {
    $.ajax({
            type: "get",
            url: "/list/"+id,
            datatype: "json",
            data:{
                rows:"5",
                pageNum:currentPage
            },
            success:function (data) {
                // console.log(data);
                // console.log(data["data"].length);
                if(data['status'] === 103)
                {
                    dangerNotice("服务器异常，获得文章信息失败");
                }

                else {
                    putInArticle(data['data']);
                    scrollTo(0,0);//回到顶部
                }

                //分页功能
                var length = data['data'].length;

                var pageNum = data['data'][length -1]['pageNum'];//当前所在页码
                var pageSize = data['data'][length -1]['pageSize'];//每页显示条目
                var total = data['data'][length -1 ]['total'];//总记录数

                layui.use('laypage',function () {
                    var laypage = layui.laypage;

                    //执行一个laypage实例
                    laypage.render({
                        elem:'pageBox',
                        curr:pageNum,
                        count:total,
                        limit:pageSize,
                        theme: '#FFB800',
                        prev:"<",
                        next:">",
                        layout:['count','prev','page','next','skip'],
                        jump:function (obj, first) {
                            //这个方法是在你选择页数后触发执行，在这里完成当你点击页码后需要向服务请求数据的操作
                            if (first)
                            {
                                //第一次不执行
                                return;
                            }
                            var curr_page = obj.curr;
                            showArticleByCategoryId(categoryId,curr_page);
                        }


                    })
                });

                // $("#pagination").paging({
                //     rows:pageSize,//每页显示条数
                //     pageNum:pageNum,//当前所在页码
                //     pages:pages,//总页数
                //     total:total,//总记录数
                //     callback:function(currentPage){
                //         showArticleByCategoryId(id,currentPage);
                //     }
                // });
                //
                //
                // //点击页数
                // $('.ui-pagination-page-item').on('click',function () {
                //     var pageNum = $(this).attr('data-current');
                //     window.location.href = encodeURI('')
                // })

                // $.each(json,function (i, item) {
                //     var articleInfo = document.querySelector("#articleInfo");
                //     jQuery(articleInfo.content.querySelector("#BigImg")).attr("style", "background-image:url(" + item.pictureUrl + ");");
                //     articleInfo.content.querySelector("#title").innerHTML = item.title;
                //     articleInfo.content.querySelector("#PostTime").innerHTML = item.createBy;
                //     articleInfo.content.querySelector("h6").innerHTML = item.id;
                //     articleInfo.content.querySelector("#desc").innerHTML = item.summary;
                //     document.getElementById("articles").appendChild(articleInfo.content.cloneNode(true));
                // })
            }
        });
    }
    
    function putInArticle(data) {
        $(".articles").empty();
        var articles = $(".articles");
        // console.log(data);

        // var items = data['articles'];
        $.each(data,function (i, item) {
            if (i !== (data.length) - 1 )
            {
                // console.log(item['PictureUrl']);
                document.querySelector("#category").innerHTML = item['Category'];
                document.querySelector("#categoryName").innerHTML = item['Category'];
                var article = $(' <div class="post-default post-has-right-thumb one-article'+i+'">\n' +
                    '                    <div class="d-flex flex-wrap">\n' +
                    '                        <div class="post-thumb align-self-stretch order-md-2">\n' +
                    '                            <a href="/article/'+ item['ArticleId'] +'" >\n' +
                    '                                <div class="bg-img" style="background-image:url('+item['PictureUrl']+')"></div>\n' +
                    '                            </a>\n' +
                    '                        </div>\n' +
                    '                        <div class="post-data order-md-1" onclick="showArticle(this)">\n' +
                    '                            <!-- Tag -->\n' +
                    '                            <div class="cats">'+
                    '                            </div>\n' +
                    '                            <!-- Title -->\n' +
                    '                            <div class="title">\n' +
                    '                                <h2><a href="#">'+item['ArticleTitle']+'</a></h2>\n' +
                    '                            </div>\n' +
                    '                            <!-- Post Meta -->\n' +
                    '                            <ul class="nav meta align-items-center">\n' +
                    '                                <li class="meta-author">\n' +
                    '                                    <img src="http:/static.lhanman.cn/static/images/author.jpg" alt="" class="img-fluid">\n' +
                    '                                    <a href="#">Lhan</a>\n' +
                    '                                </li>\n' +
                    '                                <li class="meta-date"><a href="#"><i class="fa fa-calendar"></i>&nbsp;'+item['CreateBy']+'</a></li>\n' +
                    '                                <li class="meta-comments"><a href="#"><i class="fa fa-comment"></i>&nbsp;'+item['commentNum']+'</a></li>\n' +
                    '                                <li class="meta-traffic"><i class="fa fa-eye"></i><a class="traffic">&nbsp;'+item['traffic']+'&nbsp;热度</a></li>' +
                    '                            </ul>\n' +
                    '                            <h6 style="display: none;">'+item['ArticleId']+'</h6>      '+
                    '                            <!-- Post Desc -->\n' +
                    '                            <div class="desc">\n' +
                    '                                <p>\n' +
                    '\n' +                              item['ArticleSummary']+
                    '                                </p>\n' +
                    '                            </div>\n' +
                    '                            <!-- Read More Button -->\n' +
                    '                            <a href="#" class="btn btn-primary">阅读全文</a>\n' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '                </div>');

                articles.append(article);
                var articleInfo = document.querySelector(".one-article"+i);
                $.each(item.articleTagNames,function (i,tagName) {
                    jQuery(articleInfo.querySelector(".cats")).append($('<i class="fa fa-tag"></i><a href="#" class="tag"'+i+'>'+tagName+'</a>'))
                });

                // jQuery(document.querySelector(".bg-img")).attr("style", "background-image:url(" + item['PictureUrl'] + ");");
            }

        })
    }

function showArticle(_this) {
    var articleId = $(_this).children("h6").text();
    var url = "/article/"+articleId;
    window.location.href=url;
}