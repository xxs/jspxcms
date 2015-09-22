$(function(){
	validataOSFun();
	nthchildIE8Fun();
	rankingListFun(); //排行榜列表切换
	appendShadowFun(); //添加遮罩层
	appendTipsFun(); //添加tips层
	adStatisticsFun(); //广告统计点击次数
	footerMyCollect(); //页面底部我的收藏
})

//验证当前操作系统是MAC、Windows
function validataOSFun(){
	var cur_os_class = "";
	if(navigator.userAgent.indexOf("Mac OS X")>0) {
		cur_os_class = "macbody";
	}

	$("body").addClass(cur_os_class);
}

//IE8以下的浏览器不支持nth-child
function nthchildIE8Fun(){
	if($.browser.msie && parseInt($.browser.version) <= 8){
		$(".applyliststyle100").find("li:nth-child(7n)").addClass("rowlastli");
	}
}

/********** 百度统计 **********/
/*
	pageURL : 请求的URL
*/
function baiDuStatisticsPageviewFun(pageURL){
	_hmt.push(['_trackPageview', pageURL]);
}

//下载、购买操作按钮
function operaBtnFun(ulBox){
	var $a_tri = $("." + ulBox).find(".btn-gray");
	var init_text;

	$a_tri.hover(function(){
		var _this = $(this);
		var state = _this.attr("data-opera-state");

		if(state == "download"){
			var cls = "down-icon";
			var text = "立即下载";
		}else if(state == "link"){
			var cls = "link-icon";
            var text = "打开链接";
            _this.attr('target', '');
		}else{
            var cls = "buy-icon";
            var text = "立即购买";
        }

		init_text = _this.find("i").text();
		_this.addClass("btn-gray-hover");
		_this.find("i").text(text);
		_this.find("span").remove();
		_this.prepend('<span class="'+ cls +'"></span>');
		_this.find("span").stop().animate({"top":"1px"},400,function(){
			_this.find("span").stop().animate({"top":"5px"},200);
		});
	},function(){
		var _this = $(this);
		_this.removeClass("btn-gray-hover");
		_this.find("i").text(init_text);
		_this.find("span").remove();
	});
}

//下载、购买操作按钮
function operaBtnFunBig(ulBox){
	var $a_tri = $("." + ulBox).find(".btn-state");
	var init_text;

	$a_tri.hover(function(){
		var _this = $(this);
		var state = _this.attr("data-opera-state");

        if(state == "download"){
            var cls = "down-icon";
            var text = "立即下载";
        }else if(state == "link"){
        	var cls ="link-icon";
            var text = "打开链接";
            _this.attr('target', '');
        }else{
            var cls = "buy-icon";
            var text = "立即购买";
        }

		init_text = _this.find("i").text();
		_this.addClass("btn-state-hover");
		_this.find("i").text(text);
		_this.find("span").remove();
		_this.prepend('<span class="'+ cls +'"></span>');
		_this.find("span").stop().animate({"top":"5px"},400,function(){
			_this.find("span").stop().animate({"top":"10px"},200);
		});
	},function(){
		var _this = $(this);
		_this.removeClass("btn-state-hover");
		_this.find("i").text(init_text);
		_this.find("span").remove();
	});
}

//排行榜列表切换
function rankingListFun(){
	var $ul_list = $(".rankingcut"),
		$li_tri = $ul_list.find("li");
	$li_tri.hover(function(){
		var _this = $(this);
		_this.parents("ul").find("li").removeClass("current_dtmr");
		_this.addClass("current_dtmr");

		var idx = _this.index();
		_this.parents(".monoblock_r").find(".ullist_mr").hide();
		_this.parents(".monoblock_r").find(".ullist_mr:eq("+ idx +")").show();
	});
}

/*
	* hover li add class
	* ulBox 
	* addCls : li添加class
*/
function applyListHoverFun(ulBox,addCls){
	var $li_tri = $("." + ulBox).find("li");
	$li_tri.hover(function(){
		var _this = $(this);
		_this.addClass(addCls);
	},function(){
		var _this = $(this);
		_this.removeClass(addCls);
	});
}


/********** 判断用户是否需要登录 **********/
function isLoginAjax(){ //登录检查 state:{comment:评论，buy:购买，download:下载，updata:更新，open：打开}
	var login_post_url = "user/check_login";

	//百度统计
	baiDuStatisticsPageviewFun(login_post_url);

	$.ajax({
       type : "POST",
       url : login_post_url,
       data : {},
       async: false, //false:同步，true:异步(默认)
       dataType: "json",
       success : function(res){ //{1：已登录 , 0：未登录}
       		if(res.res == 1){
       			$("#isLogin").val(1);
       		}else{
       			$("#isLogin").val(0);
       		}
       }
    });
}

/********** 显示遮罩背景 **********/
function appendShadowFun(){
	if($("#bgShadow").length <= 0){
		$('body').append(returnShadowFun());
	}
}
function returnShadowFun(){ //背景阴影
	return '<div class="bgshadow" id="bgShadow"></div>';
}
function showShadowFun(){ //显示背景阴影
	$("#bgShadow").show();
}
function hideShadowFun(){ //隐藏背景阴影
	$("#bgShadow").hide();
}

/********** tips提示 **********/
function appendTipsFun(){
	if($("#tipsWrap").length <= 0){
		$('body').append(returnTipsFun());
	}
	sureTipsFun();
	closeTipsFun();
}
function returnTipsFun(){ //返回tips的html
	var loginTitLang = '提示',
		loginLang = '友情提示：登录后才能购买',
		loginBtnLang = '确定';
	return '<div class="tipswrap" id="tipsWrap">'
			+'<div class="headertips">'
			+'<h4>'+loginTitLang+'</h4>'
			+'<a href="javascript:void(0);" class="close"></a>'
			+'</div>'
			+'<div class="bodyertips">'
			+'<p class="promptmsg_b">'+loginLang
			+'</p>'
			+'</div>'
			+'<div class="footertips">'
			+'<div class="btngroup_f clearfix">'
			+'<a href="javascript:void(0);" class="surebtn_bf" id="promptSureBtn">'+loginBtnLang+'</a>'
			+'</div>'
			+'</div>'
			+'</div>';
}
function sureTipsFun(){ //关闭登录的弹层
	$("#promptSureBtn").bind("click",function(){
		hideShadowFun();
		hideTipsFun();
	})
}
function showTipsFun(str,opera){
	var textStr = '';
	var str = str || textStr;
	var opera = opera || "";

	if(str != ""){
		$("#tipsWrap").find(".promptmsg_b").text(str);
	}

	if(opera == "login"){
		var url = window.location.href; //登录完成后，要跳回当前页面
		$("#promptSureBtn").text("登录")
						   .attr({"href":"https://passport.cocos.com/sso/signin?client_id=183&url="+url});
	}else{
		$("#promptSureBtn").text("确定").attr({"href":"javascript:void(0);"});
	}

	showShadowFun();
	$("#tipsWrap").show();
}
function hideTipsFun(){
	$("#tipsWrap").hide();
}
function closeTipsFun(){
	$("#tipsWrap .close").bind("click",function(){
		hideShadowFun();
		hideTipsFun();
	});
}
function autoHideTipsFun(){
	var ts = setTimeout(function(){
		hideShadowFun();
		hideTipsFun();
		clearTimeout(ts);
	},3000);
}

/********** 广告统计点击次数 **********/
function adStatisticsFun(){
	$(".adstatistics").unbind("click");
	$(".adstatistics").bind("click",function(){
		var _this = $(this);
		var adId = _this.attr("advert-id");

		var ad_post_url = "search/ad_browse/"+ adId +".html";
		
		//百度统计
		baiDuStatisticsPageviewFun(ad_post_url);

		$.ajax({
	       type : "POST",
	       url : ad_post_url,
	       data : {},
	       async: false, //false:同步，true:异步(默认)
	       dataType: "json",
	       success : function(res){}
	    });
	})
}

/*************** 多级导航 ***************/ 
function navwrapFun(){
	var $li_item = $("#classifyNavbar").find("li");
		$li_item.hover(function(){
			var _this = $(this);

			var has_child_li =  _this.children(".childmenu_hcnm").length;
			var cur_li_pos_left = liPositionFun(_this); /*1px:两条边线重合*/

			if(_this.children("a").hasClass("first_cnm")){
				var padding_left = "55px";
			}else{
				var padding_left = "36px";
			}

			_this.children("a").find(".icon_nm").stop().animate({"left":"30px"},300);
			_this.children("a").stop().animate({"padding-left":padding_left},300);

			_this.children("a").addClass("lihover_cnm");
			_this.parents("li").children("a").addClass("lihover_cnm");


			if(has_child_li > 0){
				_this.children(".childmenu_hcnm").show().css({"left":cur_li_pos_left});
			}
		},function(){
			var _this = $(this);

			if(_this.children("a").hasClass("first_cnm")){
				var padding_left = "45px";
			}else{
				var padding_left = "26px";
			}

			_this.children("a").find(".icon_nm").stop().animate({"left":"20px"},300);
			_this.children("a").stop().animate({"padding-left":padding_left},300);

			_this.children("a").removeClass("lihover_cnm");
			_this.children(".childmenu_hcnm").hide();
		});
}
function liPositionFun(tri){
	var css_left = parseFloat(tri.css("left"));
	var li_wid = tri.width();

	//var Left = css_left + li_wid;

	var Left = li_wid;

	return Left;
}

//区别中英文，获取字符串的实际长度
function strLength(str){ 
    var rea_L = 0,charCode;
    for(var i = 0 ; i < str.length ; i++){
        charCode = str.charCodeAt(i);
        if(charCode >= 0 && charCode <=128){
            rea_L ++;
        }else{
            rea_L += 2;
        }
    }
    return rea_L;
}

//中英文截断
function strCut(str,len){
    if(!str || !len){
        return "";
    }
    var a = 0,tempStr = "";//临时字符串
    for(var i = 0 ; i < str.length ; i++){
        (str.charCodeAt(i) > 255) ? a+=2 :a++;
        if(a > len){
            return tempStr; //如果增加计数后长度大于限定长度，就直接返回临时字符串
        }
        tempStr += str.charAt(i);
    }
    return str;
}


/*************** 页面底部我的收藏  ***************/ 
function footerMyCollect(){
	$("#myCollect").unbind("click");
	$("#myCollect").bind("click",function(){
		isLoginAjax();
		if($("#isLogin").val() == 0){
			var errText = '请先登录';
			showTipsFun(errText,"login");

			return false;
		}
	});
}

function get_content_url(id){
    var refer = window.location.href;
    var get_cont_post_url = 'jsapi/get_content_url/'+id+'.html';

    //百度统计
	baiDuStatisticsPageviewFun(get_cont_post_url);

    $.ajax({
        type:'POST',
        async:false,
        data:{refer:refer},
        url:get_cont_post_url,
        dataType:'text',
        success:function(msg){
            url = msg;
        }
    });
    return url;
}
