<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>index</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
      更多内容 <a href="http://liutime.com">欢迎访问遛时间</a>
  
  	<form action="login"  method="post">
  	<table>
  		<tr>
  			 
  			<th>账号</th>
  			<td><input type="text"  name="name" /> </td>
  		</tr>
  		<tr>
  			 
  			<th>mm</th>
  			<td><input type="text"  name="password" /></td>
  		</tr>
  		<tr>
  			<td colspan="2"><input type="submit" value="添加"> </td>
  		</tr>
  	</table>
  	</form>
  
  </body>
</html>