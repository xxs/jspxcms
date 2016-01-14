/**
 * Created by dongchangkun on 2015-05-08.
 */
$(function(){
    $("#news_list_ul li a").on("click", function(){

        // 判断是否已禁用，已禁用不处理
        if($(this).parent().hasClass("disabled")){
            return false;
        }
        // 获取当前点击按钮的索引
        var index = $("#news_list_ul li a").index($(this));
        // 获取一共有多少个按钮
        var length = $("#news_list_ul li a").length;
        // 初始化页码
        var pageNo = 1;
        // 如果点击的是第一按钮【首页】
        if(index == 0){
            // 不处理，页码默认为1
        } else if(index == 1){
            // 如果点击的是第二个按钮【上一页】
            // 页码设置为当前激活页码-1
            pageNo = $("#news_list_ul li.active a").text() - 1;
        } else if(index == length - 2){
            // 如果点击的是倒数第二个按钮【下一页】
            // 页码设置为当前激活页码+1
            pageNo = parseInt($("#news_list_ul li.active a").text()) + 1;
        } else if(index == length - 1){
            // 如果点击的是最后一个按钮【尾页】
            // 页码设置为分页总数
            pageNo = $("#newsList1 ul").length;
        } else {
            // 如果直接点击页码
            // 页码设置为所点击的页码
            pageNo = $(this).text();
        }
        setPageActive(pageNo);
    });

    function setPageActive(pageNo){
        // 获取总页数
        var totalPage = $("#newsList1 ul").length;
        // 清除所有新闻列表的active样式，隐藏所有
        $("#newsList1 ul").removeClass("active");
        // 给索引是pageNo-1的列表增加active样式，显示列表
        $("#newsList1 ul:eq(" + (pageNo - 1) + ")").addClass("active");
        // 所有的分页移除active样式
        $("#news_list_ul li").removeClass("active");
        // 索引为pageNo+1的分页增加active样式
        $("#news_list_ul li:eq(" + (parseInt(pageNo) + 1) + ")").addClass("active");
        // 如果pageNo是第一页，添加【上一页】按钮的disabled样式
        if(pageNo == 1){
            $("#news_list_ul li:eq(1)").addClass("disabled");
        } else {
            $("#news_list_ul li:eq(1)").removeClass("disabled");
        }
        // 如果pangeNo是最后一页，添加【下一页】按钮的disabled样式
        if(pageNo == totalPage){
            $("#news_list_ul li:eq(" + (totalPage + 2) + ")").addClass("disabled");
        } else {
            $("#news_list_ul li:eq(" + (totalPage + 2) + ")").removeClass("disabled");
        }
    }
});

$(function(){
    $("#info_list_ul li a").on("click", function(){

        // 判断是否已禁用，已禁用不处理
        if($(this).parent().hasClass("disabled")){
            return false;
        }
        // 获取当前点击按钮的索引
        var index = $("#info_list_ul li a").index($(this));
        // 获取一共有多少个按钮
        var length = $("#info_list_ul li a").length;
        // 初始化页码
        var pageNo = 1;
        // 如果点击的是第一按钮【首页】
        if(index == 0){
            // 不处理，页码默认为1
        } else if(index == 1){
            // 如果点击的是第二个按钮【上一页】
            // 页码设置为当前激活页码-1
            pageNo = $("#info_list_ul li.active a").text() - 1;
        } else if(index == length - 2){
            // 如果点击的是倒数第二个按钮【下一页】
            // 页码设置为当前激活页码+1
            pageNo = parseInt($("#info_list_ul li.active a").text()) + 1;
        } else if(index == length - 1){
            // 如果点击的是最后一个按钮【尾页】
            // 页码设置为分页总数
            pageNo = $("#newsList2 ul").length;
        } else {
            // 如果直接点击页码
            // 页码设置为所点击的页码
            pageNo = $(this).text();
        }
        setPageActive(pageNo);
    });

    function setPageActive(pageNo){
        // 获取总页数
        var totalPage = $("#newsList2 ul").length;
        // 清除所有新闻列表的active样式，隐藏所有
        $("#newsList2 ul").removeClass("active");
        // 给索引是pageNo-1的列表增加active样式，显示列表
        $("#newsList2 ul:eq(" + (pageNo - 1) + ")").addClass("active");
        // 所有的分页移除active样式
        $("#info_list_ul li").removeClass("active");
        // 索引为pageNo+1的分页增加active样式
        $("#info_list_ul li:eq(" + (parseInt(pageNo) + 1) + ")").addClass("active");
        // 如果pageNo是第一页，添加【上一页】按钮的disabled样式
        if(pageNo == 1){
            $("#info_list_ul li:eq(1)").addClass("disabled");
        } else {
            $("#info_list_ul li:eq(1)").removeClass("disabled");
        }
        // 如果pangeNo是最后一页，添加【下一页】按钮的disabled样式
        if(pageNo == totalPage){
            $("#info_list_ul li:eq(" + (totalPage + 2) + ")").addClass("disabled");
        } else {
            $("#info_list_ul li:eq(" + (totalPage + 2) + ")").removeClass("disabled");
        }
    }
});
