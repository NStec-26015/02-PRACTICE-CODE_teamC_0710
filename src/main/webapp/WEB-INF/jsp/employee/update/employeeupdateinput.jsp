<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>社員更新(入力)</title>
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
				<h3 class="card-title">社員更新(入力)</h3>
				<br>
				<h4 class="card-text">社員情報を入力して下さい。</h4>
				<c:forEach var="msg" items="${errMsgs}">
					<div class="alert alert-danger" role="alert">${msg}</div>
				</c:forEach>
				<form action="<%=request.getContextPath()%>/empupdateinput"
					method="post">
					<table class="table table-bordered table-striped">
						<tbody>
							<tr>
								<td class="bg-dark text-light" width="60">名前</td>
								<td width="180"><input type="text" name="name"
									value="${updEmpInputViewData.empName}" class="form-control"></td>
							</tr>
							<tr>
								<td class="bg-dark text-light" width="60">所属部門</td>
								<td width="180"><select name="deptId" size="1"
									class="form-control">
										<c:forEach var="dept" items="${deptAllList}">
											<c:if test="${!empty updEmpInputViewData}">
												<c:if test="${dept.deptId == updEmpInputViewData.deptId}">
													<option value="${dept.deptId}" selected="selected">
														<c:out value="${dept.deptName}" /></option>
												</c:if>
												<c:if test="${dept.deptId != updEmpInputViewData.deptId}">
													<option value="${dept.deptId}">
														<c:out value="${dept.deptName}" />
													</option>
												</c:if>
											</c:if>
											<c:if test="${empty updEmpInputViewData }">
												<option value="${dept.deptId}">
													<c:out value="${dept.deptName}" />
												</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td class="bg-dark text-light" width="60">電話番号</td>
								<td width="180"><input type="tel" name="phone"
									value="${updEmpInputViewData.phone}" placeholder="000-0000-0000"
									class="form-control"></td>
							</tr>
							<tr>
								<td class="bg-dark text-light" width="60">E-mailアドレス</td>
								<td width="180"><input type="text" name="mailAddress"
									value="${updEmpInputViewData.mailAddress}" placeholder="aaa@example.co.jp"
									class="form-control"></td>
							</tr>
						</tbody>
					</table>
					<input type="hidden" name="empId" value="${updEmpInputViewData.empId}">
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