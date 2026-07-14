package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dto.Department;

/**
 * P018【部門更新確認画面】再入力ボタン用 コントローラー<br>
 * URL: /deptupdatereinput
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/deptupdatereinput")
public class DepartmentUpdateReInputButtonServlet extends HttpServlet {

	/**
	 * 部門更新確認画面の再入力ボタンから実行され、パラメータを取得してセッションに保存し、入力画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(true);
		Object isRegisted = session.getAttribute("deptUpdatedFlg");
		if (isRegisted == null||(boolean)isRegisted) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}

		Department department = getInputParameterDepartment(req);
		session.setAttribute("updDeptInput", department);
		resp.sendRedirect("deptupdateinput");
		return;
	}

	/**
	 * 入力パラメータを取得し新しい部門情報として返却
	 * @param req HTTPリクエスト
	 * @return 入力パラメータの部門情報
	 */
	private Department getInputParameterDepartment(HttpServletRequest req) {
		int deptId = Integer.parseInt(req.getParameter("deptId"));
		String deptName = req.getParameter("name");

		Department department = new Department();
		department.setDeptId(deptId);
		department.setDeptName(deptName);

		return department;
	}
}
