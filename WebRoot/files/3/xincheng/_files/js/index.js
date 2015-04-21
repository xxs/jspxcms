
//banner
window.addEvent('load', function(){
	var banners = $('main_banners').getElements('ul.banners > li');
	var first = null, current = null;
	
	banners.each(function(el){
		if(!el.inNext){
			el.inNext = [function(current,next){
				current.fade('show');
				next.fade('hide');
				current.fade('out');
				next.fade('in');
			}, 500];
		}
		if(!el.inPrev){
			el.inPrev = [function(current,prev){
				current.fade('show');
				prev.fade('hide');
				current.fade('out');
				prev.fade('in');
			}, 500];
		}		
		if(!el.duration){
			el.duration = 5000;
		}
		
		if(!first){
			first = el;
			current = el;
		}

		current.store('next', el);
		el.store('prev', current);
		current = el;
	});
	current.store('next', first);
	first.store('prev', current);
	current = first;
	
	
	var myTimer = null;
	
	var s = function(delay){
		var d = current.duration;
		myTimer = nextBanner.delay(d + delay);
	}
	
	var nextBanner = function(){
		if(myTimer){
			clearTimeout(myTimer);			
		}
		var next = current.retrieve('next');
		var switchInfo = current.inNext;
		switchInfo[0].call(this, current, next);
		current = next;
		s(switchInfo[1]);
	};
	
	var prevBanner = function(){
		if(myTimer){
			clearTimeout(myTimer);			
		}
		var prev = current.retrieve('prev');
		var switchInfo = current.inPrev;
		switchInfo[0].call(this, current, prev);
		current = prev;
		s(switchInfo[1]);
	};
	
	$('banner_prev').addEvent('click', prevBanner).setStyle('display', 'block');
	$('banner_next').addEvent('click', nextBanner).setStyle('display', 'block');
	
	//启动
	s(0);
});



window.addEvent('resize', function() {
	window.size = window.getSize();
});
window.fireEvent('resize');



window.domreadied = false;
window.scroll = {
	'down': false,
	'up': false,
	'prevPos': window.getScroll().y,
	'pos': null,
	'downEvents': [],
	'upEvents': [],
	'Events': []
};
window.addEvent('scroll', function() {
	if(!window.domreadied){
		return;
	}
	var scroll = window.scroll;
	scroll.pos = window.getScroll().y;
	scroll.down = false;
	scroll.up = false;
	
	if(scroll.prevPos < scroll.pos){
		scroll.down = true;
	}
	else if(scroll.prevPos > scroll.pos){
		scroll.up = true;
	}
	
	scroll.prevPos = scroll.pos;
	
	var top = scroll.pos;
	var bottom = top + window.size.y;
	
	
	scroll.Events.each(function(f){
		f.call(this, top, bottom);
	});

	if(scroll.down){
		scroll.downEvents.each(function(f){
			f.call(this, top, bottom);
		});
	}else if(scroll.up){
		scroll.upEvents.each(function(f){
			f.call(this, top, bottom);
		});
	}		
	

	
});//scroll


Fx.Count = new Class({
	Extends: Fx,

	initialize: function(element, options){
		this.element = this.subject = document.id(element);
		this.setOptions({transition: Fx.Transitions.linear, duration: 1000});
		this.parent(options);
	},

	set: function(now){
		this.element.set('text', Math.round(now));
		return this;
	}
	
});
//数字
var box = $('c_strong');
var coord = box.getCoordinates();
var f = function(){
	var top = window.getScroll().y;
	var bottom = top + window.size.y;

	if((coord.bottom > top ) && (coord.top < bottom ) ){
		var list = $$('#c_strong strong');
		list.each(function(el){
			var count = el.get('text').toInt();
			var fx = new Fx.Count(el);
			fx.start(0, count);
		});
		window.removeEvent('scroll', f);
	}
};
window.addEvent('scroll', f);


window.addEvent('domready', function() {
	window.smoothScroll = new Fx.Scroll(window, {
		duration: 'short',
		wheelStops: false
	});
	

	
	//侧导航
	(function(){
		$('section_nav').getElements('a').each(function(el){
			var li = el.getParent();
			var sect = $(li.get('class'));
			el.addEvent('click', function(e){
				e.stop();
				window.smoothScroll.toElement(sect);
			});
			var coord = sect.getCoordinates();
			window.scroll.Events.push(function(top, bottom){
				if( (coord.top < (top+bottom)/2 ) && ((top+bottom)/2 < coord.bottom ) ){
					li.addClass('current');
				}
				else{
					li.removeClass('current');
				}
			});
		});
	})();
	
	//视差滚动
	(function(){
		var sections = document.getElements('div.body > section');
		sections.each(function(sect){
			var header = sect.getElement('header'); 
			var coord = header.getCoordinates();
			window.scroll.Events.push(function(top, bottom){
				if( (coord.bottom > top ) && (coord.top < bottom ) ){
					//视线内
					//var y = window.size.y / (bottom+coord.height) * (coord.top - bottom);
					if(window.size.x > coord.height){
						var y = -(window.size.x - coord.height) * (top - (coord.top-window.size.y)) / (coord.height + window.size.y) ; 
						header.setStyle('background-position', '50% ' + y + 'px');
					}
					else{
						var x = -(coord.height - window.size.x) * (top - (coord.top-window.size.y)) / (coord.height + window.size.y) ; 
						header.setStyle('background-position', '' + x + 'px 50%');
					}
				}
			});
		});
	})();	
	
	
	//BACK2TOP
	$('back_to_top').getElement('a').addEvent('click', function(e){
		e.stop();
		window.smoothScroll.toElement('main_banners');
	});
	
	//done
	window.domreadied = true;
	window.fireEvent('scroll');
});//domready
