<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>

<style type="text/css">
body{
	background-color: #EFDCCE; 
}

.center{
	margin: auto;
	width: 65%;
	border: 3px solid #8f847b;
	padding: 20px;
	padding-bottom: 20px;
}

#loginBox{
	margin: auto;
	margin-top: 100px;
	width: 450px;
	height: 400px;
	background-color: #ffffff;
}

#title{
	padding: 10px;
	color: #2f2c29;
}

#chk_save_id{
	
}

th{
	color: #5f5852;
	background-color: #efced1;
	padding-right: 10px;
	padding-left: 10px;
}

#loginBtn{
	width: 330px;
	height: 40px;
	margin-top: 20px;
	background-color: #2f2c29;
	color: #ffffff;
}

</style>

</head>
<body>

<div id="loginBox" align="center">
	<div id = "title">
		<h1><b><i>Login page</i></b></h1>
	</div>
	<form action="loginAf.jsp" method="post">
	<div class="center">
		<table style="border-spacing: 10px">
			<tr>
				<th>ID</th>
				<td>
					<input type="text" id="id" name="id"><br>
				</td>
			</tr>
			<tr>
				<th>Password</th>
				<td>
					<input type="password" name="pwd">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="checkbox" id="chk_save_id">save ID
				</td>
			</tr>
		</table>
	</div>
	<div>
		<input type="submit" id="loginBtn" value="Login">
	</div>
	<div>
		<h5>Have no account? <a href="regi.jsp">Sign up</a></h5>
	</div>
	</form>
</div>

<script type="text/javascript">
/*
	cookie	:	id저장, pw저장 == String	client
	session	:	login한 정보 == Object	server
 */
 
let user_id = $.cookie("user_id");	// 쿠키 가져옴

if(user_id != null){	// 저장한 id가 있음
	$("#id").val(user_id);
	$("#chk_save_id").prop("checked", true);
}
 
$("#chk_save_id").click(function() {
	if($("#chk_save_id").is(":checked")){	// id 저장 체크할때,
		if($("#id").val().trim() == ""){	// id에 빈글자
			alert("id를 입력해 주십시오");
			$("#chk_save_id").prop("checked", false);
		}
		else{	// 제대로 입력해서 cookie id 저장
			$.cookie("user_id", $("#id").val().trim(), { expires:7, path:'./'});
		}
	}
	else{					// id 저장 체크 해제할때,
		$.removeCookie("user_id", {path:'./'});
	}
}); 

</script>

</body>
</html>