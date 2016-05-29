//弹出层 by Amber 2013-07-12
jQuery.fn.center = function(loaded) {
	var obj = this;
	body_width = parseInt($(window).width());
	body_height = parseInt($(window).height());
	block_width = parseInt(obj.width());
	block_height = parseInt(obj.height());
    //alert(block_height);
	//if(block_height<=60){
	///	 block_height=380;
			 
	// }//定位在上部
	console.log(block_height);
	left_position = parseInt((body_width/2) - (block_width/2)  + $(window).scrollLeft());
	if (body_width<block_width) { left_position = 0 + $(window).scrollLeft(); };
	
	top_position = $(window).scrollTop()+150;//parseInt((body_height/2)-(block_width/2)+ $(window).scrollTop());
	if (body_height<block_height) { top_position = 0 + $(window).scrollTop(); };
	
	if(!loaded) {
		
		obj.css({'position': 'absolute'});
		obj.css({ 'top': top_position, 'left': left_position });
		$(window).bind('resize', function() { 
			obj.center(!loaded);
		});
		//$(window).bind('scroll', function() { 
			//obj.center(!loaded);
		//});
		
	} else {
		obj.stop();
		obj.css({'position': 'absolute'});
		obj.animate({ 'top': top_position }, 130, 'linear');
	}
}

function closepopbox(y){
	
	if(y||$('.mainlist #showMes').html()!=null){
		$('.popbox').fadeOut(function(){ $('#screen').hide(); $('.popbox .mainlist').html("");});
		if(window.location.pathname=="/cart/shoppingcart.php"){//说明是在推荐产品加的，刷新下列表 2013-08-12 by Amber
			getlist(1,0);//刷新列表
			shoppingcartCount();//刷新头部数量
		}
	}
	
	return false;
}

function showpopbox(){
 

	var h = $(document).height();
	var w = $(document).width();
	$('#screen').css({ 'height': h+50 });	
	$('#screen').css({ 'width': w });	
	$('#screen').show();
	$('.popbox').fadeIn();
	$('.popbox').center();
	return false;
}