// JavaScript Document
$(document).ready(function(){
	if(window.isMobile){
		window.onbeforeunload = function(e){
			window.parent.loadTipsStart();
		}
	}else{
		$("html").niceScroll({zindex:999999,cursoropacitymax:0.7});
		window.onbeforeunload = function(e){
			window.parent.loadTipsStart();
		}
		$(".clickItems").each(function(index, element) {
            var father=$(this);
			father.click(function(){
				var model=father.attr("model");
				var action=father.attr("action");
				var ctrl=father.attr("ctrl");
				window.parent.ifrClick(model,action,ctrl);
				return true;	
			});
        });
	}
});

function setCookie(cookiename, cookievalue, hours) {
	var date = new Date();
	date.setTime(date.getTime() + Number(hours) * 3600 * 1000);
	document.cookie = cookiename + "=" + cookievalue + "; path=/;expires = " + date.toGMTString();
}
function getCookie(cookiename) {
	var result;
	var mycookie = document.cookie;
	var start2 = mycookie.indexOf(cookiename + "=");
	if (start2 > -1) {
		start = mycookie.indexOf("=", start2) + 1;
		var end = mycookie.indexOf(";", start);
		if (end == -1) {
			end = mycookie.length;
		}
		result = unescape(mycookie.substring(start, end));
	}
	return result;
}