(function($) {
	$.fn.captcha = function(url,options) {
		var settings = {
			title: "load new image",
			preferred: ['top','right'],
			spacing: 1
		};
		$.extend(settings,options);
		var input = this;
		var image = null;
		var timeout = null;
		var imageShow = function() {
			image.show();
			image.positionOf(input,{preferred:settings.preferred,spacing:settings.spacing});			
		};
		input.focus(function() {
			if(image==null) {
				image = $("<img>",{
					"src" : url + (url.indexOf("?")==-1?"?d="+new Date()*1:"&d="+new Date*1),
					"title" : settings.title
				}).css({
					"display":"none",
					"position":"absolute",
					"cursor" : "pointer",
					"border":"1px solid #ccc"
				}).appendTo(document.body).offset({left:-99999,top:-99999}).click(function() {
					clearTimeout(timeout);
					input.focus();
					this.src = url + (url.indexOf("?")==-1?"?d="+new Date()*1:"&d="+new Date*1);
				}).load(function() {
					imageShow();
				}).show();
			} else {
				imageShow();
			}
		}).blur(function() {
			timeout = setTimeout(function(){
				image.hide();
			},200);			
		});
	};
})(jQuery);