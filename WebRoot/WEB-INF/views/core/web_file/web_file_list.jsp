<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<style type="text/css">
* html{overflow-y:scroll;}
</style>
<script type="text/javascript">
var swfupload;
$(function() {
	$("#pagedTable").tableHighlight();
	$("#sortHead").headSort();
	$("#createDirForm").validate();
	$("#renameForm").validate();
	$("#createDirDialog").dialog({
		autoOpen: false,
		width: 300,
		height: 150,
		modal: true,
		position: { my: "center top+20", at: "center top", of: window },
		buttons: {
			"OK": function() {
				$("#createDirForm").submit();
				$(this).dialog("close");
			},
			Cancel: function() {
				$(this).dialog("close");
			}
		}
	});
	$("#renameDialog").dialog({
		autoOpen: false,
		width: 300,
		height: 150,
		modal: true,
		position: { my: "center bottom", at: "center center", of: window },
		buttons: {
			"OK": function() {
				$("#renameForm").submit();
				$(this).dialog("close");
			},
			Cancel: function() {
				$(this).dialog("close");
			}
		}
	});

	var settings = {
		flash_url : "${ctx}/vendor/swfupload/swfupload.swf",
		upload_url: "upload.do;jsessionid=<%=request.getRequestedSessionId()%>",
		file_post_name: "file",
		post_params: {
			"parentId" : "${parentId}"
		},
		//file_size_limit : "100 MB",
		file_types : "*.*",
		file_types_description : "All Files",
		//file_upload_limit : 100,
		file_queue_limit : 0,
		custom_settings : {
			progressTarget : "fsUploadProgress",
			cancelButtonId : "btnCancel"
		},
		button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,

		debug: false,

		// Button settings
		button_image_url: "${ctx}/vendor/swfupload/button_upload_text.png",
		button_width: "61",
		button_height: "22",
		button_placeholder_id: "spanButtonPlaceHolder",
		
		// The event handler functions are defined in handlers.js
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete,
		queue_complete_handler : queueComplete	// Queue plugin event
	};
	function queueComplete() {
		location.href=location.href;
	}
	swfupload = new SWFUpload(settings);
	
	$("span[imgUrl]").each(function(){
		var span = $(this);
		var img = null;
		var toShow = true;
		span.mouseenter(function(){
			if(!img) {
				img = Bw.imageDim(span.attr("imgUrl"),{maxWidth:300,maxHeigth:200,of:span});
				img.load(function(){
					if(toShow) {
						img.show();
						img.positionSideOf(span);
					}	
				});
			} else {
				img.show();
				img.positionSideOf(span,true);
			}
		}).mouseleave(function(){
			toShow = false;
			if(img) {
				img.offset({"left":"0","top":"0"});
				img.hide();				
			}
		});
	});
});
function optRename(id,name){
	$("#renameDialog").dialog("open");
	$("#renameForm input[name='id']").val(id);
	$("#renameForm input[name='name']").val(name).select();
}
function optMove(form) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	<c:url value="../web_file/choose_dir.do" var="moveUrl">
		<c:param name="parentId" value="${parentId}"/>
	</c:url>
	var url = "${moveUrl}";
	$("input[name='ids']:checkbox:checked").each(function(){
		url += "&ids="+encodeURI($(this).val());
	});
	url += "&d="+new Date()*1;
 	$("<div>",{"title":"<s:message code='webFile.moveTo'/>"}).appendTo(document.body).load(url, function(){
 		$(this).dialog({
 			width: 350,
 			height: 450,
 			modal: true,
 			position: { my: "center top", at: "center top", of: window },
 			buttons: {
 				"OK": function() {
 					var dest = $("#f7_id").val();
 					if(dest.length<=0) {
 						alert("please select dir!");
 						return;
 					}
 					$(this).dialog("close");
 					$("<input>",{
 						"type":"hidden",
 						"name":"dest",
 						"value":dest
 					}).appendTo(form);
 					form.action = "move.do";
 					form.submit();
 				},
 				Cancel: function() {
 					$(this).dialog("close");
 				}
 			}
 		});
 	}); 
}
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
function optDelete(form) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(!confirmDelete()) {
		return false;
	}
	form.action='delete.do';
	form.submit();
	return true;
}
function optPost(form,action) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	form.action=action;
	form.submit();
	return true;
}
<c:if test="${!empty refreshLeft}">
parent.frames['left'].reload();
</c:if>
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div id="createDirDialog" title="<s:message code='webFile.createDir'/>" style="display:none;">
	<form id="createDirForm" action="mkdir.do" method="post">
		<input type="hidden" name="parentId" value="${parentId}"/>
		<label><input type="text" name="dir" class="required" maxlength="150" style="width:180px;"/></label>
	</form>
</div>
<div id="renameDialog" title="<s:message code='webFile.rename'/>" style="display:none;">
	<form id="renameForm" action="rename.do" method="post">
		<input type="hidden" name="parentId" value="${parentId}"/>
		<input type="hidden" name="id"/>
		<label><input type="text" name="name" class="required" maxlength="150" style="width:180px;"/></label>
	</form>
</div>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="webFile.management"/> - <s:message code="list"/> - ${parentId}</span>
	<span class="c-total">(<s:message code="totalElements" arguments="${fn:length(list)}"/>)</span>
</div>
<form action="list.do" method="get">
	<fieldset class="c-fieldset">
    <legend><s:message code="search"/></legend>
	  <label class="c-lab"><s:message code="webFile.name"/>: <f:text name="search_name" value="${search_name[0]}" style="width:150px;"/></label>
	  <label class="c-lab"><input type="submit" value="<s:message code="search"/>"/></label>
	  <div style="clear:both"></div>
  </fieldset>
</form>
<form action="batch_update.do" method="post">
<f:hidden name="parentId" value="${parentId}"/>
<div class="ls-bc-opt">
	<shiro:hasPermission name="core:web_file:zip_upload">
	<div class="ls-btn"><label for="zipUpload"><input type="checkbox" id="zipUpload" onclick="swfupload.setFileTypes(this.checked?'*.zip':'*.*',this.checked?'ZIP Files':'All Files');swfupload.setUploadURL(this.checked?'zip_upload.do;jsessionid=<%=request.getRequestedSessionId()%>':'upload.do;jsessionid=<%=request.getRequestedSessionId()%>');"/><s:message code="webFile.zipUpload"/></label></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:web_file:upload">
	<div class="ls-btn"><span id="spanButtonPlaceHolder"></span></div>
	<div class="ls-btn"><input id="btnCancel" type="button" value="<s:message code="cancel"/>" onclick="swfupload.cancelQueue();" disabled="disabled"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:web_file:create_dir">
	<div class="ls-btn"><input type="button" value="<s:message code="webFile.createDir"/>" onclick="$('#createDirDialog').dialog('open');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:web_file:create">
	<div class="ls-btn"><input type="button" value="<s:message code="webFile.createText"/>" onclick="location.href='create.do?parentId=${fnx:urlEncode(parentId)}&${searchstring}';"/></div>
	</shiro:hasPermission>
	<div class="ls-btn"></div>
	<shiro:hasPermission name="core:web_file:zip_download">
	<div class="ls-btn"><input type="button" value="<s:message code="webFile.zipDownload"/>" onclick="return optPost(this.form,'zip_download.do');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:web_file:zip">
	<div class="ls-btn"><input type="button" value="<s:message code="webFile.zip"/>" onclick="return optPost(this.form,'zip.do');"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:web_file:move">
	<div class="ls-btn"><input type="button" value="<s:message code="webFile.move"/>" onclick="return optMove(this.form);"/></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="core:web_file:delete">
	<div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
	</shiro:hasPermission>
	<div class="ls-btn"></div>
	<div class="ls-btn"><input type="button" value="<s:message code="webFile.parentDir"/>" onclick="location.href='list.do?parentId=${fnx:urlEncode(ppId)}&${searchstring}';"<c:if test="${empty ppId}"> disabled="disabled"</c:if>/></div>
	<div style="clear:both"></div>
</div>
<div id="fsUploadProgress"></div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="list.do?page_sort={0}&page_sort_dir={1}&parentId=${fnx:urlEncode(parentId)}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="280"><s:message code="operate"/></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="name"><s:message code="webFile.name"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="lastModified"><s:message code="webFile.lastModified"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="type"><s:message code="webFile.type"/></span></th>
    <th class="ls-th-sort"><span class="ls-sort" pagesort="length"><s:message code="webFile.length"/></span></th>
  </tr>
  </thead>
  <tbody id="dblclickBody">
  <c:forEach var="bean" varStatus="status" items="${list}">
  <tr>
    <td><c:if test="${!bean.parent}"><input type="checkbox" name="ids" value="<c:out value='${bean.id}'/>"/></c:if></td>
    <td align="center">
    	<c:choose>
    		<c:when test='${bean.directory}'>
    			<c:url value="list.do?${searchstring}" var="editUrl">
    				<c:param name="parentId" value="${bean.id}"/>
    			</c:url>
    		</c:when>
    		<c:otherwise>
    			<c:url value="edit.do?${searchstring}" var="editUrl">
    				<c:param name="id" value="${bean.id}"/>
    				<c:param name="parentId" value="${parentId}"/>
    				<c:param name="position" value="${pagedList.number*pagedList.size+status.index}"/>
    			</c:url>
    		</c:otherwise>
    	</c:choose>
      <a id="edit_opt_${status.index}" href="${editUrl}" class="ls-opt" style="display:none;"><s:message code="edit"/></a>
      
			<shiro:hasPermission name="core:web_file:zip_download">
      <c:url value="zip_download.do?${searchstring}" var="zipDownloadUrl">
 				<c:param name="ids" value="${bean.id}"/>
 			</c:url>
      <a id="zip_download_opt_${status.index}" href="${zipDownloadUrl}" class="ls-opt"<c:if test="${bean.parent}"> disabled="disabled"</c:if>><s:message code="webFile.zipDownload"/></a>
      </shiro:hasPermission>
      
			<shiro:hasPermission name="core:web_file:zip">
      <c:url value="zip.do?${searchstring}" var="zipUrl">
 				<c:param name="ids" value="${bean.id}"/>
 				<c:param name="parentId" value="${parentId}"/>
 			</c:url>
      <a id="zip_opt_${status.index}" href="${zipUrl}" class="ls-opt"<c:if test="${bean.parent}"> disabled="disabled"</c:if>><s:message code="webFile.zip"/></a>
			</shiro:hasPermission>

			<shiro:hasPermission name="core:web_file:unzip">
      <c:url value="unzip.do?${searchstring}" var="unzipUrl">
 				<c:param name="ids" value="${bean.id}"/>
 				<c:param name="parentId" value="${parentId}"/>
 			</c:url>
      <a id="unzip_opt_${status.index}" href="${unzipUrl}" class="ls-opt"<c:if test="${bean.parent||!bean.zip}"> disabled="disabled"</c:if>><s:message code="webFile.unzip"/></a>
      </shiro:hasPermission>
      
			<shiro:hasPermission name="core:web_file:open">
    	<c:choose>
    		<c:when test="${bean.directory}">
    			<c:set var="openUrl" value="${editUrl}"/>
    		</c:when>
    		<c:otherwise>
    			<c:set var="openUrl" value="${bean.url}"/>
    		</c:otherwise>
    	</c:choose>
      <a id="open_opt_${status.index}" href="${openUrl}"<c:if test="${!bean.directory}"> target="_blank"</c:if> class="ls-opt"<c:if test="${bean.parent}"> disabled="disabled"</c:if>><s:message code="webFile.open"/></a>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="core:web_file:rename">
      <a href="javascript:void(0);" onclick="optRename('${bean.id}','<c:out value="${bean.name}"/>');" class="ls-opt"<c:if test="${bean.parent}"> disabled="disabled"</c:if></a><s:message code="webFile.rename"/></a>
 			</shiro:hasPermission>
 			
			<shiro:hasPermission name="core:web_file:delete">
 			<c:url value="delete.do?${searchstring}" var="deleteUrl">
 				<c:param name="ids" value="${bean.id}"/>
 				<c:param name="parentId" value="${parentId}"/>
 			</c:url>
      <a href="${deleteUrl}" onclick="return confirmDelete();" class="ls-opt"<c:if test="${bean.parent}"> disabled="disabled"</c:if></a><s:message code="delete"/></a>
      </shiro:hasPermission>
      
     </td>
    <td onclick="location.href='${editUrl}';" style="cursor:pointer;">
    	<div id="beanname${status.index}" class="file-${bean.type}"><span<c:if test="${bean.image}"> imgUrl="${bean.url}"</c:if>><c:out value="${bean.name}"/></span></div>
    </td>
    <td onclick="location.href='${editUrl}';" style="cursor:pointer;"><fmt:formatDate value="${bean.lastModified}" pattern="yyyy-MM-dd HH:mm"/></td>
    <td onclick="location.href='${editUrl}';" style="cursor:pointer;"><s:message code="webFile.type.${bean.type}"/></td>
    <td align="right" onclick="location.href='${editUrl}';" style="cursor:pointer;"><c:if test="${bean.file}"><fmt:formatNumber value="${bean.lengthKB}" pattern="#,##0"/> KB</c:if></td>
  </tr>
  </c:forEach>
  </tbody>
</table>
</form>
</body>
</html>