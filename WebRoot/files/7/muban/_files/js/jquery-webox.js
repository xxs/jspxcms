/**
 *
 *	Plugin: Jquery.webox
 *	Developer: Blank
 *	Version: 1.0 Beta
 *	Update: 2012.07.08
 *
**/
jQuery.extend({
	webox:function(option){
		var _x,_y,m,allscreen=false;
		if(!option){
			alert('options can\'t be empty');
			return;
		};
		if(!option['html']&&!option['iframe']){
			alert('html attribute and iframe attribute can\'t be both empty');
			return;
		};
		option['parent']='webox';
		option['locked']='locked';
		jQuery(document).ready(function(e){
			jQuery('.webox').remove();
			jQuery('.background').remove();
			var width=option['width']?option['width']:400;
			var height=option['height']?option['height']:240;
			jQuery('body').append('<div class="background" style="display:none;"></div><div class="webox" style="width:'+width+'px;height:'+height+'px;display:none;"><div id="inside" style="height:'+height+'px;"><h1 id="locked" onselectstart="return false;">'+(option['title']?option['title']:'webox')+'<a class="span" href="javascript:void(0);"></a></h1>'+(option['iframe']?'<iframe class="w_iframe" src="'+option['iframe']+'" frameborder="0" width="100%" scrolling="yes" style="border:none;overflow-x:hidden;height:'+parseInt(height-30)+'px;"></iframe>':option['html']?option['html']:'')+'</div></div>');
			if(navigator.userAgent.indexOf('MSIE 7')>0||navigator.userAgent.indexOf('MSIE 8')>0){
				jQuery('.webox').css({'filter':'progid:DXImageTransform.Microsoft.gradient(startColorstr=#55000000,endColorstr=#55000000)'});
			}if(option['bgvisibel']){
				jQuery('.background').fadeTo('slow',0.3);
			};
			jQuery('.webox').css({display:'block'});
			jQuery('#'+option['locked']+' .span').click(function(){
				jQuery('.webox').css({display:'none'});
				jQuery('.background').css({display:'none'});
			});
			var marginLeft=parseInt(width/2);
			var marginTop=parseInt(height/2);
			var winWidth=parseInt(jQuery(window).width()/2);
			var winHeight=parseInt(jQuery(window).height()/2.2);
			var left=winWidth-marginLeft;
			var top=winHeight-marginTop;
			jQuery('.webox').css({left:left,top:top});
			jQuery('#'+option['locked']).mousedown(function(e){
				if(e.which){
					m=true;
					_x=e.pageX-parseInt(jQuery('.webox').css('left'));
					_y=e.pageY-parseInt(jQuery('.webox').css('top'));
				}
				}).dblclick(function(){
					if(allscreen){
						jQuery('.webox').css({height:height,width:width});
						jQuery('#inside').height(height);
						jQuery('.w_iframe').height(height-30);
						jQuery('.webox').css({left:left,top:top});
						allscreen = false;
					}else{
						allscreen=true;
						var screenHeight = jQuery(window).height();
						var screenWidth = jQuery(window).width();jQuery
						('.webox').css({'width':screenWidth-18,'height':screenHeight-18,'top':'0px','left':'0px'});
						jQuery('#inside').height(screenHeight-20);
						jQuery('.w_iframe').height(screenHeight-50);
					}
				});
			}).mousemove(function(e){
				if(m && !allscreen){
					var x=e.pageX-_x;
					var y=e.pageY-_y;
					jQuery('.webox').css({left:x});
					jQuery('.webox').css({top:y});
				}
			}).mouseup(function(){
				m=false;
			});
			jQuery(window).resize(function(){
				if(allscreen){
					var screenHeight = jQuery(window).height();
					var screenWidth = jQuery(window).width();
					jQuery('.webox').css({'width':screenWidth-18,'height':screenHeight-18,'top':'0px','left':'0px'});
					jQuery('#inside').height(screenHeight-20);
					jQuery('.w_iframe').height(screenHeight-50);
				}
			});
	}
});