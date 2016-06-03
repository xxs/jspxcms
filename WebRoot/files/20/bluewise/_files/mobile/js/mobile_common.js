$(function(){
	var div = $('<div id="mobile_mini_tip" style="position: fixed;"></div>');
	$('body').append(div);
	document.addEventListener("deviceready", onDeviceReady, false);
	
	var mainWidth = $('#main').width();
	$('img').each(function(){
		imgautowidth(this);
	}).live('load', function(event) {
		imgautowidth(this);
	});;
	function imgautowidth(el){ 
		var $el = $(el), width = $el.width();
		//console.log(width, mainWidth);
		if(width > mainWidth){
			$el.width('100%');
			if($el.width() > mainWidth){
				$el.width(mainWidth);
			}
		}
	}
	/*setTimeout(function(){
		if(!window.isedit){
			data = ['#mainmenu', '#top','#container','#banner','#bottom'];
			$.each(data, function(index, val) {
				if($(val).length<=0) return ;
				var maxTop = 0;
				if($(val+' #MoblieControl').length > 0){
					return ;
				}
				$(val+' .block').each(function(index, el) {
					var currTop = $(this).offset().top + $(this).height();
					if(currTop > maxTop){
						maxTop = currTop;
					}
				});
				var oldHeight = $(val).height();
				var height = maxTop-$(val).offset().top;
				$(val).animate({height:height+'px'});

				var data = {};
				data.height = height;
				data.div = val;

			});
		}
	}, 500);*/
});
function showTip(msg, timeout){
	var top = 10,left;
	$('#mobile_mini_tip').html(msg).fadeIn();
	left = $(window).width() / 2 - $('#mobile_mini_tip').width() / 2;
	$('#mobile_mini_tip').css({top:top, left:left});
	setTimeout(function(){
		$('#mobile_mini_tip').fadeOut()
	}, timeout?timeout:3000);
}
function onDeviceReady() {
    document.addEventListener("backbutton", onBackKeyDown, false);
}

function onBackKeyDown() {
	if(confirm('Exit?'))
		navigator.app.exitApp();
}
var isapp =Request('isapp');
if(isapp){
	SetCookie('isapp', 1);
}
if(isapp || getCookie('isapp'))
	document.write('<script type="text/javascript" charset="utf-8" src="phonegap.js"></script>');

if($('meta[name="viewport"]').length<=0){
	$('title').before('<meta name="viewport" content="maximum-scale=5.0,minimum-scale=1.0,user-scalable=yes">');
}