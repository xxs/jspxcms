var isIE6=false;
document.write('<!--[if IE 6]><script>isIE6=true;<\/script><![endif]-->');

/* PNG修复(<img>) code by Jin @ 2011.12.02 */
function pngfix(){
	var els=document.images,len=els.length;
	for(var i=0; i<len; i++){
		var el=els[i],W=el.width,H=el.height;
		if(el.src.match(/\.png/i) && !el.style.filter){
			el.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+el.src+"',sizingMethod='scale')";
			el.src="/bbx/images/transparent.gif";
			el.width=W;
			el.height=H;
		}
	}
}
if(isIE6) attachEvent('onload', pngfix);

/*得到Html参数,Code By Nicenic.com, IceFire*/
function Request(strName){
	var strHref = window.document.location.href;
	var intPos = strHref.indexOf("?");
	var strRight = strHref.substr(intPos + 1);
	var arrTmp = strRight.split("&");
	for(var i=0,len=arrTmp.length; i<len; i++){ 
		var arrTemp = arrTmp[i].split("=");
		if(arrTemp[0].toUpperCase() == strName.toUpperCase()){
		if(arrTemp[1].indexOf("#")!=-1) arrTemp[1] = arrTemp[1].substr(0, arrTemp[1].indexOf("#"));
			return arrTemp[1]; 
		}
	}
	return "";
}
function showUrl(){document.write(window.location.hostname);}

//cookie相关
function checkNum(nubmer){
    var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
    if (re.test(nubmer))return true;
	return false;
}
function SetCookie(name,value,hours){
	var hourstay = 30*24*60*60*1000; //此 cookie 将被默认保存 30 天
	if(checkNum(hours)){
		hourstay = hours;
	}
    var exp  = new Date();
    exp.setTime(exp.getTime() + hourstay*60*60*1000);
    document.cookie = name + "="+ escape(value) + ";expires=" + exp.toGMTString();
}
function getCookie(name){     
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr != null) return unescape(arr[2]); return null;
}
function delCookie(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
//表单是否显示
function showForm(){
	if(getCookie('userlogin')==1){
		var formIsShow = document.getElementsByTagName('div');
		for(var i=0;i<formIsShow.length;i++){
			if(formIsShow[i].getAttribute("name")=="formIsShow"&&formIsShow[i].getAttribute("value")==1){
				formIsShow[i].style.display='block';
			}
		}
	}
}

if(!document._write){
	document._write = document.write;

	document.write = function(msg){
		if(msg == '_write' || msg == '_write_stop') return ;
		document._write(msg);
	}
}
window.onload = function(){
	$('.block').each(function(i){
		var $this = $(this),
			j = 100-i;
		if($this.css('zIndex')=='auto' || !$this.css('zIndex')){
			$this.css('zIndex',j);
		}
	});

	$(window).resize(function(){
		$('.placeholder').show().each(function(){
			var $this = $(this), this_offset = $this.offset();
		  	if(this_offset.top + $this.height() > $('body').height()) $this.hide();
		  	else if(this_offset.left + $this.width() > $('body').width()){
		  		console.log($this[0], $('body').width() - this_offset.left);
		  		$this.width($('body').width() - this_offset.left);
		  	}
		})
	}).resize();
}

function setDivHeight(div){
	var i = 0;
	var id = setInterval(function(){
		var obj = $(div);
		var maxTop = 0;
		$('.block', obj).each(function(index){
			var currTop = $(this).offset().top + $(this).height();
			if(currTop > maxTop){
				maxTop = currTop;
			}
		});
		var height = maxTop-obj.offset().top;
	
		obj.height(height);
		i++;
		if(i>=50) clearInterval(id);
	}, 500);
}