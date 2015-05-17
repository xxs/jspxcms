var str = '<style type="text/css">.suspension { position: fixed; z-index: 999999; right: 10px; bottom: 10px; width: 80px; height: 204px; }.suspension-box { position: relative; float: right; }.suspension-box a.a { display: block; width: 50px; height: 50px; background-color: #FF3333; margin-bottom: 1px; cursor: pointer; }.suspension-box a.back_top { background-color: #880000; }.suspension-box a.qq:hover,.suspension-box a.tel:hover,.suspension-box a.code:hover { opacity: 0.8; filter: alpha(opacity=80); }.suspension-box a.back_top:hover { background-color: #880000; }.suspension-box a i { height: 21px;display: block; float: left; width: 25px; margin-left: 15px; margin-top: 15px; background-image: url(http://www.ssmoban.com/images/suspension-bg.png); background-repeat: no-repeat; text-indent: -9999px; }.suspension-box a.qq i { background-position: 0 0; }.suspension-box a.tel i { background-position: -25px 0; }.suspension-box a.code i { background-position: 0 -25px; }.suspension-box a.back_top i { background-position: -28px -25px; }.suspension-box div.code { position: absolute; left: -197px; *left: -207px; top: 102px; display: none; width: 178px; height: 85px; padding-left: 10px; padding-top: 10px; background-color: #fff; border: 1px solid #D9D9D9; }.suspension-box div.code p { width: 155px; margin-top: 5px; text-align: center; }.suspension-box div.code i { width: 8px; height: 15px; position: absolute; top: 10px; right: -8px; background-image: url(http://www.ssmoban.com/images/trangle-right-suspension.png); background-repeat: no-repeat; }.suspension-box div.tencent {z-index:9999; position: absolute; left: -187px; top: 0px; width: 177px; height: 85px; display:none; background-color: #fff; border: 1px solid #D7D7D7; }.suspension-box div.tencent p.tencent-p { position: absolute; left: -187px; top: 51px; display: none; width: 177px; height: 85px; background-color: #fff; border: 1px solid #D7D7D7;}.suspension-box div.tencent i.tencent-i { width: 8px; height: 15px; position: absolute; top: 10px; right: -8px; text-indent: -9999px; background-image: url(http://www.ssmoban.com/images/trangle-right-suspension.png); background-repeat: no-repeat; }.suspension-box div.tencent b.tencent-b { position: absolute; left: 10px; top: 10px; width: 62px; height: 64px; ; background-repeat: no-repeat; }.suspension-box div.tencent span.tencent-span { position: absolute; left: -21px; top: 7px; border-radius: 22px; width: 45px; height: 48px; text-align: center; z-index: 999999; overflow: hidden; }.suspension-box div.tencent .tencent-qq {position: absolute; left: 3px;top:0px; height: 45px; width: 39px; }.suspension-box div.tel { position: absolute; left: -187px; top: 51px; display: none; width: 177px; height: 85px; background-color: #fff; border: 1px solid #D7D7D7; }.suspension-box div.tel i { width: 8px; height: 15px; position: absolute; top: 20px; right: -8px; background-image: url(http://www.ssmoban.com/images/trangle-right-suspension.png); background-repeat: no-repeat; }.suspension-box div.tel strong,.suspension-box div.tencent-p strong { display: block; margin-left: 17px; margin-top: 16px; font-size: 16px; font-weight: normal; }.suspension-box div.tel p,.suspension-box div.tencent-p p { margin-left: 17px; font-size: 23px; font-family: arial; color: #FD696D; }.icon { margin-top:-5px; }.nav a.cart-title-name { color: #000; text-decoration: none; line-height: normal; }.nav a.cart-title-name:hover { color: #ef353d; text-decoration: underline; line-height: normal; }.nav a font { padding: 8px 15px; background:#fd7878; border-radius: 5px; }.nav a font:hover { padding: 8px 15px; background:#ea5050; border-radius: 5px; }.suspension-box div.code p.code-p {	width: 75px;	float: right;	margin-right: 18px;	margin-top: 18px;	font-size: 16px;color: #333;}.suspension-box div.code i.code-i {width: 8px;	height: 15px;	position: absolute;	top: 10px;right: -8px;text-indent: -9999px;	background-image: url(http://www.ssmoban.com/images/trangle-right-suspension.png);	background-repeat: no-repeat;}.suspension-box div.code b.code-b {	position: absolute;	left: 10px;	top: 10px;	width: 62px;	height: 64px;	background-image: url(http://www.ssmoban.com/images/circle-tencent.png);	background-repeat: no-repeat;}.suspension-box div.code span.code-span {	position: absolute;	left: -21px;	top: 7px;	border-radius: 22px;	width: 45px;	height: 48px;	text-align: center;	z-index: 999999;	overflow: hidden;}.suspension-box div.code .code-qq {	position: absolute;	left: 3px;	top:0px;	height: 45px;	width: 39px;}</style><div class="suspension"><div class="suspension-box"><a href="javascript:void(0);" target="_blank" class="qq j-icon a"><i>qq</i></a><a class="a tel j-icon"><i>tel</i></a><!--<a class="code j-icon a"><i>code</i></a>--><a class="back_top j-back-top a"><i>back_top</i></a><div class="tencent j-tencent j-box"><div class="tencent-p"><strong>QQ技术交流群：</strong><p>424137943</p><i></i></div></div><div class="tel j-box"><strong>咨询热线：</strong><p>4008717656</p><i></i></div><!--<div class="code j-code j-box"><a href="http://www.taobao.com/webww/ww.php?ver=3&touid=ssmoban&siteid=cntaobao&status=2&charset=utf-8" target="_blank"><i class="code-i">wangwang</i><b class="code-b"><span class="code-span"><img class="code-qq" src="http://www.ssmoban.com/images/wangwang.png" alt=""></span></b><p class="code-p">旺旺客服<br>点击交谈</p></a></div>--></div></div>';
//加载字符串
$("body").append(str);
// 效果
$(".suspension").find(".j-icon").mouseover(
    function(){
        $(".suspension").find(".j-box").eq($(this).index()).show().siblings(".j-box").hide();
    }
);
//判断ie浏览器
	
	if(navigator.appVersion.match(/11./i) == '11.' || navigator.appVersion.match(/10./i) == '10.') {
		$(".suspension").css("right","30px");
	}

$(".suspension .j-back-top").hide()

$(window).scroll(function(){
    if($(window).scrollTop() > 100){
        $(".suspension .j-back-top").fadeIn()
    }else {
        $(".suspension .j-back-top").fadeOut()
    }
});
$(".j-tencent").hover(function(){
            $(".tencent-qq").stop(true,false).animate({width:"51",height:"59",left:"-3"});
        },function(){
            $(".tencent-qq").stop(true,false).animate({width:"39",height:"45",left:"3"});
        });
		$(".j-code").hover(function(){
            $(".code-qq").stop(true,false).animate({width:"51",height:"59",left:"-3"});
        },function(){
            $(".code-qq").stop(true,false).animate({width:"39",height:"45",left:"3"});
        });
// 效果
$(".suspension").mouseleave(function(event){
    event.preventDefault();
    $(".suspension").find(".j-box").hide();
});

$(".j-back-top").click(function(){
    $("html,body").animate({scrollTop:0},500);
});