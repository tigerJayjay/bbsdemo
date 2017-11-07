<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../elements/head.jsp"  %>
<script type="text/javascript">
function check(){
	var title = document.getElementById("title");
	var content = document.getElementById("content");
	
	if(title.value==""){
		alert('标题不能为空!');
		return false;
	}
	if(content.value==""){
		alert('内容不能为空!');
		return false;
	}
}
</script>
  
 <body>
 <form action="/bbsdemo/MessageServlet?action=send" method="post" onsubmit="return check()">
	<div id="main">
		<div class="mainbox">			
			<div class="menu">
				<span>当前用户：<a href="main.jsp">${sessionScope.loginuser.userName}</a></span>
				<span><a href="/bbsdemo/UserServlet?action=findUsers">发短消息</a></span>
				<span><a href="/bbsdemo/jsp/main.jsp">返回首页</a></span>
				<span><a href="/bbsdemo/UserServlet?action=logout">退出</a></span>
			</div>
			<div class="content">
				<div class="message">
						<div class="tmenu">
							<ul class="clearfix">
								<li>
									发送给：
									<select name="toUser">
									    <c:if test="${userList==null}">
									    	<c:redirect url="/UserServlet">
									    		<c:param name="action" value="findUsers"></c:param>
									    	</c:redirect>
									    </c:if>
						  	 			<c:forEach items="${userList}" var="user">
						  	 			   <c:choose>
						  	 			      <c:when test="${!empty sendto && user.userName==sendto}">
						  	 			      		<option value="${sendto}" selected="selected">${sendto}</option>
						  	 			      </c:when>
						  	 			      <c:otherwise>
						  	 			      		<option value="${user.userName}">${user.userName}</option>
						  	 			      </c:otherwise>
						  	 			   </c:choose>
						  	 			</c:forEach>
						  	 		</select>
								</li>								
								<li>标题：<input type="text" name="title" id="title" value="${msg.title}"/></li>
							</ul>
						</div>
						<div class="view">
							<textarea name="content" id="content">${msg.msgcontent}</textarea>
							<div class="send"><input type="submit" name="submit" value=" " /></div>
						</div>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>
