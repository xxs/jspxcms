//banner
window.addEvent('domready', function(){
	var el = $('wx_qr');
	$('weixin_link').addEvent('mouseenter', function(){
		el.setStyle('visibility', 'visible');
	});
	$('weixin_link').addEvent('mouseout', function(){
		el.setStyle('visibility', 'hidden');
	});
});

