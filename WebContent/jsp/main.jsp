<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../elements/head.jsp"></jsp:include>
<script>
	function delconfirm(id){
		var res = confirm('确定删除吗?');
		if(res){
			location.href='/bbsdemo/MessageServlet?action=delMsg&msgid='+id;
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
			<span><a href="/bbsdemo/UploadServlet?action=listFile">文件空间</a></span>
			<span><a href="/bbsdemo/jsp/upload.jsp">上传文件</a></span>
			<span><a href="/bbsdemo/UserServlet?action=logout">退出</a></span>
		</div>
		<div class="content">
	    <div class="messageList">
	    	<c:if test="${page==null}">
			<c:redirect url="/MessageServlet">
			    <c:param name="username" value="${sessionScope.loginuser.userName}"></c:param>
				<c:param name="action" value="listMsg"></c:param>
			</c:redirect>
		</c:if>
		<c:if test="${page.totalSize==0}">
		   	   	  <ul class="page-spliter">
		   	   	  <p>暂无消息</p>
		   	   	  </ul>
		   	   </c:if>
		<c:forEach items="${page.listResult}" var="msg">
		   	   	  	<ul class="page-spliter">
						<li class='${msg.state==0?"unReaded":""}'>
						<p><strong><a href="/bbsdemo/MessageServlet?action=showMsg&msgid=${msg.msgid}">${msg.title}</a></strong>&nbsp;
						<c:if test="${fn:length(msg.msgcontent) > 8}">
							${fn:substring(msg.msgcontent,0,7)}...
						</c:if>
						<c:if test="${fn:length(msg.msgcontent) <= 8}">
							${msg.msgcontent}
						</c:if>
						</p>
						<em>
							<a href="javascript:void(0)" onclick="delconfirm(${msg.msgid})">删除</a>&nbsp;
							<a href="/bbsdemo/UserServlet?action=findUsers&sendto=${msg.username}&msgid=${msg.msgid}">回信</a>
							&nbsp;${msg.msg_create_date}
						</em>
						</li>
					</ul>
		</c:forEach>
	    </div>
	    </div>
	</div>
	<c:url var="url" value="/MessageServlet?username=${loginuser.userName}">
		<c:param name="action" value="listMsg"></c:param>
	</c:url>
	<div style="width:850px;height:30px;margin:auto;text-align:center">
	<span>页数:${page.pageIndex}/${page.pageCount}</span>
	<c:if test="${page.pageIndex>1}">
		<a href="${url}&pageIndex=1">首页</a>&nbsp;
		<a href="${url}&pageIndex=${page.pageIndex-1}">上一页</a>&nbsp;
	</c:if>
	<c:if test="${page.pageIndex<page.pageCount}">
		<a href="${url}&pageIndex=${page.pageIndex+1}">下一页</a>&nbsp;
		<a href="${url}&pageIndex=${page.pageCount}">末页</a>
	</c:if>
	<script>
		function redirect(select,username){
			var pageIndex = select.value;
			location.href='/bbsdemo/MessageServlet?username='+username+'&pageIndex='+pageIndex+
					'&action=listMsg';
		}
	</script>
	<span>跳转至</span>
	<select id = "select" onchange="redirect(this,'${loginuser.userName}')">
		<c:forEach begin="1" step="1" end="${page.pageCount}" varStatus="varS">
			<option value="${varS.count}">${varS.count}</option>
		</c:forEach>
	</select>
	</div>
	<c:remove var="page"/>
</div>
</body>
</html>
