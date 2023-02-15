<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
	int seq = Integer.parseInt(request.getParameter("seq"));

	BbsDao dao = BbsDao.getInstance();
	BbsDto dto = dao.getBbs(seq); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>게시글 수정</h1>

<div align="center">
<form action="updateBbsAf.jsp?seq=<%=seq %>" method="post">
<table border="1">
<col width="200"><col width="500">
<tr>
	<th>아이디</th>
	<td><%=dto.getId()%></td>
</tr>
<tr>
	<th>제목</th>
	<td>
		<input type="text" name="title" value="<%=dto.getTitle()%>">
	</td>
</tr>
<tr>
	<th>내용</th>
	<td>
		<textarea rows="30" cols="50px" name="content"><%=dto.getContent()%></textarea>
	</td>
</tr>
</table>
<br><br>
<button type="submit">수정완료</button>
</form>
</div>

</body>
</html>