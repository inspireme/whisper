<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="wsp" tagdir="/WEB-INF/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="srcCtx" value="${pageContext.request.contextPath}/resource"/>
<c:set var="title" value="whisper-demo"></c:set>
<c:set var="copyRightStatement" value="whisper"></c:set>

<!-- DEBUG INFO START-->
<!-- <%=request.getHeader("Referer") %> -->
<!-- ${pageContext.request.servletPath} -->
<!-- DEBUG INFO END-->





