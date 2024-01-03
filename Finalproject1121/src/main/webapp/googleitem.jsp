<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GoogleSearch</title>
<style type="text/css">
#padding{
	padding: 0px 0px 15px 15px;
}
.footer-image {
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 300px; 
            height: auto; 
}
a {
	color: #0B173B;
	font-size: 30px;
	text-decoration: none;
}
a:hover{
text-decoration:underline;
}
.border-style {
	border-radius: 70px/90px;
}
</style>
</head>
<body>
<body style='background-color: #ffffff'>
<form action='${requestUri}' method='get'>

	<div style='position: absolute;margin-top:190px;margin-left:50px'>
		<%
		String[][] orderList = (String[][]) request.getAttribute("query");
		for (int i = 0; i < orderList.length; i++) {
			String s=orderList[i][1];
			//s=s.substring(7);
			System.out.println("--------");
			System.out.println(s);
		%>
		
		<a href='<%=s%>'><%=orderList[i][0]%> </a> <br>連結<br>
		<br>
		<%
}
%>
	</div>
	<div>
		<img src="images/icon.png"
			style='position: absolute; width: 150px; height: 70px; left: 50%; top: 46%; margin-top: -280px; margin-left: -590px'>
	</div>
		<div>
		<input type='text' class="border-style" id="padding" name='keyword'
			style='font-size: 150%; position: absolute; left: 50%; top: 47.5%; margin-top: -280px; margin-left: -400px; width: 800px; height: 25px'
			placeholder = '請輸入關鍵字' value='<%=request.getParameter("keyword")%>'/>
	</div>
	<div>
		<img src="images/copyright.png" alt="Footer Image" class="footer-image">
	</div>
	

</form>

</body>
</html>

