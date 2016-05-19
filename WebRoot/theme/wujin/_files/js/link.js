function DY_scroll(wraper,prev,next,img,speed,or)
{
var wraper = $(wraper);
var prev = $(prev);
var next = $(next);
var img = $(img);
var w = 302;/*移动的速度*/
var s = speed;
next.click(function()
{
img.stop(true,true).animate({'marginLeft':-w},function()
{
img.find('.lkson').eq(0).appendTo(img);
img.css({'margin-left':0});
});
});
prev.click(function()
{
img.find('.lkson:last').prependTo(img);

img.css({'margin-left':-w});
img.stop(true,true).animate({"marginLeft":0});
});
if (or == true)
{
ad = setInterval(function() { next.click();},s*1000);
wraper.hover(function(){clearInterval(ad);},function(){ad = setInterval(function() { next.click();},s*1000);});
}
}
$(function(){
	DY_scroll('.i_link','.btn2','.btn1','.lkgd',3,true);	
})
