<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${ctx}/vendor/imgareaselect/css/imgareaselect-animated.css" />
<script type="text/javascript" src="${ctx}/vendor/imgareaselect/scripts/jquery.imgareaselect.js"></script>
<style type="text/css">
html,body{height:100%;}
.img_table{width:100%;margin-top:10px;}
</style>
</head>
<body>
<table class="img_table" cellpadding="1" cellspacing="1">
  <tr>
    <td width="55%" align="right" valign="top"><img id="photo" src="javascript:false" style="border:1px solid #333;"/></td>
    <td width="45%" align="center" valign="top">
      <div id="preview" style="width:${targetWidth}px;height:${targetHeight}px;overflow:hidden;border:1px solid #333;">
        <img id="previewImg" src="javascript:false" style="width:${targetWidth}px;height:${targetHeight}px;"/>
      </div>
      <form action="img_crop.do" target="<c:out value='${targetFrame}'/>" method="post" style="padding-top:10px">
        <input type="hidden" id="scale" name="scale"/>
        <input type="hidden" id="top" name="top"/>
        <input type="hidden" id="left" name="left"/>
        <input type="hidden" id="width" name="width"/>
        <input type="hidden" id="height" name="height"/>
        <input type="hidden" name="src" value="<c:out value='${src}'/>"/>
        <input type="hidden" name="targetWidth" value="${targetWidth}"/>
        <input type="hidden" name="targetHeight" value="${targetHeight}"/>
        <input type="hidden" name="name" value="<c:out value='${name}'/>"/>
        <input type="button" value="<s:message code="crop"/>" onclick="this.form.submit();setTimeout(function(){window.close()},100);"/>
      </form>
    </td>
  </tr>
</table>
<script type="text/javascript">
var src = "${src}";
var targetWidth=${targetWidth};
var targetHeight=${targetHeight};
var id = "scaleImg"+new Date()*1;
var imgHtml="<img id='"+id+"' src='"+src+"' style='position:absolute;top:-99999px;left:-99999px'/>";
$(imgHtml).appendTo(document.body);

var container=550;
var scale=1,width,height;
$("#"+id).load(function() {
	width=this.width;
	height=this.height;
    if((width!=0&&height!=0)&&(width>container||height>container)) {
	    scale = container/width < container/height ? container/width : container/height;
	    $("#photo").width(width*scale).height(height*scale);
	} else {
	    $("#photo").width(width).height(height);
	}
	$("#photo").attr("src",src).ready(function(){
		$("#photo").imgAreaSelect({minWidth:targetWidth*scale,minHeight:targetHeight*scale,aspectRatio:"${targetWidth}:${targetHeight}",x1:0,y1:0,x2:targetWidth*scale,y2:targetHeight*scale,
		    onSelectChange:preview,handles:true});
	});
	$("#previewImg").attr("src",src).ready(function() {
		show(targetWidth*scale,targetHeight*scale,0,targetWidth*scale,0,targetHeight*scale);
	});	
});
function preview(img, selection) {
	if (!selection.width || !selection.height){
		return;  
	}
	show(selection.width,selection.height,selection.x1,selection.x2,selection.y1,selection.y2);  
}
function show(w,h,x1,x2,y1,y2) {
	var scaleX = targetWidth / w;
	var scaleY = targetHeight / h;
	$("#preview img").css({
		width: Math.round(scaleX * width * scale),
		height: Math.round(scaleY * height * scale),
		marginLeft: -Math.round(scaleX * x1),
		marginTop: -Math.round(scaleY * y1)
	});
	var s = scale*targetWidth/w;
	$("#scale").val(s);
	$("#left").val(parseInt(x1/scale));
	$("#top").val(parseInt(y1/scale));
	$("#width").val(parseInt(w/scale));
	$("#height").val(parseInt(h/scale));
}
</script>
</body>
</html>