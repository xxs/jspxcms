document.write("<div id=\"idBox\" class=\"lightbox\" style=\"display:none;border-radius:5px;box-shadow:0px 0px 15px #000;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"IdBoxTable\" style=\"background:#fff;border-radius:5px 5px\"><tr><td style=\"filter:progid:DXImageTransform.Microsoft.gradient(startColorStr=#D5D6E3,endColorStr=#FFffff);padding:3px 3px 3px 6px;color:#000000;font-size:12px;font-weight:bold;text-align:left;height:36px;line-hieght:36px;border-bottom:1px solid #ccc;\"><span id=\"boxName\">显示框</span></td><td align=\"right\" style=\"filter:progid:DXImageTransform.Microsoft.gradient(startColorStr=#D5D6E3,endColorStr=#ffffff);padding:3px 6px 3px 3px;border-bottom:1px solid #ccc;padding-right:0px;\"><a href=\"javascript:void(0)\" onclick=\"box.Close();\" style=\"color:#0066cc;text-decoration:none;\"><span id=\"boxClose\" title=\"关闭\" style=\"cursor:pointer;color:#c00; font:600 18px/20px verdana,arial;border-left:1px solid #ccc;padding:8px 10px 7px 10px;height:36px;line-hieght:36px;\">×</span></a></td></tr><tr><td colspan=\"2\"><iframe id=\"showiframe\" src=\"\" frameborder=\"0\" width=\"800\" height=\"500\"></iframe></td></tr></table></div>");

var isIE = (document.all) ? true : false;

var isIE6 = isIE && ([/MSIE (\d+)\.0/i.exec(navigator.userAgent)][0][1] == 6);

var $G = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};

var Class = {
	create: function() {
		return function() { this.initialize.apply(this, arguments); }
	}
}

var Extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
}

var Bind = function(object, fun) {
	return function() {
		return fun.apply(object, arguments);
	}
}

var Each = function(list, fun){
	for (var i = 0, len = list.length; i < len; i++) { fun(list[i], i); }
};

var Contains = function(a, b){
	return a.contains ? a != b && a.contains(b) : !!(a.compareDocumentPosition(b) & 16);
}


var OverLay = Class.create();
OverLay.prototype = {
  initialize: function(options) {

	this.SetOptions(options);
	
	this.Lay = $G(this.options.Lay) || document.body.insertBefore(document.createElement("div"), document.body.childNodes[0]);
	
	this.Color = this.options.Color;
	this.Opacity = parseInt(this.options.Opacity);
	this.zIndex = parseInt(this.options.zIndex);
	
	with(this.Lay.style){ display = "none"; zIndex = this.zIndex; left = top = 0; position = "fixed"; width = height = "100%"; }
	
	if(isIE6){
		this.Lay.style.position = "absolute";
		//ie6设置覆盖层大小程序
		this._resize = Bind(this, function(){
			this.Lay.style.width = Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px";
			this.Lay.style.height = Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px";
		});
		//遮盖select
		this.Lay.innerHTML = '<iframe style="position:absolute;top:0;left:0;width:100%;height:100%;filter:alpha(opacity=0);"></iframe>'
	}
  },
  //设置默认属性
  SetOptions: function(options) {
    this.options = {//默认值
		Lay:		null,//覆盖层对象
		Color:		"#000",//背景色
		Opacity:	50,//透明度(0-100)
		zIndex:		999999//层叠顺序
    };
    Extend(this.options, options || {});
  },
  //显示
  Show: function() {
	//兼容ie6
	if(isIE6){ this._resize(); window.attachEvent("onresize", this._resize); }
	//设置样式
	with(this.Lay.style){
		//设置透明度
		filter = "alpha(opacity:" + this.Opacity + ")"; 
		opacity = this.Opacity / 100;
		backgroundColor = this.Color; display = "block";
	}
  },
  //关闭
  Close: function() {
	this.Lay.style.display = "none";
	if(isIE6){ window.detachEvent("onresize", this._resize); }
  }
};



var LightBox = Class.create();
LightBox.prototype = {
  initialize: function(box, options) {
	
	this.Box = $G(box);//显示层
	
	this.OverLay = new OverLay(options);//覆盖层
	
	this.SetOptions(options);
	
	this.Fixed = !!this.options.Fixed;
	this.Over = !!this.options.Over;
	this.Center = !!this.options.Center;
	this.onShow = this.options.onShow;
	
	this.Box.style.zIndex = this.OverLay.zIndex + 99990;
	//this.Box.style.display = "none";
	//兼容ie6用的属性
	if(isIE6){
		this._top = this._left = 0; this._select = [];
		this._fixed = Bind(this, function(){ this.Center ? this.SetCenter() : this.SetFixed(); });
	}
  },
  //设置默认属性
  SetOptions: function(options) {
    this.options = {//默认值
		Over:	true,//是否显示覆盖层
		Fixed:	true,//是否固定定位
		Center:	true,//是否居中
		onShow:	function(){}//显示时执行
	};
    Extend(this.options, options || {});
  },
  //兼容ie6的固定定位程序
  SetFixed: function(){
	this.Box.style.top = document.documentElement.scrollTop - this._top + this.Box.offsetTop + "px";
	this.Box.style.left = document.documentElement.scrollLeft - this._left + this.Box.offsetLeft + "px";
	
	this._top = document.documentElement.scrollTop; this._left = document.documentElement.scrollLeft;
  },
  //兼容ie6的居中定位程序
  SetCenter: function(){
	this.Box.style.marginTop = document.documentElement.scrollTop - this.Box.offsetHeight / 2 + "px";
	this.Box.style.marginLeft = document.documentElement.scrollLeft - this.Box.offsetWidth / 2 + "px";
  },
  //显示
  Show: function(options) {
  	if(typeof options != 'undefined' && options.width) document.getElementById('showiframe').width = options.width;
	if(typeof options != 'undefined' && options.height) document.getElementById('showiframe').height = options.height;
    //刷新验证码
    //getObject('img').src='/has_client/include/code.asp?'+(new Date().getTime());

	//固定定位
	this.Box.style.position = this.Fixed && !isIE6 ? "fixed" : "absolute";

	//覆盖层
	this.Over && this.OverLay.Show();
	
	this.Box.style.display = "";
	//document.login_club.username.focus();
	
	//居中
	if(this.Center){
		this.Box.style.top = this.Box.style.left = "50%";
		//设置margin
		if(this.Fixed){
			this.Box.style.marginTop = - this.Box.offsetHeight / 2 + "px";
			this.Box.style.marginLeft = - this.Box.offsetWidth / 2 + "px";
		}else{
			this.SetCenter();
		}
	}
	
	//兼容ie6
	if(isIE6){
		if(!this.Over){
			//没有覆盖层ie6需要把不在Box上的select隐藏
			this._select.length = 0;
			Each(document.getElementsByTagName("select"), Bind(this, function(o){
				if(!Contains(this.Box, o)){ o.style.visibility = "hidden"; this._select.push(o); }
			}))
		}
		//设置显示位置
		this.Center ? this.SetCenter() : this.Fixed && this.SetFixed();
		//设置定位
		this.Fixed && window.attachEvent("onscroll", this._fixed);
	}
	
	this.onShow();
  },
  //关闭
  Close: function() {
	this.Box.style.display = "none";
	this.OverLay.Close();
	if(isIE6){
		window.detachEvent("onscroll", this._fixed);
		Each(this._select, function(o){ o.style.visibility = "visible"; });
	}
  }
};

var box = new LightBox("idBox");