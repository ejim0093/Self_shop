<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<!-- tiles.member.xml의 definition의 하위 태그의 put-attribute 태그의 name이 title인 값을 표시 -->
	<title><tiles:insertAttribute name="title"/></title>
	<style type="text/css">
		#container {
			width: 100%;
			margin: 0px auto;
			text-align: center;
			border: 0px solid #ebedf0;
		}
		#header {
			padding: 5px;
			margin-bottom: 5px;
			border: 0px solid #ebedf0;
			background-color: #f5f7fa;
		}
		#sidebar-left {
			width: 15%;
			height: 700px;
			padding: 5px;
			margin-right: 5px;
			margin-bottom: 5px;
			float: left;
			background-color: #f5f7fa;
			border: 0px solid #bdbcbc;
			font-size: 10px;		
		}
		
		#content {
			width: 75%;
			padding: 5px;
			margin-right: 5px;
			float: left;
			border: 0px solid #bcbcbc;
		}
		#footer {
			clear: both;
			padding: 5px;
			border: 0px solid #bcbcbc;
			background-color: #f5f7fa;
		}
	</style>
</head>
<body>
	<div id="container">
		<div id="header">
			<!-- tiles.member.xml의 definition의 하위 태그의 put-attribute 태그의 name이 header인 값을 표시 -->
			<tiles:insertAttribute name="header"/>
		</div>
		
		<div id="sidebar-left">
			<!-- tiles.member.xml의 definition의 하위 태그의 put-attribute 태그의 name이 body인 값을 표시 -->
			<tiles:insertAttribute name="side"/>
		</div>
		
		<div id="content">
			<!-- tiles.member.xml의 definition의 하위 태그의 put-attribute 태그의 name이 body인 값을 표시 -->
			<tiles:insertAttribute name="body"/>
		</div>
		
		<div id="footer">
			<!-- tiles.member.xml의 definition의 하위 태그의 put-attribute 태그의 name이 body인 값을 표시 -->
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>
</body>
</html>