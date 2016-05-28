<%@ tag pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="s" uri="http://www.springframework.org/tags" %><%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ attribute name="id" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="name" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="value" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="required" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="scale" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="exact" type="java.lang.String" required="false" rtexprvalue="true"%>
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
		<td width="320">
  		<div>
  			<input type="text" id="${id}" name="${name}" value="${value}" onchange="fn_${id}(this.value);"<c:if test="${!empty required && required=='true'}"> class="required"</c:if> style="width:300px;"/>
  		</div>
      <div style="margin-top:2px;">
        <input id="${name}Button" type="button" value="<s:message code='choose'/>"/> &nbsp;
        <script type="text/javascript">
        $(function() {
          Cms.f7.uploads("${name}","${name}",{
            settings: {"title": "<s:message code="webFile.chooseUploads"/>"},
            callbacks: {"ok": function(src){
            	fn_${id}(src);
            }}
          });
        });
        </script>
        <input type="button" onclick="imgCrop('${id}');" value="<s:message code='crop'/>"/> &nbsp;
        <span id="${name}SwfButton"></span><input type="button" value="<s:message code="upload"/>" class="swfbutton"/> &nbsp;
        <input id="${name}SwfCancel" type="button" value="<s:message code="cancel"/>" onclick="${name}SwfUpload.cancelQueue();" disabled="disabled"/>
        <script type="text/javascript">
	      var ${name}SwfUpload = Cms.swfUploadImage("${name}",{
	    	  jsessionid: "<%=request.getSession().getId()%>",
	        file_size_limit: "${GLOBAL.upload.imageLimit}",
	        file_types: "${GLOBAL.upload.imageTypes}"
	      });
	      </script>
      </div>
      <div style="margin-top:2px;">
        <s:message code="width"/>: <f:text id="w_${id}" value="${width}" default="210" style="width:70px;"/> &nbsp;
        <s:message code="height"/>: <f:text id="h_${id}" value="${height}" default="140" style="width:70px;"/>
      </div>
      <div style="margin-top:2px;">
        <label><input type="checkbox" id="s_${id}"<c:if test="${empty scale || scale=='true'}"> checked="checked"</c:if>/><s:message code="scale"/></label> &nbsp;
        <label><input type="checkbox" id="e_${id}"<c:if test="${!empty exact && exact=='true'}"> checked="checked"</c:if>/><s:message code="exact"/></label> &nbsp;
        <label><input type="checkbox" id="wm_${id}"<c:if test="${!empty watermark && watermark=='true'}"> checked="checked"</c:if>/><s:message code="watermark"/></label>
      </div>
      <f:hidden id="t_${id}" value="${(!empty thumbnail) ? thumbnail : 'false'}"/>
     	<f:hidden id="tw_${id}" value="${(!empty thumbnailWidth) ? thumbnailWidth : '116'}"/>
     	<f:hidden id="th_${id}" value="${(!empty thumbnailWidth) ? thumbnailWidth : '77'}"/>
    </td>
    <td width="0">
      <div id="${name}SwfProgress" style="position:absolute;"></div>
    </td>
    <td align="center" valign="middle">
      <img id="img_${id}" style="display:none;"/>
		</td>
	</tr>
</table>
<script type="text/javascript">
fn_${id}("${value}");
</script>

