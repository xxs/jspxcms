// JavaScript Document
var thisTypeData=true;
$(function(){
	setLestenList();
});
function setLestenList(){
	if(window.isMobile){
		$(".menuIco").bind("touchend", function(e){
			var e = e || window.event;
			e.preventDefault();
			window.parent.menuMobileEnd();
			//var typeData=$(".mDropDown").attr("typeData");
			if(window.thisTypeData){
				$(".mDropDown").css({"display":"block","opacity":1});
				//$(".mDropDown").attr("typeData","2");
				window.thisTypeData=false;
			}else{
				$(".mDropDown").css({"display":"none","opacity":0});
				//$(".mDropDown").attr("typeData","1");
				window.thisTypeData=true;
			}
		});
	}else{
		$(".menuIco").click(function(){
			//var typeData=$(".mDropDown").attr("typeData");
			if(window.thisTypeData){
				$(".mDropDown").css({"display":"block","opacity":1});
				//$(".mDropDown").attr("typeData","2");
				window.thisTypeData=false;
			}else{
				$(".mDropDown").css({"display":"none","opacity":0});
				//$(".mDropDown").attr("typeData","1");
				window.thisTypeData=true;
			}
		});
	}
}