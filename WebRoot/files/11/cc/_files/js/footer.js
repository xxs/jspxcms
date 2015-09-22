$(function(){
	initScrollTopFun(); //显示回到顶部
	scrollEventFun(); //显示回到顶部
	returnTopFun(); //回到顶部
	showWeixinCodeFun(); //微信二维码
	footerWeixinCodeFun(); //页面底部微信二微码
})

//显示回到顶部
function initScrollTopFun(){
	var  init_scroll_top = $(document).scrollTop();
	showReturnTopFun(init_scroll_top);
}

function scrollEventFun(){
	$(window).scroll(function(){
		var scroll_top = $(document).scrollTop();
		showReturnTopFun(scroll_top);
	})
}
function showReturnTopFun(scroll_top){
	if(scroll_top >= 200){
		$("#returnTop").fadeIn();
	}else{
		$("#returnTop").fadeOut();
	}

	var scroll_height = $(document).height() - $(window).height() - $(".footerwrap").height();

	if(scroll_top > scroll_height){
		$("#rightSideBar").css("bottom",$(".footerwrap").height()+"px");
	}else{
		$("#rightSideBar").css("bottom","10px");
	}
}

//回到顶部
function returnTopFun(){
	$("#returnTop").bind("click",function(){
		$('html,body').stop().animate({'scrollTop':0},500);
	});
}

//微信二维码
function showWeixinCodeFun(){
	$("#showWeixinCode").hover(function(){
		$("#weixinCodeBox").stop().show().animate({left : "-172px"},400,function(){
			$("#weixinCodeBox").stop().show().animate({left : "-175px"},400);
		});
	},function(){
		$("#weixinCodeBox").stop().hide();
	});
}


//页脚划过微信、qq显示其二维码
function footerWeixinCodeFun(){
	var $share_a = $("#footerShareList").find("a"),
		$dis_code = $(".discode_sf");
	$share_a.hover(function(){
		var _this = $(this);
		$dis_code.hide();
		_this.parent().find(".discode_sf").show();
	},function(){
		var _this = $(this);
		_this.parent().find(".discode_sf").hide();
	});
}