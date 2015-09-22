/*index page*/ 
function indexPage(){
	window.bannerAnimate = new BannerAnimate({"imglist":$("#bannerImgBox"),"lilist":"liitem_ib","pointlist":$("#bannerPointBox"),"curpoint":'current_bpb'});
	window.hotRankingImgSlideWrap = new HotRankingImgSlideWrap({});
	window.payWrap = new PayWrap({}); //购买apply

	navwrapFun(); //多层导航
	applyListHoverFun("ullist_mr","hoverli_umr");
	operaBtnFun("ullist_mc"); //下载、购买操作按钮
	operaBtnFun("ullist_mr"); //下载、购买操作按钮
}

/*category Page*/ 
function categoryPage(){
	window.bannerAnimate = new BannerAnimate({"imglist":$("#bannerImgBox"),"lilist":"liitem_ib","pointlist":$("#bannerPointBox"),"curpoint":'current_bpb'});
	window.hotRankingImgSlideWrap = new HotRankingImgSlideWrap();
	window.categoryWrap = new CategoryWrap();
	window.payWrap = new PayWrap({}); //购买apply
	
	navwrapFun(); //多层导航
	applyListHoverFun("ullist_mr","hoverli_umr");
	operaBtnFun("ullist_mr"); //下载、购买操作按钮
}

/*special Page*/ 
function specialPage(){
	window.payWrap = new PayWrap({}); //购买apply

	operaBtnFun("specialapply_c"); //下载、购买操作按钮
}

/*404 page*/
function notFoundPage() {
 	window.payWrap = new PayWrap({}); //购买apply

 	applyListHoverFun("ullist_mr","hoverli_umr");
	operaBtnFun("applyliststyle100"); //下载、购买操作按钮
	operaBtnFun("ullist_mr"); //下载、购买操作按钮
} 

/*预购清单*/
function ToBuyPage(){
	navwrapFun(); //多层导航
	window.ToBuyWrap = new ToBuyWrap();
}

/*标签页面*/
function tagApplyPage(){
	window.tagApplyWrap = new TagApplyWrap();
} 

/*publish more work Page*/ 
function publishmoreworkPage(){
	window.payWrap = new PayWrap({}); //购买apply
	operaBtnFun("applyliststyle100"); //下载、购买操作按钮
	window.publishmoreworkWrap = new PublishMoreWorkWrap({});
	window.musicCategoryWrap = new MusicCategoryWrap({}); //音乐试听
}

/*************** 热门排行区 图片划动动画 ***************/ 

// function imagesSlideFun(){
// 	$("#slideApply").imagesSlider({
// 		leftSlide : 'arrowLeft',
// 		rightSlide : 'arrowRight',
// 		auto : false		
// 	});
// }

function HotRankingImgSlideWrap(){
	this.slideApply = $("#slideApply"); 
	this.arrowLeft = $("#arrowLeft");
	this.arrowRight = $("#arrowRight");
	this.isClickL = false; //是否点击了右边的箭头
	this.isStopL = true; //左边箭头的点击事件的动画是否已经执行完成（动画没有执行完成会克隆多个）
	this.isStopR = true; //右边箭头的点击事件的动画是否已经执行完成
	this.isStopA = true; //动画是否执行完成  

	this.init();
}
HotRankingImgSlideWrap.prototype = {
	init : function(){
		this.classifyNavbarFun();
		this.slideApplyFun();
	},
	//分类导航
	classifyNavbarFun : function(){
		var that = this;
	},
	liPositionFun : function(tri){
		var css_left = parseFloat(tri.css("left"));
		var li_wid = tri.width();

		var Left = css_left + li_wid;
		return Left;
	},

	//划动应用
	slideApplyFun : function(){
		var that = this;
		var $li_tri = that.slideApply.find("li"),
			liWid = $li_tri.get(0) ? $li_tri.get(0).offsetWidth : "", //包含border的宽度
			liLen = $li_tri.length;

		that.slideApply.width((liWid+16)*liLen);
		that.applySlideImgClickFun();
	},
	applySlideImgClickFun:function(){
		var that = this;
		var $lTri = that.arrowLeft,
			$rTri = that.arrowRight;
		$lTri.unbind("click");
		$lTri.bind("click",function(){
			if(that.isStopL && that.isStopA){
				that.isStopL = false;
				that.isClickL = true; 
				that.applySlideImgAnimateFun();
			}
		});

		$rTri.unbind("click");
		$rTri.bind("click",function(){
			if(that.isStopR && that.isStopA){
				that.isStopR = false;
				that.isClickL = false;
				that.applySlideImgAnimateFun();
			}
		});
	},
	applySlideImgAnimateFun:function(){
		var that = this;
		var ul_init_left = that.slideApply.css("left");
		var $Li = that.slideApply.find("li"),
			$li_first = that.slideApply.find('li:first'),
			$li_last = that.slideApply.find('li:last'),
			liWid = parseInt($Li.get(0).offsetWidth + 16);

		var Sum = ((-liWid) + parseFloat(ul_init_left))+"px";
		if(!that.isClickL){  //默认、点击右键
			that.slideApply.append($li_first.clone()); //克隆第一个
			that.slideApply.stop().animate({"left":Sum},500,function(){
				$li_first.remove();
				that.isStopA = true;
				that.isStopR = true;
				that.slideApply.css("left",ul_init_left);
			});
		}else{ //点击左键
			that.slideApply.css("left",Sum).prepend($li_last.clone()); //克隆第一个
			that.slideApply.stop().animate({"left":"-8px"},500,function(){
				that.isStopA = true;
				that.isStopL = true;
				$li_last.remove();
				that.slideApply.css("left",ul_init_left);
			});
		}	
	}
}


/*************** 应用列表 分页 ***************/ 
function CategoryWrap(){
	this.applyTotal = $("#applyListTotal").val();
	this.applyulBox = $("#catApplyList");

	this.winScroll = $("#categoryApply").offset().top - 20;

	this.pager = $("#applyListPage");
	this.pageNum = 1; //第几页
	this.pageSize = 30; //每次获取多少条数据

	this.init();
}
CategoryWrap.prototype = {
	init : function(){
		this.showPagerListFun(this.applyTotal);		
	},
	getAjaxDate : function(num){
		var that = this;
		that.pageNum = num;

		var data = {};
		var category = $("#curCategory").val();

		var ranking = $("#rankingDate").val();
		var sort = $("#sortOrder").val(); //正序(asc)、倒序(desc)

		var get_category_post_url = "stuff/get_category_data/cat" + category + "/" +ranking + "/" + sort +"/" + that.pageNum + ".html";
		
		//百度统计
       	baiDuStatisticsPageviewFun(get_category_post_url);

		$.ajax({  
           type : "POST",
           url : get_category_post_url,
           data : data,
           dataType: "json",
           success : function(res){
                if(res.res == "success"){
                	that.applyulBox.html(that.returnApplyHtmlFun(res.data));
	       			that.showPagerListFun(res.total);
                }
           }
        });
	},
	returnApplyHtmlFun : function(data){
		var that = this;
        var str = "";
        for(var i = 0; i < data.length ; i++){
        	var detailsUrl = "stuff/show/"+data[i].id+".html";
        	var isNew = data[i].isnew || 0,
        		isHot = data[i].hot || 0,
        		bHtml;
        	if(isNew == 1 && isHot == 0){
        		bHtml = '<b class="new_a"></b>';
        	}else if(isNew == 0 && isHot == 1){
        		bHtml = '<b class="hot_a"></b>';
        	}else if(isNew == 1 && isHot == 1){
        		bHtml = '<b class="hot_a"></b>';
        	}else{
        		bHtml = '';
        	}
        	if(/^\/uploads/.test(data[i].icon)){
        		var api_url = $("#api_url").val() || "https://launcher.cocos.com"
        		var icon_url = api_url + data[i].icon;
        	}else{
        		var icon_url = data[i].icon;
        	}

        	lihtml = '<li>'
					 + bHtml
	                 + '<a href="'+ detailsUrl +'" target="_blank" class="itemapply_a" title="'+ data[i].title +'">'
	                 + '<b class="markbg_ia"></b>'
	                 + '<span class="icondis_ia"><img src="'+ icon_url +'" alt="'+ data[i].title +'"></span>'
	                 + '<span class="text_ia">'+ data[i].title +'</span>'
	                 + '</a>'
	                 + '</li>'
            str += lihtml;
        }

        return str;
	},
	showPagerListFun:function(total){
        var that = this;
        var pagecount = Math.ceil(total/that.pageSize); //共显示多少页
        var scrollPos = that.winScroll; //翻页后滚动条的位置

        if(total <= that.pageSize){
            that.pager.hide();
        }else{
            that.pager.show();
            that.pager.pager({
            	scrollPos : scrollPos,
                pagenumber: that.pageNum,
                pagecount: pagecount,
                buttonClickCallback: function(idx){
                    that.getAjaxDate(idx);
                }
            });
        }
    }
}

/*************** 预购清单 ***************/ 
function ToBuyWrap(){
	this.applyTotal = $("#applyListTotal").val();
	this.applyulBox = $("#tobuyApplyList"); 
	this.showRecommendApply = $("#showRecommendApply");

	this.winScroll = 0;

	this.pager = $("#applyListPage");
	this.pageNum = 1; //第几页
	this.pageSize = 28; //每次获取多少条数据

	this.init();
}
ToBuyWrap.prototype = {
	init : function(){
		this.getAjaxDate(this.pageNum);		
	},
	getAjaxDate : function(num){
		var that = this;
		that.pageNum = num;

		var data = {};
		data.page = that.pageNum;

		var collection_list_post_url = "jsapi/collection_list.html";
		
		//百度统计
       	baiDuStatisticsPageviewFun(collection_list_post_url);

		$.ajax({  
           type : "POST",
           url : collection_list_post_url,
           data : data,
           dataType: "json",
           success : function(res){
                if(res.res == 1){
                	that.applyulBox.html("");
                	if(res.total == 0){
                		that.showRecommendApply.show();
                	}else{
                		that.applyulBox.html(that.returnApplyHtmlFun(res.data));
	                	that.applyHoverFun();
	                	that.delToBuyFun();
		       			that.showPagerListFun(res.total);
		       			that.showRecommendApply.hide();
                	}
                }
           }
        });
	},
	returnApplyHtmlFun : function(data){
		var that = this;
        var str = "";
        for(var i = 0; i < data.length ; i++){
        	var detailsUrl = "stuff/show/"+data[i].tool_id+".html";
        	if(/^\/uploads/.test(data[i].icon)){
        		var api_url = $("#api_url").val() || "https://launcher.cocos.com"
        		var icon_url = api_url + data[i].icon;
        	}else{
        		var icon_url = data[i].icon;
        	}

        	lihtml = '<li>'
        			 + '<a class="del_ia" data-toolsId='+ data[i].tool_id +' href="javascript:void(0);"></a>'
	                 + '<a href="'+ detailsUrl +'" target="_blank" class="itemapply_a" title="'+ data[i].title +'">'
	                 + '<b class="markbg_ia"></b>'
	                 + '<span class="icondis_ia"><img src="'+ icon_url +'" alt="'+ data[i].title +'"></span>'
	                 + '<span class="text_ia">'+ data[i].title +'</span>'
	                 + '</a>'
	                 + '</li>'
            str += lihtml;
        }

        return str;
	},
	applyHoverFun : function(){
		var that = this;
		that.applyulBox.find("li").hover(function(){
			var _this = $(this);
			_this.find(".del_ia").fadeIn();
		},function(){
			var _this = $(this);
			_this.find(".del_ia").fadeOut();
		});
	},

	//删除预购清单
	delToBuyFun : function(){
		var that = this;
		var collect_ajax = false;
		var del_app = that.applyulBox.find(".del_ia");
		del_app.unbind("click");
		del_app.bind('click',function(event){
			var event = window.event || event;

			if (event.stopPropagation) {event.stopPropagation();}
			var _this = $(this);
			var data = {};
			data.tool_id = _this.attr("data-toolsId");

			var collection_del_post_url = "/jsapi/collection_del.html";

			//百度统计
       		baiDuStatisticsPageviewFun(collection_del_post_url);


			isLoginAjax();
			if($("#isLogin").val() == 0)	{
				return false;
			}

			if(!collect_ajax){
				collect_ajax = true;
				$.ajax({
					type : "POST",
					url : collection_del_post_url,
					data : data,
					dataType: "json",
					success : function(res){
						collect_ajax = false;
						if(res.res == 1){
							that.getAjaxDate(1);
						}else{
							var errText = '删除失败。';
							showTipsFun(errText);
						}
					}
				});
			}
		});
	},
	showPagerListFun:function(total){
        var that = this;
        var pagecount = Math.ceil(total/that.pageSize); //共显示多少页
        var scrollPos = that.winScroll; //翻页后滚动条的位置

        if(total <= that.pageSize){
            that.pager.hide();
        }else{
            that.pager.show();
            that.pager.pager({
            	scrollPos : scrollPos,
                pagenumber: that.pageNum,
                pagecount: pagecount,
                buttonClickCallback: function(idx){
                    that.getAjaxDate(idx);
                }
            });
        }
    }
}


/*************** 标签页面 ***************/ 
function TagApplyWrap(){
	this.curTagName = $("#currentTagName").val();
	this.tagApplyList = $("#tagApplyList");

	this.pager = $("#applyPage");
	this.pageNum = 1; //第几页
	this.pageSize = 28; //每次获取多少条数据

	this.init();
}
TagApplyWrap.prototype = {
	init : function(){
		this.getTagApplyListFun(this.pageNum);
	},
	getTagApplyListFun : function(num){
		var that = this;
		that.pageNum = num;
		that.pager.hide();

		var get_tag_post_url = "jsapi/get_tag_data/"+ that.curTagName +"/"+ that.pageNum +".html";

		//百度统计
       	baiDuStatisticsPageviewFun(get_tag_post_url);

		var data = {};
			$.ajax({  
			type : "POST",
			url : get_tag_post_url,
			data : data,
			dataType: "json",
			success : function(res){
				that.tagApplyList.createApplyList({
					json_data : res.data
				});
				that.showPagerListFun(res.total);
				operaBtnFun("applyliststyle100"); //下载、购买操作按钮
				window.payWrap = new PayWrap({}); 
			}
		});
	},
	showPagerListFun:function(total){
        var that = this;
        var pagecount = Math.ceil(total/that.pageSize); //共显示多少页
        var scrollPos = that.winScroll; //翻页后滚动条的位置

        if(total <= that.pageSize){
            that.pager.hide();
        }else{
            that.pager.show();
            that.pager.pager({
            	scrollPos : scrollPos,
                pagenumber: that.pageNum,
                pagecount: pagecount,
                buttonClickCallback: function(idx){
                    that.getTagApplyListFun(idx);
                }
            });
        }
    }
}

function PublishMoreWorkWrap(){
	this.applylist = $(".applyliststyle100");
	this.displayapply = $(".display_cb");
	this.applyparent = $(".catedetails_cb");

	this.init();
}
PublishMoreWorkWrap.prototype = {
	init : function(){
		this.displayApplyFun();
	},
	displayApplyFun : function(){
		var that = this;
		var init_hei = this.applyparent.css("max-height");
		var init_hei1 = $(".catedetails1_cb").css("max-height");
		var aTri = that.displayapply.find("a");

		aTri.unbind("click");
		aTri.bind("click",function(){
			var _this = $(this);
			var display_text,packup_text;
			display_text = "展开";
			packup_text = "收起";

			if(_this.parent().hasClass("packup_c")){ 
				_this.parent().removeClass("packup_c");
				_this.html(display_text+"<i></i>");

				var initHei = init_hei;
				if(_this.parents(".categorymodule_b").find(".catedetails_cb").hasClass("catedetails1_cb")){
					initHei = init_hei1;
				}
				_this.parent().parent().find(".catedetails_cb").css({"max-height":initHei});
			}else{
				_this.parent().addClass("packup_c");
				_this.html(packup_text+"<i></i>");
				_this.parent().parent().find(".catedetails_cb").css({"max-height":"none"});
			}
		})
	}
}