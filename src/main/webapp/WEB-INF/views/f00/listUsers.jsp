<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/common/resourceImport.jsp"%><!--共通部分をインポート  -->
<title>${title}⇒ユーザー一覧</title>
<script type="text/javascript">
	function view(id) {
		var url = "${ctx}/userservice/detail/" + id;
		var request = new XMLHttpRequest();
		request.open("GET", url, true);
		request.setRequestHeader("Content-Type", "application/x-javascript;");
		request.onreadystatechange = function() {
			if (request.readyState == 4) {
				if (request.status == 200) {
					if (request.responseText) {
						document.getElementById("detailDiv").innerHTML = request.responseText;
					}
				}
			}
		};
		request.send(null);
	}
</script>

</head>

<body>
	<jsp:include page="/portal/menu" />

	<wsp:message />

	<div id="userSearchCondition">
		<form id="queryForm" action="${ctx}/user/list">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="table table-bordered table-striped">
				<tr>
					<th>ユーザー名称</th>
					<td><input class="inputTxt" name="name" type="text" id="name"
						value="${query.name}" /></td>
					<th>ログインID</th>
					<td><input class="inputTxt" name="loginId" type="text"
						id="loginId" value="${query.loginId}" /></td>
				</tr>

				<tr>
					<th></th>
					<td colspan="3"><input type="submit" value="検索" /><input
						type="button" value="新規"
						onclick="window.open().location.href='${ctx}/user/edit?id='" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="userList" style="margin-top: 10px">
		<table class="table table-bordered table-striped">
			<tr>
				<td>項番</td>
				<td>名前</td>
				<td>ログインユーザー</td>
				<td>メールアドレス</td>
				<td>TEL</td>
				<td>携帯</td>
				<td>事業部門</td>
				<td>作成時間</td>
				<td>更新時間</td>
				<td>操作</td>
			</tr>
			<c:forEach var="user" items="${userList}" varStatus="status">
				<tr>
					<td>${status.index+1}</td>
					<td>${user.name}</td>
					<td>${user.loginId}</td>
					<td>${user.email}</td>
					<td>${user.phone}</td>
					<td>${user.mobile}</td>
					<td>${user.department}</td>
					<td>${user.createTime}</td>
					<td>${user.updateTime}</td>
					<td><a href="${ctx}/user/edit?id=${user.id}" target="_blank">update</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="${ctx}/user/delete/${user.id}">delete</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="javascript:view(${user.id})">view</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="detailDiv" style="margin-top: 10px"></div>

</body>
</html>
