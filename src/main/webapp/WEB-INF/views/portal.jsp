<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>社内管理システム</title>
<%@ include file="/common/resourceImport.jsp"%><!--共通部分をインポート  -->
</head>
<body>
	<jsp:include page="/portal/menu" />

	<div class="container" style="background-color: #fff">
		<div class="table-responsive">
			<table class="table table-bordered table-striped">
				<thead>
					<tr>
						<th>#</th>
						<th>名前</th>
						<th>メールアドレス</th>
						<th>電話番号</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row">1</th>
						<td>煌木 太郎</td>
						<td>taro.kirameki@example.com</td>
						<td>09011112222</td>
					</tr>
					<tr>
						<th scope="row">2</th>
						<td>煌木 次郎</td>
						<td>jiro.kirameki@example.com</td>
						<td>09033334444</td>
					</tr>
					<tr>
						<th scope="row">3</th>
						<td>煌木 花子</td>
						<td>hanako.kirameki@example.com</td>
						<td>09055556666</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<footer style="background-color: gray">Footer</footer>

</body>
</html>
