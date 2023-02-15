<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	MemberDto login = (MemberDto)session.getAttribute("login");
	if(login == null){
		%>
		<script>
		alert('로그인 해 주십시오');
		location.href = "login.jsp";
		</script>
		<%
	}	
%>  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<body>

<h1>글쓰기</h1>

<div align="center">
<form action="bbswriteAf.jsp" id="frm" method="post">
<table border="1">
<col width="200"><col width="400">
	<tr>
		<th>아이디</th>
		<td>
			<input type="text" name="id" size="50px" value="<%=login.getId()%>" readonly="readonly">
		</td>
	</tr>
	<tr>
		<th>제목</th>
		<td>
			<input type="text" id="title" name="title" size="50px">
		</td>
	</tr>
	<tr>
		<th>내용</th>
		<td>
			<textarea rows="30" id="content" cols="50px" name="content"></textarea>
		</td>
	</tr>
</table>
<br><br>
<button type="button">글쓰기</button>
</form>
</div>

<script type="text/javascript">
$(document).ready(function () {
	$("button").click(function () {
		
		if($("#title").val().trim() == ""){
			alert("제목을 기입해 주세요");
			return;
		} else if($("#content").val().trim() == ""){
			alert("내용을 기입해 주세요");
			return;
		} else{
			$("#frm").submit();	
		}
	});
});

</script>

</body>
</html>