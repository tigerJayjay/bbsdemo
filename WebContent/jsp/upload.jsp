<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../elements/head.jsp"></jsp:include>
<script>
    function check(){
    	var filename = document.getElementById("filename");
    	if(filename.value == ""){
    		alert('未选择任何文件！');
    		return false;
    	}
    }
</script>
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
		    		<ul class="page-spliter"><p>${msg}</p></ul>
		    		<c:remove var="msg"/>
					<ul class="page-spliter">
					     <p>
					     	<form action="/bbsdemo/UploadServlet?action=upload" method="post" enctype="multipart/form-data" onsubmit="return check()">
					     	       <input type="hidden" value="${loginuser.userName}" name="username">
					     	       <input type="file" name="filename" id="filename">
					     	       <input type="submit" value="开始上传"/>
					     	</form>
					     </p>
					</ul>
		    </div>
	    </div>
	</div>

</div>
</body>
</html>