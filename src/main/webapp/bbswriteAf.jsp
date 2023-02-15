<%@page import="dto.MemberDto"%>
<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	request.setCharacterEncoding("utf-8");	
	
	String id = request.getParameter("id");
	String title = request.getParameter("title");
	String content = request.getParameter("content");

	// back-end
	BbsDao dao = BbsDao.getInstance();
	
	boolean isS = dao.addBbs(new BbsDto(id, title, content));
	
	if(isS == true){
		%>
		<script type="text/javascript">
		alert("글쓰기 완료했습니다");
		location.href = "bbslist.jsp";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
		alert("글쓰기 실패했습니다");
		location.href = "bbslist.jsp";
		</script>
		<%
	}
%>