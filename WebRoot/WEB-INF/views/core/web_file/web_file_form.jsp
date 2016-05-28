<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script type="text/javascript">
$(function() {
	$("#validForm").validate({
		<c:if test="${oprt=='edit'}">
		submitHandler: function(form) {
			<c:if test="${oprt=='create'||bean.editable}">
      $('#name').val($('#baseName').val()+'.'+$('#extension').val());
      </c:if>
			$(form).ajaxSubmit({
				success: function() {
					var name = $("#name").val();
					$("#origName").val(name);
					showMessage("<s:message code="saveSuccess"/>");
				}
			});
			return false;
		}
		</c:if>
	});
	$("input[name='baseName']").focus();

	$("#text").keydown(function(event) {
		if((event.keyCode==83 || event.keyCode==115) && event.ctrlKey){
			$(this.form).submit();
			return false;
		}
	});
});
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
<c:if test="${!empty refreshLeft}">
parent.frames['left'].reload();
</c:if>
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="webFile${type}.management"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden id="origName" name="origName" value="${bean.name}"/>
<f:hidden name="parentId" value="${parentId}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			<shiro:hasPermission name="core:web_file_${type}:create">
			<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?parentId=${fnx:urlEncode(parentId)}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:web_file_${type}:copy">
			<div class="in-btn"><input type="button" value="<s:message code="copy"/>" onclick="location.href='create.do?cid=${bean.id}&parentId=${fnx:urlEncode(parentId)}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:web_file_${type}:delete">
			<div class="in-btn"><input type="button" value="<s:message code="delete"/>" onclick="if(confirmDelete()){location.href='delete.do?ids=${bean.id}&parentId=${fnx:urlEncode(parentId)}&${searchstring}';}"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<div class="in-btn"></div>
			<div class="in-btn"><input type="button" value="<s:message code="prev"/>" onclick="location.href='edit.do?id=${side.prev.id}&position=${position-1}&parentId=${fnx:urlEncode(parentId)}&${searchstring}';"<c:if test="${empty side.prev}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"><input type="button" value="<s:message code="next"/>" onclick="location.href='edit.do?id=${side.next.id}&position=${position+1}&parentId=${fnx:urlEncode(parentId)}&${searchstring}';"<c:if test="${empty side.next}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			<div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='list.do?parentId=${fnx:urlEncode(parentId)}&${searchstring}';"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="webFile.directory"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><c:out value="${parentId}"/></td>
  </tr>
  <c:choose>
  <c:when test="${oprt=='create'||bean.editable}">
  <tr>
    <td class="in-lab" width="15%"><s:message code="webFile.name"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:hidden id="name" name="name" value="${bean.name}"/>
    	<f:text id="baseName" name="baseName" value="${oprt=='edit' ? (bean.baseName) : ''}" class="required" maxlength="150" style="width:200px;"/> .
    	<select id="extension" tabindex="-1">
    		<f:options items="${fn:split('html,htm,js,css,txt,xml,ftl,vm,jsp,jspx,asp,aspx,php,tld,tag,properties,sql',',')}" selected="${fn:toLowerCase(bean.extension)}"/>
    	</select>
    	<c:if test="${oprt=='edit' && bean.editable && (fn:toLowerCase(bean.extension)=='html' || fn:toLowerCase(bean.extension)=='htm')}">
    	&nbsp; &nbsp;
    	<input type="button" id="viewButton" value="<s:message code='webFile.wysiwyg'/>"/>
    	</c:if>
    </td>
  </tr>
  <tr id="textEdit">
    <td class="in-lab" width="15%"><s:message code="webFile.text"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<f:textarea id="text" name="text" wrap="off" spellcheck="false" value="${bean.text}" style="width:95%;height:300px;"/>
    	<iframe id="textView" src="javascript:false;" scrollbars="yes" resizable="yes" style="width:98%;height:300px;display:none;"></iframe>
    </td>
  </tr>
  <script type="text/javascript">
  	$("#viewButton").toggle(function(){
  		$(this).val("<s:message code='webFile.edit'/>");
  		$("#textView").attr("src","${ctx}/template${bean.id}");
  		//var textViewDoc = window.frames["textView"].document;
  		//textViewDoc.open();
  		//textViewDoc.write($("#text").val());
  		//textViewDoc.close();
  		$("#text").hide();
  		$("#textView").show();
  	},function() {
  		$(this).val("<s:message code='webFile.wysiwyg'/>");
  		$("#textView").attr("src","javascript:false;");
  		$("#text").show();
  		$("#textView").hide();
  	});
  	$(function(){
  		$("#baseName").change(function() {
  			$('#name').val($('#baseName').val()+'.'+$('#extension').val());
  		});
  	});
  </script>
  </c:when>
  <c:when test="${bean.image}">
  <tr>
    <td class="in-lab" width="15%"><s:message code="webFile.name"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text id="name" name="name" value="${bean.name}" class="required" maxlength="150" style="width:300px;"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="webFile.preview"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><img id="fileImg" src="javascript:false" style="border:0;"/><script type="text/javascript">$(function(){Bw.imageDim("${bean.url}",{maxWidth:500,to:"#fileImg"});});</script></td>
  </tr>
  </c:when>
  <c:otherwise>
  <tr>
    <td class="in-lab" width="15%"><s:message code="webFile.name"/>:</td>
    <td class="in-ctt" width="85%" colspan="3"><f:text id="name" name="name" value="${bean.name}" class="required" maxlength="150" style="width:300px;"/></td>
  </tr>  
  </c:otherwise>
  </c:choose>
  <tr>
    <td colspan="4" class="in-opt">
      <div class="in-btn"><input type="submit" value="<s:message code="save"/>"/></div>
      <c:if test="${oprt=='create'}">
      <div class="in-btn"><input type="submit" value="<s:message code="saveAndReturn"/>" onclick="$('#redirect').val('list');"/></div>
      <div class="in-btn"><input type="submit" value="<s:message code="saveAndCreate"/>" onclick="$('#redirect').val('create');"/></div>
      </c:if>
      <div style="clear:both;"></div>
    </td>
  </tr>
</table>
</form>
</body>
</html>