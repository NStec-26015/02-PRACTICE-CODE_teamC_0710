<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>メニュー</title>
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
				<h3 class="card-title">社員管理メニュー</h3>
				<br>
				<c:if test="${illegalOperationMsg != null }">
					<div class="alert alert-danger" role="alert">${illegalOperationMsg}</div>
				</c:if>
				<ul class="nav justify-content-center">
					<li class="nav-item m-3"><a
						href="<%=request.getContextPath()%>/emplist"
						class="btn btn-primary btn-lg"> 社員一覧 </a></li>
					<li class="nav-item m-3"><a
						href="<%=request.getContextPath()%>/empregistinput"
						class="btn btn-primary btn-lg"> 社員登録 </a></li>
				</ul>
				<br>
				<br>
				<ul class="nav justify-content-center">
					<li class="nav-item m-3"><a
						href="<%=request.getContextPath()%>/deptlist"
						class="btn btn-primary btn-lg"> 部門一覧 </a></li>
					<li class="nav-item m-3"><a
						href="<%=request.getContextPath()%>/deptregistinput"
						class="btn btn-primary btn-lg"> 部門登録 </a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>