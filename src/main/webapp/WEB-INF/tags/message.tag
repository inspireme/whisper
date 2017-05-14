<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty messages}">
	<c:forEach var="msg" items="${messages}">
     ${fn:escapeXml(msg)} 
	</c:forEach>
</c:if>
