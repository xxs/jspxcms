function PayWrap(){
	this.tri_source = null; //下载、购买触发源
	this.tools_id = null; //tools id
	this.balance = null; //余额

	this.candownapply = $(".candownapply"); //立即下载
	this.canbuyapply = $(".canbuyapply"); //立即购买

	this.bugGoogsDialog = $("#bugGoogsDialog"); //购买商品
	this.dealDetails = $("#dealDetails");
	this.toolsNameHide = $("#toolsNameHide");
	this.toolsPrice = $("#toolsPrice");
	this.Balance = $("#Balance"); //用户余额
	this.goRecharge = $("#goRecharge");//去充值 
	this.sureBuyBtn = $("#sureBuyBtn"); //立即购买
	this.balanceTips = $("#balanceTips"); //余额不够时提示
	this.copyRightType = $("#copyRightType"); //授权类型
	this.btnGroup = $("#btnGroup");


	this.downTimesLimitDialog = $("#downTimesLimitDialog"); //下载次数限制
	this.promptText = $("#promptText");
	this.immediatelySub = $("#immediatelySub");
	this.downLoadCocos = $("#downLoadCocos");

	this.AuthorizeDialog = $("#AuthorizeDialog"); //授权信息dialog
	this.copyrightSel = $("#copyrightSel");
	this.copyrightUrl = $("#copyrightUrl");
	this.havaRead = $("#havaRead");


	this.init();
} 
PayWrap.prototype = {
	init : function(){
		this.initBuyDialogFun(); //添加购买层
		this.initDownTimesDialogFun(); //下载次数提示
		this.initAuthorizeDialogFun(); //授权信息提示

		this.clickBuyFun(); //点击购买
		this.clickDownFun(); //点击立即下载
	},
	//获取下载链接
	getDownLinkFun : function(target){ 
		var that = this;
		var data = get_content_url(that.tools_id);
		if(data == ""){
			return false;
		}

        data = eval('('+data+')');
        if(data.status == 0){
            that.showDownTimesDialogFun("unlimit");
            return false;
        }
        var time = parseInt(data.times);
        if(time == 5 || time == 8){
        	that.showDownTimesDialogFun("limit");
        }else{
        	that.openLinkFun(data.url,target);
        }        
	},
	//获取打开链接
    getOpenLinkFun : function(target){
    	var that = this;
    	var data = get_content_url(that.tools_id);
    	if(data == ""){
			return false;
		}

        data = eval('('+data+')');
        window.open(data.url);
    },
    //提交form表单。打开获取到的链接
    openLinkFun : function(url,target){   
        $('#download_form').attr('action',url);
        $('#download_form').attr('target',target);
        $('#download_form').submit();
    },
	clickDownFun : function(){ //点击立即下载的时候判断用户是否登录
		var that = this;
		that.candownapply.unbind("click");
		that.candownapply.bind("click",function(){
			var _this = $(this);
			var state = _this.attr("data-opera-state");
			
			that.tri_source = _this;
			that.tools_id = _this.attr("data-toolsid") || 0;

			if(state == "download"){
				isLoginAjax(); //判断用户是否登录
				if($("#isLogin").val() == 1){
					that.showAuthorizeDialogFun("download"); //授权信息
				}else{
					var errText = '友情提示：登录后才能下载';
					showTipsFun(errText,"login");

					return false;
				}
			}else if(state == 'link'){
                isLoginAjax(); //判断用户是否登录
                if($("#isLogin").val() == 1){
                	that.showAuthorizeDialogFun("openlink"); //授权信息
                }else{
                    var errText = '友情提示：登录后才能访问';
                    showTipsFun(errText,"login");

                    return false;
                }
            }

		});
	},
	clickBuyFun : function(){
		var that = this;
		that.canbuyapply.unbind("click");
		that.canbuyapply.bind("click",function(){
			var _this = $(this);
			var state = _this.attr("data-opera-state");

			that.tri_source = _this;
			that.tools_id = _this.attr("data-toolsid") || 0;
			
			if(state == "buy"){
				isLoginAjax(); //判断用户是否登录
				if($("#isLogin").val() == 1){
					that.showAuthorizeDialogFun("buy"); //授权信息
				}else{
					var errText = '友情提示：登录后才能购买';
					showTipsFun(errText,"login");
				}
			}
		})
	},
	getBalanceAjax : function(){
		var that = this;
		var data = {};

		$.ajax({
			type : "POST",
			url : "/order/balance",
			data : data,
			dataType: "json",
			success : function(res){ //res:{1:成功，0:失败}
				//var res = {res:1,balance:0.03}
			 	if(res.res == 1){
			 		that.balance = res.balance;
			 		that.getCopyRightFun();
				}else{
					var errText = '请求异常。';
					showTipsFun(errText);
		 		}
			}
	   })
	},

	//获取授权信息（独享、共享）、及购买人数
	getCopyRightFun : function(){
		var that = this;
		var data = {};
		data.tools_id = that.tools_id;

		$.ajax({
			type : "POST",
			url : "jsapi/right_type.html",
			data : data,
			dataType: "json",
			success : function(res){ //res:{1:成功，0:失败}
				if(res.res == 1){
					var right_type = res.data.right_type || 0; //版权类型 1：独享 0：共享
					var buy_times = res.data.buy || 0; //购买次数

					that.showBuyDialogFun(right_type,buy_times);
				}else{
					var errText = '请求异常。';
					showTipsFun(errText);
				}
				
			}
	   })
	},

	/****************************** 购买商品diaglog ******************************/
	returnBuyHtmlFun : function(){ //购买商品html
		var that = this;
		var lang_title = '购买商品',
			lang_goodsname = '商品名',
			lang_unit = '元',
			lang_use = '使用余额',
			lang_recharge = '我要充值',
			lang_btn = '立即购买';
		return  '<div class="outsidedialog" id="bugGoogsDialog">'
                +'<div class="dialog buygoodsdialog">'
			    +'<div class="headerdialog">'
			    +'<h4>'+ lang_title +'</h4>'
			    +'<a href="javascript:void(0);" class="close"></a>'
			    +'</div>'
			    +'<div class="bodyerdialog">'
				+'<div class="tradedetails_b clearfix">'
				+'<input type="hidden" id="toolsNameHide"/>'
				+'<label class="name_tb">'+ lang_goodsname +'：</label>'
				+'<span class="spec_tb"></span>'
				+'<span class="money_tb clearfix"><i id="toolsPrice"></i>'+ lang_unit +'</span>'
				+'</div>'
				+'<div class="usebalance_db clearfix">'
				+'<label for="useBalance">'+ lang_use +'：</label>'
				+'<span id="Balance"></span> '
				+'<i>'+ lang_unit +'</i>'
				+'<a id="goRecharge" href="https://open.cocos.com/charge" class="gorecharge_udb" target="_blank">'+lang_recharge+'</a>'
				+'</div>'
				+'</div>'
				+'<div class="footerdialog footerlinedialog">'
				+'<div class="clearfix">'
				+'<div class="buytimes_f" id="copyRightType">'
				+'</div>'
				+'<div class="opear_f">'
				+'<div class="btngroup_f clearfix" id="btnGroup">'
				+'<a href="javascript:void(0);" class="surebtn_bf" id="sureBuyBtn">'+ lang_btn +'</a>'
				+'</div>'
				+'<span class="tips_bf" id="balanceTips">余额不足，请充值</span>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>'
                +'</div>';
	},
	initBuyDialogFun : function(){ //初始化购买的dialog
		var that = this;
		if(that.bugGoogsDialog.length <= 0){
			$('body').append(that.returnBuyHtmlFun());
			that.bugGoogsDialog = $("#bugGoogsDialog"); //购买商品
			that.dealDetails = $("#dealDetails");
			that.toolsNameHide = $("#toolsNameHide");
			that.toolsPrice = $("#toolsPrice");
			that.Balance = $("#Balance"); //用户余额
			that.goRecharge = $("#goRecharge");//去充值 
			that.sureBuyBtn = $("#sureBuyBtn"); //立即购买
			that.balanceTips = $("#balanceTips"); //余额不够时提示
			this.copyRightType = $("#copyRightType"); //授权类型
			this.btnGroup = $("#btnGroup");
		}
	},
	showBuyDialogFun : function(rightType,buyTimes){ //显示购买商品的dialog
		var that = this;
		var balance = parseFloat(that.balance).toFixed(2) || 0.00, //用户余额
			toolsprice = parseFloat(that.tri_source.attr('data-pricenum')) || 0, //app 的价格appName;
			appName = that.tri_source.attr("data-apply-name");

		that.toolsNameHide.val(appName);
		that.bugGoogsDialog.find(".spec_tb").text(that.toolsNameHide.val());
		that.toolsPrice.text(toolsprice);
		that.Balance.text(that.balance);
		that.balanceTips.hide();

		var cont = "";
		if(rightType == 1){
			cont += '<p><i>*</i>此内容作者设置为独享版权，只有1人可购买！</p><p>当前购买数：<b>'+ buyTimes +'</b></p>';

			if(buyTimes == 1){
				that.btnGroup.html('<a href="javascript:void(0);" class="disablebtn_bf">已被买走</a>');
			}
		}else{
			cont += '<p><i>*</i>此内容为共享版权内容</p><p>当前购买数：<b>'+ buyTimes +'</b></p>';
		}

		that.copyRightType.html(cont);
		

		showShadowFun();
		that.bugGoogsDialog.show();//显示购买人商品的弹框
		that.closeBuyDialogFun(toolsprice); //关闭
		that.goRechargeFun(); //去充值 
		that.sureBuyFun(toolsprice);//立即购买
	},
	floatFormatSubFun : function(arg1,arg2){ //float浮点型 精度计算
	    var r1,r2,m,n;
	    var arg2 = arg2 || "0.00";
	    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	    m=Math.pow(10,Math.max(r1,r2));

	    //last modify by deeka
	    //动态控制精度长度
	    n=(r1>=r2)?r1:r2;

    	return ((arg1*m-arg2*m)/m).toFixed(n);
	},
	hideBuyDialogFun : function(){ //隐藏购买商品的dialog
		var that = this;
		hideShadowFun();
		that.bugGoogsDialog.hide();
	},
	closeBuyDialogFun : function(){ //关闭购买商品的dialog
		var that = this;
		var $_close = that.bugGoogsDialog.find(".close");
		$_close.bind("click",function(){
			that.hideBuyDialogFun();
		});
	},
	goRechargeFun : function(){
		var that = this;
		that.goRecharge.unbind("click");
		that.goRecharge.bind("click",function(){
			that.hideBuyDialogFun();
		});
	},
	//立即购买
	sureBuyFun : function(toolsprice){
		var that = this;
		var $_tAjax = false; //阻止立即购买重复提交

		that.sureBuyBtn.unbind("click");
		that.sureBuyBtn.bind("click",function(){
			var _this = $(this);
			var tool_id = that.tools_id;

			that.balanceTips.hide();
			if(that.balance < toolsprice){ 
				that.balanceTipsShowFun(); //余额不足，请充值 提示
			}else{ //余额购买_ajax提交就可以
				var data = {};
				data.tool_id = that.tools_id;
				data.price = that.toolsPrice.text();

				if(!$_tAjax){
					$_tAjax = true;
					$.ajax({
					  type : "POST",
					  async : false,
					  url : "/order/balance_pay",
					  data : data,
					  dataType: "json",
					  success : function(res){ //res:{1:成功，0:失败}
					  		$_tAjax = false;
				 			that.hideBuyDialogFun();
				 			if(res.res == 1){
								window.location.href = "/stuff/show/"+ tool_id +".html";
							}else{
								var lang_tips = '购买失败';
								showTipsFun(lang_tips);
							}
					  }
					})
				}
			}
		});
	},
	//余额不足，请充值 提示
	balanceTipsShowFun : function(){
		var that = this;
		that.balanceTips.stop().show();
	},


	/****************************** 下载次数提示 ******************************/ 
	returnDownTimesHtmlFun : function(){
		var that = this;
		return  '<div class="outsidedialog" id="downTimesLimitDialog">'
                +'<div class="dialog downtimeslimitdialog">'
			    +'<div class="headerdialog">'
			    +'<h4>提示</h4>'
			    +'<a href="javascript:void(0);" class="close"></a>'
			    +'</div>'
			    +'<div class="bodyerdialog">'
				+'<p class="text_d" id="promptText">上传原创内容到Cocos商店审核通过，即可获取无限下载次数。</p>'
				+'<div class="opera_d clearfix">'
				+'<p class="btn_od">'
				+'<a href="javascript:void(0);" class="immediately_od" id="immediatelySub">立即提交内容</a>'
				+'<span class="or_od">或者</span>'
				+'<a href="javascript:void(0);" class="downcocos_od" id="downLoadCocos">下载安装Cocos无限下载</a>'
				+'</p>'
				+'</div>'
				+'</div>'
				+'<div class="footerdialog">'
				+'<p class="msg_f">您分享的内容也许会造福非常多在游戏开发第一战线的兄弟。</p>'
                +'</div>';
	},
	initDownTimesDialogFun : function(){
		var that = this;
		if(that.downTimesLimitDialog.length <= 0){
			$('body').append(that.returnDownTimesHtmlFun());
			that.downTimesLimitDialog = $("#downTimesLimitDialog");
			that.promptText = $("#promptText");
			that.immediatelySub = $("#immediatelySub");
			that.downLoadCocos = $("#downLoadCocos");
		}
	},
	showDownTimesDialogFun : function(arg){
		var that = this;
		var prompt_text,btn1_text,btn1_url,btn2_text,btn2_url;

		var store_submit_cont_url = "https://open.cocos.com/store/add",
			install_cocos_url = "https://www.cocos.com/download/";

		if(arg == "unlimit"){ //无权限下载
			prompt_text = "上传原创内容到Cocos商店审核通过，即可获取无限下载次数。";
            btn1_text = "立即提交内容";
            btn2_text = "下载安装Cocos无限下载";
            btn1_url = store_submit_cont_url;
            btn2_url = install_cocos_url;

            that.immediatelySub.attr({"href":btn1_url,"target":"_blank"}).text(btn1_text);
		}else{
			prompt_text = "上传原创内容到Cocos商店获取更多积分奖励！";
            btn1_text = "立即下载";
            btn2_text = "立即提交内容";
            btn1_url = "javascript:void(0);";
            btn2_url = store_submit_cont_url;

            that.immediatelySub.attr({"href":btn1_url,"target":""}).text(btn1_text);
		}
		showShadowFun(); //显示遮罩
		that.downTimesLimitDialog.show();
		that.promptText.text(prompt_text);
		that.downLoadCocos.attr({"href":btn2_url,"target":"_blank"}).text(btn2_text);
		that.immediatelyDownFun(); //立即下载
		that.closeDownTimesDialogFun();
	},
	immediatelyDownFun : function(){
		var that = this;
		var $a_tri = that.downTimesLimitDialog.find(".btn_od a");
		$a_tri.unbind("click");
		$a_tri.bind("click",function(){
			var _this = $(this);
			that.hideDownTimesDialogFun();

			if(_this.attr("href") == "javascript:void(0);"){
				var data = get_content_url(that.tools_id);
       			data = eval('('+data+')');
       			that.openLinkFun(data.url,"_parent");
			}
		});
	},
	hideDownTimesDialogFun : function(){ //隐藏购买商品的dialog
		var that = this;
		hideShadowFun();
		that.downTimesLimitDialog.hide();
	},
	closeDownTimesDialogFun : function(){ //关闭购买商品的dialog
		var that = this;
		var $_close = that.downTimesLimitDialog.find(".close");
		$_close.bind("click",function(){
			that.hideDownTimesDialogFun();
		});
	},

	/****************************** 授权信息提示 ******************************/ 
	returnAuthorizeHtmlFun : function(){
		var that = this;
		return  '<div class="outsidedialog" id="AuthorizeDialog">'
                +'<div class="dialog authorizedialog">'
			    +'<div class="headerdialog">'
			    +'<h4>提示</h4>'
			    +'<a href="javascript:void(0);" class="close"></a>'
			    +'</div>'
			    +'<div class="bodyerdialog">'
			    +'<div class="copyright_b clearfix">'
			    +'<label class="sel_cb" id="copyrightSel">'
			    +'<span></span>'
			    +'<input type="checkbox" id="copyRightChecked"/>'
			    +'</label>'
			    +'<label for="copyRightChecked">该内容作者 需要您详细阅读 “<a href="javascript:void(0)" id="copyrightUrl" target="_blank">版权要求</a>” 后才能购买(下载)</label>'
			    +'</div>'
				+'</div>'
				+'<div class="footerdialog">'
				+'<p class="btngroup_f">'
			    +'<a id="havaRead" class="surebuybtn_bf" href="javascript:void(0);">我已经阅读并同意</a>'
			    +'</p>'
			    +'</div>'
			    +'</div>'
			    +'</div>'
	},
	initAuthorizeDialogFun : function(){
		var that = this;
		if(that.AuthorizeDialog.length <= 0){
			$('body').append(that.returnAuthorizeHtmlFun());
			that.AuthorizeDialog = $("#AuthorizeDialog");
			this.copyrightSel = $("#copyrightSel");
			this.copyrightUrl = $("#copyrightUrl");
			this.havaRead = $("#havaRead");
		}
	},
	showAuthorizeDialogFun : function(state){
		var that = this;
		var data = {};

		$.ajax({
			type : "POST",
			url : "jsapi/get_copyright_info/"+ that.tools_id +".html",
			data : data,
			dataType: "json",
			success : function(res){
			 	if(res.right_agree == 1){ //需要授权信息
			 		showShadowFun(); //显示遮罩
					that.AuthorizeDialog.show();
					that.copyrightSel.find("span").hide();
					that.copyrightSel.find("input[type='checkbox']").prop("checked",false);

					that.copyrightUrl.attr("href",res.copy_right);
					that.closeAuthorizeDialogFun();
					that.copyRightSelFun();
					that.havaReadFun(state);
			 	}else{
			 		if(state == "buy"){
			 			that.getBalanceAjax();
			 		}else if(state == "download"){
			 			that.getDownLinkFun('_parent');
			 		}else if(state =="openlink"){
                    	that.getOpenLinkFun('_blank');
			 		}
			 	}
			}
	    })
	},
	//同意授权
	copyRightSelFun : function(){
		var that = this;
		that.copyrightSel.find("input").change(function(){
			var _this = $(this);
			if(_this[0].checked){
				that.copyrightSel.find("span").hide();
			}
		})
	},
	//我已经阅读并同意授权
	havaReadFun : function(state){
		var that = this;
		that.havaRead.unbind("click");
		that.havaRead.bind("click",function(){
			if(!that.copyrightSel.find("input[type='checkbox']").is(":checked")){
				that.copyrightSel.find("span").show();
				return false;
			}else{
				hideShadowFun(); //显示遮罩
				that.AuthorizeDialog.hide();
				if(state == "buy"){
		 			that.getBalanceAjax();
		 		}else if(state == "download"){
		 			that.getDownLinkFun('_parent');
		 		}else if(state =="openlink"){
                	that.getOpenLinkFun('_blank');
		 		}
			}
		});
	},
	hideAuthorizeDialogFun : function(){ //隐藏购买商品的dialog
		var that = this;
		hideShadowFun();
		that.AuthorizeDialog.hide();
	},
	closeAuthorizeDialogFun : function(){ //关闭购买商品的dialog
		var that = this;
		var $_close = that.AuthorizeDialog.find(".close");
		$_close.bind("click",function(){
			that.hideAuthorizeDialogFun();
		});
	}
}

