/**
 * Created by china_000 on 2015-05-13.
 */
// banner视觉差效果
$(function(){
    var settings = {
        ratio: "0.4",
        bgSelector: ".banner",
        isBackgroundImg: false
    };
    $.fn.bgVisual = function(option){
        $.extend(settings, option);
        var $bg = $(this);
        if(settings.isBackgroundImg){
            $bg.css({"background-attachment":"fixed","background-position-y": $(window).scrollTop() * settings.ratio * -1});

            $(window).on("scroll", function(){
                //if($bg.css("background-attachment") == "scroll"){
                //    $bg.css("background-attachment", "fixed");
                //}
                $bg.css({"background-position-y": $(window).scrollTop() * settings.ratio * -1});
                console.log();
            });
        } else {
            $bg.css({position: "fixed", top: $(window).scrollTop() * settings.ratio * -1, "z-index": -1});
            $(window).on("scroll", function(){
                $bg.css({top: $(window).scrollTop() * settings.ratio * -1});
            });
        }
    };
});

// 百度地图api调用
$(function(){
    var settings = {
        mapContainer: "baidumap",      // 百度地图容器
        searchKey: "中国金融认证中心",   // 搜索关键字
        map: null,                     // 地图实例对象
        mPoint: null                   // 地图中心点实例对象
    };
    $.fn.baiduMap = function(){
        settings.mapContainer = $(this)[0].id;//获取地图容器ID
        // 异步引用api
        var script = document.createElement("script");
        script.type = "text/javascript";
        script.src = "http://api.map.baidu.com/api?v=2.0&ak=YLh0IE4VR9lXc8nPeYBFnsn0&callback=$.showBaiduMap";
        document.body.appendChild(script);

        // 窗体大小改变监听，移动地图至中心点
        $(window).on("resize", function(){
            settings.map.centerAndZoom(settings.mPoint, 14);
        });
    };

    $.showBaiduMap = function(){
        // 百度地图API功能
        settings.map = new BMap.Map(settings.mapContainer, {enableMapClick:false});// 创建Map实例
        settings.mPoint = new BMap.Point(116.3801290000, 39.8887370000); // 创建中心点实例
        var circle = new BMap.Circle(settings.mPoint,1500,{fillColor:"red", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});// 创建圆形遮盖物
        var navigationControl = new BMap.NavigationControl({
            anchor: BMAP_ANCHOR_BOTTOM_RIGHT // 创建平移和缩放工具，定位右下
        });
        var local =  new BMap.LocalSearch(settings.map, {renderOptions: {map: settings.map, autoViewport: false}});// 创建区域搜索实例
        local.disableFirstResultSelection();// 禁用选中第一个搜索结果
        local.searchNearby(settings.searchKey,settings.mPoint,1000);// 中心点1000范围内搜索关键字

        settings.map.centerAndZoom(settings.mPoint, 14);// 设置中心点及缩放

        settings.map.addControl(navigationControl);// 添加平移缩放控件
        settings.map.addOverlay(circle);// 添加圆形遮盖物
    };
});

$(function(){
    $("#wx").on("mouseenter", wxHover);
    $("#wx").on("mouseleave", wxUnhover);

    function wxHover(){
        $wx = $(this);
        $wx.unbind("mouseenter");
        $wx.find(".qr_code").fadeIn();
    }

    function wxUnhover(){
        $wx = $(this);
        $wx.find(".qr_code").fadeOut("normal", function(){
            $("#wx").on("mouseenter", wxHover);
        });
    }
});