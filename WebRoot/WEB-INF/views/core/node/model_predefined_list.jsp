<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jspxcms.core.domain.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
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
<style type="text/css">
</style>
<script type="text/javascript">
$(function() {
	$("input[name=control][checked!=checked]").each(function(){
		$(this).parent().parent().find("input,select").not(this).attr("disabled","disabled").addClass("disabled");
	});
	$("#pagedTable").tableHighlight();
	$("#fieldForm").validate();
	$("input[name='control']").change(function(){
		if(this.checked) {
			$(this).parent().parent().find("input,select").not(this).removeAttr("disabled").removeClass("disabled");
		} else {
			$(this).parent().parent().find("input,select").not(this).attr("disabled","disabled").addClass("disabled");
		}
	});
});
function checkControl(name,checked) {
	$("input[name='"+name+"']").each(function() {
		$(this).prop("checked",checked).change();
	});
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <div class="c-position"><s:message code="model.management"/> - <s:message code="model.type.${model.type}"/> - <s:message code="modelField.addPredefinedField"/> - ${model.name}</div>
</div>
<form id="fieldForm" action="batch_save.do" method="post">
<f:hidden name="modelId" value="${model.id}"/>
<tags:search_params/>
<div class="ls-bc-opt">
	<div class="ls-btn"><input type="submit" value="<s:message code="save"/>"/></div>
	<div class="ls-btn"></div>
	<div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='list.do?modelId=${model.id}&${searchstring}';"/></div>
	<div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead>
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" checked="checked" onclick="checkControl('control',this.checked);"/></th>
    <th><s:message code="modelField.name"/></th>
    <th><s:message code="modelField.label"/></th>
    <th><s:message code="modelField.dblColumn"/></th>
  </tr>
  </thead>
  <tbody>
  <c:set var="names" value="${model.predefinedNames}"/>
  <c:if test="${!fnx:contains_co(names,'parent')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">parent
      <input type="hidden" name="name" value="parent"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.parent'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'name')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">name
      <input type="hidden" name="name" value="name"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.name'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'number')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">number
      <input type="hidden" name="name" value="number"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.number'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'domain')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">domain
      <input type="hidden" name="name" value="domain"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.domain'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'domainPath')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">domainPath
      <input type="hidden" name="name" value="domainPath"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.domainPath'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'link')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">link
      <input type="hidden" name="name" value="link"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.link'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'newWindow')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">newWindow
      <input type="hidden" name="name" value="newWindow"/>
      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.newWindow'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'metaKeywords')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">metaKeywords
      <input type="hidden" name="name" value="metaKeywords"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.metaKeywords'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'metaDescription')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">metaDescription
      <input type="hidden" name="name" value="metaDescription"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.metaDescription'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'workflow')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">workflow
      <input type="hidden" name="name" value="workflow"/>
      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.workflow'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'infoPerms')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">infoPerms
      <input type="hidden" name="name" value="infoPerms"/>
      <input type='hidden' name='property' value='{"type":3,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.infoPerms'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'nodePerms')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">nodePerms
      <input type="hidden" name="name" value="nodePerms"/>
      <input type='hidden' name='property' value='{"type":3,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.nodePerms'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'viewGroups')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">viewGroups
      <input type="hidden" name="name" value="viewGroups"/>
      <input type='hidden' name='property' value='{"type":3,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.viewGroups'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'contriGroups')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">contriGroups
      <input type="hidden" name="name" value="contriGroups"/>
      <input type='hidden' name='property' value='{"type":3,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.contriGroups'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'commentGroups')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">commentGroups
      <input type="hidden" name="name" value="commentGroups"/>
      <input type='hidden' name='property' value='{"type":3,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.commentGroups'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'nodeModel')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">nodeModel
      <input type="hidden" name="name" value="nodeModel"/>
      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.nodeModel'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'infoModel')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">infoModel
      <input type="hidden" name="name" value="infoModel"/>
      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.infoModel'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'nodeTemplate')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">nodeTemplate
      <input type="hidden" name="name" value="nodeTemplate"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.nodeTemplate'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'infoTemplate')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">infoTemplate
      <input type="hidden" name="name" value="infoTemplate"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.infoTemplate'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'generateNode')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">generateNode
      <input type="hidden" name="name" value="generateNode"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.generateNode'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'generateInfo')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">generateInfo
      <input type="hidden" name="name" value="generateInfo"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.generateInfo'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'staticMethod')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">staticMethod
      <input type="hidden" name="name" value="staticMethod"/>
      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.staticMethod'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'staticPage')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">staticPage
      <input type="hidden" name="name" value="staticPage"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2,"defValue":1,"required":false}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.staticPage'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'smallImage')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">smallImage
      <input type="hidden" name="name" value="smallImage"/>
      <input type='hidden' name='property' value='{"type":7,"innerType":2,"required":false}'/>
      <input type='hidden' name='custom' value='{"imageWidth":"140","imageHeight":"93"}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.smallImage'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'largeImage')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">largeImage
      <input type="hidden" name="name" value="largeImage"/>
      <input type='hidden' name='property' value='{"type":7,"innerType":2,"required":false}'/>
      <input type='hidden' name='custom' value='{"imageWidth":"290","imageHeight":"200"}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.largeImage'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'text')}">
  <tr>
    <td><input type="checkbox" name="control"/></td>
    <td align="center">text
      <input type="hidden" name="name" value="text"/>
      <input type='hidden' name='property' value='{"type":50,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='node.text'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'parameters')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">parameters
      <input type="hidden" name="name" value="parameters"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2,"required":false}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="高级参数"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'attrs')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">attrs
      <input type="hidden" name="name" value="attrs"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2,"required":false}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="高级属性"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'brand')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">brand
      <input type="hidden" name="name" value="brand"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2,"required":false}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="商品品牌"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  </tbody>
</table>
</form>
</body>
</html>
