// JScript 文件
function scrollx(p){ 
var d = document,dd = d.documentElement,db = d.body,w = window,o = d.getElementById(p.id),ie6 = /msie 6/i.test(navigator.userAgent),style,timer; 
if(o){ 
o.style.cssText +=";position:"+(p.f&&!ie6?'fixed':'absolute')+";"+(p.l==undefined?'right:20px;':'left:'+p.l+'px;')+(p.t!=undefined?'top:'+p.t+'px':'bottom:0'); 
if(p.f&&ie6){ 
o.style.cssText +=';left:expression(documentElement.scrollLeft + '+(p.l==undefined?dd.clientWidth-o.offsetWidth:p.l)+' + "px");top:expression(documentElement.scrollTop +'+(p.t==undefined?dd.clientHeight-o.offsetHeight:p.t)+'+ "px" );'; 
dd.style.cssText +=';background-image: url(about:blank);background-attachment:fixed;'; 
}else{ 
if(!p.f){ 
w.onresize = w.onscroll = function(){ 
clearInterval(timer); 
timer = setInterval(function(){ 
//双选择为了修复chrome 下xhtml解析时dd.scrollTop为 0 
var st = (dd.scrollTop||db.scrollTop),c; 
c = st - o.offsetTop + (p.t!=undefined?p.t:(w.innerHeight||dd.clientHeight)-o.offsetHeight); 
if(c!=0){ 
o.style.top = o.offsetTop + Math.ceil(Math.abs(c)/10)*(c<0?-1:1) + 'px'; 
}else{ 
clearInterval(timer); 
} 
},10) 
} 
} 
} 
} 
} 

function hiddensp(){
     var  div  =  document.getElementById("sp_div"); 
	div.parentNode.removeChild(div); 
}

    var host="http://www.535e.com";
	spname="3492";
    var spinfo="";
	spinfo+="<div  id='sp_div' style='bottom:0px;width:250px;height:400px;z-index:999;background-color: Transparent;' >"; 
	spinfo+="<div Style='position: absolute; bottom:0px; right:0px; z-index:999;'>";  
	spinfo+="<a href=\"JavaScript:;\" onclick=\"hiddensp()\" style=\"color:Red;text-decoration:none;font-size:12px;\">关闭</a>";  
	spinfo+="</div>"; 
	spinfo+="<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0'"; 
	spinfo+="width='250' height='400' id='FLVPlayer'>"; 
	spinfo+="<param name='wmode' value='transparent'>"; 
	spinfo+="<param name='movie' value='"+host+"/shiyong/FLVPlayer_Progressive.swf' />"; 
	spinfo+="<param name='salign' value='lt' />"; 
	spinfo+="<param name='quality' value='high' />"; 
	spinfo+="<param name='scale' value='noscale' />"; 
	spinfo+="<param name='FlashVars' value='&MM_ComponentVersion=1&skinName="+host+"/shiyong/Clear_Skin_1&streamName="+host+"/shiyong/4/"+spname+"&autoPlay=true&autoRewind=false' />"; 
	spinfo+="<embed wmode='transparent' src='"+host+"/shiyong/FLVPlayer_Progressive.swf' flashvars='&MM_ComponentVersion=1&skinName="+host+"/shiyong/Clear_Skin_1&streamName="+host+"/shiyong/4/"+spname+"&autoPlay=true&autoRewind=false'"; 
	spinfo+="quality='high' scale='noscale' width='250' height='400' name='FLVPlayer' salign='LT'"; 
	spinfo+="type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />"; 
	spinfo+="</object>"; 
	spinfo+="</div>"; 
	document.write(spinfo);
	scrollx({id:'sp_div',f:1})





