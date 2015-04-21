window.addEvent('domready', function(){
	/*
	 * 开发模式需要自定义这一部分。
	 * 每一个banner对应的<li>都可以定义对应的switch信息，切换时将会使用。
	 * 格式为 banner_el.switchInfo = [func, duration]，
	 * func为实际执行的切换动作。参数为(current_li, next_li)；
	 * duration参数用来定义需要预留多少时间给func执行。这个时间和func实际需要的时间可能是不一样的。
	 * 例如:
	 * $('banner_big').switchInfo = [function(){ this.fade('out'); }, 500];
	 * 
	 * 每个<li>额外可以定义一个duration，为实际展示的时间，默认为5000
	 * $('banner_big').duration = 5000;
	 */	
	
	var fx = {
		property: 'left',
		transition: Fx.Transitions.linear
	};
	
	var banners = $('main_banners').getElements('.banners > li');
	
	banners.each(function(el){
		el.inNext = [function(current, next){
			var header = next.getElement('header');
			var content = next.getElement('section');
			
			header.setStyle('left', '100%');
			content.setStyle('left', '100%');
			
			banners.each(function(el){
				if( (el != current) && (el != next) ){
					el.setStyle('z-index', 1999);
				}
			});
			current.setStyle('z-index', 2000);
			next.setStyle('z-index', 2001);
			content.setStyle('margin-left', 0);
			
			(new Fx.Tween(header,fx)).start(window.size.x, 0);
			var t = new Fx.Tween(content, fx);
			var width = content.getSize().x;
			t.start.delay(500, t, [window.size.x, (window.size.x - width)/2]);
		}, 1000];
		
		el.inPrev = [function(current, prev){
			var header = prev.getElement('header');
			var content = prev.getElement('section');
			
			header.setStyle('left', '-100%');
			content.setStyle('left', '-100%');
			
			banners.each(function(el){
				if( (el != current) && (el != prev) ){
					el.setStyle('z-index', 1999);
				}
			});
			current.setStyle('z-index', 2000);
			prev.setStyle('z-index', 2001);
			content.setStyle('margin-left', 0);
			
			(new Fx.Tween(header,fx)).start(-window.size.x, 0);
			var t = new Fx.Tween(content, fx);
			var width = content.getSize().x;
			t.start.delay(500, t, [-width, (window.size.x - width)/2]);
		}, 1000];
	});
	
	var b = $('banner_brand');
	b.duration = 8000;
//	b.getElement('a').addEvent('click', function(e){
//		e.stop();
//		window.smoothScroll.toElement('scheme');
//	});

});

