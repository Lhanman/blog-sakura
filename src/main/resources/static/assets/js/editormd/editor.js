
    var flag = 1;
    var aCategory = "";
    //
    // $('#originalAuthorHide').hide();
    // $('.articleUrlHide').hide();

    var fnClose = function(e){
        e.returnValue = '确定离开当前页面吗?';
    };
    window.addEventListener('beforeunload',fnClose);

    function getCookie(cname)
    {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i=0; i<ca.length; i++)
        {
            var c = ca[i].trim();
            if (c.indexOf(name)===0) return c.substring(name.length,c.length);
        }
        return "";
    }
    var token = getCookie("Lhan-blog-token");
   $.ajaxSetup({

        beforeSend: function(jqXHR, settings) {

            /*header里加请求头参数*/
            jqXHR.setRequestHeader('Authorization', token)

        },
        success:function(result,status,xhr){

        },
        error:function(xhr,status,error){

        },
        complete:function(xhr,status){

        }
    });

    var testEditor;
    $(function() {
        testEditor = editormd("my-editormd", { //注意1：这里的就是上面的DIV的id属性值
                width: "100%",
                height: 740,
                placeholder:"开始你的创作吧(●'◡'●)",
                syncScrolling: true, //设置双向滚动
                path: "../../../lib/", //lib目录的路径
                previewTheme : "dark", //代码块使用dark主题
                codeFold : true,
                emoji:true,
                tocm : true, // Using [TOCM]
                tex : true, // 开启科学公式TeX语言支持，默认关闭
                flowChart : true, // 开启流程图支持，默认关闭
                sequenceDiagram : true, // 开启时序/序列图支持，默认关闭,
                htmlDecode : true, //不过滤标签
                imageUpload : true, //上传图片
                imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp","JPG","JPEG","GIF","PNG","BMP","WEBP"],
                imageUploadURL : "/uploadImage",
                onload:function () {
                    // console.log('onload', this);
                },
                saveHTMLToTextarea: true, //注意3：这个配置，方便post提交表单
                toolbarIcons : function () {
                    return ["bold","del","italic","quote","|","h1","h2","h3","h4","h5","h6","|","list-ul","list-ol","hr","|","link","image","code","code-block","table","datetime","html-entities","emoji","|","watch","preview","fullscreen","clear","search","|","help","info"]
                }
            });

        });

    var publishBtn = $('.publishBtn');
    var articleTitle = $('#lhan-editor-title');
    var articleContent = $('#my-editormd-html-code');
    var noticeBoxTitle = $('.notice-box-title');
    var noticeBoxContent = $('.notice-box-content');
    var noticeBox = $('.notice-box');

    publishBtn.click(function () {
        var articleTitleValues =  articleTitle.val();
        var articleContentValues = articleContent.val();
        if(articleTitleValues.length === 0){
            noticeBoxTitle.show();
        } else if (articleContentValues.length === 0){
            noticeBoxContent.show();
        } else{
            $('#my-alert').modal();
            $.ajax({
                type:"GET",
                url:"category/findCategoriesName",
                async:false,
                data:{
                },
                dataType:"json",
                success:function (data) {
                    var selectCategories = $('#select-categories');
                    selectCategories.empty();
                    selectCategories.append($('<option class="categoriesOption" value="choose">请选择</option>'));
                    for(var i=0;i<data['data'].length;i++){
                        selectCategories.append($('<option class="categoriesOption" value="' + data['data'][i] + '">' + data['data'][i] + '</option>'));
                    }
                    if(aCategory !== "" && aCategory.length > 0){
                        selectCategories.val(aCategory);
                    }
                },
                error:function () {
                }
            });
        }
        // 定时关闭错误提示框
        var closeNoticeBox = setTimeout(function () {
            noticeBox.hide();
        },3000);
    });

    function publishSuccessPutIn(data) {
        $('#removeDiv').html('');
        var sec = $('<div id="all"></div>');
        var success = $('<div class="success"></div>');
        var successBox = $('<div class="success-box"></div>');
        var successArticleTitle = $('<div class="successArticleTitle"><span>文章标题:&nbsp;' + data.articleTitle + '</span></div>');
        var successWord = $('<div class="success-word"><p><i class="am-success am-icon-check-square-o" style="color: #5eb95e"></i> 发布成功</p></div>');
        var successTimeAndUser = $('<div class="success-time-user">' +
            '<p><i class="am-icon-calendar"></i>&nbsp;' + data.modifiedBy + '&nbsp;&nbsp;&nbsp;&nbsp;<i class="am-icon-user"></i>&nbsp; ' +data.author+ '</p>' +
            '</div>');
        var successBtn = $('<div class="successBtn">' +
            '<a href="/editor" class="reWriteBtn am-btn am-btn-danger am-round">写新文章</a>' +
            '<a href="/article/' +data.id+ '" class="lookArticleBtn am-btn am-btn-danger am-round">查看文章</a>' +
            '</div>');

        successBox.append(successArticleTitle);
        successBox.append(successWord);
        successBox.append(successTimeAndUser);
        successBox.append(successBtn);
        success.append(successBox);
        sec.append(success);
        $('#removeDiv').append(sec);
    }

    //验证是否有权限写博客
    $.ajax({
        type:"GET",
        url:"/verifyId",
        data:{
        },
        dataType:"json",
        success:function (data) {
            if(data['status'] !== 0){
                var noticeBoxWrite = $('<div class="notice-box-write">' +
                    '<div class="am-alert am-alert-danger">' +
                    '<p>在线写博客功能现不对外开放，您所写的文章都将发布无效喔<button type="button" class="verifyId am-close">&times;</button></p>' +
                    '</div>' +
                    '</div>');
                $('#app').append(noticeBoxWrite);
            }
            $('.verifyId').click(function () {
                $('.notice-box-write').hide();
            })
        },
        error:function () {
        }
    });



    // 获得草稿文章
    $.ajax({
        type:"GET",
        url:"/getDraftArticle",
        async:false,
        data:{
        },
        dataType:"json",
        success:function (data) {
            if(data['status'] === 0){
                $('#lhan-editor-title').val(data['data']['articleTitle']);
                $('#my-editormd-markdown-doc').html(data['data']['articleContent']);
                aCategory = data['data']['articleCategory'];
                var tags = data['data']['articleTagsName'];
                $('#img').attr('src',data['data']['articlePictureUrl']);
                var tag = $('.tag');
                for(var i in tags){
                    tag.append($('<div style="display: inline-block;"><p class="tag-name" contenteditable="true">' + tags[i] + '</p>' +
                        '<i class="am-icon-times removeTag" style="color: #CCCCCC"></i></div>'));
                }
                var articleId = data['data']['id'];
                if(articleId !== 0){
                    $('#coverUpload').addClass('am-disabled');
                    $('.surePublishBtn ').attr("id",articleId);
                }

            }
        },
        error:function () {
        }
    });

    // 插入标签
    var addTagsBtn = $('.addTagsBtn');
    $(function() {
        var i = 0;
        var $wrapper = $('.tag');
        var appendPanel = function(index) {
            var panel = $('<div style="display: inline-block;"><p class="tag-name" contenteditable="true"></p>' +
                '<i class="am-icon-times removeTag" style="color: #CCCCCC"></i></div>');
            $wrapper.append(panel);
            $('.tag-name').click(function () {
                $(this).focus();
            });
        };
        addTagsBtn.on('click', function() {
            if(i >= 4){
                addTagsBtn.attr('disabled','disabled');
            }
            var value=$('.tag-name').eq(i-1).html();
           if(value !== ""){
               appendPanel(i);
               i++;
               console.log(i)
           }
        });

        $('.tag').on('click','.removeTag',function () {
            $(this).parent().remove();
            i--;
            if(i <= 4){
                addTagsBtn.removeAttr('disabled');
            }
            console.log(i)
        });

    });

    // 显示文章作者
    // var articleType = $('#select-type');
    // $('#select-type').blur(function () {
    //     if(articleType.val() == "转载"){
    //         $('#originalAuthorHide').show();
    //         $('.articleUrlHide').show();
    //     } else if (articleType.val() == "原创"){
    //         $('#originalAuthorHide').hide();
    //         $('.articleUrlHide').hide();
    //     }
    // });
    // 发表博客
    var surePublishBtn = $('.surePublishBtn');
    var articleCategories = $('#select-categories');
    // var articleGrade = $('#select-grade');
    // var originalAuthor = $('#originalAuthor');
    // var articleUrl = $('#articleUrl');
    surePublishBtn.click(function () {
        var tagNum = $('.tag').find('.tag-name').length;
        var articleTagsValue = [];
        for(var j=0;j<tagNum;j++){
            articleTagsValue[j] = $('.tag-name').eq(j).html();
        }
        // var articleTypeValue = articleType.val();
        var articleCategoriesValue = articleCategories.val();
        var form = new FormData();
        form.append("file",document.getElementById("doc-form-file").files[0]);
        // var articleGradeValue = articleGrade.val();
        // var originalAuthorValue = originalAuthor.val();
        // var articleUrlValue = articleUrl.val();
        if(articleTagsValue.length === 0 || articleTagsValue[tagNum-1] === ""){
            $('.notice-box-tags').show();
        }
        // else if (articleTypeValue === "choose"){
        //     $('.notice-box-type').show();
        // }
        else if (articleCategoriesValue === "choose"){
            $('.notice-box-categories').show();
        }
        // else if (articleGradeValue === "choose"){
        //     $('.notice-box-grade').show();
        // }
        // else if (articleType.val() == "转载" && originalAuthorValue === ""){
        //     $('.notice-box-originalAuthor').show();
        // }
        // else if (articleType.val() == "转载" && articleUrlValue === ""){
        //     $('.notice-box-url').show();
        // }
        else {
            $.ajax({
                type:"POST",
                url:"article/publishArticle",
                traditional: true,// 传数组
                data:{
                    id : $('.surePublishBtn').attr("id"),
                    articleTitle : articleTitle.val(),
                    articleContent : articleContent.val(),
                    articleTagsValue : articleTagsValue,
                    // articleType : articleTypeValue,
                    articleCategory : articleCategoriesValue,
                    // articleGrade : articleGradeValue,
                    // originalAuthor : originalAuthorValue,
                    // articleUrl : articleUrlValue,
                    articleHtmlContent : testEditor.getHTML(),
                },
                contentType:"application/x-www-form-urlencoded; charset=utf-8",
                dataType:"json",
                success:function (data) {
                    if(data['status'] === 101){
                        window.removeEventListener('beforeunload',fnClose);
                        layer.alert("未登录，没有权限",{
                            icon:5
                        },function () {
                            window.location.replace("/login");
                        });
                    } else if(data['status'] === 205) {
                        layer.msg("发布失败!都说了不开放咯!",{
                            icon:5
                        })
                    } else if(data['status'] === 206) {
                        layer.msg("服务器异常",{
                            icon:7
                        })
                    } else if (data['status'] === 207)
                    {
                        layer.msg("博客封面出现异常",{
                            icon:7
                        });
                    }
                    else if (data['status'] === 208)
                    {
                        layer.msg("未上传博客封面",{
                            icon:5
                        })
                    }
                    else if (data['status'] === 209)
                    {
                        var index = layer.alert("更新成功", {
                            title: '更新信息'
                        }, function () {

                            // 关闭弹出层
                            layer.close(index);

                            window.parent.location.reload();
                            var iframeIndex = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(iframeIndex);

                        });
                    }
                    else {
                        $('#my-alert').modal('close');
                        window.removeEventListener('beforeunload',fnClose);
                        publishSuccessPutIn(data['data']);
                    }
                },
                error:function () {
                    alert("发表博客异常")
                }
            })
        }

        // 定时关闭错误提示框
        var closeNoticeBox = setTimeout(function () {
            noticeBox.hide();
        },3000);
    });

    function submitBlogCover() {
        var type ="file";           //后台接收需要的参数名称
        var id = "doc-form-file"; //input的值
        var formData = new FormData();
        formData.append(type,$("#"+id)[0].files[0]); //生成一对表单属性
        $.ajax({
            type:"POST",
            url:"/uploadBlogCover",
            data:formData,
            processData:false,
            contentType:false,
            dataType:'json',
            success:function (data) {
                if(data['status'] === 207)
                {
                    layer.msg("博客封面出现异常",{
                        icon:5
                    });
                }
                else if (data['status'] === 205)
                {
                    layer.msg("上传失败，功能不对外开放!",{
                        icon:5
                })
                }
                else if (data['status'] === 208)
                {
                   layer.msg(data['message'],{
                       icon:5
                   })
                }
                else if (data['status'] === 0){
                    layer.msg("上传成功",{
                        icon:1
                    })
                }

            }
        })
    }

    // //检查图片
    // function checkImage() {
    //     var fileName = $("#doc-form-file").val();
    //     var flag=true;
    //     if(fileName==""){
    //         alert("请选择图片");
    //     }
    //     else{
    //         var size = $("#doc-form-file")[0].files[0].size;
    //         if(size/1000>100000){
    //             flag=false;
    //             alert("图片大小不能超过100KB");
    //         }
    //     }
    //     if(flag){
    //         onLoadImage();
    //     }
    //     else{//清除input type=file的显示内容
    //         $("#doc-form-file").val("");
    //     }
    //     return flag;
    // }
    //
    // //预览图片
    // function onLoadImage() {
    //     var file=$('#doc-form-file').get(0).files[0];
    //     var reader = new FileReader();
    //     //将文件以Data URL形式读入页面
    //     reader.readAsDataURL(file);
    //     reader.function(e)
    //     {
    //         //显示文件
    //         $("#onLoadImage").html('<img src="' + this.result + '" alt="" />');
    //     }
    // }
