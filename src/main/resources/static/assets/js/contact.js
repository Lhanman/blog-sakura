//留言功能
$("#CommentBtn").click(function () {
    var  commentName = $("#CommentName").val();
    commentName = $.trim(commentName);
    if (commentName == "")
    {
        alert("称呼不能为空");
        return;
    }
    var commentEmail = $("#CommentEmail").val();
    commentEmail = $.trim(commentEmail);
    if (commentEmail === "")
    {
        alert("Email不能为空")
        return;
    }
    if (!checkEmail(commentEmail))
    {
        alert("请输入正确的email格式");
        return;
    }
    var commentContent = $("#CommentContent").val();
    commentContent = $.trim(commentContent);
    if(commentContent == "")
    {
        alert("客官，你还没说两句话呢~   ┭┮﹏┭┮");
        return;
    }
    var message = {
        name:commentName,
        email:commentEmail,
        content:commentContent
    };
    $.ajax({
        type:'Post',
        contentType:"application/json;charset=UTF-8",
        url:'message/publishMessage',
        dataType:'json',
        data:JSON.stringify(message),
        success:function () {
            $('#addModal').modal();
        },
        error:function () {
            alert("服务器出现错误")
        }
    })


});

function checkEmail(commentEmail) {
    var myReg = /^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;

    if (myReg.test(commentEmail))
    {
        return true;
    }
    else
    {
        return false
    }
}

// 模态框确认按钮点击事件
$('#addConfirmBtn').click(function () {
    // 刷新页面
    location.reload();
});