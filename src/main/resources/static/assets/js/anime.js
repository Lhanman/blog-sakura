
setTimeout(function(){
    jQuery.ajax({
        type: 'GET',
        url: '',
        success: function() {
            $('div.bangumibg').width($('a.bangumItem').width());
        },
        error:function(){
        }
    });
},500);

window.onresize = function(){
    $('div.bangumibg').width($('a.bangumItem').width());
};
window.onload = function () {
    getAnimeByPage(1);
};

function getAnimeByPage(currentPage)
{
    $.ajax({
        type: "get",
        url: "/getAnime",
        datatype: "json",
        data:{
            pageNum:currentPage
        },
        success:function (data) {
            var data = JSON.parse(data);
            if(data['status'] === 103)
            {
                alert("服务器异常，获得信息失败");
            }
            else
            {
                putInAnime(data['data']);
                scrollTo(0,0);//回到顶部
            }

            //分页功能
            var length = data['data'].length;

            var pageNum = data['data'][length-1]['pageNum'];//当前页码
            var pageSize = data['data'][length-1]['pageSize'];//每页显示条目
            var total = data['data'][length-1]['total'];//总记录数

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
                        getAnimeByPage(curr_page);

                    }


                })
            });


        }
    });
}


function putInAnime(data) {

    $('.animes').empty();
    var animes = $(".animes");

    $.each(data,function (i, item) {
        if (i !== (data.length) - 1 ) {
            var anime = $('<a href="' + item['url'] + '" target="_blank" class="bangumItem" title="' + item['evaluate'] + '">\n' +
                '                <div class="bangumibg AnimeCover" style="background-image:url('+item['background']+'); width: 550px;height: 130px"></div>\n' +
                '                <div class="mainMsg">\n' +
                '                    <img src="' + item['background'] + '"/>\n' +
                '                    <div class="textBox">\n' +
                '                        <p>' + item['title'] + '</p>' +
                '                        最近话:<span>' + item['long_title'] + '</span><br>\n' +
                '                        首播日期:<span>' + item['release_date_show'] + '</span><br><br><hr>\n' +
                '                        <div class="jinduBG">\n' +
                '                            <div class="jinduText">' + item['myProgress'] + '</div>\n' +
                '                            <div class="jinduFG" style="width:' + item['progressWidth'] + '%">\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '\n' +
                '                </div>\n' +
                '            </a>');
            animes.append(anime);

        }
    })
}




