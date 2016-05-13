// JavaScript Document
var problemListLeftCount=0;
var fatherWidth=400;
var problemListWidth=0;
$(function(){
	setViews();
	setLesten();
});
$(window).load(function(){
	$("#hasNumZz").addClass("sms-xz");
});
$(window).resize(function() {
	setViews();
});
function setViews(){
	window.fatherWidth=$(".problemList").width()?$(".problemList").width():400;
	window.problemListWidth=$(".problemList-li").length*window.fatherWidth;
	$(".problemList-li").css({"width":window.fatherWidth+"px"});
	$(".problemList-ul").css({"width":window.problemListWidth+"px"});
	if(window.problemListLeftCount==0){
		$(".problemList-left").css({"display":"none"});
	}else{
		$(".problemList-ul").css({"left":(window.fatherWidth*window.problemListLeftCount)+"px"}); 
	}
	if($(".problemList-li").length<2){
		$(".problemList-right").css({"display":"none"});
	}
}
function setLesten(){
	if(window.isMobile){
		$(".problemList-right").bind("touchstart", function(e){
			var e = e || window.event;
			e.preventDefault();
			window.problemListLeftCount--;
			$(".problemList-ul").css({"left":(window.fatherWidth*window.problemListLeftCount)+"px"});
			if(window.problemListLeftCount<0){
				$(".problemList-left").css({"display":"block"});
			}
			if(window.problemListLeftCount+$(".problemList-li").length<2){
				$(".problemList-right").css({"display":"none"});	
			}
		});
		$(".problemList-left").bind("touchstart", function(e){
			var e = e || window.event;
			e.preventDefault();
			window.problemListLeftCount++;
			$(".problemList-ul").css({"left":(window.fatherWidth*window.problemListLeftCount)+"px"});
			if(window.problemListLeftCount==0){
				$(".problemList-left").css({"display":"none"});
			}else{
				$(".problemList-right").css({"display":"block"});
			}
		});
	}else{
		$(".problemList-right").click(function(){
			window.problemListLeftCount--;
			$(".problemList-ul").css({"left":(window.fatherWidth*window.problemListLeftCount)+"px"});
			if(window.problemListLeftCount<0){
				$(".problemList-left").css({"display":"block"});
			}
			if(window.problemListLeftCount+$(".problemList-li").length==1){
				$(".problemList-right").css({"display":"none"});	
			}
		});
		$(".problemList-left").click(function(){
			window.problemListLeftCount++;
			$(".problemList-ul").css({"left":(window.fatherWidth*window.problemListLeftCount)+"px"});
			if(window.problemListLeftCount==0){
				$(".problemList-left").css({"display":"none"});
			}else{
				$(".problemList-right").css({"display":"block"});
			}
		});
	}
}