<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
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
	
	int seq = Integer.parseInt(request.getParameter("seq"));

	BbsDao dao = BbsDao.getInstance();
	if (dao.readcheck(seq, login.getId())){
		dao.readcountBbs(seq, login.getId());
	}
	BbsDto dto = dao.getBbs(seq);
%>  
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>상세 글보기</h1>

<div align="center">
<table border="1">
<col width="200"><col width="400">
	<tr>
		<th>작성자</th>
		<td>
			<%=dto.getId()%>
		</td>
	</tr>
	<tr>
		<th>제목</th>
		<td>
			<%=dto.getTitle() %>
		</td>
	</tr>
	<tr>
		<th>작성일</th>
		<td>
			<%=dto.getWdate() %>
		</td>
	</tr>
	<tr>
		<th>조회수</th>
		<td>
			<%=dto.getReadcount() %>
		</td>
	</tr>
	<tr>
		<th>답글정보</th>
		<td>
			<%=dto.getRef() %>-<%=dto.getStep() %>-<%=dto.getDepth() %>
		</td>
	</tr>
	<tr height="400">
		<th>내용</th>
		<td>
			<%=dto.getContent() %>
		</td>
	</tr>
</table>
<br><br>

<button type="button" onclick="answerBbs(<%=dto.getSeq() %>)" >답글</button>
<button type="button" onclick="location.href='bbslist.jsp'">글목록</button>

<%
if(dto.getId().equals(login.getId())){
	%>
	<button type="button" onclick="deleteBbs(<%=dto.getSeq() %>)">삭제</button>
	<button type="button" onclick="updateBbs(<%=dto.getSeq() %>)">수정</button>
	<%
}
%>
</div>

<script type="text/javascript">
function answerBbs(seq) {
	location.href = "answer.jsp?seq=" + seq;
}
function deleteBbs(seq) {
	location.href = "deleteBbsAf.jsp?seq=" + seq;
}
function updateBbs(seq) {
	location.href = "updateBbs.jsp?seq=" + seq;
}

</script>

</body>
</html>