<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学士后 在线短信平台</title>
<link type="text/css" rel="stylesheet" href="css/sms.css" />
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
</head>

<body>
<div id="regTitle" class="png"></div>
<div id="regForm" class="userForm png">
	<form action="UserServlet?action=regist" onsubmit = "return check()" method="post">
		<dl>
			<dt>用 户 名：</dt>
			<dd><input id="username" type="text" name="username" value='${registuser.username}'/></dd>
			<span id="msg"></span>
			<dt>密　　码：</dt>
			<dd><input id="password" type="password" name="password" value='${registuser.password}'/></dd>
			<dt>确认密码：</dt>
			<dd><input id="affirm"  type="password" name="affirm" value='${registuser.password}'/></dd>
			<dt>邮　　箱：</dt>
			<dd><input id="email" type="text" name="email" value='${registuser.email}'/></dd>
			<c:remove var="registuser" scope="session"/>
		</dl>
		<div class="buttons">
			<input class="btn-reg png" type="submit" name="register" value=" " /><input class="btn-reset png" type="reset" name="reset" value=" " />
		</div>
		<div class="goback"><a href="index.jsp" class="png">返回登录页</a></div>
	</form>
</div>
<script type="text/javascript">
function check(){
	var username = document.getElementById("username");
	var password = document.getElementById("password");
	var affirm = document.getElementById("affirm");
	var email = document.getElementById("email");
	
	if(username.value==""){
		alert('用户名不能为空!');
		return false;
	}
	var reguser = /\s{1,}_{1,}/g;
	if(username.value.match(reguser)!=null){
		alert('用户名不能包含非法字符!');
		return false;
	}
	if(username.value.length<6){
		alert('用户名长度必须大于6');
		return false;
	}
	if(password.value==""){
		alert('密码不能为空!');
		return false;
	}
	var regpwd = /^[a-z0-9A-Z_]{8,}$/;
	if(password.value.match(regpwd)==null){
		alert('密码长度必须是大于8的英文字母、数字或下划线!');
		return false;
	}
	if(affirm.value != password.value){
		alert('两次密码输入不同!')
		return false;
	}
	if(email.value ==""){
		alert('邮箱不能为空!');
		return false;
	}
	var reg = /^[0-9A-Za-z]{5,10}@[0-9A-Za-z]{2,3}.[a-z]{2,3}$/;
	if(email.value.match(reg)==null){
		alert('邮箱格式不正确!');
		return false;
	}
}
$(function(){
	$("#username").on("blur",function(){
		  var username = document.getElementById("username").value;
		  var email = document.getElementById("email").value;
			var url = "${pageContext.request.contextPath}/CheckServlet";
	  	    var data = {"username":username,"email":email};
			$.post(url,data,function(result){
				$("#msg").css("color","red");
				$("#msg").html(result);
			},"text");
	});
})

</script>
</body>
</html>
