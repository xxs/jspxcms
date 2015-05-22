<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script type="text/javascript">
$(function() {
	$("#validForm").validate();
	$("#realpath").hide();
	$("input[name='name']").focus();
});
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<c:set var="numberExist"><s:message code="site.number.exist"/></c:set>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="site.management"/> - <s:message code="${oprt=='edit' ? 'edit' : 'create'}"/></span>
</div>
<form id="validForm" method="post">
<tags:search_params/>
<f:hidden name="oid" value="${bean.id}"/>
<f:hidden name="position" value="${position}"/>
<input type="hidden" id="redirect" name="redirect" value="edit"/>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><s:message code="site.name"/>:</td>
    <td class="in-ctt" width="35%">${bean.name}</td>
    <td class="in-lab" width="15%"><s:message code="site.fullName"/>:</td>
    <td class="in-ctt" width="35%">${bean.fullName}</td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="site.domain"/>:</td>
    <td class="in-ctt" width="35%">
    	${bean.domain}
    </td>
    <td class="in-lab" width="15%"><s:message code="site.number"/>:</td>
    <td class="in-ctt" width="35%">${bean.number}</td>
  </tr>
  <tr>
    <td class="in-lab" width="15%"><s:message code="site.templateTheme"/>:</td>
    <td class="in-ctt" width="35%">
	    	<select name="templateTheme">
	    		<f:options items="${themeList}" selected="${bean.templateTheme}"/>
	    	</select>
    </td>
  </tr>
  <tr>
    <td class="in-lab" ></td>
    <td colspan="3" class="in-opt">
      <div class="in-btn"><input type="button" value="生成整站html+js+css" onclick="this.form.action='make_all_site.do?id=${bean.id}';this.form.submit();""/></div>
      <div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='list.do?${searchstring}';"/></div>
      <div style="clear:both;"></div>
    </td>
  </tr>
</table>
</form>
</body>
</html>