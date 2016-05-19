$(function(){
	
	dingwei();
	var $p=$(".cp_sa");
	if($p.length>0){
		$p.find(".s1:nth-child(4n)").css("margin-right","0");
	};
	function getScrollTop()
	{
		var scrollTop=0;
		if(document.documentElement&&document.documentElement.scrollTop)
		{
			scrollTop=document.documentElement.scrollTop;
		}
		else if(document.body)
		{
			scrollTop=document.body.scrollTop;
		}
		return scrollTop;
	}

	$(".head_nav").click(function(){
		$("#header").fadeIn('fast');
		$("#head").fadeOut();	
	})
})
function dingwei(){
	var webAddress = location.href;
	webAddress = webAddress.split("?")[0];
	webAddress = webAddress.split("/");
	webAddress = webAddress[webAddress.length-1]
	webAddress = webAddress.split(".")[0];
	webAddress = webAddress.split("_")[0];
	
	$('.nav li').find("a").each(function() {
		var Address = $(this).attr('href');	
			Address = Address.split("?")[0];
			Address = Address.split("/");
			Address = Address[Address.length-1]
			Address = Address.split(".")[0];
			Address = Address.split("_")[0];
	});
}