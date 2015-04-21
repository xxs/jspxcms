<%@ tag pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="s" uri="http://www.springframework.org/tags" %><%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ attribute name="id" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="name" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="value" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="scale" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="width" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="height" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="thumbnail" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="thumbnailWidth" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="thumbnailHeight" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="watermark" type="java.lang.String" required="false" rtexprvalue="true"%>
<c:if test="${empty id}"><c:set var="id" value="${name}"/></c:if>
<script type="text/javascript">
function fn_${id}(src) {
	Cms.scaleImg("img_${id}",300,100,src);
};
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="45%">
  		<div>
  			<f:text id="${id}" name="${name}" value="${value}" onchange="fn_${id}(this.value);" style="width:180px;"/>
  			<input type="button" value="<s:message code='choose'/>" disabled="disabled"/>
  		</div>
      <div style="margin-top:2px;"><input type="file" id="f_${id}" name="f_${name}" size="23" style="width:235px;"/></div>      
      <table class="upload-table" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><s:message code="width"/>: <f:text id="w_${id}" value="${width}" default="100" style="width:70px;"/></td>
          <td><label><input type="checkbox" id="s_${id}"<c:if test="${!empty scale&&scale=='true'}"> checked="checked"</c:if>/><s:message code="scale"/></label><f:hidden id="s_${id}" value="${scale}" default="false"/></td>
          <td><input type="button" onclick="uploadImg('${id}',this);" value="<s:message code='upload'/>"/></td>
        </tr>
        <tr>
          <td><s:message code="height"/>: <f:text id="h_${id}" value="${height}" default="100" style="width:70px;"/></td>
          <td><label><input type="checkbox" id="wm_${id}"<c:if test="${!empty watermark&&watermark=='true'}"> checked="checked"</c:if>/><s:message code="watermark"/></label><f:hidden id="wm_${id}" value="${watermark}" default="false"/></td>
          <td><input type="button" onclick="imgCrop('${id}');" value="<s:message code='crop'/>"/></td>
        </tr>
      </table>      
     	<f:hidden id="t_${id}" value="${(!empty thumbnail&&thumbnail=='true') ? 'true' : 'false'}"/>
     	<f:hidden id="tw_${id}" value="${(!empty thumbnailWidth) ? thumbnailWidth : '116'}"/>
     	<f:hidden id="th_${id}" value="${(!empty thumbnailWidth) ? thumbnailWidth : '86'}"/>
    </td>
    <td width="55%" align="center" valign="middle">
      <img id="img_${id}" style="display:none;"/>
		</td>
	</tr>
</table>
<script type="text/javascript">
fn_${id}("${value}");
</script>

