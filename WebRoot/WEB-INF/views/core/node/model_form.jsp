<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
	$("#validForm").validate();
	$("input[name='name']").focus();
});
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="model.management"/> - <s:message code="model.type.node"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<div class="ls-bc-opt margin-top5">
	<div id="radio">
	<c:forEach var="type" items="${types}">
		<input type="radio" id="radio_${type}" onclick="location.href='list.do?queryType=${type}&${searchstring}';"<c:if test="${queryType==type}"> checked="checked"</c:if>/><label for="radio_${type}"><s:message code="model.type.${type}"/></label>
	</c:forEach>
	</div>
	<script type="text/javascript">
		$("#radio").buttonset();
	</script>
</div>
<form id="validForm" action="${oprt=='edit' ? 'update' : 'save'}.do" method="post">
<tags:search_params/>
<f:hidden name="queryType" value="${queryType}"/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td colspan="4" class="in-opt">
			<shiro:hasPermission name="core:model:create">
			<div class="in-btn"><input type="button" value="<s:message code="create"/>" onclick="location.href='create.do?queryType=${queryType}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:model_field:list">
			<div class="in-btn"><input type="button" value="<s:message code="model.fieldList"/>" onclick="location.href='../model_field/list.do?modelId=${bean.id}&queryType=${queryType}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<div class="in-btn"></div>
			<shiro:hasPermission name="core:model:copy">
			<div class="in-btn"><input type="button" value="<s:message code="copy"/>" onclick="location.href='create.do?id=${bean.id}&queryType=${queryType}&${searchstring}';"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<shiro:hasPermission name="core:model:delete">
			<div class="in-btn"><input type="button" value="<s:message code="delete"/>" onclick="if(confirmDelete()){location.href='delete.do?ids=${bean.id}&queryType=${queryType}&${searchstring}';}"<c:if test="${oprt=='create'}"> disabled="disabled"</c:if>/></div>
			</shiro:hasPermission>
			<div class="in-btn"></div>
			<div class="in-btn"><input type="button" value="<s:message code="prev"/>" onclick="location.href='edit.do?id=${side.prev.id}&position=${position-1}&queryType=${queryType}&${searchstring}';"<c:if test="${empty side.prev}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"><input type="button" value="<s:message code="next"/>" onclick="location.href='edit.do?id=${side.next.id}&position=${position+1}&queryType=${queryType}&${searchstring}';"<c:if test="${empty side.next}"> disabled="disabled"</c:if>/></div>
			<div class="in-btn"></div>
			<div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='list.do?queryType=${queryType}&${searchstring}';"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="model.name"/>:</td>
    <td class="in-ctt" width="35%"><f:text name="name" value="${oprt=='edit' ? bean.name : ''}" class="required" style="width:180px;"/></td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="model.type"/>:</td>
    <td class="in-ctt" width="35%"><s:message code="model.type.${queryType}"/><f:hidden name="type" value="node"/></td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="model.coverTemplate"/>:</td>
    <td class="in-ctt" width="35%">
    	<f:text id="customs_coverTemplate" name="customs_coverTemplate" value="${bean.customs['coverTemplate']}" class="required" maxlength="255" style="width:160px;"/><input id="customs_coverTemplateButton" type="button" value="<s:message code='choose'/>"/>
	    <script type="text/javascript">
	    $(function(){
	    	Cms.f7.webFile("${ctx}","customs_coverTemplate",{
	    		settings: {"title": "select"}
	    	});
	    });
	    </script>
    </td>
    <td class="in-lab" width="15%"><s:message code="model.listTemplate"/>:</td>
    <td class="in-ctt" width="35%">
    	<f:text id="customs_listTemplate" name="customs_listTemplate" value="${bean.customs['listTemplate']}" maxlength="255" style="width:160px;"/><input id="customs_listTemplateButton" type="button" value="<s:message code='choose'/>"/>
	    <script type="text/javascript">
	    $(function(){
	    	Cms.f7.webFile("${ctx}","customs_listTemplate",{
	    		settings: {"title": "select"}
	    	});
	    });
	    </script>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="node.generateNode"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
	  	<select name="customs_generateNode">
	      <f:option value="true" selected="${bean.customs['generateNode']}"><s:message code="on"/></f:option>
	      <f:option value="false" selected="${bean.customs['generateNode']}" default="false"><s:message code="off"/></f:option>
	  	</select> &nbsp;
	    <f:text name="customs_nodePath" value="${bean.customs['nodePath']}" style="text-align:right;width:300px;"/>
	    <select name="customs_nodeExtension">
	      <f:options items="${fn:split('.html,.htm,.shtml,.shtm',',')}" selected="${bean.customs['nodeExtension']}"/>
	      <option value=""></option>
	    </select> &nbsp;
	    <label><f:checkbox name="customs_defPage" value="${bean.customs['defPage']}" default="true"/><s:message code="node.defPage"/></label>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="node.generateInfo"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
	  	<select name="customs_generateInfo">
	      <f:option value="true" selected="${bean.customs['generateInfo']}"><s:message code="on"/></f:option>
	      <f:option value="false" selected="${bean.customs['generateInfo']}" default="false"><s:message code="off"/></f:option>
	  	</select> &nbsp;
	    <f:text name="customs_infoPath" value="${bean.customs['infoPath']}" style="text-align:right;width:300px;"/>
	    <select name="customs_infoExtension">
	      <f:options items="${fn:split('.html,.htm,.shtml,.shtm',',')}" selected="${bean.customs['infoExtension']}"/>
	      <option value=""></option>
	    </select>
    </td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="node.staticMethod"/>:</td>
    <td class="in-ctt" width="35%">
    <select name="customs_staticMethod" class="required">
      <f:option value="0" selected="${bean.customs['staticMethod']}"><s:message code="node.staticMethod.0"/></f:option>
      <f:option value="1" selected="${bean.customs['staticMethod']}"><s:message code="node.staticMethod.1"/></f:option>
      <f:option value="2" selected="${bean.customs['staticMethod']}"><s:message code="node.staticMethod.2"/></f:option>
      <f:option value="3" selected="${bean.customs['staticMethod']}"><s:message code="node.staticMethod.3"/></f:option>
      <f:option value="4" selected="${bean.customs['staticMethod']}" default="4"><s:message code="node.staticMethod.4"/></f:option>
    </select>
    </td>
    <td class="in-lab" width="15%"><em class="required">*</em><s:message code="node.staticPage"/>:</td>
    <td class="in-ctt" width="35%">
    	<f:text name="customs_staticPage" value="${bean.customs['staticPage']}" default="1" class="required digits" style="width:180px;"/>
    </td>
  </tr>
  <tr>
    <td colspan="4" class="in-opt">
      <div class="in-btn"><input type="submit" value="<s:message code="save"/>"/></div>
      <div class="in-btn"><input type="submit" value="<s:message code="saveAndReturn"/>" onclick="$('#redirect').val('list');"/></div>
      <c:if test="${oprt=='create'}">
      <div class="in-btn"><input type="submit" value="<s:message code="saveAndCreate"/>" onclick="$('#redirect').val('create');"/></div>
      </c:if>
      <div style="clear:both;"></div>
    </td>
  </tr>
</table>
</form>
</body>
</html>