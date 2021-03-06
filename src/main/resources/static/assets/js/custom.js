/*==================================================================================
    Custom JS (Any custom js code you want to apply should be defined here).
====================================================================================*/

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


//图片懒加载
$(function() {
    // 获取window的引用:
    var $window = $(window);
    // 获取包含data-src属性的img，并以jQuery对象存入数组:
    var lazyImgs = _.map($('img[data-src]').get(), function (i) {
        return $(i);
    });
    // 定义事件函数:
    var onScroll = function () {
        // 获取页面滚动的高度:
        var wtop = $window.scrollTop();
        // 判断是否还有未加载的img:
        if (lazyImgs.length > 0) {
            // 获取可视区域高度:
            var wheight = $window.height();
            // 存放待删除的索引:
            var loadedIndex = [];
            // 循环处理数组的每个img元素:
            _.each(lazyImgs, function ($i, index) {
                // 判断是否在可视范围内:
                if ($i.offset().top - wtop - 350 < wheight) {
                    // 设置src属性:
                    $i.attr('src', $i.attr('data-src'));
                    // 添加到待删除数组:
                    loadedIndex.unshift(index);
                }
            });
            // 删除已处理的对象:
            _.each(loadedIndex, function (index) {
                lazyImgs.splice(index, 1);
            });
        }
    };
    // 绑定事件:
    $window.scroll(onScroll);
    // 手动触发一次:
    onScroll();
});