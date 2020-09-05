//获得登录用户未读消息
$.ajax({
    type:'post',
    url:'/getUserNews',
    dataType:'json',
    data:{
    },
    success:function (data) {
        var thisPageName = window.location.pathname + window.location.search;
        var news = $('.news');
        if(data['status'] !== 101 && data['data'] !== 0){
            news.append($('<span class="newsNum am-margin-right am-fr am-badge am-badge-danger am-round">' + data['data']+'</span>'));
            if(thisPageName === "/user"){
                if(data['data'] !== 0){
                    $('.commentMessage').find('a').append($('<span class="commentNotReadNum am-margin-right am-fr am-badge am-badge-danger am-round">' + data['data']+ '</span>'));
                }
            }
        }
    },
    error:function () {
    }
});