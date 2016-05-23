//漂浮的
function addEvent(obj,evtType,func,cap){
    cap=cap||false;
if(obj.addEventListener){
     obj.addEventListener(evtType,func,cap);
   return true;
}else if(obj.attachEvent){
        if(cap){
         obj.setCapture();
         return true;
     }else{
      return obj.attachEvent("on" + evtType,func);
   }
}else{
   return false;
    }
}
function getPageScroll(){
    var xScroll,yScroll;
if (self.pageXOffset) {
   xScroll = self.pageXOffset;
} else if (document.documentElement && document.documentElement.scrollLeft){
   xScroll = document.documentElement.scrollLeft;
} else if (document.body) {
   xScroll = document.body.scrollLeft;
}
if (self.pageYOffset) {
   yScroll = self.pageYOffset;
} else if (document.documentElement && document.documentElement.scrollTop){
   yScroll = document.documentElement.scrollTop;
} else if (document.body) {
   yScroll = document.body.scrollTop;
}
arrayPageScroll = new Array(xScroll,yScroll);
return arrayPageScroll;
}
function GetPageSize(){
    var xScroll, yScroll;
    if (window.innerHeight && window.scrollMaxY) { 
        xScroll = document.body.scrollWidth;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else if (document.body.scrollHeight > document.body.offsetHeight){
        xScroll = document.body.scrollWidth;
        yScroll = document.body.scrollHeight;
    } else {
        xScroll = document.body.offsetWidth;
        yScroll = document.body.offsetHeight;
    }
    var windowWidth, windowHeight;
    if (self.innerHeight) {
        windowWidth = self.innerWidth;
        windowHeight = self.innerHeight;
    } else if (document.documentElement && document.documentElement.clientHeight) {
        windowWidth = document.documentElement.clientWidth;
        windowHeight = document.documentElement.clientHeight;
    } else if (document.body) {
        windowWidth = document.body.clientWidth;
        windowHeight = document.body.clientHeight;
    } 
    if(yScroll < windowHeight){
        pageHeight = windowHeight;
    } else { 
        pageHeight = yScroll;
    }
    if(xScroll < windowWidth){ 
        pageWidth = windowWidth;
    } else {
        pageWidth = xScroll;
    }
    arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight) 
    return arrayPageSize;
}
var AdMoveConfig=new Object();
AdMoveConfig.IsInitialized=false;
AdMoveConfig.ScrollX=0;
AdMoveConfig.ScrollY=0;
AdMoveConfig.MoveWidth=0;
AdMoveConfig.MoveHeight=0;
AdMoveConfig.Resize=function(){
    var winsize=GetPageSize();
    AdMoveConfig.MoveWidth=winsize[2];
    AdMoveConfig.MoveHeight=winsize[3];
    AdMoveConfig.Scroll();
}
AdMoveConfig.Scroll=function(){
    var winscroll=getPageScroll();
    AdMoveConfig.ScrollX=winscroll[0];
    AdMoveConfig.ScrollY=winscroll[1];
}
addEvent(window,"resize",AdMoveConfig.Resize);
addEvent(window,"scroll",AdMoveConfig.Scroll);
function AdMove(id){
    if(!AdMoveConfig.IsInitialized){
        AdMoveConfig.Resize();
        AdMoveConfig.IsInitialized=true;
    }
    var obj=document.getElementById(id);
    obj.style.position="absolute";
    var W=AdMoveConfig.MoveWidth-obj.offsetWidth;
    var H=AdMoveConfig.MoveHeight-obj.offsetHeight;
    var x = W*Math.random(),y = H*Math.random();
    var rad=(Math.random()+1)*Math.PI/6;
    var kx=Math.sin(rad),ky=Math.cos(rad);
    var dirx = (Math.random()<0.5?1:-1), diry = (Math.random()<0.5?1:-1);
    var step = 1;
    var interval;
    this.SetLocation=function(vx,vy){x=vx;y=vy;}
    this.SetDirection=function(vx,vy){dirx=vx;diry=vy;}
    obj.CustomMethod=function(){
        obj.style.left = (x + AdMoveConfig.ScrollX) + "px";
        obj.style.top = (y + AdMoveConfig.ScrollY) + "px";
        rad=(Math.random()+1)*Math.PI/6;
        W=AdMoveConfig.MoveWidth-obj.offsetWidth;
        H=AdMoveConfig.MoveHeight-obj.offsetHeight;
        x = x + step*kx*dirx;
        if (x < 0){dirx = 1;x = 0;kx=Math.sin(rad);ky=Math.cos(rad);} 
        if (x > W){dirx = -1;x = W;kx=Math.sin(rad);ky=Math.cos(rad);}
        y = y + step*ky*diry;
        if (y < 0){diry = 1;y = 0;kx=Math.sin(rad);ky=Math.cos(rad);} 
        if (y > H){diry = -1;y = H;kx=Math.sin(rad);ky=Math.cos(rad);}
    }
    this.Run=function(){
        var delay = 10;
        interval=setInterval(obj.CustomMethod,delay);
        obj.onmouseover=function(){clearInterval(interval);}
        obj.onmouseout=function(){interval=setInterval(obj.CustomMethod, delay);}
    }
}



//对联的
function $GO(element){ 
if(arguments.length>1){ 
  for(var i=0,elements=[],length=arguments.length;i<length;i++) 
   elements.push($GO(arguments[i])); 
  return elements; 
} 
if(typeof element=="string") 
  return document.getElementById(element); 
else 
  return element; 
} 
Function.prototype.bind=function(object){ 
var method=this; 
return function(){ 
  method.apply(object,arguments); 
} 
} 
var Class={ 
create:function(){ 
  return function(){ 
   this.initialize.apply(this,arguments); 
  } 
} 
} 
Object.extend=function(destination,resource){ 
for(var property in resource){ 
  destination[property]=resource[property]; 
} 
return destination; 
} 
//对联广告类 
var float_ad=Class.create(); 
float_ad.prototype={ 
initialize:function(id,content,top,margin,left){ 
  document.write('<div id='+id+' style="position:absolute;z-index:999;">'+content+'</div>'); 
  this.id=$GO(id); 
  this.top=top; 
  if(!!left){ 
	  if(margin>0)
		  this.id.style.left=margin+"px";
	  else
		this.id.style.left="8px"; 
  }else{ 
   var divw=document.getElementById(id);
   var w = divw.clientWidth||divw.offsetWidth;
   this.id.style.left=(document.documentElement.clientWidth-8-w-margin)+"px"; 
   window.onresize=function(){ 
    this.id.style.left=(document.documentElement.clientWidth-8-w-margin)+"px"; 
   }.bind(this); 
  } 
  this.id.style.top=top+"px"; 
  
  this.interId=setInterval(this.scroll.bind(this),20); 
}, 
scroll:function(){ 
  this.stmnStartPoint = parseInt(this.id.style.top, 10); 
  this.stmnEndPoint =document.documentElement.scrollTop+ this.top; 
  if(navigator.userAgent.indexOf("Chrome")>0){ 
   this.stmnEndPoint=document.body.scrollTop+this.top; 
  } 
  if ( this.stmnStartPoint != this.stmnEndPoint ) { 
    this.stmnScrollAmount = Math.ceil( Math.abs( this.stmnEndPoint - this.stmnStartPoint ) / 15 ); 
    this.id.style.top = parseInt(this.id.style.top, 10) + ( ( this.stmnEndPoint<this.stmnStartPoint ) ? -this.stmnScrollAmount : this.stmnScrollAmount )+"px"; 
  } 
} 
} 
