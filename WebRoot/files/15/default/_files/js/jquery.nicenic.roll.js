/*
jquery.nicenic.roll.js
jquery滚动插件
2014.9.10  by minghe
耐思尼克 © 版权所有 Copyright © 2000-2014 NiceNIC.net,Inc. All rights reserved 
*/

(function($){
	

	var els = [];

	
	$.fn.roll = function(opt){
		//默认参数
		var defaultOpt = {
			top : 0,
			float : true
		};
		opt = $.extend(defaultOpt, opt);
		opt.elTop = $(this).offset().top;

		roll.init({el:this, opt:opt});

	}

	var roll = {};

	roll.init = function(data){
		//console.log(data);
		var el = data.el;
		if(data.opt.float){
			$(window).scroll(function(){
				var ctop = $(this).scrollTop();
		
				var opt = data.opt,
					$el = $(data.el);
				
				if(ctop >= opt.elTop - opt.top){
					if(!$el.data('roll')){
						$el.data('roll', true).css({
							boxShadow: "0px 2px 3px #ddd",
							position: "fixed",
							top: opt.top,
							zIndex: data.opt.zIndex ? data.opt.zIndex : 999
						});
								$(".i").css("display","none");
                            $('.enarr_current').hide();
					}
				}else{
					if($el.data('roll')){
						$el.data('roll', false).css({
							boxShadow: "",
							position: 'inherit'
						});
					}
						$(".i").css("display","");
                     $('.enarr_current').show();
				}
				
			});
		}
		$('a[roll-go]', el).click(function(){
			var go = $(this).attr('roll-go');
			var obj = $(go);
			
			var marginTop = parseInt(obj.css('margin-top')), paddingTop = parseInt(obj.css('padding-top'));
			if(isNaN(marginTop)) marginTop = 0;
			if(isNaN(paddingTop)) paddingTop = 0;


			var top = parseInt(obj.offset().top) - marginTop - paddingTop;
	
			if(data.opt.float){
				if($(el).data('roll')){
					top -= $(el).height();
				}else{
					top -= $(el).height()*2;
				}
			}

			top -= data.opt.top;

			roll.goto(top);
			
			$('a[roll-go].hot', el).removeClass('hot');
			$(this).addClass('hot');
		});
	}

	roll.goto = function(top){
		$("html,body").animate({ scrollTop: top + 'px'})
	//	$(document).scrollTop(top);
	}

})(jQuery)