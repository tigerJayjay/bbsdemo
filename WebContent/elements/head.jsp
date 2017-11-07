<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<c:if test="${empty loginuser}">
<script>
	alert("您没有登录或者已经退出，请重新登录!");
	open("index.jsp","_self");	
</script>
</c:if>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<title>在线短信平台</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/sms.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.js"></script>
</head>

