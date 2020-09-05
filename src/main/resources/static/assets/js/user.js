/*
user.html定制js

 */
$('.userList .clickLi').click(function () {
    var flag = $(this).attr('class').substring(8);
    $('#personalDate,#basicSetting,#commentMessage').css("display","none");
    $("#" + flag).css("display","block");
});

$('.basicSetting').click(function () {
    $('#phone').val("");
    $('#authCode').val("");
    $('#password').val("");
    $('#surePassword').val("");
});


//获得个人信息
function getUserPersonalInfo() {
    $.ajax({
        type:'post',
        url:'/getUserPersonalInfo',
        dataType:'json',
        data:{

        },
        success:function (data) {
            if (data['status'] === 101)
            {
                $.get("/toLogin", function (data, status, xhr) {

                    layer.alert("未登录，没有权限", {
                        icon: 5
                    }, function () {
                        window.location.replace("/login");
                    });
                });
            }
            else
            {
                $('#username').attr("value",data['data']['username']);
                var personalPhone = data['data']['phone'];
                $('#personalPhone').html(personalPhone.substring(0,3) + "*****"+personalPhone.substring(7));
                $('#birthday').attr("value",data['data']['birthday']);
                var gender = data['data']['gender'];
                if(gender === "male"){
                    $('.genderTable input').eq(0).attr("checked","checked");
                } else {
                    $('.genderTable input').eq(1).attr("checked","checked");
                }
                $('#email').attr("value",data['data']['email']);
                $('#personalBrief').val(data['data']['personalBrief']);

                $('#headPortrait').attr("src",data['data']['avatarimgUrl']);
            }
        },
        error:function () {

        }
    })
}
getUserPersonalInfo();
//保存个人资料
var savePersonalInfoDataBtn = $('#savePersonalDateBtn');
var username = $('#username');
var birthday = $('#birthday');
var gender = $('.genderTable input');
var email = $('#email');
var personalBrief = $('#personalBrief');

savePersonalInfoDataBtn.click(function () {
    var usernameValue = username.val();
    var genderValue = "male";
    if(usernameValue.length === 0){
        layer.msg("昵称不能为空!",{
            icon:7
        });
    }
    else if (!gender[0].checked && !gender[1].checked)
    {
        layer.msg("性别不能为空!",{
            icon:7
        });
    }
    else if (!checkDateFormat(birthday.val()))
    {
        layer.alert('时间格式不正确！',{
            icon:5
        });
    }
    else if(checkDate(birthday.val()))
    {
        layer.alert('不能大于当前时间！',{
            icon:5
        });
    }
    else if (test(email.val()))
    {
        layer.alert('请输入有效的邮箱！',{
            icon:5
        });
    }

    else {
        if (gender[0].checked) {
            genderValue = "male";
        } else {
            genderValue = "female";
        }
        $.ajax({
            type: 'post',
            url: '/savePersonalData',
            dataType: 'json',
            data: {
                username: username.val(),
                birthday: birthday.val(),
                gender: genderValue,
                email: email.val(),
                personalBrief: personalBrief.val()
            },
            success: function (data) {
                if (data['status'] === 101) {

                    layer.alert("未登录，没有权限", {
                        icon: 5
                    }, function () {
                        window.location.replace("/login");
                    });
                } else {
                    if (data['status'] === 509) {
                        layer.msg("更改失败!昵称太长了呀!", {
                            icon: 5
                        });
                    } else if (data['status'] === 503) {
                        layer.msg("更改失败!昵称不能为空鸭!", {
                            icon: 5
                        });
                    } else if (data['status'] === 504) {
                        layer.msg("更改个人信息成功!", {
                            icon: 1
                        },function () {
                            window.location.replace("/");
                        });
                    } else if (data['status'] === 505) {
                        layer.msg("昵称已被某人抢先了鸭!", {
                            icon: 5
                        });
                    } else {
                        layer.msg("更改个人信息失败!", {
                            icon: 5
                        });
                    }
                }

            },
            error: function () {

            }
        });
    }
});

    //检验时间大小(与当前时间比较)
    function checkDate(obj){
        var obj_value=obj.replace(/-/g,"/");//替换字符，变成标准格式(检验格式为：'2009-12-10')
        var date1=new Date(Date.parse(obj_value));
        var date2=new Date();//取今天的日期
        if(date1>date2){
            return true;
        }
        return false;
    }

    function checkDateFormat(d){
        return /^(((\d+\/0[1-9]\/((0[1-9])|([1-2][0-9])|(3[0-1])))|(\d+\/1[0-2]\/((0[1-9])|([1-2][0-9])|(3[0-1]))))|((\d+-0[1-9]-((0[1-9])|([1-2][0-9])|(3[0-1])))|(\d+-1[0-2]-((0[1-9])|([1-2][0-9])|(3[0-1])))))$/.test(d);

    }


    function test(obj){
    //对电子邮件的验证
        if (obj.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) !== -1)
            return false;
        else
            return true;
    }

var phone = $('#phone');
var authCode = $('#authCode');
var password = $('#password');
var surePassword = $('#surePassword');

phone.blur(function () {
    var pattren = /^1[345789]\d{9}$/;
    var phoneValue = phone.val();
    if(pattren.test(phoneValue)){
        phone.removeClass("wrong");
        phone.addClass("right");
    } else {
        phone.removeClass("right");
        phone.addClass("wrong");
    }
    $.ajax({
        type:'post',
        url:'/findPhoneIsExist',
        dataType:'json',
        data:{
            phone:phone.val()
        },
        success : function (data) {
            if (data['status'] === 0)
            {
                phone.removeClass("wrong");
                phone.addClass("right");
            }
            else
            {

                phone.removeClass("right");
                phone.addClass("wrong");
            }
        }
    })
});

// phone.focus(function () {
//     $('.notice').css("display","none");
// });

// 定义发送时间间隔(s)
var my_interval;
my_interval = 60;
var timeLeft = my_interval;
//重新发送计时函数
var timeCount = function() {
    window.setTimeout(function() {
        if(timeLeft > 0) {
            timeLeft -= 1;
            $('#authCodeBtn').html(timeLeft + "秒重新发送");
            timeCount();
        } else {
            $('#authCodeBtn').html("重新发送");
            timeLeft=60;
            $("#authCodeBtn").attr('disabled',false);
        }
    }, 1000);
};


//发送短信验证码
$('#authCodeBtn').click(function () {
    $('#authCodeBtn').attr('disabled',true);
    var phoneLen = phone.val().length;
    if(phoneLen === 0){
        layer.msg("手机号不能为空!", {
            icon: 5
        });
        $('#authCodeBtn').attr('disabled',false);
    } else {
        if(phone.hasClass("right")){
            $.ajax({
                type:'post',
                url:'/getCode',
                dataType:'json',
                data:{
                    phone:$('#phone').val(),
                    sign:"changePassword"
                },
                success:function (data) {
                    if(parseInt(data['status']) === 0) {
                        layer.msg("发送验证码成功!", {
                            icon: 1
                        });
                        timeCount();
                    } else {
                        layer.msg("发送验证码失败!", {
                            icon: 5
                        });
                    }
                },
                error:function () {
                }
            });
        } else {
            layer.msg("手机号不存在!", {
                icon: 5
            });
            $('#authCodeBtn').attr('disabled',false);
        }
    }
});

//修改密码
$('#changePasswordBtn').click(function () {
    if(phone.val().length === 0){
        layer.msg("手机号不能为空!", {
            icon: 5
        });
    } else if (phone.hasClass("wrong")){
        layer.msg("手机号不正确!", {
            icon: 5
        });
    } else if (authCode.val().length === 0){
        layer.msg("验证码不能为空!", {
            icon: 5
        });
    } else if (password.val().length === 0){
        layer.msg("新密码不能为空!", {
            icon: 5
        });
    } else if (surePassword.val().length === 0){
        layer.msg("确定密码不能为空!", {
            icon: 5
        });
    } else{
        if (password.val() !== surePassword.val()){
            layer.msg("确定密码不正确!", {
                icon: 5
            });
        } else {
            $.ajax({
                type:'post',
                url:'/changePassword',
                dataType:'json',
                data:{
                    phone : phone.val(),
                    authCode:authCode.val(),
                    newPassword:password.val()
                },
                success:function (data) {
                    if(data['status'] === 902){
                        dangerNotice("验证码不正确")
                    }else if (data['status'] === 508){
                        dangerNotice("手机号不存在")
                    }else if(data['status'] === 901){
                        dangerNotice("手机号不正确");
                    } else {
                        layer.msg("密码修改成功,请重新登录",{
                            icon:1
                        });
                        setTimeout(function () {
                            location.reload();
                        },3000);
                    }
                },
                error:function () {
                    dangerNotice("修改密码失败");
                }
            })
        }
    }
});


//填充用户评论
function putInCommentInfo(data) {
    var msgContent = $('.msgContent');
    msgContent.empty();
    if(data['result'].length === 0){
        msgContent.append($('<div class="noNews">' +
            '这里啥都没有喔,没有人回复你' +
            '</div>'));
    }
    else
    {
        msgContent.append($(' <div class="msgReadTop">' +
            '未读消息：<span class="msgIsReadNum">' + data['msgIsNotReadNum'] + '</span>' +
            '<button class="msgIsRead layui-btn layui-btn-sm layui-btn-radius layui-btn-warm">全部标记为已读</button>' +
            '</div>'));
        $.each(data['result'], function (index, obj) {
            var msgRead = $('<div class="msgRead" id="p' + obj['id'] + '"></div>')
            var msgReadSign = $('<span class="msgReadSign"></span>');
            if(obj['isRead'] === false){
                msgRead.append(msgReadSign);
                msgRead.append($('<span class="msgType msgColor" >评论</span>'));

            }
            else
            {
                msgRead.append($('<span class="msgTypeRead msgColor">评论</span>'));
            }

            if(obj['pId'] === 0){
                msgRead.append($('<span class="msgHead">' +
                    '<a class="msgPerson" style="color: blue">' + obj['answerer'] + '</a>评论了你的博文' +
                    '</span>'));
            } else {
                if (obj['answerer'] === "LhanMan")
                {
                    msgRead.append($('<span class="msgHead">' +
                        '<a class="msgPerson" style="color: red">' + obj['answerer'] + '</a>回复了你的评论' +
                        '</span>'));
                }
                else {
                    msgRead.append($('<span class="msgHead">' +
                        '<a class="msgPerson" style="color: blue">' + obj['answerer'] + '</a>回复了你的评论' +
                        '</span>'));
                }
            }
            msgRead.append($('<div class="msgTxt">' +
                '<span class="am-intro-title">博客标题:</span>' +
                '<span><a class="articleTitle" href="/article/' + obj['articleId'] + '#p' + obj['id'] + '" target="_blank">' + obj['articleTitle'] + '</a></span>' +
                '<span class="msgDate">' + obj['commentDate'] + '</span>' +
                '</div><hr>'));
            msgContent.append(msgRead);
        });

        //已读一条消息
        $('.articleTitle').click(function () {
            var parent = $(this).parent().parent().parent();
            var isRead = true;
            var num = $('.msgIsReadNum').html();
            if (parent.find($('.msgReadSign')).length !== 0)
            {
                isRead = false;
            }
            if (isRead === false)
            {
                var id = parent.attr('id').substring(1);
                $.ajax({
                    type:'get',
                    url:'/readThisMsg',
                    dataType:'json',
                    data:{
                        id:id
                        
                    },
                    success:function (data) {

                    },
                    error:function () {

                    }
                });
                //去掉未读红点
                parent.find($('.msgReadSign')).removeClass('msgReadSign');
                //未读消息减1
                $('.msgIsReadNum').html(--num);

                //将评论背景变灰
                parent.find($('.msgColor')).removeClass('msgType');
                parent.find($('.msgColor')).addClass('msgTypeRead');


                //去掉左侧栏未读消息
                if (num === 0)
                {
                    $('.commentNotReadNum').remove();
                }
                else
                {
                    $('.commentNotReadNum').html(num);
                }

                // 去掉导航栏下拉框未读消息
                var newsNum = $('.newsNum').html();
                --newsNum;
                if (newsNum === 0)
                {
                    $('.newsNum').remove();
                }
                else
                {
                    $('.newsNum').html(newsNum);
                }
            }
        });

        //全部标记已读
        $('.msgIsRead').click(function () {
            var num = $('.msgIsReadNum').html();
            if(num !== 0){
                $.ajax({
                    type:'get',
                    url:'/readAllMsg',
                    dataType:'json',
                    data:{
                    },
                    success:function (data) {
                        if(data['status'] === 101){
                            layer.alert("未登录，没有权限", {
                                icon: 5
                            }, function () {
                                window.location.replace("/login");
                            });
                        }
                        else if (data['status'] === 601)
                        {
                            layer.msg("已经将所有标记已读了",{
                                icon:7
                            })
                        }
                        else {
                            $('.msgIsReadNum').html(0);
                            $('.msgContent').find($('.msgReadSign')).removeClass('msgReadSign');
                            $('.msgContent').find($('.msgColor')).removeClass('msgType');
                            $('.msgContent').find($('.msgColor')).addClass('msgTypeRead');
                            $('.commentNotReadNum').remove();

                            $('.newsNum').remove();

                        }
                    },
                    error:function () {
                    }
                })
            }
        })



    }
}


function getUserComment(currentPage) {
    $.ajax({
        type:'post',
        url:'/getUserComment',
        dataType:'json',
        data:{
            pageNum:currentPage,
            pageSize:"10"
        },
        success:function (data) {
            if (data['status'] === 101)
            {
                layer.alert("未登录，没有权限", {
                    icon: 5
                }, function () {
                    window.location.replace("/login");
                });
            }
            putInCommentInfo(data['data']);
            scrollTo(0,0);

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
                        getUserComment(curr_page);
                    }


                })
            });
        },
        error:function () {
            layer.alert("发生错误",{
                icon:5
            })
        }
    })
}

// 点击评论管理
$('.commentMessage').click(function () {
    getUserComment(1);
});






