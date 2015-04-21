<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<style type="text/css">
html,body{height:100%;margin:0;padding:0;overflow:auto;}

#top{height:76px;width:100%;top:0;left:0;position:absolute;}
#header{height:46px;background:url('${ctx}/back/images/bg.png');padding:0 5px;}
#logo{float:left;height:100%;width:50%;background:url('${ctx}/back/images/admin_logo.png') no-repeat 5px center;}
#logout{float:right;width:50px;height:100%;display:block;background:url('${ctx}/back/images/logout.png') no-repeat center center;}
#homepage{float:right;width:50px;height:100%;display:block;background:url('${ctx}/back/images/home.png') no-repeat center center;}
#welcome{float:right;height:100%;line-height:46px;padding:0 10px 0 20px;background:url('${ctx}/back/images/user.png') no-repeat 0 center;}
#sites{float:right;padding:12px 10px 0 0;}

#nav-menu{height:30px;padding-left:15px;overflow:hidden;background:url("${ctx}/back/images/top_menu_bg.png");}
.nav-menu-li{float:left;height:30px;line-height:30px;}
.nav-menu-a{float:left;padding:0 20px;font-weight:bold;color:#fff;font-size:14px;}
.nav-menu-a:link,.nav-menu-a:visited,.nav-menu-a:hover,.nav-menu-a:active{text-decoration:none;outline-style:none;}
.nav-menu-sep{width:2px;background:url("${ctx}/back/images/sep.png") no-repeat;}
.nav-menu-hover{background-color:#718fe4;}
.nav-menu-select{background-color:#637dc7;}

.sub-menu{display:none;position:absolute;z-index:100;background-color:#fff;}
.sub-menu-top{line-height:6px;padding-left:10px;background:url("${ctx}/back/images/submenu_top.png");}
.sub-menu-top-nested{background:url("${ctx}/back/images/submenu_top.png") right;}
.sub-menu-bottom{line-height:6px;padding-left:10px;background:url("${ctx}/back/images/submenu_bottom.png");}
.sub-menu-bottom-nested{background:url("${ctx}/back/images/submenu_bottom.png") right;}
.sub-menu-li{line-height:26px;border-left:1px solid #ccc;border-right:1px solid #ccc;}
.sub-menu-div{padding:0 20px;cursor:pointer;font-size:12px;font-weight:normal;color:#333;}
/* .sub-menu-a:link,.sub-menu-a:visited,.sub-menu-a:hover,.sub-menu-a:active{text-decoration:none;outline-style:none;} */
.sub-menu-hover{background-color:#718fe4;color:#fff;}

#container{position:absolute;width:100%;top:76px;bottom:0;}

/*for ie6 start*/
* html{padding-top:76px;}
* html #top{height:76px;position:absolute;top:0;width:100%;}
* html #container{height:100%;}
/*for ie6 end*/
</style>
<script type="text/javascript">
//后台首页需显示在顶层框架中
if(top!=this){top.location=this.location;}

$(function(){
	$("#nav-menu > li > a").each(function(){
		$(this).hover(function () {
		  $(this).addClass("nav-menu-hover");
    }, function () {
      $(this).removeClass("nav-menu-hover");
    });
	});
	$(".nav-menu-a,.sub-menu-div").each(function(){
		$(this).click(function() {
			$(this).blur();
			$("#nav-menu a").each(function() {
				$($(this).attr("menu")).removeClass("nav-menu-select");
			});
			$($(this).attr("menu")).addClass("nav-menu-select");
		});
	});
	
	var delayTime=[];	
	$("#nav-menu > li > a").each(function(index) {
		var a = $(this);
		var sm = $($(this).attr("submenu"));
		if(sm.length>0) {
			a.hover(function() {
				clearTimeout(delayTime[index]);
				sm.show();
				sm.positionOf(a,{spacing:1});
			},function() {
				delayTime[index] = setTimeout(function() {
					sm.hide();
				},100);
			});
			sm.hover(function() {
				clearTimeout(delayTime[index]);
			},function() {
				delayTime[index] = setTimeout(function() {
					sm.hide();
				},100);				
			});
			sm.find(".sub-menu-div").each(function() {
				$(this).hover(function() {
					$(this).addClass("sub-menu-hover");
				},function() {
					$(this).removeClass("sub-menu-hover");					
				});
			});
		}
	});

});
var leftFrame,centerFrame;
function navigation(leftUrl,centerUrl) {
	if(!leftFrame) {
		var cf = window.frames['container'].frames;
	  centerFrame=cf['center'];
	  leftFrame=cf['left'];  
	}
	if(centerUrl) {
	  centerFrame.location.href = centerUrl;
	}
	if(leftUrl) {
	  leftFrame.location.href = leftUrl;
	}
}
function keepSession() {
	$.get("${ctx}/keep_session.servlet?d="+new Date()*1);
}
setInterval("keepSession()",600000);
</script>
</head>
<body>
<div id="top">
	<div id="header">
	  <div id="logo"></div>
	  <a id="logout" href="logout.do" title="退出系统"></a>
	  <a id="homepage" href="${site.url}" target="_blank" title="网站首页"></a>
	  <div id="welcome">欢迎 <b>${user.username}</b></div>
	  <c:if test="${fn:length(siteList) gt 0}">
	  <div id="sites">
	  	<c:choose>
	  	<c:when test="${fn:length(siteList) le 1}">
	  		<div style="padding-top:2px;">${site.name}(${site.org.name})</div>
	  	</c:when>
	  	<c:otherwise>
		  	<select onchange="location.href='index.do?_site='+$(this).val();">
			  <c:forEach var="s" items="${siteList}">
			  	<option value="${s.id}"<c:if test="${site.id==s.id}"> selected="selected"</c:if>>${s.name}(${s.org.name})</option>
			  </c:forEach>
			  </select>
	  	</c:otherwise>
	  	</c:choose>		  
	  </div>
	  </c:if>
	  <div style="clear:both;"></div>
	</div>
	<ul id="nav-menu">
	<c:set var="firstNav" value="${true}"/>
	<c:forEach var="m" items="${menus}">
		<c:set var="hasPermission" value="${false}"/>
		<c:if test="${empty m.perm}"><c:set var="hasPermission" value="${true}"/></c:if>
		<c:if test="${!hasPermission}"><shiro:hasPermission name="${m.perm}"><c:set var="hasPermission" value="${true}"/></shiro:hasPermission></c:if>
		<c:if test="${hasPermission}">
			<c:set var="centerUrl" value="${m.centerUrl}"/>
			<c:set var="hasSubPermission" value="${false}"/>
			<c:forEach var="sub" items="${m.children}">
				<c:if test="${!hasSubPermission}">
				<c:if test="${empty sub.perm}"><c:set var="hasSubPermission" value="${true}"/></c:if>
				<c:if test="${!hasSubPermission}"><shiro:hasPermission name="${sub.perm}"><c:set var="hasSubPermission" value="${true}"/></shiro:hasPermission></c:if>
				<c:if test="${hasSubPermission}">
					<c:set var="centerUrl" value="${sub.centerUrl}"/>
				</c:if>
				</c:if>
			</c:forEach>
			<c:if test="${!firstNav}"><li class="nav-menu-li nav-menu-sep"></li></c:if>
			<li class="nav-menu-li"><a href="javascript:navigation('${m.leftUrl}','${centerUrl}');" class="nav-menu-a${firstNav ? ' nav-menu-select' : ''}" id="menu-${m.id}" menu="#menu-${m.id}" submenu="#sm-${m.id}"><s:message code="${m.name}" text="${m.name}"/></a></li>
			<c:set var="firstNav" value="${false}"/>
		</c:if>
	</c:forEach>
  </ul>
</div>
<c:forEach var="m" items="${menus}">
	<c:set var="hasPermission" value="${false}"/>
	<c:if test="${empty m.perm}"><c:set var="hasPermission" value="${true}"/></c:if>
	<c:if test="${!hasPermission}"><shiro:hasPermission name="${m.perm}"><c:set var="hasPermission" value="${true}"/></shiro:hasPermission></c:if>
	<c:if test="${hasPermission}">
		<c:if test="${fn:length(m.children)>0}">
		<ul id="sm-${m.id}" class="sub-menu">
		<li class="sub-menu-top"><div class="sub-menu-top-nested">&nbsp;</div></li>
		<c:forEach var="m" items="${m.children}">
			<c:set var="hasPermission" value="${false}"/>
			<c:if test="${empty m.perm}"><c:set var="hasPermission" value="${true}"/></c:if>
			<c:if test="${!hasPermission}"><shiro:hasPermission name="${m.perm}"><c:set var="hasPermission" value="${true}"/></shiro:hasPermission></c:if>
			<%--全局功能须有super角色 --%>
			<c:if test="${hasPermission && (fn:startsWith(m.perm,'core:user_global:') || fn:startsWith(m.perm,'core:org_global:') || fn:startsWith(m.perm,'core:site:') || fn:startsWith(m.perm,'core:conf_global:'))}">
				<shiro:lacksRole name="super"><c:set var="hasPermission" value="${false}"/></shiro:lacksRole>
			</c:if>
			<c:if test="${hasPermission}">
				<li class="sub-menu-li"><div onclick="navigation('${m.leftUrl}','${m.centerUrl}');" class="sub-menu-div" menu="#menu-${m.parent.id}"><s:message code="${m.name}" text="${m.name}"/></div></li>			
			</c:if>
		</c:forEach>
		<li class="sub-menu-bottom"><div class="sub-menu-bottom-nested">&nbsp;</div></li>
		</ul>
		</c:if>
	</c:if>
</c:forEach>

<div id="container">
  <iframe name="container" style="position:absolute;width:100%;height:100%;" frameborder="0" src="container.do"></iframe>
</div>
</body>
</html>