<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="0" width="100%">
		<tr>
			<td>
				<a href="${contextPath }/main.do">
					<img alt="이미지" src="${contextPath }/resources/images/health.jpg" width="250px">
				</a>
			</td>
			<td><font size="30">[헤더]보약!!</font></td>
			<td>
				<c:choose>
					<c:when test="${isLogOn==true && member != null }">
						<h3>환영 합니다 ${member.name }님 </h3>
						<a href="${contextPath }/member/logout.do"><h3>로그아웃</h3></a>
					</c:when>
					<c:otherwise>
						<a href="${contextPath }/member/loginForm.do"><h3>로그인</h3></a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
</body>
</html>