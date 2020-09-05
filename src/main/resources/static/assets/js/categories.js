/*
为categories.html定制的JavaScript

 */
window.onload = function () {
    $.ajax({
        type:"get",
        url:"category/list",
        datatype: "json",
        success:function (json) {
            $.each(json,function (i,item) {
                //填充分类信息
                var categoryInfo = document.querySelector("#categoryInfo");
                jQuery(categoryInfo.content.querySelector("a")).attr("style","background-image:url(http://static.lhanman.cn/static/assets/images/category/"+item.id+".jpg);");
                categoryInfo.content.querySelector(".cat-title").innerHTML = item.name+" ("+item.number+")";
                categoryInfo.content.querySelector("h6").innerHTML = item.id;
                document.getElementById("category").appendChild(categoryInfo.content.cloneNode(true));

            });
        }
    })
};

function showArticleByCategoryId(_this) {
    var categoryId = $(_this).children("h6").text();
    var url = "category/"+ categoryId;
    window.location.href = url;
}

