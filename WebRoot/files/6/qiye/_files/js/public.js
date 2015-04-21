jQuery(function(){
jQuery(function(){jQuery(".hoverTag .chgBtn").hover(function(){jQuery(this).parent().find(".chgBtn").removeClass("chgCutBtn");jQuery(this).addClass("chgCutBtn");var cutNum=jQuery(this).parent().find(".chgBtn").index(this);jQuery(this).parents(".hoverTag").find(".chgCon").hide();jQuery(this).parents(".hoverTag").find(".chgCon").eq(cutNum).show();})})

jQuery(function(){jQuery(".clickTag .chgBtn").click(function(){jQuery(this).parent().find(".chgBtn").removeClass("chgCutBtn");jQuery(this).addClass("chgCutBtn");var cutNum=jQuery(this).parent().find(".chgBtn").index(this);jQuery(this).parents(".clickTag").find(".chgCon").hide();jQuery(this).parents(".clickTag").find(".chgCon").eq(cutNum).show();})})


$(".keautoCon").css({"left":-($(".keautoCon").width()-$(document).width())/2}); 
$(".kebox2").height($(".main2Con").height()+400)
$(window).resize(function(){
	$(".keautoCon").css({"left":-($(".keautoCon").width()-$(document).width())/2}); 
	$(".kebox2").height($(".main2Con").height()+400)
});

$(".m2r_nu1 li:odd").addClass("m2r_evenLi");
$(".m2chg1 .chgBtn:first,.m2chg2 .chgBtn:first").css("border-right","none");

})
jQuery(window).error(function(){
  return true;
});
jQuery("img").error(function(){
  $(this).hide();
});

function mainfun(mainObj,t){
		 function getID(id){return document.getElementById(id)}
	     function getTag(tag,obj){return (typeof obj=='object'?obj:getID(obj)).getElementsByTagName(tag); }
		 function alph(obj,n){if(document.all){obj.style.filter="alpha(opacity="+n+")";}}
		 var cut = 0;
		 var timer='';
		 var num = getTag('li',getTag('div',getID(mainObj))[0]).length;		
		 var getpic = getTag('li',getTag('div',getID(mainObj))[0]);
		 var getbtn = getTag('li',getTag('div',getID(mainObj))[1]);
		 var gettext = getTag('li',getTag('div',getID(mainObj))[2]);		 
		 for(i=0;i<num;i++){getpic[i].style.display="none";gettext[i].style.display="none";getbtn[i].onclick=(function(i){
			 return function(){ getbtn[i].className="sel";changePic(i);}})(i);}			 
			 getpic[cut].style.display="block";
			 getbtn[cut].className="sel";
			 gettext[cut].style.display="block";
		 getID(mainObj).onmouseover=function(){clearInterval(timer);}
		 getID(mainObj).onmouseout=function(){timer = setInterval(autoPlay,t);}
		function changePic(ocut){
			for(i=0;i<num;i++){cut=ocut;
			 getpic[i].style.display="none";
			 getbtn[i].className=""			
			 gettext[i].style.display="none";
			 }
			 getpic[cut].style.display="block";
			 getbtn[cut].className="sel";
			 gettext[cut].style.display="block"	
			 	     
	     }
		 function autoPlay(){
			 //alert(cut);
			 if(cut>=num-1){cut=0}else{cut++}
			 changePic(cut);
			 }
			 timer = setInterval(autoPlay,t);
		}	
		
function changeMenu(){
	function getID(id){return document.getElementById(id)}
	function getTag(tag,obj){return (typeof obj=='object'?obj:getID(obj)).getElementsByTagName(tag); }
	var sel=0;var menu = getID("menu");var getBtn = getTag("h2",menu);	var getUl = getTag("ul",menu);var num = getTag("h2",menu).length;for(i=0;i<num;i++){
		getUl[i].style.display="none";
		getBtn[i].onclick=(function(i){
			return function(){
				clickMenu(i);
				}
			})(i);
		}
		getUl[sel].style.display="block";
	function clickMenu(sel){
	for(i=0;i<num;i++){
		getUl[i].style.display="none";
		}
		getUl[sel].style.display="block";
	  }
	}	


//首页 部分js
//计算idslider2的宽度开始
var kuan1=0;
var kuand=document.getElementById('idSlider2')
var kuan=document.getElementById('idSlider2').getElementsByTagName("li");
var tpz=kuan.length;//图片总个数
for(var i=0; i<kuan.length; i++){
 kuan1+=1057;
 }
kuand.style.width=kuan1+'px';
//计算结束
var keVar = function (id) {
 return "string" == typeof id ? document.getElementById(id) : id;
};
var Class = {
  create: function() {
 return function() {
   this.initialize.apply(this, arguments);
 }
  }
}
Object.extend = function(destination, source) {
 for (var property in source) {
  destination[property] = source[property];
 }
 return destination;
}
var TransformView = Class.create();
TransformView.prototype = {
  //容器对象,滑动对象,切换参数,切换数量
  initialize: function(container, slider, parameter, count, options) {
 if(parameter <= 0 || count <= 0) return;
 var oContainer = keVar(container), oSlider = keVar(slider), oThis = this;
 this.Index = 0;//当前索引
 
 this._timer = null;//定时器
 this._slider = oSlider;//滑动对象
 this._parameter = parameter;//切换参数
 this._count = count || 0;//切换数量
 this._target = 0;//目标参数
 
 this.SetOptions(options);
 
 this.Up = !!this.options.Up;
 this.Step = Math.abs(this.options.Step);
 this.Time = Math.abs(this.options.Time);
 this.Auto = !!this.options.Auto;
 this.Pause = Math.abs(this.options.Pause);
 this.onStart = this.options.onStart;
 this.onFinish = this.options.onFinish;
 
 oContainer.style.overflow = "hidden";
 oContainer.style.position = "relative";
 
 oSlider.style.position = "absolute";
 oSlider.style.top = oSlider.style.left = 0;
  },
  //设置默认属性
  SetOptions: function(options) {
 this.options = {//默认值
  Up:   true,//是否向上(否则向左)
  Step:  5,//滑动变化率
  Time:  10,//滑动延时
  Auto:  true,//是否自动转换
  Pause:  3000,//停顿时间(Auto为true时有效)
  onStart: function(){},//开始转换时执行
  onFinish: function(){}//完成转换时执行
 };
 Object.extend(this.options, options || {});
  },
  //开始切换设置
  Start: function() {
 if(this.Index < 0){
  this.Index = this._count - 1;
 } else if (this.Index >= this._count){ this.Index = 0; }
 
 this._target = -1 * this._parameter * this.Index;
 this.onStart();
 this.Move();
  },
  //移动
  Move: function() {
 clearTimeout(this._timer);
 var oThis = this, style = this.Up ? "top" : "left", iNow = parseInt(this._slider.style[style]) || 0, iStep = this.GetStep(this._target, iNow);
 
 if (iStep != 0) {
  this._slider.style[style] = (iNow + iStep) + "px";
  this._timer = setTimeout(function(){ oThis.Move(); }, this.Time);
 } else {
  this._slider.style[style] = this._target + "px";
  this.onFinish();
  if (this.Auto) { this._timer = setTimeout(function(){ oThis.Index++; oThis.Start(); }, this.Pause); }
 }
  },
  //获取步长
  GetStep: function(iTarget, iNow) {
 var iStep = (iTarget - iNow) / this.Step;
 if (iStep == 0) return 0;
 if (Math.abs(iStep) < 1) return (iStep > 0 ? 1 : -1);
 return iStep;
  },
  //停止
  Stop: function(iTarget, iNow) {
 clearTimeout(this._timer);
 this._slider.style[this.Up ? "top" : "left"] = this._target + "px";
  }
};
window.onload=function(){
 function Each(list, fun){
  for (var i = 0, len = list.length; i < len; i++) { fun(list[i], i); }
 };
 
 var objs2 = keVar("idNum2").getElementsByTagName("li");
 var tv2 = new TransformView("idTransformView2", "idSlider2", 1057, tpz, { 
  onStart: function(){ Each(objs2, function(o, i){ o.className = tv2.Index == i ? "on" : ""; }) },//按钮样式
  Up: false
 });//6是轮播总数
 tv2.Start();
 Each(objs2, function(o, i){
  o.onmouseover = function(){
   o.className = "on";
   tv2.Auto = false;
   tv2.Index = i;
   tv2.Start();
  }
  o.onmouseout = function(){
   o.className = "";
   tv2.Auto = true;
   tv2.Start();
  }
 })
}
