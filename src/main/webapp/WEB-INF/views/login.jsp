<%@ page session="false"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>ログイン</title>
<%@ include file="/common/resourceImport.jsp"%><!--共通部分をインポート  -->
</head>

<body>
	<div id="mainWrapper">
		<div class="container">
			<div class="login-card">
				<div class="login-form">
					<form action="${ctx}/portal/dologin" method="post"
						class="form-horizontal">

						<c:if test="${messages != null}">
							<div class="alert alert-danger">
								<p>${messages }</p>
							</div>
						</c:if>

						<div class="input-group input-sm">
							<label class="input-group-addon" for="username"><i
								class="fa fa-user"></i></label> <input type="text" class="form-control"
								id="username" name="j_username" placeholder="アカウントを入力してください。"
								required>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="password"><i
								class="fa fa-lock"></i></label> <input type="password"
								class="form-control" id="password" name="j_password"
								placeholder="パースワードを入力してください。" required>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

						<div class="form-actions">
							<input type="submit"
								class="btn btn-block btn-primary btn-default" value="ログイン">
						</div>
					</form>
					<ul>
						<li>ID:cndone　PW:123</li>
					</ul>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
