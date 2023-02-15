<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	int seq = Integer.parseInt(request.getParameter("seq"));

	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	// back-end
	BbsDao dao = BbsDao.getInstance();
	boolean isS = dao.updateBbs(seq, title, content);
	
	if(isS){
		%>
		<script type="text/javascript">
		alert("게시글 수정을 완료했습니다");
		location.href = "bbsdetail.jsp?seq=<%=seq%>";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
		alert("게시글 수정을 실패했습니다");
		location.href = "bbsdetail.jsp?seq=<%=seq%>";
		</script>
		<%
	}
%>    