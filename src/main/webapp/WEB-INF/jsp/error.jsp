<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>システムエラー</title>
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
				<h3 class="card-title">システムエラー</h3>
				<br>
				<div>システムエラーです。システム管理者に連絡して下さい。</div>
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