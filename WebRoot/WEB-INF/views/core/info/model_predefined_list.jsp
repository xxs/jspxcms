<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jspxcms.core.domain.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
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
  <c:if test="${!fnx:contains_co(names,'node')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">node
      <input type="hidden" name="name" value="node"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.node'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'nodes')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">nodes
      <input type="hidden" name="name" value="nodes"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.nodes'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'specials')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">specials
      <input type="hidden" name="name" value="specials"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.specials'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'title')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">title
      <input type="hidden" name="name" value="title"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.title'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'color')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">color
      <input type="hidden" name="name" value="color"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.color'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'subtitle')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">subtitle
      <input type="hidden" name="name" value="subtitle"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.subtitle'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'fullTitle')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">fullTitle
      <input type="hidden" name="name" value="fullTitle"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.fullTitle'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'tagKeywords')}">
  <tr>
    <td><input type="checkbox" name="control" checked="info.tagKeywords"/></td>
    <td align="center">tagKeywords
      <input type="hidden" name="name" value="tagKeywords"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.tagKeywords'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'metaDescription')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">metaDescription
      <input type="hidden" name="name" value="metaDescription"/>
      <input type='hidden' name='property' value='{"type":6,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.metaDescription'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'infoPath')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">infoPath
      <input type="hidden" name="name" value="infoPath"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.infoPath'/>"/></td>
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
    <td align="center"><input type="text" name="label" value="<s:message code='info.infoTemplate'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'priority')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">priority
      <input type="hidden" name="name" value="priority"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.priority'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'publishDate')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">publishDate
      <input type="hidden" name="name" value="publishDate"/>
      <input type='hidden' name='property' value='{"type":2,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.publishDate'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'source')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">source
      <input type="hidden" name="name" value="source"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.source'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'author')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">author
      <input type="hidden" name="name" value="author"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.author'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="true"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'allowComment')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">allowComment
      <input type="hidden" name="name" value="allowComment"/>
      <input type='hidden' name='property' value='{"type":5,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.allowComment'/>"/></td>
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
    <td align="center"><input type="text" name="label" value="<s:message code='info.viewGroups'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'attributes')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">attributes
      <input type="hidden" name="name" value="attributes"/>
      <input type='hidden' name='property' value='{"type":1,"innerType":2,"required":false}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.attributes'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
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
    <td align="center"><input type="text" name="label" value="<s:message code='info.smallImage'/>"/></td>
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
    <td align="center"><input type="text" name="label" value="<s:message code='info.largeImage'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'file')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">file
      <input type="hidden" name="name" value="file"/>
      <input type='hidden' name='property' value='{"type":9,"innerType":2,"required":false}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.file'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'video')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">video
      <input type="hidden" name="name" value="video"/>
      <input type='hidden' name='property' value='{"type":8,"innerType":2,"required":false}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.video'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'images')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">images
      <input type="hidden" name="name" value="images"/>
      <input type='hidden' name='property' value='{"type":51,"innerType":2,"required":false}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.images'/>"/></td>
    <td align="center"><f:checkbox name="dblColumn" value="false"/></td>
  </tr>
  </c:if>
  <c:if test="${!fnx:contains_co(names,'text')}">
  <tr>
    <td><input type="checkbox" name="control" checked="checked"/></td>
    <td align="center">text
      <input type="hidden" name="name" value="text"/>
      <input type='hidden' name='property' value='{"type":50,"innerType":2}'/>
      <input type='hidden' name='custom' value='{}'/>
    </td>
    <td align="center"><input type="text" name="label" value="<s:message code='info.text'/>"/></td>
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
  
  </tbody>
</table>
</form>
</body>
</html>
