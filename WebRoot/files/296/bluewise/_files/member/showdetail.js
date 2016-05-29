document.write('<link rel="stylesheet" href="/js/showdetail/showhelp.css" type="text/css">');
document.write('<div id="detailedSummaryBG" style="display:none;width:500px;height:350px;position:absolute;z-index:10"></div>');
document.write('<div class="popup" id="detailedSummaryBox" style="display:none;z-index:11"> ');
document.write('<table cellpadding="0" cellspacing="0" border="0" width="100%" id="popInnerBox" onmouseover="javascript:inBoxFlag=true;" onmouseout="javascript:inBoxFlag=false;">');
document.write('<tr><td style="padding-bottom:5px"><div style="width:430px;overflow:hidden">');
document.write('<nobr><a href="#" target="_blank" id="detailedSummaryURL" class="wbkeywdbid" onclick="javascript:ct(this, \'%E6%B5%8B%E8%AF%95\', currentHitPos+\'.1\', \'OPEN_HIT_BY_MAG\');"></a></nobr></div></td>');
document.write('<td align="left" style="padding-bottom:5px;"> <a href="javascript:void(0);" onmousedown="javascript:hideDetailedSummary();" class="close-showD">');
document.write('¡Á </a></td> </tr>');
document.write('<tr><td colspan="2" style="border-top-style:solid;border-top-width:1px;border-top-color:#E1E4E5;padding-top:5px;padding-lefT:12px">');
document.write('<div class="popupContent" id="detailedSummaryContent"></div></td></tr></table></div>');
function closeBubble(event) {
	if(event.target!=document.getElementById("detailedSummaryContent") && inBoxFlag==false){
		hideDetailedSummary();
	}
}
function errorHandler() {
}
addEvent(document, "click", closeBubble);


function seturl(_1){
	if(window.RegExp&&window.encodeURIComponent){
		var _2=_1.href;
		var _3=document.sb.q.value;
		if(_2.indexOf("q=")!=-1){
			if(_3!=""){
				_1.href=_2.replace(new RegExp("q=[^&$]*"),"q="+encodeURIComponent(_3));
			}else{
				_1.href=_2.replace(new RegExp("q=[^&$]*"),"");
			}
		}else{
			if(_3!=""){
				_1.href=_2+"&q="+encodeURIComponent(_3);
			}
		}
	}
	return 1;
}
function getRadioButtonValue(_4){
for(var i=0;i<=_4.length-1;i++){
if(_4[i].checked){
return _4[i].value;
}
}
return "none";
}
function addEngine(){
if((typeof window.sidebar=="object")&&(typeof window.sidebar.addSearchEngine=="function")){
window.sidebar.addSearchEngine("http://www.yodao.com/plugins/firefox-searchplugin.src","http://www.yodao.com/plugins/firefox-searchplugin.png","Yodao","Web");
}else{
alert("Netscape 6, Mozilla or Firefox is needed to install this plugin");
}
}
function ctlog(a,q,_8,_9){
var i=new Image();
i.src="ctlog?q="+q+"&url="+encodeURIComponent(a.href)+"&pos="+_8+"&action="+_9;
return true;
}
function ct(a,q,_d,_e){
ctlog(a,q,_d,_e);
return true;
}
function getAbsolutePos(e){
	var l=e.offsetLeft;
	var t=e.offsetTop;
	while(e=e.offsetParent){
		l+=e.offsetLeft;
		t+=e.offsetTop;
	}
	var pos=new Object();
	pos.x=l;
	pos.y=t;
	return pos;
}
function getWindowSize(){
var _13=0,_14=0;
if(typeof (window.innerWidth)=="number"){
_13=window.innerWidth;
_14=window.innerHeight;
}else{
if(document.documentElement&&(document.documentElement.clientWidth||document.documentElement.clientHeight)){
_13=document.documentElement.clientWidth;
_14=document.documentElement.clientHeight;
}else{
if(document.body&&(document.body.clientWidth||document.body.clientHeight)){
_13=document.body.clientWidth;
_14=document.body.clientHeight;
}
}
}
var _15=new Object();
_15.width=_13;
_15.height=_14;
return _15;
}
function getScrollXY(){
var _16=0,_17=0;
if(typeof (window.pageYOffset)=="number"){
_17=window.pageYOffset;
_16=window.pageXOffset;
}else{
if(document.body&&(document.body.scrollLeft||document.body.scrollTop)){
_17=document.body.scrollTop;
_16=document.body.scrollLeft;
}else{
if(document.documentElement&&(document.documentElement.scrollLeft||document.documentElement.scrollTop)){
_17=document.documentElement.scrollTop;
_16=document.documentElement.scrollLeft;
}
}
}
var _18=new Object();
_18.x=_16;
_18.y=_17;
return _18;
}
function addEvent(elm,_1a,fn,_1c){
if(elm.addEventListener){
elm.addEventListener(_1a,fn,_1c);
return true;
}else{
if(elm.attachEvent){
var r=elm.attachEvent("on"+_1a,fn);
return r;
}else{
elm["on"+_1a]=fn;
}
}
}
function changeImageToNormalState(_1e){
	//var img=document.getElementById("detailedSummaryImage"+_1e);
	//img.src="./images/fangdanormal.gif";
}
function changeImageToClickState(_20){
}
function changeImageToHoverState(_21){
	//var img=document.getElementById("detailedSummaryImage"+_21);
	//img.src="./images/fangdafloat.gif";
}
var popTimer;
var gotoTitleTimer;
var canCloseTimer;
var canClose=false;
var inBoxFlag=false;
var detailedSummaryHidden=true;
var lastDocId;
var sumPoped=false;
var mainURI;
function clearPopsTimer(_23,_24){
	changeImageToNormalState(_23);
	if(_24=="1"){
		clearTimeout(popTimer);
	}
}
function hideDetailedSummary(){
	if(!canClose){
		return;
	}
	clearTimeout(gotoTitleTimer);
	var box=document.getElementById("detailedSummaryBox");
	box.style.display="none";
	var _2c=document.getElementById("detailedSummaryBG");
	_2c.style.display="none";
	var _2d=document.getElementById("detailedSummaryContent");
	if(_2d.scrollTop){
		_2d.scrollTop=0;
	}
	detailedSummaryHidden=true;
	sumPoped=false;
	resetInex();
}

downpng=new Image();
downpng.src="/js/showdetail/down.png";
uppng=new Image();
uppng.src="/js/showdetail/up.png";
var cache=new Object();
var currentHitPos=0;
function showDetailedSummary(_33, url, title, titleurl){
	sumPoped=true;
	//clearTimeout(gotoTitleTimer);
	if(title){/*
		var _37=document.getElementById(titleid);
		var url=document.getElementById("hitURL"+_33).href;*/
		var _39=document.getElementById("detailedSummaryURL");
		if(titleurl)_39.href=titleurl;
		_39.innerHTML=title;
	}

	//ctlog(_39,_34,_33,"OPEN_MAG");
	var pos=getAbsolutePos(_33);
	var box=document.getElementById("detailedSummaryBox");
	var _3c=document.getElementById("detailedSummaryBG");
	var _3d=getWindowSize();
	var _3e=getScrollXY();
	box.style.left=(pos.x+20)+"px";
	var _3f=document.getElementById("popupSummaryURL");
	var _40=document.getElementById("detailedSummaryContent");
	var _41=document.getElementById("popInnerBox");
	var _42=navigator.appVersion.split("MSIE");
	var _43=parseFloat(_42[1]);
	if(pos.y-_3e.y<350){
		box.className="popdown";
		box.style.top=pos.y+"px";
		_41.style.margin="63px 0 0 23px";
		if(_43&&_43<7){
			_3c.style.background="0 none no-repeat";
			_3c.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true,sizingMethod=scale,src='/js/showdetail/down.png')";
		}else{
			_3c.style.background="0 url('/js/showdetail/down.png') no-repeat";
		}
	}else{
		box.className="popup";
		box.style.top=(pos.y-340)+"px";
		_41.style.margin="18px 0 0 23px";
		if(_43&&_43<7){
			_3c.style.background="0 none no-repeat";
			_3c.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true,sizingMethod=scale,src='/js/showdetail/up.png')";
		}else{
			_3c.style.background="0 url('/js/showdetail/up.png') no-repeat";
		}
	}

	_3c.style.top=box.style.top;
	_3c.style.left=box.style.left;
	currentHitPos=_33;
	//lastDocId=_36;
	if(typeof (cache[_33])!="undefined"){
		updateDetailedSummary(cache[_33],_36);
	}else{
		var _44=function(_45){
			updateDetailedSummary(_45,_36);
		};
		//getDetailedSummary();
		if(url.indexOf("?")==0 && mainURI!=undefined) url=mainURI+url;
		SendQuery(url, "detailedSummaryContent");
		//DWRUtil.setValue("detailedSummaryContent","ÍøÒ³Ô¤ÀÀÕýÔÚÔØÈë...");
	}

	canClose=false;
	clearTimeout(canCloseTimer);
	canCloseTimer=setTimeout("allowClose()",200);
	if(detailedSummaryHidden){
		_3c.style.display="block";
		box.style.display="block";
	}
	detailedSummaryHidden=false;
}
function allowClose(){
	canClose=true;
}
function getDetailedSummary(){
	
}
function updateDetailedSummary(_46,_47){
	if(_47!=lastDocId){
		return;
	}
	var _48=document.getElementById("detailedSummaryContent");
	if(_46==null||_46==""){
		_46="\u5bf9\u4e0d\u8d77\uff0c\u5bf9\u8be5\u7f51\u9875\u7684\u9884\u89c8\u6682\u65f6\u4e0d\u53ef\u7528\u3002";
	}else{
		cache[currentHitPos]=_46;
	}
	
	_48.innerHTML=_46;
	gotoTitleTimer=setTimeout("gotoTitle()",100);
}
function gotoTitle(){
	var _49=document.getElementById("detailedSummaryContent");
	var _4a=document.getElementById("dssubject");
	if(_4a){
		magnifierScrollTo(_4a.offsetTop);
	}else{
		_49.scrollTop=0;
	}
}
function hideZoom(_4b){
	return;
	if(_4b=="0"){
		return;
	}
	if(!sumPoped){
		return;
	}
	hideDetailedSummary();
}
function magnifierScrollTo(y){
	var _4d=navigator.userAgent.toLowerCase();
	var ie=_4d.indexOf("msie")!=-1;
	var _4f=document.getElementById("detailedSummaryContent");
	if(ie){
		_4f.scrollTop=y-75;
	}else{
		_4f.scrollTop=y-100;
	}
}
var termNavPosition=new Array(20);
function resetInex(){
	for(var i=0;i<20;i++){
		termNavPosition[i]=0;
	}
}

