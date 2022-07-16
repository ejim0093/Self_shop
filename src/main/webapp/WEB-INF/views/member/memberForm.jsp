<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 창</title>
</head>
<body>
	<form action="${contextPath }/member/addMember.do" method="post" >
		<h1 style="text-align: center;">회원 가입창</h1>
		<table align="center">
			<tr>
				<td width="200">
					<p aiign="right">아이디</p>
				</td>
				<td>
					<input type="text" name="id"/>
				</td>
			</tr>
			<tr>
				<td width="200">
					<p aiign="right">비밀번호</p>
				</td>
				<td><input type="password" name="pwd" /></td>
			</tr>
			<tr>
				<td width="200">
					<p aiign="right">이름</p>
				</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td width="200">
					<p aiign="right">이메일</p>
				</td>
				<td><input type="text" name="email" /></td>
			</tr>
			<tr>
				<td width="200">
					<p aiign="right">회원가입</p>
				</td>
				<td><input type="submit" name="등록" value="확인"/></td>
			</tr>
		</table>
	</form>
</body>
</html>