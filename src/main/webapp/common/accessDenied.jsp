<%@ page session="false"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>AccessDenied page</title>
</head>
<body bgcolor="#F27398">
	<h1>

		 <strong>${user}</strong>様, 権限がない為、アクセスすることができません。システム管理者まで連絡してください。
		<%
		HttpSession session = request.getSession();
		if (session.getAttribute("SPRING_SECURITY_LAST_USERNAME") == null){
			response.sendRedirect(request.getContextPath()+"/portal/login");
		}

//TODO systemUtil

	%>
	</h1>
	<input type="button" onclick="javascript: history.back();" value="戻る">
</body>
</html>