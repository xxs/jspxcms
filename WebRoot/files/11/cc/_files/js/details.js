function storeDeatilsPage(){
	window.bannerAnimate = new BannerAnimate({"imglist":$("#imgListBox"),"lilist":"liitem_imdec","pointlist":$("#pointListBox"),"curpoint":'current_imdec'});
	window.storeDeatilsWrap = new StoreDeatilsWrap({});	
	window.payWrap = new PayWrap({}); //购买apply
	switchTab(); //切换排名
	operaBtnFun("ullist_mr");
	applyListHoverFun("ullist_mr","hoverli_umr");
	operaBtnFunBig("store_intro_pic");
	operaBtnFunBig("other_mi");
	rankHover();
}
function switchTab(){
	  $("#tab  a").mouseover(function(){
	  	//对应li上加上class
	  	 $.each($("#tab li"),function(i,obj){
	        obj=$(obj);
	        if(obj.hasClass("on")){
	          obj.removeClass("on");
	        }
	  	 });
	  	 $(this).parent("li").addClass("on");
	  	 //根据id判断哪个tab pane 显示；
	     var id=$(this).parent("li").attr("id");
	     if(id.indexOf("month")!=-1){
	     	$("#dayRank").hide();
	        $("#weekRank").hide();
	        $("#monthRank").show();
	     }
	     else if(id.indexOf("week")!=-1){
	        $("#dayRank").hide();
	        $("#weekRank").show();
	        $("#monthRank").hide();
	     }
	     else if(id.indexOf("day")!=-1){
	        $("#dayRank").show();
	        $("#weekRank").hide();
	        $("#monthRank").hide();
	     }
	  });
}
function rankHover(){
	$(".rank_intro_list").parent("li").hover(function(){
       $(this).children(".rank_comment_btn").show();
	},
	function(){
       $(this).children(".rank_comment_btn").hide();
	});
}
function StoreDeatilsWrap(){
	this.toolsId = $("#toolsId").val(); 
	this.addCollect = $("#addCollect"); //添加收藏 
	this.operStates = $("#operStates");

	this.Introduction = $("#Introduction");
	this.displayMoreIntro = $("#displayMoreIntro");
	this.versionCut = $("#versionCut"); //版本切换

	this.imgListBox = $("#imgListBox"); //预览图片
	this.uploadView = $("#uploadView"); //下载内容预览
	this.uppackMenu = $("#uppackMenu");
	this.treeMenuList = $("#treeMenuList");
	this.viewEffect = $("#viewEffect");
	this.treeMenuHtml = "";

	this.displayHistoryVer = $("#displayHistoryVer");//历史版本下载
	this.historyVerDown = $("#historyVerDown");

	this.statGrade = $("#statGrade"); //用户评分进度条
	this.commentListSort = $("#commentListSort"); //评论列表排序

	this.inCommentBtn = $("#inCommentBtn");
	this.commentModule = $("#commentModule"); //评论
	this.commentListBox = $("#commentListBox");
    this.commentPager = $("#commentPager");

    this.commentPen = $("#commentPen"); //撰写评论
	this.selMarkStar = $("#selMarkStar");
	this.starTips = $("#starTips");
	this.commentCont = $("#commentCont");
	this.beyondWord = $("#beyondWord");
	this.commentBtn = $("#commentBtn");
	this.comment_sort = 'new';

	this.initPage = 1;
	this.pageNum = 1; //第几页
    this.pageSize = 10; //每次获取多少条数据

	this.init();
}
StoreDeatilsWrap.prototype = {
	init : function(){
		this.addCollectFun();
		this.versionCutFun(); //版本切换
		this.versionCutHideFun(); 

		this.displayMoreIntroFun(); //展开更多简介
		this.contentImgViewFun(); //图片预览
		this.uploadViewFun(); //下载内容预览
		this.inCommentBtnFun(); //撰写评论
		this.getCommentDateFun(this.initPage); //得到评论数据 
		this.selectStarFun(); //选择评分
		this.commentContEvent();
		this.commentBtnFun();

		this.displayHistoryVerFun(); //展开历史版本
		this.historyVerDownFun(); //历史版本下载
		this.statGradeBarFun(); //用户评分进度条
		this.commentListSortFun(); //评论列表排序
		this.hideSortPulldownFun(); //隐藏排序
	},
	addCollectFun : function(){ //添加收藏
		var that = this;
		var collect_ajax = false;
		var collect_tri = that.addCollect.find("a");

		collect_tri.unbind("click");
		collect_tri.bind("click",function(){
			var _this = $(this);
			var data = {};
			data.tool_id = that.toolsId;

			isLoginAjax();
			if($("#isLogin").val() == 0)	{
				var errText = '请先登录';
				showTipsFun(errText,"login");

				return false;
			}
			
			if(_this.hasClass("cancel_cbec")){
				url = "/jsapi/collection_del.html";
			}else{
				url = "/jsapi/collection_add.html";
			}

			if(!collect_ajax){
				collect_ajax = true;
				$.ajax({
					type : "POST",
					url : url,
					data : data,
					dataType: "json",
					success : function(res){
						collect_ajax = false;
						if(res.res == 1){
							if(_this.hasClass("cancel_cbec")){
								_this.removeClass("cancel_cbec").text("收藏");
							}else{
								_this.addClass("cancel_cbec").text("取消收藏");
							}
						}
					}
				});
			}
		});
	},
	//展开更多简介
	displayMoreIntroFun : function(){
		var that = this;
		var intro_init_hei = that.Introduction.height(); //简介的初始高度

		that.Introduction.css("max-height","none"); //将高度变为auto 

		var intro_actual_hei = that.Introduction.height(); //获取简介为auto时的实际高度
		that.Introduction.css("height",intro_init_hei); //重新将div的高度赋值为原始高度

		if(intro_actual_hei <= intro_init_hei){
			return false;
		}

		that.displayMoreIntro.css("display","block");
		that.displayMoreIntro.unbind("click");
		that.displayMoreIntro.bind("click",function(){
			var _this = $(this);

			if(_this.hasClass("dismoreintro")){
				_this.html("展开<em></em>").removeClass("dismoreintro");
				that.Introduction.stop().animate({
					"height" : intro_init_hei
				},500);
			}else{
				
				_this.html("收起<em></em>").addClass("dismoreintro");
				that.Introduction.stop().animate({
					"height" : intro_actual_hei
				},500);
			}
		})
	},
	//图片预览
	contentImgViewFun : function(){
		var that = this;
		var imgObject = that.imgListBox.find("img");
		imgObject.unbind("click");
		imgObject.bind("click",function(){
			var _this = $(this);
			var imgPath = _this.attr("src");
			showShadowFun();
			$_Imgview.show({
				imgPath : imgPath
			}); // 显示图片
		});
	},

	//下载内容预览
	uploadViewFun : function(arg){
		var that = this;
		var arg = arg || $("#download_form").find("input[name='type']").val();
		var data = {};

		if(arg == "win32" || arg == "win64"){
			arg = "win";
		}
		that.treeMenuHtml = "";
		that.treeMenuList.html("");
		//that.toolsId = "127775";
		$.ajax({
            type : "POST",
            url : "/jsapi/get_zip_preview/"+ that.toolsId + "/" + arg + ".html",
            data : data,
            dataType: "json",
            success : function(res){
				if(res.res == 1){
					that.uploadView.show();
					that.treeMenuList.html(that.returnViewMenuFun(res.tree));

					//添加滚动条
					var outside_ul_client_height = that.treeMenuList.find("ul:first")[0].clientHeight;
					if(outside_ul_client_height <= 240){
						that.uppackMenu.addClass("menuborder_uu");
					}else{
						that.uppackMenu.removeClass("menuborder_uu");
					}

					that.definedScrollFun();

					//点击预览
					that.uppackMenuClickFun();
				}else{
					that.uploadView.hide();
				}
            }
        })
	},
	returnViewMenuFun : function(data){
		var that = this;
		var treeMenu = data || {};
		that.treeMenuHtml += "<ul>";

		for(var i=0; i<treeMenu.length; i++){
			var extension = treeMenu[i].extension || "";
			var name = treeMenu[i].name || "";
			var icon_url = treeMenu[i].icon ? $("#api_url").val() + "/" + treeMenu[i].icon : "";

			var extension_class;
			if(extension != ""){
				extension_class = extension + "icon";
			}else{
				extension_class = "othericon";
			}
			
			
			if(treeMenu[i].sons && treeMenu[i].sons instanceof Array){ //可以使用$.isArray(treeMenu[i].sons) treeMenu[i].sons.constructor == Array判断是否为数组
				that.treeMenuHtml += '<li>';
				that.treeMenuHtml += '<a href="javascript:void(0);" class="'+ extension_class +'" view-data-extension="'+ extension +'" view-data-icon="'+ icon_url +'">'+ name +'</a>';
				that.returnViewMenuFun(treeMenu[i].sons);
				that.treeMenuHtml += '</li>';
			}else{
				that.treeMenuHtml += '<li>'
						+ '<a href="javascript:void(0);" class="'+ extension_class +'" view-data-extension="'+ extension +'" view-data-icon="'+ icon_url +'">'+ name +'</a>'
						+'</li>';
			}
		}
		that.treeMenuHtml += "</ul>";

		return that.treeMenuHtml;
	},
	definedScrollFun : function(){
		$("#uppackMenu").mCustomScrollbar({
	        setHeight:240,
	        setTop: '0px',
	        theme:"dark-3",
	        autoDraggerLength:true,
	        scrollInertia : 0,
	        scrollButtons:{
	            scrollSpeed:20,
	            scrollAmount:50
	        }
	    });
	},
	uppackMenuClickFun : function(){
		var that = this;
		var tri = that.treeMenuList.find("a");

		var first_a = that.treeMenuList.find("a:first");
		var first_icon_url = first_a.attr("view-data-icon") || "../static/images/upload_view_img.png";
		that.viewEffect.html('<img src="'+ first_icon_url +'" />');
		that.treeMenuList.find("a").removeClass("current_uu");
		first_a.addClass("current_uu");

		tri.unbind("click");
		tri.bind("click",function(){
			var _this = $(this);
			var icon_url = _this.attr("view-data-icon") || "../static/images/upload_view_img.png";
			that.viewEffect.html('<img src="'+ icon_url +'" />');

			that.treeMenuList.find("a").removeClass("current_uu");
			_this.addClass("current_uu");
		})
	},

	//撰写评论
	inCommentBtnFun : function(){
		var that = this;
		var get_comment_cont_ajax = false;
		that.inCommentBtn.unbind("click");
		that.inCommentBtn.bind("click",function(){
			isLoginAjax(); //判断用户是否登录
			if($("#isLogin").val() == 0){
				var errText = '评论前请先登录。';
				showTipsFun(errText,"login");
				return false;
			}

			if($("#isBuyHid").val() == 0){
				var errText = '您必须拥有此内容，才能评论。';
				showTipsFun(errText);
				return false;
			}

			var data = {};
			if(!get_comment_cont_ajax){
			 	get_comment_cont_ajax = true;
			 	$.ajax({
	                type : "POST",
	                url : "/stuff/get_comment/"+ that.toolsId +".html",
	                data : data,
	                dataType: "json",
	                success : function(res){ //{1：评论成功，0：评论失败}
	  					get_comment_cont_ajax = false;
	               		//if(res.res == 0){
						//	return false;
						//}

						var content = "",
							score = 0,
							startotal = '';
						if(res.status == 1){
							content = res.comment.content;
							score = res.comment.score;
							startotal = res.comment.score;
						}
						that.starTipsFun(score);
						that.selMarkStar.attr("startotal",startotal);
						that.selMarkStar.find("a").removeClass("highlight_mstb").attr("mark",0);
						that.selMarkStar.find("a:lt("+score+")").addClass("highlight_mstb").attr("mark",1);
						that.commentCont.removeClass("errorborder_tb").val(content);
						that.beyondWord.find("em").text(content.length);
	               }
	            })
	        }

			that.commentPen.show();
			that.commentCont.focus();
		});
	},
	selectStarFun:function(){ //选择评分
		var that = this;
		var strArea = that.selMarkStar;
		strArea.find("a").hover(function(){
			var _this = $(this);
			var idx = parseInt(_this.index())+1;
			that.starTipsFun(idx);
			strArea.find("a:lt("+idx+")").addClass("highlight_mstb");
		},function(){
			var _this = $(this);
			that.leaveStarFun(); //鼠标离开星星
		});

		strArea.find("a").bind("click",function(){
			var _this = $(this);
			var idx = _this.index();
				newIdx = parseInt(idx) + 1;
			strArea.attr("startotal",newIdx);
			strArea.find("a:lt("+newIdx+")").attr("mark","1").addClass("highlight_mstb");
			strArea.find("a:gt("+idx+")").attr("mark","0").removeClass("highlight_mstb");
		})
	},
	leaveStarFun:function(){ //鼠标离开评分星星
		var that = this;
		var strArea = that.selMarkStar;
		var totalStar = 0;
		strArea.find("a").each(function(){
			var _this = $(this);
			if(_this.attr("mark") == 1){
				totalStar += 1;
			}

			if(_this.attr("mark") != 1){
				_this.removeClass("highlight_mstb");
			}
		});

		that.starTipsFun(totalStar);
	},
	starTipsFun : function(starnum){
		var that = this;
		var text;
		switch (parseInt(starnum)){
			case 1 : 
				text = "讨厌" ;
				break;
			case 2 : 
				text = "不喜欢" ;
				break;
			case 3 : 
				text = "还可以" ;
				break;
			case 4 : 
				text = "很不错" ;
				break;
			case 5 : 
				text = "好极了" ;
				break;
			default :
				text = "";
				break;
		}	
		that.starTips.text(text);
	},
	//评论内容绑定事件
	commentContEvent : function(){
		var that = this;
		that.commentCont.bind("focus",function(){
			var _this = $(this);
			_this.removeClass("errorborder_tb").addClass("focusborder_tb");
		}).bind("blur",function(){
			var _this = $(this);
			_this.removeClass("focusborder_tb");
		}).bind("keyup blur change copy cut paste",function(){
			var _this = $(this);
			var len = $.trim(_this.val()).length;
			that.beyondWord.children("em").text(len);

			if(len > 2000){
				_this.addClass("errorborder_tb");
				that.beyondWord.addClass("errorbeyondword_cb");	
			}else{
				_this.removeClass("errorborder_tb");
				that.beyondWord.removeClass("errorbeyondword_cb");	
			}
		})
	},
	//提交评论
	commentBtnFun : function(){
		var that = this;
		var tAjax = false; //阻止ajax重覆提交
		that.commentBtn.unbind("click")
		that.commentBtn.bind("click",function(){
			var _this = $("this");
			var conVal = that.commentCont.val();
			_this.parent().find(".errortips").remove();

			isLoginAjax(); //判断用户是否登录
			if($("#isLogin").val() == 0){
				var errText = '评论前请先登录。';
				showTipsFun(errText,"login");
				return false;
			}

			if($("#isBuyHid").val() == 0){
				var errText = '您必须拥有此内容，才能评论。';
				showTipsFun(errText);
				return false;
			}

			if($.trim(conVal) == ""){
				that.commentCont.addClass("errorborder_tb");
				return false;
			}

			if($.trim(conVal).length > 2000){
				that.commentCont.addClass("errorborder_tb");
				that.beyondWord.addClass("errorbeyondword_cb");	
				return false;
			}

			var data = {};
			data.id = that.toolsId;
			data.score = that.selMarkStar.attr("startotal") || 5;
			data.content = conVal;
			
			if(!tAjax){
			 	tAjax = true;
			 	$.ajax({
	                type : "POST",
	                url : "/stuff/comment_add",
	                data : data,
	                dataType: "json",
	                success : function(res){ //{1：评论成功，0：评论失败}
	  					tAjax = false;
	               		if(res.res == 1){
	               			that.getCommentDateFun(that.initPage);
	               			that.selMarkStar.find("a").removeClass("highlight_mstb").removeAttr("mark");
	               			that.starTips.text("");
	               			that.commentCont.val("");
	               			that.beyondWord.find("em").text("0");

	               			$("body,html").scrollTop(that.commentModule.offset().top);

	               			that.commentPen.hide();
	               		}else{
	               			showTipsFun("评论失败");
	               		}
	               }
	            })
	        }
		})
	},
	//获取评论数据
	getCommentDateFun : function(num){
		var that = this;
		that.pageNum = num;

		var data = {};
		data.id = that.toolsId;
		data.page = that.pageNum;
		data.sort = that.comment_sort;

		$.ajax({
           type : "POST",
           url : "/stuff/comment_list",
           data : data,
           dataType: "json",
           success : function(res){
     			//var res = that.returnDataFun();
     			if(res.data.length == 0){
     				that.commentListBox.html("<li class='emptycomment'>暂无评论</li>");
     			}else{
     				 var ulHtml = that.renderCommentDataFun(res.data);
					that.commentListBox.html(ulHtml);
					that.showCommentPagerFun(res.total);
     			}
            }
        });
	},
	//定义评论的json数据
	returnDataFun : function(){
		var json = {
			res : 1,
			total : 20,
			data : [
				{title:'捕鱼达人1',score:'0',username:'张三',comment_time:'2015年2月23日',content:'更优秀的产品、更优质的服务。'},
				{title:'捕鱼达人2',score:'3.6',username:'张三',comment_time:'2015年2月23日',content:'更优秀的产品、更优质的服务。'},
				{title:'捕鱼达人2',score:'4.3',username:'张三',comment_time:'2015年2月23日',content:'更优秀的产品、更优质的服务。'}
			]
		}

		return json;
	},
	//渲染单条评论
	renderCommentDataFun : function(data){ 
		var that = this;
        var html = "";
        var criticLang = '评论人';
        for(var i = 0; i < data.length ; i++){
        	var starHtml = createStarHtmlFun(data[i].score);
            lihtml = '<li>'
					+'<div class="name_cmdec clearfix">'
					+'<span>评分：</span>'
					+'<p class="score-star clearfix">'+ starHtml +'</p>'
					+'</div>'
					+'<p class="text_cmdec">'+criticLang+'：'+data[i].username+'&nbsp;&nbsp;'+data[i].comment_time+'</p>'
					+'<p class="exp_cmdec">'+data[i].content+'</p>'
					+'</li>';
            html += lihtml;
        }
        return html;
	},
	showCommentPagerFun:function(total){ //评论显示分页
		var that = this;
        var pagecount = Math.ceil(total/that.pageSize); //共显示多少页
        var scrollPos = that.commentModule.offset().top;
        if(total <= that.pageSize){
            that.commentPager.hide();
        }else{
            that.commentPager.show();
            that.commentPager.pager({
            	scrollPos : scrollPos,
                pagenumber: that.pageNum,
                pagecount: pagecount,
                buttonClickCallback: function(idx){
                    that.getCommentDateFun(idx);
                }
            });
        }
	},

	/******************** 版本切换 ********************/
	versionCutFun : function(){
		var that = this;
		var $ver_cut_a = that.versionCut.find("a.active_vmdec");
		var $ver_cut_down = that.versionCut.find("ul.pulldown_vmdec");
		$ver_cut_a.unbind("click");
		$ver_cut_a.bind("click",function(){
			if($ver_cut_down.is(":hidden")){
	            that.versionCut.addClass("verpackup_mdec");
	        }else{
	            that.versionCut.removeClass("verpackup_mdec");
	        }

	        that.versionChangeFun();
		})	
	},

	versionChangeFun : function(){
		var that = this;
		var $ver_cut_a = that.versionCut.find("a.active_vmdec");
		var $ver_cut_down = that.versionCut.find("ul.pulldown_vmdec");
		var $ver_cut_down_a = $ver_cut_down.find("a");

		$ver_cut_down_a.unbind("click");
		$ver_cut_down_a.bind("click",function(){
			var _this = $(this);
			var ver_type = _this.attr("data-type"),	
				ver_name = _this.text();
			var cur_ver_type = $ver_cut_a.attr("data-type"),	
				cur_ver_name = $ver_cut_a.text();

			$ver_cut_a.attr("data-type",ver_type).find("i").text(ver_name);
			$("#download_form").find("input[name='type']").val(ver_type);
			that.versionCut.removeClass("verpackup_mdec");

			$ver_cut_down.prepend('<li><a href="javascript:void(0);" data-type="'+ cur_ver_type +'">'+ cur_ver_name +'</a></li>');
			_this.parent("li").remove();

			that.uploadViewFun(ver_type);
		});
	},

	versionCutHideFun : function(){
		var that = this;
		$('body').bind("click",function(event){
	        var event = window.event || event;
	        if(event.target){ 
	            if(!event.target.parentElement){
	                return;
	            }
	            var cla = event.target.parentElement.className;    
	        }else{
	             if(!event.srcElement.parentNode){
	                return;
	            }
	            var cla = event.srcElement.parentNode.className;
	        }


	        if(cla.indexOf("active_vmdec") < 0 && cla.indexOf("versioncut_mdec") < 0){
	            that.versionCut.removeClass("verpackup_mdec");
	        }
	    });
	},

	/******************** 历史版本下载 ********************/
	displayHistoryVerFun : function(){
		var that = this;
		that.displayHistoryVer.unbind("click");
		that.displayHistoryVer.bind("click",function(){
			var _this = $(this);
			if(that.historyVerDown.is(":hidden")){
				that.historyVerDown.slideDown();
				_this.html("收起<em></em>").addClass("dismoreintro");
			}else{
				that.historyVerDown.slideUp();
				_this.html("展开<em></em>").removeClass("dismoreintro");
			}
		});
	},
	historyVerDownFun : function(){
		var that = this;
		var down_tri = that.historyVerDown.find(".downbtn_hhmdec a");
		that.historyVerDown.children("li:last-child").addClass("lastli_hmdec"); //最后一个li去掉border
		down_tri.unbind("click");
		down_tri.bind("click",function(){
			var _this = $(this);
			var id = _this.attr("data-id");
			var type = _this.attr("data-type");

			isLoginAjax(); //判断用户是否登录
			if($("#isLogin").val() == 1){
				var data = get_content_url(id);
			    data = eval('('+data+')');
			    if(data.status == 0){
			        //console.log(data.info);
			        return false;
			    }

			    $('#download_form').attr('action',data.url);
			    $('#download_form').attr('target','_parent');
			    $('#download_form input[name="type"]').val(type);
			    $('#download_form').submit();
			}else{
				var errText = '友情提示：登录后才能下载';
				showTipsFun(errText,"login");

				return false;
			}
		});
	},

	/******************** 用户评分进度条 ********************/
	statGradeBarFun : function(){
		var that = this;
		var $bar_span = that.statGrade.find(".bar_srsmdec span");
		$bar_span.each(function(){
			var _this = $(this);
			var bar_percentage = _this.text();
			_this.css("width",bar_percentage);
		});
	},

	/******************** 评论列表排序 ********************/
	commentListSortFun : function(){
		var that = this;
		var $cur_a = that.commentListSort.find("a.active_smdec");
		var $sort_down = that.commentListSort.find("ul.pulldown_asmdec");
		$cur_a.unbind("click");
		$cur_a.bind("click",function(){
			if($sort_down.is(":hidden")){
	            that.commentListSort.addClass("packup_mdec");
	        }else{
	            that.commentListSort.removeClass("packup_mdec");
	        }

	        that.cutCommentListSortFun(); //切换评论列表排序
		})
	},
	cutCommentListSortFun : function(){
		var that = this;
		var $cur_a = that.commentListSort.find("a.active_smdec");
		var $sort_down = that.commentListSort.find("ul.pulldown_asmdec");
		var $cut_a = $sort_down.find("a");
		$cut_a.unbind("click");
		$cut_a.bind("click",function(){
			var _this = $(this);
			var sort_text = _this.text(),
				sort_value = _this.attr("data-sort");
			var cur_a_text = $cur_a.find("i").text(),
				cur_a_value = $cur_a.attr("data-sort");

			$cur_a.attr("data-sort",sort_value).find("i").text(sort_text);
			that.commentListSort.removeClass("packup_mdec");
			$sort_down.prepend('<li><a href="javascript:void(0);" data-sort="'+ cur_a_value +'">'+ cur_a_text +'</a></li>');
			_this.parent("li").remove();

			that.comment_sort = sort_value;
			that.getCommentDateFun(that.initPage); //获取评论数据
		});
	},

	hideSortPulldownFun : function(){
		var that = this;
		$('body').bind("click",function(event){
	        var event = window.event || event;
	        if(event.target){ 
	            if(!event.target.parentElement){
	                return;
	            }
	            var cla = event.target.parentElement.className;    
	        }else{
	             if(!event.srcElement.parentNode){
	                return;
	            }
	            var cla = event.srcElement.parentNode.className;
	        }


	        if(cla.indexOf("active_smdec") < 0 && cla.indexOf("sort_mdec") < 0){
	            $(".sort_mdec").removeClass("packup_mdec");
	        }
	    });
	}
}


/********** create star html **********/
// star : 传小返回的小数即可，例如3.5
function createStarHtmlFun(star){ 
	var that = this;
	var starnum = that.getRemarkFun(star);
	var fullHtml = starnum.fullNum != 0 ? returnItemStarHtmlFun(starnum.fullNum,0) : "";
	var halfHtml = starnum.halfNum != 0 ? returnItemStarHtmlFun(starnum.halfNum,1) : "";
	var emptyHtml = starnum.emptyNum != 0 ? returnItemStarHtmlFun(starnum.emptyNum,2) : "";
	var starHtml = "";
	starHtml += fullHtml;
	starHtml += halfHtml;
	starHtml += emptyHtml;

	return starHtml;
}

function returnItemStarHtmlFun(num,type){
	var itemHtml = '',clas;
	switch (type){
		case 0 :
			clas = 'full-star';
			break;
		case 1 :
			clas = 'half-star';
			break;
		default :
			clas = 'empty-star';
			break;
	}
	for(var i=0;i<num;i++){
		itemHtml += '<b class="single-star '+clas+'"></b>';
	}
	return itemHtml;
}

function getRemarkFun(starnum){ //获取评价星号的个数
	var starData = {};
	if(starnum <= 0.3){
		starData.fullNum = 0; starData.halfNum = 0;  starData.emptyNum = 5;
	}else if(0.3 < starnum && starnum <= 0.7){
		starData.fullNum = 0; starData.halfNum = 1;  starData.emptyNum = 4;
	}else if(0.7 < starnum && starnum <= 1.3){
		starData.fullNum = 1; starData.halfNum = 0;  starData.emptyNum = 4;
	}else if(1.3 < starnum && starnum <= 1.7){
		starData.fullNum = 1; starData.halfNum = 1;  starData.emptyNum = 3;
	}else if(1.7 < starnum && starnum <= 2.3){
		starData.fullNum = 2; starData.halfNum = 0;  starData.emptyNum = 3;
	}else if(2.3 < starnum && starnum <= 2.7){
		starData.fullNum = 2; starData.halfNum = 1;  starData.emptyNum = 2;
	}else if(2.7 < starnum && starnum <= 3.3){
		starData.fullNum = 3; starData.halfNum = 0;  starData.emptyNum = 2;
	}else if(3.3 < starnum && starnum <= 3.7){
		starData.fullNum = 3; starData.halfNum = 1;  starData.emptyNum = 1;
	}else if(3.7 < starnum && starnum <= 4.3){
		starData.fullNum = 4; starData.halfNum = 0;  starData.emptyNum = 1;
	}else if(4.3 < starnum && starnum <= 4.7){
		starData.fullNum = 4; starData.halfNum = 1;  starData.emptyNum = 0;
	}else if(4.7 < starnum && starnum <= 5){
		starData.fullNum = 5; starData.halfNum = 0;  starData.emptyNum = 0;
	}
	return starData;
}