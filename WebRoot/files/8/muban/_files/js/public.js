//滚动新闻
function autoScroll(obj) {
   $(obj).find("ul:first").animate({
   marginTop: "-22px"
   }, 500, function() {
      $(this).css({ marginTop: "0px" }).find("li:first").appendTo(this);
   });
}

$(function(){
	//选项卡滑动切换通用
	$(".hoverTag .chgBtn").hover(function(){
		$(this).parent().find(".chgBtn").removeClass("chgCutBtn");
		$(this).addClass("chgCutBtn");
		var cutNum=$(this).parent().find(".chgBtn").index(this);
		$(this).parents(".hoverTag").find(".chgCon").hide();
		$(this).parents(".hoverTag").find(".chgCon").eq(cutNum).show();
	});
	//选项卡点击切换通用
	$(".clickTag .chgBtn").click(function(){
		$(this).parent().find(".chgBtn").removeClass("chgCutBtn");
		$(this).addClass("chgCutBtn");
		var cutNum=$(this).parent().find(".chgBtn").index(this);
		$(this).parents(".clickTag").find(".chgCon").hide();
		$(this).parents(".clickTag").find(".chgCon").eq(cutNum).show();
	});
	//top
	$(".chgImgBox").css("width",$("body").width());
	$(".chgImg").css("left",($("body").width()-1920)/2);

	var myar = setInterval('autoScroll("#scrollDiv")', 3000);
	$("#scrollDiv").hover(
		function() {
			clearInterval(myar);
		}, function() {
			myar = setInterval('autoScroll("#scrollDiv")', 3000)
		}
	);

	var menu = false;
	var menuIndex = -1;
	$(".subLi").hover(
		function(){
			if (menu == false || menuIndex != $(this).index()){
				$(".subnavigation").html($(this).find(".hidden").html());
				$(".navigation_hoverA").hide();
				$(this).find(".navigation_hoverA").show();
				$(".subnavigation").show();
				menu = true;
			}
			menuIndex = $(this).index();
		}
	);

	$(".hidLi").hover(function(){
		$(".subnavigation").fadeOut(100);
		$(".navigation_hoverA").hide();
		menu = false;
	});
	$(".navigation").hover(
		function(){return false},
		function(){
			$(".subnavigation").fadeOut(100);
			$(".navigation_hoverA").hide();
			menu = false;
		}
	);

	//2013-04-09js
	$(".mcase2_list2 li").hover(function(){
	 $(this).find(".mcase2_ptxtBg,.mcase2_ptxt").slideDown(200);	
	},function(){
	 $(this).find(".mcase2_ptxtBg,.mcase2_ptxt").slideUp(200);	
	});

	//作品展示
	$(".mah_r li").click(function(){
		$(".mah_r li").removeClass("mah_cutImg");
		$(this).addClass("mah_cutImg");
		$(".mah_bigImg img").attr("src",$(this).find(".hidden img").attr("src"));
		$("html,body").animate({scrollTop:$(".mah_con").offset().top},1000);
	});

	//弹出框
	$(".mc2_boxList ul").css("width",$(".mc2_boxList li").size()*327);
	$(".mc2_boxList li").hover(function(){
		var cutNum=$(".mc2_boxList li").index(this);
		$(".mc2_alrBox").eq(cutNum).fadeIn(500);
	});
	$(".mc2_alrBox").hover(function(){return false},function(){
		$(".mc2_alrBox").fadeOut(500);
	});

	$(".mc2_btnl").hover(
		function(){
			$(this).attr("src","images/mbtn1a.jpg");
		},function(){
			$(this).attr("src","images/mbtn1.jpg");
		}
	);

	$(".mc2_btn2").hover(
		function(){
			$(this).attr("src","images/mbtn2a.jpg");
		},function(){
			$(this).attr("src","images/mbtn2.jpg");
		}
	);

	var srNum=0;	
	var reNum=$(".mc2_boxList li").size();
	$(".mc2_btn2").click(function(){
		srNum++
		if(srNum>reNum-3){
			srNum=0;	
		}
		$(".mc2_boxList ul").animate({"left":-srNum*327},500);
	});
	$(".mc2_btnl").click(function(){
		srNum--
		if(srNum<0){
		srNum=reNum-3;	
		}
		$(".mc2_boxList ul").animate({"left":-srNum*327},500);
	});

	//本月新增
	$(".yunn_chg .yunn_s1").hover(function(){
		$(this).parent().find(".yunn_s1").removeClass("yunn_s2");
		$(this).addClass("yunn_s2");
		var cutNuma=$(this).parent().find(".yunn_s1").index(this);
		$(this).parents(".yunn_chg").find(".yunn_imgBox").hide();
		$(this).parents(".yunn_chg").find(".yunn_imgBox").eq(cutNuma).show();
	});

	//屏蔽页面错误
	$(window).error(function(){
	  return true;
	});
	$("img").error(function(){
	  $(this).hide();
	});

});

//2013-07-02
$(function(){
	$(".msc_c1btn li").hover(function(){var msc_cut1=$(".msc_c1btn li").index(this);$(".msc_c1btn li").removeClass("msc_c1cut");$(this).addClass("msc_c1cut");$(".msc_c1box").hide();$(".msc_c1box").eq(msc_cut1).show()});
	$(".msc_menu1").hover(function(){$(".msc_menu3").slideDown(100)},function(){$(".msc_menu3").slideUp(100)});
	$(".msc_c1btn li:last").css("border","none");
	$(".msc_lay1").hover(function(){$(".msc_lay1Btn,.msc_lay1Box").show();},function(){$(".msc_lay1Btn,.msc_lay1Box").hide();});
	$(".msc_lay2").hover(function(){$(".msc_lay2hover,.msc_lay2box").show();},function(){$(".msc_lay2hover,.msc_lay2box").hide();});
	
	//查看更多
	$(".msc2_hideBtn").click(function(){$(".msc2_itmBox").toggleClass("msc2_itmBox2")});
	//详细页面
	
	$(".ke_imgUL li img").click(function(){
		$(".ke_imgUL li img").removeClass("hover");
		$(this).addClass("hover");
	});

	
	$(".msc3_tab tr:odd,.mcs3_tab tr:odd").find("td").css("background","#FBFBFB");
	
	//商品评论切换
	$(".msc3_chg3 li").click(function(){
		var msc3Inx=$(".msc3_chg3 li").index(this);$(".msc3_chg3 li").removeClass("msc3_chg3Cut");$(this).addClass("msc3_chg3Cut");
		$(".msc3_plItm").hide();$(".msc3_plItm").eq(msc3Inx).show();
	});

	$("#searchBtn").click(function(){
		if ($("#keywords").val()){
			location.href = $pageInfo.siteUrl + "/channels/168.html#/" + $("#keywords").val() + "/empty/default/0";
		}
	});

	$(".yun_keyword a").click(function(){
		location.href = $pageInfo.siteUrl + "/channels/168.html#/" + $(this).html() + "/empty/default/0";
	});

	$(".yunnl_menu dt").click(function(){
		$(".yunnl_menu dt").removeClass("yunnl_cutDt");$(".yunnl_menu dd").hide();
		$(this).addClass("yunnl_cutDt");$(this).next("dd").show();
	});
	
	
	/*收件信息2013-07-08*/
	// $(".mcr_radion").click(function(){$(".mcr_up1b li").removeClass("mcr_cutLi");$(".mcr_up1b li").addClass("mcr_upLi");$(this).parents("li").addClass("mcr_cutLi");$(this).parents("li").removeClass("mcr_upLi");});
	
	// $(".newmcr_radion,.mcr_editor").click(function(){$(".mcr_newInfo").show();});
	// $(".oldmcr_radion").click(function(){$(".mcr_newInfo").hide();});
	
	// $(".mcr_btn3").click(function(){$(".mcr_up1b").hide();$(".mcr_up1a").show()});
 //    $(".mcr_clik1").click(function(){$(".mcr_up1a").hide();$(".mcr_up1b").show()});
	
	//$(".mcr_btn4").click(function(){$(".mcr_up2b").hide();$(".mcr_up2a").show()});
	//$(".mcr_clik2").click(function(){$(".mcr_up2a").hide();$(".mcr_up2b").show()});
	// $(".mcr_radion5").click(function(){$(".mcr_int1").show()});
	// $(".mcr_radion6").click(function(){$(".mcr_int1").hide()});
	// $(".mcr_radion8,.mcr_editor2").click(function(){$(".mcr_upcon").show()});
	// $(".mcr_radion9").click(function(){$(".mcr_upcon").hide()})
	// $(".mcr_li3").click(function(){$(".mcr_li3").removeClass("mcr_li3Cut");$(this).addClass("mcr_li3Cut")});
	
	//$(".mcr_clik3").click(function(){$(".mcr_up3a").hide();$(".mcr_up3b").show()});
	// $(".mcr_btn5").click(function(){$(".mcr_up3b").hide();$(".mcr_up3a").show();});
	// $(".mcr_radion10").click(function(){$(".mcr_other").show();$(".mcr_fpinfo").hide();});
	// $(".mcr_radion11,.mcr_radion9,.mcr_li3Cut,.mcr_radion12").click(function(){$(".mcr_other").hide();$(".mcr_fpinfo").show();});
	
	$(".msc3_bs1").click(function(){$(this).siblings().removeClass("msc3_bscut1");$(this).addClass("msc3_bscut1")});

	$("#consigneeSelect ul li input").click(function(){$(this).parent().siblings().removeClass("mcr_cutLi").addClass("mcr_upLi");$(this).parent().addClass("mcr_cutLi");});
});