// JavaScript Document
function menuMobileStart(menuWidth){
	$(".menu-load").css({"display":"block"});
	$("#mini-menu").attr("type","1");
	$("#mini-menu").removeClass("pointer2");
	$("#mini-menu").addClass("pointer");
	$(".top-left").css({width:window.menuLeft+"px"});
	$(".top-right").css({width:(window.windowWidth-window.menuLeft)+"px"});
	$(".left-menu-two").css({left:(window.menuLeft)+"px"});
	$(".left-menu").css({width:(window.menuLeft)+"px"});
	$(".center-left").css({width:(window.menuLeft+menuWidth)+"px"});
	$(".center-right").css({right:-(window.menuLeft+menuWidth)+"px"});
}
function menuMobileEnd(){
	$(".menu-load").css({"display":"none"});
	$("#mini-menu").attr("type","2");
	$("#mini-menu").removeClass("pointer");
	$("#mini-menu").addClass("pointer2");
	$(".top-left").css({width:"0px"});
	$(".top-right").css({width:"100%"});
	$(".left-menu-two").css({left:"0px"});
	$(".left-menu").css({width:"0px"});
	$(".center-left").css({width:"0px"});
	$(".center-right").css({width:"100%","right":"0px"});
	
	$(".label-user").attr("opentype","0");
	$(".label-user").stop().animate({height:"0px"},300,function(){
		$(".label-user").css({"display":"none"});
	});
	$(".label-gd").attr("opentype","0");
	$(".label-gd").stop().animate({height:"0px"},300,function(){
		$(".label-gd").css({"display":"none"});
	});
	$(".label-weixin").attr("opentype","0");
	$(".label-weixin").stop().animate({height:"0px"},300,function(){
		$(".label-weixin").css({"display":"none"});
	});
}
function menuStart(menuWidth){
	$("#mini-menu").attr("type","2");
	$("#mini-menu").removeClass("pointer");
	$("#mini-menu").addClass("pointer2");
	$(".top-left").stop().animate({width:"0px"},animiTime);
	$(".top-right").stop().animate({width:"100%"},animiTime);
	$(".left-menu-two").stop().animate({left:(window.menuIco)+"px"},animiTime);
	$(".left-menu").stop().animate({width:(window.menuIco)+"px"},animiTime);
	$(".center-left").stop().animate({width:(window.menuIco+menuWidth)+"px"},animiTime);
	$(".center-right").stop().animate({width:(window.windowWidth-window.menuIco-menuWidth)+"px"},animiTime);
}
function menuEnd(menuWidth){
	$("#mini-menu").attr("type","1");
	$("#mini-menu").removeClass("pointer2");
	$("#mini-menu").addClass("pointer");
	$(".top-left").stop().animate({width:window.menuLeft+"px"},animiTime);
	$(".top-right").stop().animate({width:(window.windowWidth-window.menuLeft)+"px"},animiTime);
	$(".left-menu-two").stop().animate({left:(window.menuLeft)+"px"},animiTime);
	$(".left-menu").stop().animate({width:(window.menuLeft)+"px"},animiTime);
	$(".center-left").stop().animate({width:(window.menuLeft+menuWidth)+"px"},animiTime);
	$(".center-right").stop().animate({width:(window.windowWidth-window.menuLeft-menuWidth)+"px"},animiTime);
}
//--------------
function loadTipsEnd(){
	 $(".center-load").stop().animate({opacity:0},300,function(){
			$(".center-load").css({"display":"none"});
	});
}
function loadTipsStart(){
	/*$(".center-load").css({"display":"block"}).stop().animate({opacity:1},300);*/
}
//--------------------
function showTwoMenu(obj){
	showMenu(obj);
	var type=$("#mini-menu").attr("type");
	if(type=="1"){
			if(window.isMobile){
				$(".center-left").css({width:(window.menuLeft+window.menuRight)+"px"});
				$(".center-right").css({right:-(window.menuLeft+window.menuRight)+"px"});
				$("#left-menu-two").getNiceScroll().resize();
			}else{
				$(".center-left").stop().animate({width:(window.menuLeft+window.menuRight)+"px"},animiTime);
				$(".center-right").stop().animate({width:(window.windowWidth-window.menuLeft-window.menuRight)+"px"},animiTime,function(){
					$("#left-menu-two").getNiceScroll().resize();	
				});
			}
	}else{
			if(window.isMobile){
				$(".center-left").css({width:(window.menuIco+window.menuRight)+"px"});
				$(".center-right").css({"right":-(window.menuIco+window.menuRight)+"px"});
				$("#left-menu-two").getNiceScroll().resize();
			}else{
				$(".center-left").stop().animate({width:(window.menuIco+window.menuRight)+"px"},animiTime);
				$(".center-right").stop().animate({width:(window.windowWidth-window.menuIco-window.menuRight)+"px"},animiTime,function(){
					$("#left-menu-two").getNiceScroll().resize();	
				});
			}
	}
}
function hideTwoMenu(){
	if(window.isMobile){
		$(".center-left").css({width:"0px"});
		$(".center-right").css({width:"100%"});
	}else{
		var type=$("#mini-menu").attr("type");
		if(type=="1"){
				$(".center-left").stop().animate({width:(window.menuLeft)+"px"},animiTime);
				$(".center-right").stop().animate({width:(window.windowWidth-window.menuLeft)+"px"},animiTime);
		}else{
				$(".center-left").stop().animate({width:(window.menuIco)+"px"},animiTime);
				$(".center-right").stop().animate({width:(window.windowWidth-window.menuIco)+"px"},animiTime);
		}
	}
}
//--------------------
function showMenu(obj){
	$(".menu-two-items").css({"display":"none"});
	$(".menu-two-items").removeClass("onItems");
	$("#"+obj).addClass("onItems");
	$("#"+obj).css({"display":"block"});	
	$(".m-t-i-list .list-items").removeClass("menu-cheack");
}
function doMenuLink(obj){
	if(!window.isMobile){
		var list=$("#"+obj).find(".m-t-i-list").find(".list-items").first().find("a");
		if(typeof(list.attr("url"))!="undefined"){
			if(loadIFrame(list.attr("url"))){
				$(".list-items").removeClass("menu-cheack");
				list.parent(".list-items").addClass("menu-cheack");
			}
		}
	}
}
//------------
function loadIFrame(url){
	if(window.isMobile){
		menuMobileEnd();
		loadTipsStart();
		if(url!=""){
			setTimeout(function(){
				$("#frame").attr("src",url);
			},window.animiTime+1);
			return true;
		}else{
			return false;	
		}
	}else{
		loadTipsStart();
		if(url!=""){
			$("#frame").attr("src",url);
			return true;
		}else{
			return false;	
		}
	}
}
function ifrClick(model,action,ctrl){
	loadTipsStart();
	if(model!=""&&model!=null){
		$(".menu-items").removeClass("menu-on");
		$(".menu-items").addClass("pointer3");
		$("#"+model).removeClass("pointer3");
		$("#"+model).addClass("menu-on");
		if(action!=""&&action!=null){
			window.openMenu=true;
			showTwoMenu(action);
			$(".list-items").removeClass("menu-cheack");
			$("#"+ctrl).parent(".list-items").addClass("menu-cheack");
		}else{
			hideTwoMenu();
		}
	}
}
function takeMenuTrue(model,action,ctrl){
	if(!window.isMobile){
		if(model!=""&&model!=null){
			$(".menu-items").removeClass("menu-on");
			$(".menu-items").addClass("pointer3");
			if($("#"+model).length>0){
				//判断是否存在，代理可能不存在的情况
				$("#"+model).removeClass("pointer3");
				$("#"+model).addClass("menu-on");
				if(action!=""&&action!=null){
					window.openMenu=true;
					showTwoMenu(action);
					if(ctrl!=""&&ctrl!=null){
						$(".list-items").removeClass("menu-cheack");
						$("#"+ctrl).parent(".list-items").addClass("menu-cheack");
					}
				}
			}
		}
	}
}
function setCookieMain(cookiename, cookievalue, hours) {
	var date = new Date();
	date.setTime(date.getTime() + Number(hours) * 3600 * 1000);
	document.cookie = cookiename + "=" + cookievalue + "; path=/;expires = " + date.toGMTString();
}
//改变颜色
function setSkinColor(val){
	setCookieMain('new_skin_color',val,7*24);
	window.location.reload();
}