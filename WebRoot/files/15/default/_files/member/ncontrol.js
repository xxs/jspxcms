// JavaScript Document
var windowWidth=0;
var windowHeight=0;
//-----
var menuLeft=150;
var menuRight=180;
var menuIco=55;
var animiTime=500;
var openMenu=false;
var commTop=0;
var touchX=0;
var touchXTwo=0;
var supportTouch=false;//触控事件；
$(function(){
	getWindowSize();
	setViews();
	setLesten();
});
$(window).load(function(){
	if(!window.isMobile){$("#left-menu-two").getNiceScroll().resize();}
});
$(window).resize(function() {
	setViews();
});
function getWindowSize(){
	window.windowWidth=$(window).width()?$(window).width():document.body.scrollWidth;
	window.windowHeight=$(window).height()?$(window).height():document.body.scrollHeight;
	setCookieMain("myscreenheight",window.windowHeight,24*7);
	window.commTop=$(".comm-top").height();
}
function setViews(){
	window.supportTouch= "createTouch" in document;
	getWindowSize();
	if(window.isMobile){
		$(".mobile-ndisplay").css({"display":"none"});
		$(".menu-hide span").css({"display":"none"});
		$(".menu-hide span").removeClass("i-left");
		$(".top-right .t-r-menu .menu-items ").css({"padding":"0px 15px"});
		//-------------
		$("#mini-menu").attr("type","2");
		$("#mini-menu").removeClass("pointer");
		$("#mini-menu").addClass("pointer2");		
		$(".top-left").css({"width":"0px"});	
		$(".top-right").css({"width":"100%"});
		$(".center-left").css({"height":(window.windowHeight-window.commTop)+"px","top":window.commTop+"px","width":"0px"});
		$(".center-right").css({"height":(window.windowHeight-window.commTop)+"px","top":window.commTop+"px","width":"100%","right":"0px"});
		$(".left-menu").css({"height":($(".comm-center").height()-35)+"px"});
		//$(".comm-center").css({"height":(window.windowHeight-window.commTop)+"px","top":window.commTop+"px"});
		$(".left-menu").addClass("animi"); 
		$(".left-menu-two").addClass("animi");
		$(".top-left").addClass("animi");
		$(".top-right").addClass("animi");
		$(".center-left").addClass("animi");
		$(".center-right").addClass("animi");
	}else{
		/*top*/
		$(".top-right").css({"width":(window.windowWidth-$(".top-left").width())+"px"});
		/*left*/
		//$(".comm-center").css({"height":(window.windowHeight-window.commTop)+"px","top":window.commTop+"px"});
		$(".left-menu").css({"height":($(".comm-center").height()-35)+"px"});
		/*right*/
		$(".center-right").css({"height":(window.windowHeight-window.commTop)+"px","top":window.commTop+"px","width":(window.windowWidth-$(".center-left").width())+"px","right":"0px"});
		$(".center-left").css({"height":(window.windowHeight-window.commTop)+"px","top":window.commTop+"px"});
		$(".label-gd").css({"right":($("#btn-username").width()+51-($(".label-gd").width()-$("#btn-gd").width()-51)/2-1)+"px"});
		
		$(".label-weixin").css({"right":($(".t-r-menu").width()-$(".label-weixin").width()-1)+"px"});
		
		$("#left-menu").niceScroll({zindex:901,cursoropacitymax:0.3,cursoropacitymin:0.1,touchbehavior:false,horizrailenabled:false});
		$("#left-menu-two").niceScroll({zindex:901,cursoropacitymax:0.3,cursoropacitymin:0.1,touchbehavior:false,horizrailenabled:false});
	}
}
//--------------------------------------------------
function setLesten(){
	$("#frame").load(function(e) {
       	loadTipsEnd();
    });
	if(window.isMobile&&window.supportTouch){
		/*window.addEventListener("orientationchange", function() {
			var temp=window.windowWidth;
			window.windowHeight=window.windowWidth;
			window.windowWidth=temp;
		}, false);*/
		//---------------------------------------
		$("#frame").bind("touchstart", function(e){
		});
		$("#frame").bind("touchend", function(e){
		});
		//---------------------------------------
		$(".menu-load").bind("touchstart", function(e){
			  var e = e || window.event;
			   e.preventDefault();
			   menuMobileEnd();
		});
		//---------------------------------------
		$("#mini-menu").bind("touchstart", function(e){
			var e = e || window.event;
			e.preventDefault();
			var type=$(this).attr("type");
			var menuWidth=0;
			if(window.openMenu){
				menuWidth=window.menuRight;
			}
			if(type=="1"){
				menuMobileEnd();
			}else{
				menuMobileStart(menuWidth);
			}
		});
		//-----------------------------
		
		$(".left-menu .menu-items").each(function(index, element) {
				/*
				touchstart    e.originalEvent.targetTouches[0].pageX
				touchend     e.originalEvent.changedTouches[0].pageX 
				*/
				var father=$(this);
				father.bind("touchend", function(e){
					var doThis=true;
					var maxH=$("#left-menu .menu-items").size()*45;
					if($("#left-menu").height()<maxH){
						var e = e || window.event;
						e.preventDefault();
						var x=e.originalEvent.changedTouches[0].pageX;
						var cha=x-window.touchX;
						if(cha!=0){
							$("#left-menu").scrollTop($("#left-menu").scrollTop()-cha);
							window.touchX=x;
							doThis=false;
						}
					}
					if(doThis){
						/*if(father.is(".menu-on")){
							if(!window.openMenu){
								menuMobileEnd();
							}
							return;
						}*/
						$(".left-menu .menu-items").removeClass("menu-on");
						$(".left-menu .menu-items").addClass("pointer3");
						father.removeClass("pointer3");
						father.addClass("menu-on");
						if(typeof(father.attr("forType"))!="undefined"){
							if(!window.openMenu){
								window.openMenu=true;
								showTwoMenu(father.attr("forType"));
								doMenuLink(father.attr("forType"));
							}else{
								window.openMenu=true;
								showMenu(father.attr("forType"));
								doMenuLink(father.attr("forType"));
							}
						}else{
							if(window.openMenu){
								window.openMenu=false;
								hideTwoMenu();
							}
							if(typeof(father.attr("forUrl"))!="undefined"){
								loadIFrame(father.attr("forUrl"));
							}
						}	
					}
				});
				father.bind("touchstart", function(e){
					var e = e || window.event;
					var maxH=$("#left-menu .menu-items").size()*45;
					if($("#left-menu").height()<maxH){
						window.touchX=e.originalEvent.targetTouches[0].pageX;
					}
				});
		});
		
		$(".list-items-link").bind("touchend", function(e){
			var maxH=$(".onItems h1").size()*36+$(".onItems li").size()*45;
			var doThis=true;
			if($("#left-menu-two").height()<maxH){
				var e = e || window.event;
				var x=e.originalEvent.changedTouches[0].pageX;;
				var cha=x-window.touchXTwo;
				if(cha!=0){
					$("#left-menu-two").scrollTop=$("#left-menu-two").scrollTop-(cha);
					window.touchXTwo=x;
					doThis=false;
				}
			}
			if(doThis){
				 if(typeof($(this).attr("url"))!="undefined"){
					if(loadIFrame($(this).attr("url"))){
						$(".list-items").removeClass("menu-cheack");
						$(this).parent(".list-items").addClass("menu-cheack");
					}
					return false;
				}else{
					return true;	
				}	
			}
		});
		$(".list-items-link").bind("touchstart", function(e){
			var e = e || window.event;
			var maxH=$(".onItems h1").size()*36+$(".onItems li").size()*45;
			if($("#left-menu-two").height()<maxH){
				window.touchXTwo=e.originalEvent.targetTouches[0].pageX;
			}
		});
		//---------------------用户下拉菜单
		$("#btn-username").bind("touchstart", function(e){
				//关闭工单
				//$("#btn-gd .fa").removeClass("xuanzhuan");
				$(".label-gd").attr("opentype","0");
				$(".label-gd").stop().animate({height:"0px"},300,function(){
					$(".label-gd").css({"display":"none"});
				});
				$(".label-weixin").attr("opentype","0");
				$(".label-weixin").stop().animate({height:"0px"},300,function(){
					$(".label-weixin").css({"display":"none"});
				});
				//---end
				var e = e || window.event;
				e.preventDefault();
				if($(".label-user").attr("opentype")=="0"){
					//$("#btn-username .fa").addClass("xuanzhuan");
					$(".label-user").attr("opentype","1");
					$(".label-user").css({"display":"block","height":"0px"}).stop().animate({height:"297px"},300);
				}else{
					//$("#btn-username .fa").removeClass("xuanzhuan");
					$(".label-user").attr("opentype","0");
					$(".label-user").stop().animate({height:"0px"},300,function(){
						$(".label-user").css({"display":"none"});
					});
				}
		});
		$(".label-user a").click(function(){
			//$("#btn-username .fa").removeClass("xuanzhuan");
			$(".label-user").attr("opentype","0");
			$(".label-user").stop().animate({height:"0px"},300,function(){
				$(".label-user").css({"display":"none"});
			});
		});
		//---------------------用户下拉菜单
		$("#btn-gd").bind("touchstart", function(e){
				//关闭用户
				//$("#btn-username .fa").removeClass("xuanzhuan");
				$(".label-user").attr("opentype","0");
				$(".label-user").stop().animate({height:"0px"},300,function(){
					$(".label-user").css({"display":"none"});
				});
				$(".label-weixin").attr("opentype","0");
				$(".label-weixin").stop().animate({height:"0px"},300,function(){
					$(".label-weixin").css({"display":"none"});
				});
				//----end
				var e = e || window.event;
				e.preventDefault();
				if($(".label-gd").attr("opentype")=="0"){
					//$("#btn-gd .fa").addClass("xuanzhuan");
					$(".label-gd").attr("opentype","1");
					$(".label-gd").css({"right":($("#btn-username").width()+31-($(".label-gd").width()-$("#btn-gd").width()-31)/2)+"px","display":"block"}).stop().animate({height:"93px"},300);
				}else{
					//$("#btn-gd .fa").removeClass("xuanzhuan");
					$(".label-gd").attr("opentype","0");
					$(".label-gd").stop().animate({height:"0px"},300,function(){
						$(".label-gd").css({"display":"none"});
					});
				}
		});
		//-------微信
		$("#btn-weixin").bind("touchstart", function(e){
				//关闭用户
				//$("#btn-username .fa").removeClass("xuanzhuan");
				$(".label-user").attr("opentype","0");
				$(".label-user").stop().animate({height:"0px"},300,function(){
					$(".label-user").css({"display":"none"});
				});
				$(".label-gd").attr("opentype","0");
				$(".label-gd").stop().animate({height:"0px"},300,function(){
					$(".label-gd").css({"display":"none"});
				});
				//----end
				var e = e || window.event;
				e.preventDefault();
				if($(".label-weixin").attr("opentype")=="0"){
					//$("#btn-gd .fa").addClass("xuanzhuan");
					$(".label-weixin").attr("opentype","1");
					$(".label-weixin").css({"right":($(".t-r-menu").width()-$(".label-weixin").width()-1)+"px","display":"block"}).stop().animate({height:"235px"},300);
				}else{
					//$("#btn-gd .fa").removeClass("xuanzhuan");
					$(".label-weixin").attr("opentype","0");
					$(".label-weixin").stop().animate({height:"0px"},300,function(){
						$(".label-weixin").css({"display":"none"});
					});
				}
		});
		$(".label-gd a").click(function(){
			//$("#btn-gd .fa").removeClass("xuanzhuan");
			$(".label-gd").attr("opentype","0");
			$(".label-gd").stop().animate({height:"0px"},300,function(){
				$(".label-gd").css({"display":"none"});
			});
		});
		//--------------
		$(".clickItems").each(function(index, element) {
            var father=$(this);
			father.click(function(){
				menuMobileEnd();
				loadTipsStart();
				$("#frame").attr("src",father.attr("url"));
			});
		});
	}else{
		//-------微信
		$("#btn-weixin").mouseover(function(){
			//$("#btn-gd .fa").addClass("xuanzhuan");
			$(".label-weixin").css({"right":($(".t-r-menu").width()-$(".label-weixin").width()-1)+"px","display":"block"}).stop().animate({height:"235px"},300);
		});
		$("#btn-weixin").mouseout(function(){
			//$("#btn-gd .fa").removeClass("xuanzhuan");
			$(".label-weixin").stop().animate({height:"0px"},300,function(){
				$(".label-weixin").css({"display":"none"});
			});
		});
		$(".label-weixin").mouseover(function(){
			//$("#btn-gd .fa").addClass("xuanzhuan");
			$(".label-weixin").css({"right":($(".t-r-menu").width()-$(".label-weixin").width()-1)+"px","display":"block"}).stop().animate({height:"235px"},300);
		});
		$(".label-weixin").mouseout(function(){
			//$("#btn-gd .fa").removeClass("xuanzhuan");
			$(".label-weixin").stop().animate({height:"0px"},300,function(){
				$(".label-weixin").css({"display":"none"});
			});
		});
		//-------皮肤
		$("#btn-skin").mouseover(function(){
			//$("#btn-gd .fa").addClass("xuanzhuan");
			$(".label-skin").css({"right":($(".t-r-menu").width()-$(".label-skin").width()-1)+"px","display":"block"}).stop().animate({height:"188px"},300);
		});
		$("#btn-skin").mouseout(function(){
			//$("#btn-gd .fa").removeClass("xuanzhuan");
			$(".label-skin").stop().animate({height:"0px"},300,function(){
				$(".label-skin").css({"display":"none"});
			});
		});
		$(".label-skin").mouseover(function(){
			//$("#btn-gd .fa").addClass("xuanzhuan");
			$(".label-skin").css({"right":($(".t-r-menu").width()-$(".label-skin").width()-1)+"px","display":"block"}).stop().animate({height:"188px"},300);
		});
		$(".label-skin").mouseout(function(){
			//$("#btn-gd .fa").removeClass("xuanzhuan");
			$(".label-skin").stop().animate({height:"0px"},300,function(){
				$(".label-skin").css({"display":"none"});
			});
		});
		$(".skin-val a").each(function(index, element) {
            $(this).click(function(){
				setSkinColor($(this).attr("val"));
			});
        });
		//-----------工单下拉菜单
		$("#btn-gd").mouseover(function(){
			//$("#btn-gd .fa").addClass("xuanzhuan");
			$(".label-gd").css({"right":($("#btn-username").width()+51-($(".label-gd").width()-$("#btn-gd").width()-51)/2-1)+"px","display":"block"}).stop().animate({height:"93px"},300);
		});
		$("#btn-gd").mouseout(function(){
			//$("#btn-gd .fa").removeClass("xuanzhuan");
			$(".label-gd").stop().animate({height:"0px"},300,function(){
				$(".label-gd").css({"display":"none"});
			});
		});
		$(".label-gd").mouseover(function(){
			//$("#btn-gd .fa").addClass("xuanzhuan");
			$(".label-gd").css({"right":($("#btn-username").width()+51-($(".label-gd").width()-$("#btn-gd").width()-51)/2-1)+"px","display":"block"}).stop().animate({height:"93px"},300);
		});
		$(".label-gd").mouseout(function(){
			//$("#btn-gd .fa").removeClass("xuanzhuan");
			$(".label-gd").stop().animate({height:"0px"},300,function(){
				$(".label-gd").css({"display":"none"});
			});
		});
		//-----------用户下拉菜单
		$("#btn-username").mouseover(function(){
			//$("#btn-username .fa").addClass("xuanzhuan");
			$(".label-user").css({"display":"block"}).stop().animate({height:"297px"},300);
		});
		$("#btn-username").mouseout(function(){
			//$("#btn-username .fa").removeClass("xuanzhuan");
			$(".label-user").stop().animate({height:"0px"},300,function(){
				$(".label-user").css({"display":"none"});
			});
		});
		$(".label-user").mouseover(function(){
			//$("#btn-username .fa").addClass("xuanzhuan");
			$(".label-user").css({"display":"block"}).stop().animate({height:"297px"},300);
		});
		$(".label-user").mouseout(function(){
			//$("#btn-username .fa").removeClass("xuanzhuan");
			$(".label-user").stop().animate({height:"0px"},300,function(){
				$(".label-user").css({"display":"none"});
			});
		});
		
		$(".menu-load").bind("click", function(e){
			menuMobileEnd();
		});
		//---------------------------------------
		$("#mini-menu").bind("click", function(e){
			var type=$(this).attr("type");
			var menuWidth=0;
			if(window.openMenu){
				menuWidth=window.menuRight;
			}
			if(type=="1"){
				menuStart(menuWidth);
			}else{
				menuEnd(menuWidth);
			}
		});
		//----------------
		$(".left-menu .menu-items").each(function(index, element) {
			var father=$(this);
			father.bind("click", function(e){
				/*if(father.is(".menu-on")){
					if(window.isMobile&&!window.openMenu){
						menuMobileEnd();
					}
					return;
				}*/
				$(".left-menu .menu-items").removeClass("menu-on");
				$(".left-menu .menu-items").addClass("pointer3");
				father.removeClass("pointer3");
				father.addClass("menu-on");
				if(typeof(father.attr("forType"))!="undefined"){
					if(!window.openMenu){
						window.openMenu=true;
						showTwoMenu(father.attr("forType"));
						doMenuLink(father.attr("forType"));
					}else{
						window.openMenu=true;
						showMenu(father.attr("forType"));
						doMenuLink(father.attr("forType"));
					}
				}else{
					if(window.openMenu){
						window.openMenu=false;
						hideTwoMenu();
					}
					if(typeof(father.attr("forUrl"))!="undefined"){
						loadIFrame(father.attr("forUrl"));
					}
				}
			});
		});
		//---------------------
		$(".list-items-link").bind("click", function(e){
			if(typeof($(this).attr("url"))!="undefined"){
				if(loadIFrame($(this).attr("url"))){
					$(".list-items").removeClass("menu-cheack");
					$(this).parent(".list-items").addClass("menu-cheack");
				}
				return false;
			}else{
				return true;	
			}
		});
		//---------------------
		$(".clickItems").each(function(index, element) {
            var father=$(this);
			father.click(function(){
				loadTipsStart();
				var model=father.attr("model");
				var action=father.attr("action");
				var ctrl=father.attr("ctrl");
				$(".menu-items").removeClass("menu-on");
				$(".menu-items").addClass("pointer3");
				$("#"+model).removeClass("pointer3");
				$("#"+model).addClass("menu-on");
				if(action!=""&&action!=null){
					window.openMenu=true;
					showTwoMenu(action);
					$(".list-items").removeClass("menu-cheack");
					$("#"+ctrl).parent(".list-items").addClass("menu-cheack");
				}
				$("#frame").attr("src",father.attr("url"));
				return true;	
			});
        });
	}
}
function setLesten2(){
	if(window.isMobile&&window.supportTouch){
		$("#domainlist .list-items-link").bind("touchend", function(e){
			var maxH=$(".onItems h1").size()*36+$(".onItems li").size()*45;
			var doThis=true;
			if($("#left-menu-two").height()<maxH){
				var e = e || window.event;
				var x=e.originalEvent.changedTouches[0].pageX;;
				var cha=x-window.touchXTwo;
				if(cha!=0){
					$("#left-menu-two").scrollTop=$("#left-menu-two").scrollTop-(cha);
					window.touchXTwo=x;
					doThis=false;
				}
			}
			if(doThis){
				 if(typeof($(this).attr("url"))!="undefined"){
					if(loadIFrame($(this).attr("url"))){
						$(".list-items").removeClass("menu-cheack");
						$(this).parent(".list-items").addClass("menu-cheack");
					}
					return false;
				}else{
					return true;	
				}	
			}
		});
		$("#domainlist .list-items-link").bind("touchstart", function(e){
			var e = e || window.event;
			var maxH=$(".onItems h1").size()*36+$(".onItems li").size()*45;
			if($("#left-menu-two").height()<maxH){
				window.touchXTwo=e.originalEvent.targetTouches[0].pageX;
			}
		});	
	}else{
		$("#domainlist .list-items-link").bind("click", function(e){
			if(typeof($(this).attr("url"))!="undefined"){
				if(loadIFrame($(this).attr("url"))){
					$(".list-items").removeClass("menu-cheack");
					$(this).parent(".list-items").addClass("menu-cheack");
				}
				return false;
			}else{
				return true;	
			}
		});
	}
}
function setLesten3(){
	if(window.isMobile&&window.supportTouch){
		$("#vhostlist .list-items-link").bind("touchend", function(e){
			var maxH=$(".onItems h1").size()*36+$(".onItems li").size()*45;
			var doThis=true;
			if($("#left-menu-two").height()<maxH){
				var e = e || window.event;
				var x=e.originalEvent.changedTouches[0].pageX;;
				var cha=x-window.touchXTwo;
				if(cha!=0){
					$("#left-menu-two").scrollTop=$("#left-menu-two").scrollTop-(cha);
					window.touchXTwo=x;
					doThis=false;
				}
			}
			if(doThis){
				 if(typeof($(this).attr("url"))!="undefined"){
					if(loadIFrame($(this).attr("url"))){
						$(".list-items").removeClass("menu-cheack");
						$(this).parent(".list-items").addClass("menu-cheack");
					}
					return false;
				}else{
					return true;	
				}	
			}
		});
		$("#vhostlist .list-items-link").bind("touchstart", function(e){
			var e = e || window.event;
			var maxH=$(".onItems h1").size()*36+$(".onItems li").size()*45;
			if($("#left-menu-two").height()<maxH){
				window.touchXTwo=e.originalEvent.targetTouches[0].pageX;
			}
		});	
	}else{
		$("#vhostlist .list-items-link").bind("click", function(e){
			if(typeof($(this).attr("url"))!="undefined"){
				if(loadIFrame($(this).attr("url"))){
					$(".list-items").removeClass("menu-cheack");
					$(this).parent(".list-items").addClass("menu-cheack");
				}
				return false;
			}else{
				return true;	
			}
		});
	}
}
function setLesten4(){
	if(window.isMobile&&window.supportTouch){
		$("#youjulist .list-items-link").bind("touchend", function(e){
			var maxH=$(".onItems h1").size()*36+$(".onItems li").size()*45;
			var doThis=true;
			if($("#left-menu-two").height()<maxH){
				var e = e || window.event;
				var x=e.originalEvent.changedTouches[0].pageX;;
				var cha=x-window.touchXTwo;
				if(cha!=0){
					$("#left-menu-two").scrollTop=$("#left-menu-two").scrollTop-(cha);
					window.touchXTwo=x;
					doThis=false;
				}
			}
			if(doThis){
				 if(typeof($(this).attr("url"))!="undefined"){
					if(loadIFrame($(this).attr("url"))){
						$(".list-items").removeClass("menu-cheack");
						$(this).parent(".list-items").addClass("menu-cheack");
					}
					return false;
				}else{
					return true;	
				}	
			}
		});
		$("#youjulist .list-items-link").bind("touchstart", function(e){
			var e = e || window.event;
			var maxH=$(".onItems h1").size()*36+$(".onItems li").size()*45;
			if($("#left-menu-two").height()<maxH){
				window.touchXTwo=e.originalEvent.targetTouches[0].pageX;
			}
		});	
	}else{
		$("#youjulist .list-items-link").bind("click", function(e){
			if(typeof($(this).attr("url"))!="undefined"){
				if(loadIFrame($(this).attr("url"))){
					$(".list-items").removeClass("menu-cheack");
					$(this).parent(".list-items").addClass("menu-cheack");
				}
				return false;
			}else{
				return true;	
			}
		});
	}
}
function setLestenFolder(prefix){
	if(window.isMobile&&window.supportTouch){
		$("#folder"+prefix+" .list-items-link").bind("touchend", function(e){
			var maxH=$(".onItems h1").size()*36+$(".onItems li").size()*45;
			var doThis=true;
			if($("#left-menu-two").height()<maxH){
				var e = e || window.event;
				var x=e.originalEvent.changedTouches[0].pageX;;
				var cha=x-window.touchXTwo;
				if(cha!=0){
					$("#left-menu-two").scrollTop=$("#left-menu-two").scrollTop-(cha);
					window.touchXTwo=x;
					doThis=false;
				}
			}
			if(doThis){
				 if(typeof($(this).attr("url"))!="undefined"){
					if(loadIFrame($(this).attr("url"))){
						$(".list-items").removeClass("menu-cheack");
						$(this).parent(".list-items").addClass("menu-cheack");
					}
					return false;
				}else{
					return true;	
				}	
			}
		});
		$("#folder"+prefix+" .list-items-link").bind("touchstart", function(e){
			var e = e || window.event;
			var maxH=$(".onItems h1").size()*36+$(".onItems li").size()*45;
			if($("#left-menu-two").height()<maxH){
				window.touchXTwo=e.originalEvent.targetTouches[0].pageX;
			}
		});	
	}else{
		$("#folder"+prefix+" .list-items-link").bind("click", function(e){
			if(typeof($(this).attr("url"))!="undefined"){
				if(loadIFrame($(this).attr("url"))){
					$(".list-items").removeClass("menu-cheack");
					$(this).parent(".list-items").addClass("menu-cheack");
				}
				return false;
			}else{
				return true;	
			}
		});
	}
}