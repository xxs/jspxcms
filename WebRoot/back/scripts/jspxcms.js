Cms={};
Cms.check = function(name, checked) {
	$("input:checkbox[name="+name+"]").each(function() {
		this.checked=checked;
	});
};
Cms.checkeds = function(name) {
	return $("input:checkbox:checked[name="+name+"]").length;
};
Cms.moveTop = function(name,nested) {
	nested=nested||2;
	$.each($("input:checkbox:checked[name="+name+"]").toArray().reverse(),function() {
		var toMove = $(this);
		for(var i=0;i<nested;i++) {
			toMove = toMove.parent();
		}
		toMove.prevAll().last().before(toMove);
	});
};
Cms.moveUp = function(name,nested) {
	nested=nested||2;
	$("input:checkbox:checked[name="+name+"]").each(function() {
		var toMove = $(this);
		for(var i=0;i<nested;i++) {
			toMove = toMove.parent();
		}
		toMove.prev().before(toMove);
	});
};
Cms.moveDown = function(name,nested) {
	nested=nested||2;
	$.each($("input:checkbox:checked[name="+name+"]").toArray().reverse(),function() {
		var toMove = $(this);
		for(var i=0;i<nested;i++) {
			toMove = toMove.parent();
		}
		toMove.next().after(toMove);
		//hack 在ie8中将首行往下移动，出现诡异的布局混乱
		toMove.prev().mouseover().mouseout();
	});
};
Cms.moveBottom = function(name,nested) {
	nested=nested||2;
	$("input:checkbox:checked[name="+name+"]").each(function() {
		var toMove = $(this);
		for(var i=0;i<nested;i++) {
			toMove = toMove.parent();
		}
		toMove.nextAll().last().after(toMove);
		//hack 在ie8中将首行往下移动，出现诡异的布局混乱
		toMove.prevAll().last().mouseover().mouseout();
	});
};
Cms.uploadFile = function(action,name,button) {
	var fileId = "f_"+name;
	var urlId = name;
	var nameId = name+"Name";
	var lengthId = name+"Length";
    if($("#"+fileId).val()=="") {alert("file not selected!");return;}
	button.disabled = true;
	$.ajaxFileUpload({
		url:action,
		secureuri:false,
		fileElementId:fileId,
		dataType: "json",
		success: function (data, status) {
			if(typeof(data.error) != "undefined") {
				if(data.error != "") {
					alert(data.error);
				}else {
					$("#"+urlId).val(data.fileUrl).change();
					$("#"+nameId).val(data.fileName).change();
					$("#"+lengthId).val(data.fileLength).change();
				}
			}
			button.disabled = false;
		},
		error: function (data, status, e) {
			button.disabled = false;
			alert(e);
		}
	});
	return false;
};
Cms.uploadImg = function(action,name,button) {
	var fileId = "f_"+name;
	var urlId = name;
    if($("#"+fileId).val()=="") {alert("file not selected!");return;}
    var data={};
    if($("#wm_"+name).length!=0) {
    	data.watermark=$("#wm_"+name).val();
    }
    if($("#s_"+name).length!=0) {
    	data.scale=$("#s_"+name).prop("checked");
    }
    if($("#w_"+name).length!=0) {
    	data.width=$("#w_"+name).val();
    }
    if($("#h_"+name).length!=0) {
    	data.height=$("#h_"+name).val();
    }
    if($("#t_"+name).length!=0) {
    	data.thumbnail=$("#t_"+name).val();
    }
    if($("#tw_"+name).length!=0) {
    	data.thumbnailWidth=$("#tw_"+name).val();
    }
    if($("#th_"+name).length!=0) {
    	data.thumbnailHeight=$("#th_"+name).val();
    }
	button.disabled = true;
	$.ajaxFileUpload({
		url:action,
		secureuri:false,
		fileElementId:fileId,
		dataType: "json",
		data:data,
		success: function (data, status) {
			if(typeof(data.error) != "undefined") {
				if(data.error != "") {
					alert(data.error);
				}else {
					$("#"+urlId).val(data.fileUrl).change();
				}
			}
			button.disabled = false;
		},
		error: function (data, status, e) {
			button.disabled = false;
			alert(e);
		}
	});
	return false;
};
Cms.scaleImg = function(imgId,maxWidth,maxHeight,src) {
	if(src=="") {
		$("#"+imgId).hide();
		return;
	}
	if(src.indexOf("?"==-1)) {
		src += "?d="+new Date()*1;
	} else {
		src += "&d="+new Date()*1;
	}
	var id = "scaleImg"+new Date()*1;
	var imgHtml="<img id='"+id+"' src='"+src+"' style='position:absolute;top:-99999px;left:-99999px'/>";
	$(imgHtml).appendTo($("#"+imgId).parent());
	$("#"+id).load(function() {
		var width=this.width,height=this.height;
	    if((width!=0&&height!=0)&&(width>maxWidth||height>maxHeight)) {
		    var scale = width/maxWidth > height/maxHeight ? width/maxWidth : height/maxHeight;
		    $("#"+imgId).width(width/scale).height(height/scale);
		} else {
		    $("#"+imgId).width(width).height(height);
		}
	    $(this).remove();
		$("#"+imgId).attr("src",src).show();
	}).error(function(){
		$("#"+imgId).hide();		
	});
	return id;
};
Cms.imgCrop = function(action,name) {
	var frameId = "img_area_select_iframe";
	var iframe = $("#"+frameId).get(0);
	if(typeof(iframe)=="undefined") {
		var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" src="javascript:false" style="position:absolute;top:-99999px;left:-99999px"/>';
		$(iframeHtml).appendTo(document.body);
		iframe = $("#"+frameId).get(0);
	}
	var url = action+"?d="+new Date()*1+"&src="+$("#"+name).val()+"&targetWidth="+$("#w_"+name).val()+"&targetHeight="+$("#h_"+name).val()+"&targetFrame="+frameId+"&name="+name;
	window.open(url,"img_are_select","height=575, width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=auto, resizable=yes,location=no, status=no");
};
// jQuery plugin
(function($) {
	$.fn.tableHighlight = function() {
		var highlight = null;
		this.children("tbody").children("tr").click(function(){
			if(highlight) {
				$(highlight).children("td").removeClass("table-highlight-click");
			}
			highlight = this;
			$(highlight).children("td").removeClass("table-highlight-over").addClass("table-highlight-click");
		});
		this.children("tbody").children("tr").mouseover(function(){
			if(this==highlight) {
				return;
			}
			$(this).children("td").addClass("table-highlight-over");
		});
		this.children("tbody").children("tr").mouseout(function(){
			if(this==highlight) {
				return;
			}
			$(this).children("td").removeClass("table-highlight-over");
		});
	};
})(jQuery);
(function($) {
	$.fn.headSort = function() {
		var sort = $(this).attr("pagesort");
		var dir = $(this).attr("pagedir");
		var url = $(this).attr("pageurl");
		$(".ls-sort").each(function() {
			if(sort == $(this).attr("pagesort")) {
				$(this).addClass("ls-sort-" + dir);
			}
		});
		$(this).find(".ls-th-sort").each(function() {
			var sortSpan = $(this).find(".ls-sort");
			var currSort = sortSpan.attr("pagesort");
			var currDir = sort==currSort&&dir=="asc"?"desc":"asc";
			var currUrl = $.format(url,currSort,currDir);
			$(this).click(function() {
				window.open(currUrl,"_self");
			});
			$(this).hover(function() {
				if(currSort!=sort) {
					sortSpan.addClass("ls-sort-asc");
				}
			},function() {
				if(currSort!=sort) {
					sortSpan.removeClass("ls-sort-asc");
				}
			});
		});
	};
})(jQuery);
