(function($){
	$.fn.createApplyList = function(options){
		var defaults = {
			json_data : ""
		};
		var options = $.extend(defaults,options,{});

		return this.each(function(){
			var $this = $(this);

			if(options.json_data == "" && options.json_data == undefined){
				return false;	
			}

			$this.html(returnApplyHtmlFun());
		})

		//渲染列表应用list
		function returnApplyHtmlFun(){
	        var keyword = options.keyword || ""; //搜索keyword高亮
	        var str = "";
	        var data = options.json_data;
	        for(var i = 0; i < data.length ; i++){
	            var detailsUrl = "stuff/show/"+data[i].id+".html";
	            var isNew = data[i].isnew || 0,
	                isHot = data[i].hot || 0,
	                bHtml;

	            //new、hot标签
	            if(isNew == 1 && isHot == 0){
	                bHtml = '<b class="new_a"></b>';
	            }else if(isNew == 0 && isHot == 1){
	                bHtml = '<b class="hot_a"></b>';
	            }else if(isNew == 1 && isHot == 1){
	                bHtml = '<b class="hot_a"></b>';
	            }else{
	                bHtml = '';
	            }

	            //icon url
	            if(/^\/uploads/.test(data[i].icon)){
	                var api_url = $("#api_url").val() || "https://launcher.cocos.com"
	                var icon_url = api_url + data[i].icon;
	            }else{
	                var icon_url = data[i].icon;
	            }

	            //每排最后一个li添加class,去掉最后一个li的margin-right
	            var row_lastli = "";
	            var reg = /^\d*$/;
	            if(reg.test((i+1)/7)){
	                row_lastli = "rowlastli";
	            }

	            //类别
	            var cat_name = (data[i].cat_name != null) ? data[i].cat_name : "";
            	var cat_id = data[i].category;

	            //apply name截断
	            var res_apply_name = (strLength(data[i].title) > 26) ? strCut(data[i].title,26)+"..." : data[i].title;
	            var res_apply_name1 = res_apply_name;
	            if(keyword != ""){
		            var reg_name = "/"+keyword+"/ig";
		            var res_apply_name1 = res_apply_name.replace(eval(reg_name),"<b>"+ keyword +"</b>");
	            }

	            //btn
	            var btn_html = "";
	            if(data[i].attribute == "link"){//{link:打开链接,source:立即下载}
	                btn_html = '<a data-toolsid="'+ data[i].id +'" data-apply-name="'+ data[i].title +'" data-opera-state="link" class="btns btn-gray candownapply" href="javascript:void(0);" onclick="_hmt.push([\'_trackEvent\', \'web_search\', \'web_search_tools_download\', \''+ data[i].title +'\']);">'
	                     + '<i>免费</i>'
	                     + '</a>';
	            }else{
	            	if(data[i].payment == 0){ //{0:免费,1:付费}
	            		btn_html = '<a data-toolsid="'+ data[i].id +'" data-apply-name="'+ data[i].title +'" data-opera-state="download" class="btns btn-gray candownapply" href="javascript:void(0);" onclick="_hmt.push([\'_trackEvent\', \'web_search\', \'web_search_tools_download\', \''+ data[i].title +'\']);">'
		                     + '<i>免费</i>'
		                     + '</a>';
	            	}else{
		                if(data[i].if_buy == 0){ //{0:购买,1:已购买}
		                    btn_html = '<a data-pricenum="'+ data[i].price_cn +'" data-toolsid="'+ data[i].id +'" data-apply-name="'+ data[i].title +'" data-opera-state="buy" class="btns btn-gray canbuyapply" href="javascript:void(0);" onclick="_hmt.push([\'_trackEvent\', \'web_search\', \'web_search_tools_download\', \''+ data[i].title +'\']);">'
		                     + '<i>￥'+ data[i].price_cn +'</i>'
		                     + '</a>';
		                }else{
		                    btn_html = '<a data-toolsid="'+ data[i].id +'" data-apply-name="'+ data[i].title +'" data-opera-state="download" class="btns btn-gray candownapply" href="javascript:void(0);" onclick="_hmt.push([\'_trackEvent\', \'web_search\', \'web_search_tools_download\', \''+ data[i].title +'\']);">'
		                     + '<i>已购买</i>'
		                     + '</a>';
		                }
	                }
	            }

	            lihtml = '<li class="applyitem '+ row_lastli +'">'
	                     + bHtml
	                     + '<a href="'+ detailsUrl +'" target="_blank" class="applydis_a" title="'+ data[i].title +'">'
	                     + '<b class="markbg_aa"></b>'
	                     + '<span class="icon_aa"><img src="'+ icon_url +'" alt="'+ data[i].title +'"></span>'
	                     + '<span class="name_aa">'+ res_apply_name1 +'</span>'
	                     + '</a>'
	                     + '<a href="stuff/category/'+ cat_id +'/score_count.html" class="applycat_a">'+ cat_name +'</a>'
	                     + btn_html
	                     + '</li>'
	            str += lihtml;
	        }

	        return str;
	    }

		/********* 字符串操作 **********/ 
		//区别中英文，获取字符串的实际长度
		function strLength(str){ 
			var rea_L = 0,charCode;
			for(var i = 0 ; i < str.length ; i++){
				charCode = str.charCodeAt(i);
				if(charCode >= 0 && charCode <=128){
					rea_L ++;
				}else{
					rea_L += 2;
				}
			}
			return rea_L;
		}

		//中英文截断
		function strCut(str,len){
			if(!str || !len){
				return "";
			}
			var a = 0,tempStr = "";//临时字符串
			for(var i = 0 ; i < str.length ; i++){
				(str.charCodeAt(i) > 255) ? a+=2 :a++;
				if(a > len){
					return tempStr; //如果增加计数后长度大于限定长度，就直接返回临时字符串
				}
				tempStr += str.charAt(i);
			}
			return str;
		}
	}
})(jQuery);