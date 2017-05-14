<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.whisper.common.util.SystemUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header style="background-color: gray">
	<table>
		<tr>
			<c:forEach var="authority" items="${authorityList}"
				varStatus="status">
				<td><a href="${pageContext.request.contextPath}${authority.authUrl}">${authority.displayName}</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
				</td>
			</c:forEach>

			<td>
				<%
					out.println(SystemUtil.whoAmI());
				%>様|&nbsp;&nbsp;&nbsp;
			</td>
			<td style="width: 30%"><c:url value="/j_spring_security_logout"
					var="logoutUrl" />
				<form id="logout" action="${logoutUrl}" method="post">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form> <c:if test="${pageContext.request.userPrincipal.name != null}">
					<a href="javascript:document.getElementById('logout').submit()">ログアウト</a>
				</c:if></td>
		</tr>
	</table>
</header>