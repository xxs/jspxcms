/********** banner animate **********/
function BannerAnimate(options){
	this.options = options || {};
	this.bannerImg = this.options.imglist;
	this.bannerpoint =  this.options.pointlist;
	this.lilist = this.options.lilist;
	this.curpoint = this.options.curpoint;
	this.flag = true;
	this.n = 0;
	this.t = null;
	this.init();
}
BannerAnimate.prototype= {
	init : function(){
		this.tabChangeFun();
		this.bannerClickFun();
		this.slideClickFun();
	},
	tabChangeFun:function(){
		var that = this;
		var $li = that.bannerImg.find("."+that.lilist),
			$li_first = that.bannerImg.find('li:first'),
			liWid = parseInt($li.width()),
			liLen = $li.length;

		if(liLen > 1){
			that.t = setInterval(function(){
				that.n++;
				if(that.n == liLen){
					$li_first.css({position:'relative',left:that.n*liWid});
					that.flag=false;
				}
				that.bannerPointFun();
				that.bannerAnimateFun();
			},5000);
		}
	},
	bannerAnimateFun:function(){
		var that = this;
		var $li = that.bannerImg.find("."+that.lilist),
			$li_first = that.bannerImg.find('li:first'),
			liWid = -parseInt($li.width()),
			liLen = $li.length;
		var Sum = parseInt(liWid * that.n);
		that.bannerImg.stop().animate({"left":Sum},500,function(){
			if(!that.flag){
				that.bannerImg.css('left',0);
				$li_first.css({position:'static',left:0});
				that.n = 0;
				that.flag = true;
			}
		});
	},
	bannerPointFun:function(){
		var that = this;
		var index = that.n;
		var idxTotal = that.bannerpoint.find("a").length;
		if(index == idxTotal){index = 0;}
		that.bannerpoint.find("a").removeClass(that.curpoint);
		that.bannerpoint.find("a:eq("+index+")").addClass(that.curpoint);
	},
	bannerClickFun:function(){
		var that = this;
		var $sec_a = that.bannerpoint.find("a");
		$sec_a.unbind("click");
		$sec_a.bind("click",function(){
			var _this = $(this);
			that.n = _this.index();

			clearInterval(that.t);
			that.bannerPointFun();
			that.bannerAnimateFun();
			that.tabChangeFun();
		});
	},
	slideClickFun:function(){
		var that = this;
		$("#leftSlide").bind("click",function(){
			var curIndex = that.bannerpoint.find("."+that.curpoint).index();
			var liLen = that.bannerImg.find("li").length;

			that.n = parseInt(curIndex) - 1;
			if(that.n < 0){
				that.n = parseInt(liLen) - 1;
			}

			clearInterval(that.t);
			that.bannerPointFun();
			that.bannerAnimateFun();
			that.tabChangeFun();
		});

		$("#rightSlide").bind("click",function(){
			var curIndex = that.bannerpoint.find("."+that.curpoint).index();
			var liLen = that.bannerImg.find("li").length;

			that.n = parseInt(curIndex) + 1;
			if(that.n == liLen){
				that.n = 0;
			}

			clearInterval(that.t);
			that.bannerPointFun();
			that.bannerAnimateFun();
			that.tabChangeFun();
		});
	}
}