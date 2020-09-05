/*
发送评论
 */
var articleId ="";
$.ajax({
    type:'HEAD',//获取头信息
    url:window.location.href,
    async:false,
    success : function (data,status,xhr) {
        articleId = xhr.getResponseHeader("articleId");
    }
});

function toLogin(){
    $.get("/toLogin",function(data,status,xhr){
        window.location.replace("/login");
    });
}



//客官说两句话吧！！！
function clickCommentBtn() {
    var commentContent = $('#commentContent').val();
    commentContent = $.trim(commentContent);
    // var commentName = $('#YourName').val();
    // commentName = $.trim(commentName);

        $.ajax({
            type:'POST',
            url:'comment/publishComment',
            dataType:'json',
            data:{
                commentContent:commentContent,
                articleId:articleId
            },
            success:function (data) {
                if(data['status'] === 101){
                    $.get("/toLogin", function (data, status, xhr) {

                        layer.alert("未登录，登录才可以评论", {
                            icon: 5
                        }, function () {
                            window.location.replace("/login");
                        });
                    });
                }
                else if (commentContent === "")
                {
                    layer.msg("评论内容不能为空",{
                        icon:7
                    })
                }

                else
                {
                    putInComment(data['data']);
                    $('#commentContent').val("");
                    layer.msg("发表评论成功",{
                        icon:1
                    });


                }
            },
            error:function () {
                alert("发表评论失败");
            }

        })

}
