<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>部門更新(入力)</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
</head>
<body class="bg-success">
	<br>
	<div class="container">
		<div class="card text-center">
			<div class="card-body">
				<h3 class="card-title">部門更新(入力)</h3>
				<br>
				<h4 class="card-text">部門情報を入力して下さい。</h4>
				<c:forEach var="msg" items="${errMsgs}">
					<div class="alert alert-danger" role="alert">${msg}</div>
				</c:forEach>
				<form action="<%=request.getContextPath()%>/deptupdateinput"
					method="post">
					<table class="table table-bordered table-striped">
						<tbody>
							<tr>
								<td class="bg-dark text-light" width="60">名前</td>
								<td width="180"><input type="text" name="name"
									value="${updDeptInputViewData.deptName}" class="form-control"></td>
							</tr>
						</tbody>
					</table>
					<br>
					<input type="hidden" name="deptId" value="${updDeptInputViewData.deptId}">
					<button type="submit" class="btn btn-primary btn-lg">確認</button>
				</form>
				<br>
				<ul class="nav justify-content-center">
					<li><a href="<%=request.getContextPath()%>/menu"
						class="btn btn-info btn-lg">メニュー</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>