/*search Page*/
function searchPage(){
    window.storeSearchWrap = new StoreSearchWrap({});
} 

function StoreSearchWrap(){
    this.searchApplyList = $("#searchApplyList");
	this.search = $("#Search");
	this.searchBtn = $("#searchBtn");

    this.searchCategoryName = $("#searchCategoryName");
    this.searchPrice = $("#searchPrice");
    this.rangePrice = $("#rangePrice"); //输入价格
    this.sureSearchBtn = $("#sureSearchBtn");

    this.searchSort = $("#searchSort"); //搜索面包屑
    this.searchCondition = $("#searchCondition");//搜索条件 
	this.searchResult = $("#searchResult"); //搜索结果
	this.searchState = $("#searchState"); //搜索状态 
    
    
	this.searchApplyList = $("#searchApplyList");
    this.showRecommendApply = $("#showRecommendApply");

    this.price_min = "";
    this.price_max = "";

	this.pager = $("#applyListPage");
    this.pageNum = 1; //第几页
    this.pageSize = 28; //每次获取多少条数据

	this.init();
}
StoreSearchWrap.prototype = {
	init : function(){
       // this.searchContFun();
		this.searchInitFun();	
        this.rangePriceFun();
        this.sureSearchFun();
        this.searchSortFun();
        this.priceCutFun();
	},
	//初始化
	searchInitFun : function(){
		var that = this;
		var urlData = that.getUrlDate();
		var classifyVal = urlData.classify;
		var searchVal = urlData.text; 

		if(searchVal != undefined && searchVal != ""){
			$("#searchCont").val(decodeURIComponent(searchVal));

            //$("#searchBtn").trigger("click");
		}

        var arg = "init";
        var num = 1; //默认第一页数据

        that.backSearchInitFun();
        that.getApplyListFun(num,arg);
	},
    //获取url带参数
	getUrlDate : function(){
		var that = this;
		var urlData = {};
		var search = window.location.search != "" ? window.location.search.split("?")[1] : "";
		var searchStr = (search != "") && (search != undefined) ? search.split("&") : '';

		if(searchStr.length > 0){
			for(var i=0;i<searchStr.length;i++){
				var splitStr = searchStr[i].split("=");
				var keyStr = splitStr[0];
				urlData[keyStr] = splitStr[1];
			}
		}

		return urlData;
	},
    //搜索btn事件
	searchContFun : function(){
		var that = this;
		that.searchBtn.unbind("click");
		that.searchBtn.bind("click",function(){
			var searchVal = that.search.find("input").val(),
				searchInitVal = that.search.find("input").attr("initval");
			var sCon = (searchVal == searchInitVal) ? "" : searchVal;

            var arg = "init";
            var num = 1; //默认第一页数据

            that.backSearchInitFun();
			that.getApplyListFun(num,arg);
		});
	},
     
    //搜索完成后的初始化状态
    backSearchInitFun : function(){
        var that = this;
        that.searchPrice.find("a").removeClass("curprice_ps");
        that.rangePrice.find("input").val("");
        that.searchSort.find("a").removeClass("current_slss");
        that.searchCategoryName.find("a").removeClass("curcategory_ps");

        that.price_min = "";
        that.price_max = "";
    },

    /*
        * 获取搜索列表
        * arg : {init：点击nav搜索按钮的时候,""：点击相关分类、价格、面包屑的时候} 
        * num : 分页页码
        * defPrice : 是否是自己定义的搜索
    */
	getApplyListFun : function(num,arg){
		var that = this;
        var arg = arg || "";
        var sCon = $("#searchCont").val() || "";
		var sort = that.returnCurSort() || "default"; //按XX字段排序(download_下载,comment_评论,default_默认,new_最新上架)
        var category = that.searchCategoryName.find("a.curcategory_ps").attr("category-id") || "0";
        
		that.pageNum = num;


        if(arg == "init"){
            that.searchLoadingFun(sCon,num);
        }

		var data = {};
    	data.version =  this.vers;
    	data.language =  this.lang;
    	data.category =  category;
    	data.keyword =  sCon; //sCon
        data.price_min = that.price_min;
        data.price_max = that.price_max;
        data.page = that.pageNum;
        data.sort = sort;

        var search_post_url = "search/ajax_search/"+ that.pageNum +".html";

        //百度统计
        baiDuStatisticsPageviewFun(search_post_url);

    	$.ajax({
           type : "POST",
           url : search_post_url,
           data : data,
           async: false,
           dataType: "json",
           success : function(res){
     			that.searchState.hide().html("");
                if(res.res == "success"){ //搜索成功
                    that.searchCondition.show();

                	if(res.total == 0){
                        that.pager.hide();
                        that.searchSort.parent().hide();
                        that.searchEmptyFun(sCon);

                        if(arg == "init"){
                            if(res.category_data == null) return false;

                            that.searchCategoryName.html(that.getCategoryFun(res.category_data));
                            that.searchCategoryName.find("a:eq(0)").addClass("curcategory_ps");
                            that.searchPrice.find("a:eq(0)").addClass("curprice_ps");
                            that.categoryCutFun();
                        }
                        that.searchApplyList.hide().html("");
                        that.showRecommendApply.show();
            		}else{
                        that.searchSort.parent().show();
                        if(arg == "init"){
                            that.searchSuccessFun(sCon,res.total);
                            if(res.category_data == null) return false;

                            that.searchCategoryName.html(that.getCategoryFun(res.category_data));
                            that.searchCategoryName.find("a:eq(0)").addClass("curcategory_ps");
                            that.searchPrice.find("a:eq(0)").addClass("curprice_ps");
                            that.categoryCutFun();
                        }

                        that.searchApplyList.show();
            			that.searchApplyList.createApplyList({
                            json_data : res.data,
                            keyword : sCon
                        });

                        that.showRecommendApply.hide(); //隐藏推荐列表
                        that.showPagerListFun(res.total); //分页
            		}

                    window.payWrap = new PayWrap({});
                    operaBtnFun("applyliststyle100"); //下载、购买操作按钮
                }else{ //搜索失败
                    if(arg == "init"){
                       that.searchEmptyFun(sCon);
                	   that.searchFailFun(sCon);
                       that.showRecommendApply.show();
                    }
                }
           }
        });
	},
    //获取搜索内容的相关分类
    getCategoryFun : function(cate_data){
        var that = this;
        var all_lang = "所有";
        var html = '<a href="javascript:void(0);" category-id="0">'+ all_lang +'</a>';
        for(var i=0;i<cate_data.length;i++){
            html += '<a href="javascript:void(0);" category-id="'+ cate_data[i].id +'">'+ cate_data[i].name +'</a>';
        }

        return html;
    },
    //相关分类的切换
    categoryCutFun : function(){
        var that = this;
        var aTri = that.searchCategoryName.find("a");
        aTri.unbind("click");
        aTri.bind("click",function(){
            var _this = $(this);
            var num = 1;

            if(_this.hasClass("curcategory_ps")) return false;
            aTri.removeClass("curcategory_ps");
            _this.addClass("curcategory_ps");

            that.getApplyListFun(num);
        });
    },
    //价格切换
    priceCutFun : function(){
        var that = this;
        var priceTri = that.searchPrice.find("a");
        priceTri.unbind("click");
        priceTri.bind("click",function(){
            var _this = $(this);
            var num = 1;

            if(_this.hasClass("curprice_ps")) return false;
            priceTri.removeClass("curprice_ps");
            _this.addClass("curprice_ps");
            that.rangePrice.find("input").val("");

            var a_idx = _this.index() + 1,
                a_len = priceTri.length;
            if(a_idx > 1 && a_idx < a_len){
                that.price_min = _this.text().split("-")[0];
                that.price_max = _this.text().split("-")[1];
            }else if(a_idx == a_len){
                that.price_min = "200";
                that.price_max = "";
            }else{
                that.price_min = "";
                that.price_max = "";
            }

           that.getApplyListFun(num);
        })
    },

    //自定义价格
    rangePriceFun : function(){
        var that = this;
        var $input = that.rangePrice.find("input");
        $input.bind("keypress",function(event){
            var event = window.event || event;
            var code = event.keyCode || event.charCode;

            if (code && (code < 48 || code > 57) && code != 8 && code != 37 && code != 39) {
                event.preventDefault()
            }
        }).bind("blur",function(){
            var _this = $(this);
            var val = _this.val();
            var val1 = val.match(/^0+/) ? val.replace(/^0+/g,'0') : val;
            var val2 = val1.match(/^0+[1-9]+/) ? val1.replace(/^0+/g,'') : val1; //格式化整数前面的多个0

             _this.val(val2);
        });
    },

    //自定义价格确定
    sureSearchFun : function(){
        var that = this;
        that.sureSearchBtn.unbind("click");
        that.sureSearchBtn.bind("click",function(){
            var num = 1; //默认第一页数据
            var arg = "";
            var defPrice = true;

            that.searchPrice.find("a").removeClass("curprice_ps");

            var price_min_val = that.rangePrice.find("input[type='text']:eq(0)").val(),
                price_max_val = that.rangePrice.find("input[type='text']:eq(1)").val();

            var price_min_new_val = (price_min_val == "") ? "" : parseInt(price_min_val);
            var price_max_new_val = (price_max_val == "") ? "" : parseInt(price_max_val);

            that.price_min = (price_min_new_val !== "" && price_max_new_val !== "" && price_min_new_val > price_max_new_val) ? price_max_new_val : price_min_new_val;
            that.price_max = (price_min_new_val !== "" && price_max_new_val !== "" && price_min_new_val > price_max_new_val) ? price_min_new_val : price_max_new_val;

            that.getApplyListFun(num,arg,defPrice);
        });
    },

    //排序切换
    searchSortFun : function(){
        var that = this;
        var sortTri = that.searchSort.find("a");
        that.searchSort.find("a:eq(0)").addClass("current_slss");
        sortTri.unbind("click");
        sortTri.bind("click",function(){
            var _this = $(this);
            var num = 1;

            if(_this.hasClass("current_slss")) return false;
            sortTri.removeClass("current_slss");
            _this.addClass("current_slss");

           that.getApplyListFun(num);
        });
    },

    returnCurSort : function(){
        var that = this;
        var sort;
        var idx = that.searchSort.find("a.current_slss").index();
        switch (idx){
            case 0:
                sort = "default";
                break;
            case 2:
                sort = "new";
                break;
            case 4:
                sort = "download";
                break;
            case 6:
                sort = "comment";
                break;
            default:
                sort = "default";
                break;
        }

        return sort;
    },

	//正在搜索 {num:分页的当前页码}
	searchLoadingFun : function(sCon,num){ 
		var that = this;
    	if(num == 1){
            var searchLang = '<span class="searchload_sc">正在努力搜索中，请稍后……</span>';
        }else{
            var searchLang = '<span class="searchload_sc">正在努力加载中，请稍后……</span>';
        }

    	that.pager.hide();
    	that.searchApplyList.html("");
    	that.searchState.show().html(searchLang);
	},
	//搜索成功_HTML
	returnSuccessHtmlFun:function(){
    	var that = this;
    	var strLang = '<p>包含&nbsp;&nbsp;“<span class="searchname_rs">...</span>”&nbsp;&nbsp;的搜索结果，共计<span class="seachtotal_rs inittotal_rs">...</span>个</p>';

    	return strLang;
    },
	//搜索成功
    searchSuccessFun : function(sCon,searchNum){ 
    	var that = this;
    	that.searchState.hide();
    	that.searchResult.show().html(that.returnSuccessHtmlFun());
        that.searchResult.find(".searchname_rs").text(sCon);
        that.searchResult.find(".seachtotal_rs").removeClass("inittotal_rs").text(searchNum);
    },
    //搜索内容为空_HTML
    returnEmptyHtmlFun:function(){
    	var that = this;
    	var failStrLang = '<p class="searchempty_sc"><b></b><span>很抱歉，没有找到与&nbsp;&nbsp;“<i class="searchname_ssc">...</i>”&nbsp;&nbsp;相关的内容</p>';

        return failStrLang;
    },
    //搜索内容为空
    searchEmptyFun : function(sCon){ 
    	var that = this;
    	that.searchState.show().html(that.returnEmptyHtmlFun());
    	that.searchState.find(".searchname_ssc").text(sCon);
    },
    //搜索失败
    searchFailFun : function(sCon){
    	var that = this;
    	var failLang = '<p class="searchfail_sc"><b></b><span>搜索失败,请重试！</span></p>';
        that.searchState.show().html(failLang);
    },
    //搜索页面分页
    showPagerListFun:function(total){
        var that = this;
        var pagecount = Math.ceil(total/that.pageSize); //共显示多少页

        if(total <= that.pageSize){
            that.pager.hide();
        }else{
            that.pager.show();
            that.pager.pager({
                pagenumber: that.pageNum,
                pagecount: pagecount,
                buttonClickCallback: function(idx){
                    that.getApplyListFun(idx);
                }
            });
        }
    }
}