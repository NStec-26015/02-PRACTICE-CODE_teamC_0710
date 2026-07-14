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
 * P008【部門登録確認画面】再入力ボタン用 コントローラー<br>
 * URL: /deptregistreinput
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/deptregistreinput")
public class DepartmentRegistReInputButtonServlet extends HttpServlet {

	/**
	 * 部門登録確認画面の再入力ボタンから実行され、パラメータを取得してセッションに保存し、入力画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		Object isRegisted = session.getAttribute("deptRegistedFlg");
		if (isRegisted == null||(boolean)isRegisted) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}

		Department department = getInputParameterDepartment(req);
		session.setAttribute("newDeptInput", department);
		resp.sendRedirect("deptregistinput");
		return;
	}

	/**
	 * 入力パラメータを取得し新しい部門情報として返却
	 * @param req HTTPリクエスト
	 * @return 入力パラメータの部門情報
	 */
	private Department getInputParameterDepartment(HttpServletRequest req) {
		String deptName = req.getParameter("name");

		Department department = new Department();
		department.setDeptName(deptName);

		return department;
	}
}
