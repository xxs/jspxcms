Bw={};
Bw.getParameterValues = function(name) {
	var search = location.search;
	var values = [];
	if(search.length>0) {
		search = search.substring(1);
		var paramArr = search.split("&");
		for(var i in params) {
			var pair = paramArr[i].split("=");
			var key = pair[0];
			var value = pair[1];
			if (key == name) {
				values[values.length] = value;
			}			
		}
	}
	return values;
};
Bw.getParameter = function(key) {
	var search = location.search;
	var values = [];
	if(search.length>0) {
		search = search.substring(1);
		var paramArr = search.split("&");
		for(var i in params) {
			var pair = paramArr[i].split("=");
			var key = pair[0];
			var value = pair[1];
			if (key == name) {
				return value;
			}			
		}
	}
};
Bw.imageDim = function(src, options) {
	if(src == undefined || src=="") {
		//$("#"+imgId).hide();
		return;
	}
	if(!(options && options.cache)) {
		if(src.indexOf("?")==-1) {
			src += "?";
		} else {
			src += "&";
		}
		src += "d="+new Date()*1;		
	}
	var img = $("<img>",{
		"src":src
	}).css({
		"display":"none",
		"position":"absolute"
	}).appendTo(document.body).offset({left:-99999,top:-99999}).show();
	img.load(function(){
		var dim = {"width":img.width(),"height":img.height()};
		if(options !== null && options !== undefined) {
			var maxWidth = options.maxWidth || Number.MAX_VALUE;
			var maxHeight = options.maxHeight || Number.MAX_VALUE;
			var to = options.to;
			var of = options.of;
			var scaleDim;
			if(dim.width>maxWidth||dim.height>maxHeight) {
				var widthScale = maxWidth/dim.width;
				var heightScale = maxHeight/dim.height;
				if(widthScale<heightScale) {
					scaleDim = {"width":maxWidth,"height":dim.height*widthScale};					
				} else {
					scaleDim = {"width":dim.width*heightScale,"height":maxHeight};
				}
			}
			if(to !== null && to !== undefined) {
				to = $(to);
				if(to.is("img")) {
					to.attr("src",src);
					if(scaleDim != undefined) {
						to.css({"width":scaleDim.width,"height":scaleDim.heigth});
					}
				}
			}
			if(scaleDim != undefined) {
				img.css({"width":scaleDim.width,"height":scaleDim.heigth});
			}
		}
	});
	return img;
};
(function($) {
	var position = function(preferred,self,selfWidth,selfHeight,topHeight,bottomHeight,leftWidth,rightWidth,topByTop,topByBottom,leftByLeft,leftByRight) {
		var top,left;
		if (bottomHeight - selfHeight < 0 && topHeight > bottomHeight) {
			top = topByTop;
		} else if(topHeight - selfHeight < 0 && bottomHeight > topHeight) {
			top = topByBottom;
		} else if(preferred[0]!='bottom') {
			top = topByTop;
		} else {
			top = topByBottom;
		}
		if (rightWidth - selfWidth < 0 && leftWidth > rightWidth) {
			left = leftByLeft;
		} else if(leftWidth - selfWidth < 0 && rightWidth > leftWidth) {
			left = leftByRight;
		} else if(preferred[1]!='right') {
			left = leftByLeft;
		} else {
			left = leftByRight;
		}
		
		self.offset({left:left,top:top});
		if($.fn.bgiframe && !self.is("img")) {
			self.bgiframe();
		}
	};
	$.fn.positionSideOf = function(of, options) {
		var settings = {
			preferred : ['bottom','right'],
			spacing : 1
		};
		$.extend(settings, options);

		var ofOffset = of.offset();
		var selfWidth = this.outerWidth();
		var selfHeight = this.outerHeight();
		var topHeight = ofOffset.top - $(document).scrollTop() + of.outerHeight();	
		var bottomHeight = $(document).scrollTop() + $(window).height() - ofOffset.top;
		var leftWidth = ofOffset.left - $(document).scrollLeft();
		var rightWidth = $(document).scrollLeft() + $(window).width() - ofOffset.left - of.outerWidth();
		var topByTop = ofOffset.top - of.outerHeight();
		var topByBottom = ofOffset.top;
		var leftByLeft = ofOffset.left - selfWidth - settings.spacing;
		var leftByRight = ofOffset.left + of.outerWidth() + settings.spacing;
		
		position(settings.preferred,this,selfWidth,selfHeight,topHeight,bottomHeight,leftWidth,rightWidth,topByTop,topByBottom,leftByLeft,leftByRight);
	};
	$.fn.positionOf = function(of, options) {
		var settings = {
			preferred : ['bottom','right'],
			spacing : 1
		};
		$.extend(settings, options);
		
		var ofOffset = of.offset();
		var selfWidth = this.outerWidth();
		var selfHeight = this.outerHeight();
		var leftWidth = ofOffset.left - $(document).scrollLeft() + of.outerWidth();
		var rightWidth = $(document).scrollLeft() + $(window).width() - ofOffset.left;
		var topHeight = ofOffset.top - $(document).scrollTop();	
		var bottomHeight = $(document).scrollTop() + $(window).height() - ofOffset.top - of.outerHeight();
		var topByTop = ofOffset.top - selfHeight - settings.spacing;
		var topByBottom = ofOffset.top + of.outerHeight() + settings.spacing;
		var leftByRight = ofOffset.left;
		var leftByLeft = ofOffset.left + of.outerWidth() - selfWidth;
		position(settings.preferred,this,selfWidth,selfHeight,topHeight,bottomHeight,leftWidth,rightWidth,topByTop,topByBottom,leftByLeft,leftByRight);
	};
})(jQuery);
(function($) {
	$.fn.colorPicker = function() {
		var pubColorText = this;
		var isVisible = false;
		var selectedSpan = $("#__selectedSpan");
		if(selectedSpan.length<=0) {
			selectedSpan = $("<div>",{
				"id" : "__selectedSpan"
			}).html("&nbsp;").appendTo(document.body).css({
				"line-height" : "1px",
				"width" : "10px",
				"height" : "10px",
				"border" : "1px solid #fff",
				"cursor" : "pointer",
				"z-index" : "5",
				"display" : "none",
				"position" : "absolute",
				"left" : "0",
				"top" : "0"
			});
		}
		var hoverSpan = $("#__hoverSpan");
		if(hoverSpan.length<=0) {
			hoverSpan = $("<div>",{
				"id" : "__hoverSpan"
			}).html("&nbsp;").appendTo(document.body).css({
				"line-height" : "1px",
				"width" : "10px",
				"height" : "10px",
				"border" : "1px solid #fff",
				"cursor" : "pointer",
				"z-index" : "6",
				"display" : "none",
				"position" : "absolute",
				"left" : "0",
				"top" : "0"
			});	
		}		
		var pubColorShow = $("<input>", {
			"type" : "text",
			"readonly" : "readonly",
			"tabindex" : "-1"
		}).css({
			"width" : "35px",
			"cursor" : "pointer",
			"border" : "1px solid #ccc",
			"padding" : "2px",
			"background-color" : "#FFFFFF"
		});
		this.after(pubColorShow);
		var panel = $("#__colorPanel");
		if(panel.length<=0) {
			panel = $("<div id='__colorPanel' style='position:absolute;display:none;width:228px;border:1px solid #646464;'><div style='padding:2px;text-align:center;background-color:#d6d6d6;'><div id='__colorShow' style='float:left;width:55px;height:20px;border:1px solid #000'></div><div id='__colorText' style='float:left;width:108px;height:22px;font:12px/22px \"Courier New\";'></div><div id='__colorClear' style='float:right;width:55px;height:20px;border:1px dashed #000;cursor:pointer;font:12px/20px \"Courier New\";'>clear</div><div style='clear:both;'></div></div><div style='line-height:1px;'><div style='float:left;width:30px;background-color:#000;'><div id='__colorQuickSpan' style='padding:0 10px 0 9px;'><span id='colorQuickSpan000000' style='background-color:#000000;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpan333333' style='background-color:#333333;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpan666666' style='background-color:#666666;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpan999999' style='background-color:#999999;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpanCCCCCC' style='background-color:#CCCCCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpanFFFFFF' style='background-color:#FFFFFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpanFF0000' style='background-color:#FF0000;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpan00FF00' style='background-color:#00FF00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpan0000FF' style='background-color:#0000FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpanFFFF00' style='background-color:#FFFF00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpan00FFFF' style='background-color:#00FFFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='colorQuickSpanFF00FF' style='background-color:#FF00FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><div style='clear:both;'></div></div></div><div id='__colorSpan' style='float:left;width:198px;'><span id='__colorSpan000000' style='background-color:#000000;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan003300' style='background-color:#003300;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan006600' style='background-color:#006600;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan009900' style='background-color:#009900;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00CC00' style='background-color:#00CC00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00FF00' style='background-color:#00FF00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan330000' style='background-color:#330000;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan333300' style='background-color:#333300;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan336600' style='background-color:#336600;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan339900' style='background-color:#339900;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33CC00' style='background-color:#33CC00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33FF00' style='background-color:#33FF00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan660000' style='background-color:#660000;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan663300' style='background-color:#663300;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan666600' style='background-color:#666600;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan669900' style='background-color:#669900;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66CC00' style='background-color:#66CC00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66FF00' style='background-color:#66FF00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan000033' style='background-color:#000033;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan003333' style='background-color:#003333;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan006633' style='background-color:#006633;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan009933' style='background-color:#009933;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00CC33' style='background-color:#00CC33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00FF33' style='background-color:#00FF33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan330033' style='background-color:#330033;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan333333' style='background-color:#333333;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan336633' style='background-color:#336633;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan339933' style='background-color:#339933;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33CC33' style='background-color:#33CC33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33FF33' style='background-color:#33FF33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan660033' style='background-color:#660033;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan663333' style='background-color:#663333;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan666633' style='background-color:#666633;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan669933' style='background-color:#669933;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66CC33' style='background-color:#66CC33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66FF33' style='background-color:#66FF33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan000066' style='background-color:#000066;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan003366' style='background-color:#003366;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan006666' style='background-color:#006666;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan009966' style='background-color:#009966;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00CC66' style='background-color:#00CC66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00FF66' style='background-color:#00FF66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan330066' style='background-color:#330066;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan333366' style='background-color:#333366;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan336666' style='background-color:#336666;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan339966' style='background-color:#339966;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33CC66' style='background-color:#33CC66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33FF66' style='background-color:#33FF66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan660066' style='background-color:#660066;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan663366' style='background-color:#663366;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan666666' style='background-color:#666666;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan669966' style='background-color:#669966;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66CC66' style='background-color:#66CC66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66FF66' style='background-color:#66FF66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan000099' style='background-color:#000099;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan003399' style='background-color:#003399;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan006699' style='background-color:#006699;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan009999' style='background-color:#009999;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00CC99' style='background-color:#00CC99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00FF99' style='background-color:#00FF99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan330099' style='background-color:#330099;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan333399' style='background-color:#333399;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan336699' style='background-color:#336699;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan339999' style='background-color:#339999;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33CC99' style='background-color:#33CC99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33FF99' style='background-color:#33FF99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan660099' style='background-color:#660099;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan663399' style='background-color:#663399;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan666699' style='background-color:#666699;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan669999' style='background-color:#669999;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66CC99' style='background-color:#66CC99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66FF99' style='background-color:#66FF99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan0000CC' style='background-color:#0000CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan0033CC' style='background-color:#0033CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan0066CC' style='background-color:#0066CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan0099CC' style='background-color:#0099CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00CCCC' style='background-color:#00CCCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00FFCC' style='background-color:#00FFCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan3300CC' style='background-color:#3300CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan3333CC' style='background-color:#3333CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan3366CC' style='background-color:#3366CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan3399CC' style='background-color:#3399CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33CCCC' style='background-color:#33CCCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33FFCC' style='background-color:#33FFCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan6600CC' style='background-color:#6600CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan6633CC' style='background-color:#6633CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan6666CC' style='background-color:#6666CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan6699CC' style='background-color:#6699CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66CCCC' style='background-color:#66CCCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66FFCC' style='background-color:#66FFCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan0000FF' style='background-color:#0000FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan0033FF' style='background-color:#0033FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan0066FF' style='background-color:#0066FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan0099FF' style='background-color:#0099FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00CCFF' style='background-color:#00CCFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan00FFFF' style='background-color:#00FFFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan3300FF' style='background-color:#3300FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan3333FF' style='background-color:#3333FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan3366FF' style='background-color:#3366FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan3399FF' style='background-color:#3399FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33CCFF' style='background-color:#33CCFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan33FFFF' style='background-color:#33FFFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan6600FF' style='background-color:#6600FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan6633FF' style='background-color:#6633FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan6666FF' style='background-color:#6666FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan6699FF' style='background-color:#6699FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66CCFF' style='background-color:#66CCFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan66FFFF' style='background-color:#66FFFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan990000' style='background-color:#990000;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan993300' style='background-color:#993300;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan996600' style='background-color:#996600;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan999900' style='background-color:#999900;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99CC00' style='background-color:#99CC00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99FF00' style='background-color:#99FF00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC0000' style='background-color:#CC0000;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC3300' style='background-color:#CC3300;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC6600' style='background-color:#CC6600;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC9900' style='background-color:#CC9900;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCCC00' style='background-color:#CCCC00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCFF00' style='background-color:#CCFF00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF0000' style='background-color:#FF0000;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF3300' style='background-color:#FF3300;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF6600' style='background-color:#FF6600;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF9900' style='background-color:#FF9900;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFCC00' style='background-color:#FFCC00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFFF00' style='background-color:#FFFF00;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan990033' style='background-color:#990033;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan993333' style='background-color:#993333;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan996633' style='background-color:#996633;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan999933' style='background-color:#999933;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99CC33' style='background-color:#99CC33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99FF33' style='background-color:#99FF33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC0033' style='background-color:#CC0033;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC3333' style='background-color:#CC3333;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC6633' style='background-color:#CC6633;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC9933' style='background-color:#CC9933;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCCC33' style='background-color:#CCCC33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCFF33' style='background-color:#CCFF33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF0033' style='background-color:#FF0033;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF3333' style='background-color:#FF3333;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF6633' style='background-color:#FF6633;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF9933' style='background-color:#FF9933;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFCC33' style='background-color:#FFCC33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFFF33' style='background-color:#FFFF33;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan990066' style='background-color:#990066;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan993366' style='background-color:#993366;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan996666' style='background-color:#996666;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan999966' style='background-color:#999966;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99CC66' style='background-color:#99CC66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99FF66' style='background-color:#99FF66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC0066' style='background-color:#CC0066;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC3366' style='background-color:#CC3366;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC6666' style='background-color:#CC6666;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC9966' style='background-color:#CC9966;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCCC66' style='background-color:#CCCC66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCFF66' style='background-color:#CCFF66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF0066' style='background-color:#FF0066;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF3366' style='background-color:#FF3366;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF6666' style='background-color:#FF6666;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF9966' style='background-color:#FF9966;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFCC66' style='background-color:#FFCC66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFFF66' style='background-color:#FFFF66;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan990099' style='background-color:#990099;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan993399' style='background-color:#993399;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan996699' style='background-color:#996699;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan999999' style='background-color:#999999;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99CC99' style='background-color:#99CC99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99FF99' style='background-color:#99FF99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC0099' style='background-color:#CC0099;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC3399' style='background-color:#CC3399;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC6699' style='background-color:#CC6699;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC9999' style='background-color:#CC9999;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCCC99' style='background-color:#CCCC99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCFF99' style='background-color:#CCFF99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF0099' style='background-color:#FF0099;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF3399' style='background-color:#FF3399;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF6699' style='background-color:#FF6699;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF9999' style='background-color:#FF9999;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFCC99' style='background-color:#FFCC99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFFF99' style='background-color:#FFFF99;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan9900CC' style='background-color:#9900CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan9933CC' style='background-color:#9933CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan9966CC' style='background-color:#9966CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan9999CC' style='background-color:#9999CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99CCCC' style='background-color:#99CCCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99FFCC' style='background-color:#99FFCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC00CC' style='background-color:#CC00CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC33CC' style='background-color:#CC33CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC66CC' style='background-color:#CC66CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC99CC' style='background-color:#CC99CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCCCCC' style='background-color:#CCCCCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCFFCC' style='background-color:#CCFFCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF00CC' style='background-color:#FF00CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF33CC' style='background-color:#FF33CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF66CC' style='background-color:#FF66CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF99CC' style='background-color:#FF99CC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFCCCC' style='background-color:#FFCCCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFFFCC' style='background-color:#FFFFCC;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan9900FF' style='background-color:#9900FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan9933FF' style='background-color:#9933FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan9966FF' style='background-color:#9966FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan9999FF' style='background-color:#9999FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99CCFF' style='background-color:#99CCFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpan99FFFF' style='background-color:#99FFFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC00FF' style='background-color:#CC00FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC33FF' style='background-color:#CC33FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC66FF' style='background-color:#CC66FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCC99FF' style='background-color:#CC99FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCCCFF' style='background-color:#CCCCFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanCCFFFF' style='background-color:#CCFFFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF00FF' style='background-color:#FF00FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF33FF' style='background-color:#FF33FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF66FF' style='background-color:#FF66FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFF99FF' style='background-color:#FF99FF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFCCFF' style='background-color:#FFCCFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><span id='__colorSpanFFFFFF' style='background-color:#FFFFFF;z-index:10;float:left;width:10px;height:10px;cursor:pointer;border-left:1px solid;border-top:1px solid;'>&nbsp;</span><div style='clear:both;'></div></div><div style='clear:both;'></div></div></div>");
			panel.appendTo(document.body).offset({left:-99999,top:-99999}).show();
			var colorText = $("#__colorText");
			var colorShow = $("#__colorShow");
			var colorClear = $("#__colorClear");
			$("#__colorSpan span, #__colorQuickSpan span").add(selectedSpan).mouseenter(function(){
				if(!isVisible) return;
				var attrBg = $(this).attr("attrBgColor");
				var bg = attrBg || "#"+this.id.substring(this.id.length-6).toUpperCase();
				colorText.html(bg);
				colorShow.css("background-color",bg);
				hoverSpan.css("background-color",bg);
				hoverSpan.show();
				hoverSpan.offset($(this).offset());
			});
			hoverSpan.click(function() {
				var bg = colorText.html();
				pubColorText.val(bg);
				pubColorShow.css("background-color",bg);				
			});
			colorClear.mouseover(function() {
				hoverSpan.hide();
				colorText.html("");
				colorShow.css("background-color","#FFFFFF");
			}).click(function() {
				pubColorText.val("");
				pubColorShow.css("background-color","#FFFFFF");			
			});
		}
		pubColorShow.focus(function() {
			$(this).blur();
		}).click(function() {
			if(!isVisible) {
				isVisible = true;
				panel.show();
				panel.positionOf(pubColorText);
				var color = $.trim(pubColorText.val());
				if(color!="") {
					var currColorSpan = $("#__colorSpan"+color.substring(1).toUpperCase());
					if(currColorSpan.length>0) {
						selectedSpan.show();
						selectedSpan.css("background-color",color);
						selectedSpan.attr("attrBgColor",color);
						selectedSpan.offset(currColorSpan.offset());						
					} else {
						selectedSpan.hide();
					}
					colorText.html(color);
					colorShow.css("background-color","#FFFFFF");
					colorShow.css("background-color",color);
				}
				$(document).bind("mousedown", function(event) {
					$(document).unbind("mousedown");
					setTimeout(function() {
						isVisible = false;
						hoverSpan.hide();
						selectedSpan.hide();
						panel.hide();
						pubColorText.focus();
					},200);
				});		
			}
		});
		pubColorText.change(function() {
			var color = $.trim($(this).val());
			try {
				if (color != "") {
					pubColorShow.css("background-color", "#FFFFFF");
					pubColorShow.css("background-color", color);
				} else {
					pubColorShow.css("background-color", "#FFFFFF");
				}
			} catch (ex) {
				alert("Invalid color: " + color);
				$(this).focus().select();
			}
		});
		pubColorText.change();	
	};
})(jQuery);
