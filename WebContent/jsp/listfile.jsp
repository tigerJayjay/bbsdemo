<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../elements/head.jsp"></jsp:include>
<body>
<script>
	function refresh(){
		location.href='/bbsdemo/jsp/listfile.jsp?ran='+Math.random();
	}
</script>
<div id="main">
	<div class="mainbox">
		<div class="title myMessage png"></div>		
		<div class="menu">
			<span>当前用户：<a href="main.jsp">${sessionScope.loginuser.userName}</a></span>
			<span><a href="/bbsdemo/UserServlet?action=findUsers">发短消息</a></span>
			<span><a href="/bbsdemo/jsp/online.jsp">查看在线列表</a></span>
			<span><a href="/bbsdemo/jsp/main.jsp">返回首页</a></span>
			<span><a href="/bbsdemo/UserServlet?action=logout">退出</a></span>
			<span><a href="javascript:void(0)" onclick="refresh()">刷新</a></span>
		</div>
		<div class="content">
		    <div class="messageList">
		        <c:if test="${empty mapFile }">
		        	<ul class="page-spliter">
					     <p>列表空间为空!</p>
					</ul>
		        </c:if>
		    	<c:forEach items="${mapFile}" var="file">
		    	  <c:url var="url" value="/UploadServlet">
					<c:param name="action" value="load"></c:param>
					<c:param name="fileName" value="${file.key}"></c:param>
				 </c:url>
		    		<ul class="page-spliter">
					     <p>文件名:${file.value}&nbsp;<a href="${url}">下载</a></p>
					</ul>
		    	</c:forEach>
		    </div>
	    </div>
	</div>

</div>
</body>
</html>