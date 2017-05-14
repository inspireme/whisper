<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/common/resourceImport.jsp"%><!--共通部分をインポート  -->
<title>${title}⇒ユーザー照会</title>

<script type="text/javascript">
	function ajaxSave(id) {
		var id = document.getElementById("id").value;
		var name = document.getElementById("name").value;
		var loginId = document.getElementById("loginId").value;
		var csrfName =  document.getElementById("csrfName").value;
		var csrfToken =  document.getElementById("csrfToken").value;

		var url = "${ctx}/userservice/save/";
		var request = new XMLHttpRequest();
		request.open("POST", url, true);
		request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		request.send(JSON.stringify({"id":id,"name":name,"loginId":loginId,csrfName:csrfToken}));
		request.onreadystatechange = function() {
			if (request.readyState == 4) {
				if (request.status == 200) {
					if (request.responseText) {
						alert("success");
					}
				}
			}
		};
		request.send(null);
	}
</script>

</head>
<body>
	<wsp:message />
<div class="login-card">
	<div class="login-form">
		<form id="queryForm" action="${ctx}/user/save" method="post">
			<input class="inputTxt" name="id" type="hidden" id="id"
				value="${user.id}" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>ユーザー名称</th>
					<td><input class="inputTxt" name="name" type="text" id="name"
						value="${user.name}" /></td>

				</tr>
				<tr>
					<th>ログインID</th>
					<td><input class="inputTxt" name="loginId" type="text"
						id="loginId" value="${user.loginId}" /></td>
				</tr>
				<tr>
					<th>パスワード</th>
					<td><input class="inputTxt" name="password" type="text"
						id="password" value="${user.password}" /></td>
				</tr>
				<tr>
					<th>メールアドレス</th>
					<td><input class="inputTxt" name="email" type="text"
						id="email" value="${user.email}" /></td>
				</tr>
				<tr>
					<th>TEL</th>
					<td><input class="inputTxt" name="phone" type="text"
						id="phone" value="${user.phone}" /></td>
				</tr>
				<tr>
					<th>携帯</th>
					<td><input class="inputTxt" name="mobile" type="text"
						id="mobile" value="${user.mobile}" /></td>
				</tr>
				<tr>
					<th>事業部門</th>
					<td><input class="inputTxt" name="department" type="text"
						id="department" value="${user.department}" /></td>
				</tr>
				<tr>
					<th>
					<input type="hidden" name="csrfName" id="csrfName"
							value="${_csrf.parameterName}" />
					<input type="hidden" name="${_csrf.parameterName}" id="csrfToken"
							value="${_csrf.token}" /></th>
					<td colspan="3"><input type="submit" value="登録" /><input type="button" id="saveBtn" value="ajaxSave" onclick="ajaxSave();"/></td>
				</tr>
			</table>
		</form>
	</div>
</div>

</body>
</html>
