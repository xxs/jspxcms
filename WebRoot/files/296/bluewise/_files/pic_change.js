$(function(){
	var A=$("#idNum > li").length;var B=1;
	$("#idNum li").mouseover(function(){B=$("#idNum li").index(this);showImg(B)});
	$("#idTransformView").hover(function(){
		if(C){clearInterval(C)}
	},function(){
		C=setInterval(function(){
			showImg(B);
			B++;
			if(B==A){
				B=0
			}},5000)});
	var C=setInterval(function(){
		showImg(B);
		B++;
		if(B==A){
			B=0
		}},5000)});
function showImg(A){
	$("#idSlider").stop(true,false).animate({top:-98*A},300);
	$("#idNum li").eq(A).addClass("on").siblings().removeClass("on")
}
	var rollText_k=2;
	var rollText_i=1;
	rollText_tt=setInterval("rollText(1)",4000);
	function rollText(A){
		clearInterval(rollText_tt);
		rollText_tt=setInterval("rollText(1)",4000);
		rollText_i+=A;
		if(rollText_i>rollText_k){
			rollText_i=1
		}
		if(rollText_i==0){
			rollText_i=rollText_k
		}
		for(var B=1;B<=rollText_k;B++){
			document.getElementById("ztlist"+B).style.display="none";
		}
		document.getElementById("ztlist"+rollText_i).style.display="block";
		document.getElementById("pageShow").innerHTML="<span class='num'><em>"+rollText_i+"</em> / <em>"+rollText_k+"</em></span>"}
		function zkhq(A,B){
			obj=$id(B+"_list");obzk=$id(B+"_zk");
			obhq=$id(B+"_hq");
			if(obzk.style.display!="none"){
				obzk.style.display="none";
				obhq.style.display="block"
			}else{
				obhq.style.display="none";
				obzk.style.display="block"
			}
			$("#"+B+"_list").animate({height:A},200)}
			function hdi(F,H,D){F.className="on";
			var B;
			var G;
			var E;
			var D;
			var H;
			var A;
			for(var C=1;C<=D;C++){
				G=H+"2_"+C;B=$id(G);
				E=$id(H+"3_"+C);
				A=$("#"+H+"3_"+C+" img");
				if(G!=F.id){B.className="no";
					E.style.display="none"
				}else{
					E.style.display="block";
					A.attr("src",function(){
						return $(this).attr("rel")})
				}
	}
};


