//加载头部css
$(function(){
	//判断是否存在key
	if(document.querySelector('meta[name="mstore-key"]') != null){
		//将key值取出
		var mstoreKey = document.querySelector('meta[name="mstore-key"]').getAttribute('content');
		//判断Key值是否有效，有效即进行数据请求返回用户头像与昵称
		if(mstoreKey != null){
			$.ajax({ 
				type: "POST", 
				url : "http://ms.mingsoft.net/mstore/upgraderPeopleVersion/info.do", 
				dataType:'jsonp',
				jsonp:'callback',
				jsonpCallback:"jsonpcallback",
				data: "mstore-key="+mstoreKey,		
				success: function(json){
					if(json!=null){
						//判断昵称是否存在
						if(json.upgraderVersionPeopleName != null){
							$("#mstore_sharer").text(json.upgraderVersionPeopleName);
						}
						//判断头像是第三方提供的还是后台上传的
						if(json.upgraderVersionPeopleIcon != null && json.upgraderVersionPeopleIcon != ""){
							if(json.upgraderVersionPeopleIcon.substr(0,7)=="http://"){
								$("#mstore_userIcon").attr("src",json.upgraderVersionPeopleIcon)
							}else{
								$("#mstore_userIcon").attr("src","http://ms.mingsoft.net"+json.upgraderVersionPeopleIcon)
							}
						}
					}
					
				}
			})
		}

	}
  	
	//支付宝二维码显示隐藏
  	$(".mstore-support").hover(function(){
    	$(".mstore-support-pay").slideDown();
  	},function(){
    	$(".mstore-support-pay").slideUp();
  	});
 
	
	
})


//追加头部html
document.write('<div style="width:100%;height:30px;"></div><div id="mstore-header"><div class="mstore-headbody"><div class="mstore-title">安然建站系统-演示模板</div><ul class="mstore-menu"><li><a href="http://zs.arscf.com/apply_site.jspx?themeId=10001" target="_blank" style="margin-left:0px">申请建站</a></li></ul></div></div>');
