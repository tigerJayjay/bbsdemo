<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../elements/head.jsp"></jsp:include>
<body>
<div id="main">
	<div class="mainbox">
		<div class="title myMessage png"></div>		
		<div class="menu">
			<span>当前用户：<a href="main.jsp">${sessionScope.loginuser.userName}</a></span>
			<span><a href="/bbsdemo/UserServlet?action=findUsers">发短消息</a></span>
			<span><a href="/bbsdemo/jsp/online.jsp">查看在线列表</a></span>
			<span><a href="/bbsdemo/jsp/main.jsp">返回首页</a></span>
			<span><a href="/bbsdemo/UserServlet?action=logout">退出</a></span>
		</div>
		<div class="content">
		    <div class="messageList">
				<c:forEach items="${onlineList}" var="user">
					<ul class="page-spliter">
					   <c:choose>
					   	 <c:when test="${user.userName==loginuser.userName }">
					     </c:when>
						 <c:otherwise>
						 	<p><a href="/bbsdemo/UserServlet?action=findUsers&sendto=${user.userName}">${user.userName}</a></p>
						 </c:otherwise>
					   </c:choose>
					</ul>
				</c:forEach>
				<c:if test="${fn:length(onlineList) <= 1}">
		    	  <ul class="page-spliter">
		    	  	<p>无好友在线!</p>
		    	  </ul>
		    	</c:if>
		    </div>
	    </div>
	</div>

</div>
</body>
</html>