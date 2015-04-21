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
});
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="html.management"/></span>
</div>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><s:message code="html.allHtml"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<form action="make_all_html.do" method="post">
    		<input type="button" value="<s:message code='html.makeAllHtml'/>" onclick="this.form.submit();"/>
    	</form>    	
    </td>
  </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><s:message code="html.homeHtml"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<form method="post">
	    	<input type="button" value="<s:message code='html.makeHomeHtml'/>" onclick="this.form.action='make_home_html.do';this.form.submit();"/> &nbsp;
	    	<input type="button" value="<s:message code='html.deleteHomeHtml'/>" onclick="this.form.action='delete_home_html.do';this.form.submit();"/>    	
    	</form>    	
    </td>
  </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><s:message code="html.nodeHtml"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<form action="make_node_html.do" method="post">    	
		    <f:hidden id="nodeId" name="nodeId"/>
		    <f:hidden id="nodeIdNumber"/>
		    <f:hidden id="nodeIdName"/>
		    <f:text id="nodeIdNameDisplay" readonly="readonly" style="width:160px;"/><input id="nodeIdButton" type="button" value="<s:message code='choose'/>"/>
		    <script type="text/javascript">
		    $(function(){
		    	Cms.f7.node("${ctx}","nodeId",{
		    		"settings": {"title": "<s:message code='node.f7.selectNode'/>"},
		    		"params": {}
		    	});
		    });
		    </script> &nbsp;
		    <label><f:checkbox name="includeChildren" default="true"/><s:message code="html.includeChildren"/></label> &nbsp;
	    	<input type="button" value="<s:message code='html.makeNodeHtml'/>" onclick="this.form.submit();"/> 	
    	</form>    	
    </td>
  </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" class="in-tb margin-top5">
  <tr>
    <td class="in-lab" width="15%"><s:message code="html.infoHtml"/>:</td>
    <td class="in-ctt" width="85%" colspan="3">
    	<form action="make_info_html.do" method="post">
		    <f:hidden id="nodeId2" name="nodeId"/>
		    <f:hidden id="nodeId2Number"/>
		    <f:hidden id="nodeId2Name"/>
		    <f:text id="nodeId2NameDisplay" readonly="readonly" style="width:160px;"/><input id="nodeId2Button" type="button" value="<s:message code='choose'/>"/>
		    <script type="text/javascript">
		    $(function(){
		    	Cms.f7.node("${ctx}","nodeId2",{
		    		"settings": {"title": "<s:message code='node.f7.selectNode'/>"},
		    		"params": {}
		    	});
		    });
		    </script> &nbsp;
		    <label><f:checkbox name="includeChildren" default="true"/><s:message code="html.includeChildren"/></label> &nbsp;
	    	<input type="button" value="<s:message code='html.makeInfoHtml'/>" onclick="this.form.submit();"/> 	
    	</form>    	
    </td>
  </tr>
</table>
</body>
</html>