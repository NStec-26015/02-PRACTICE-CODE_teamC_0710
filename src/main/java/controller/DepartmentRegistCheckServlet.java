package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dto.Department;
import model.service.InsertDepartmentService;

/**
 * P008【部門登録確認画面】用 コントローラー<br>
 * URL: /deptregistcheck
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/deptregistcheck")
public class DepartmentRegistCheckServlet extends HttpServlet {

	/**
	 * 部門登録確認画面を表示するメソッド<br>
	 * セッションに部門の値が保存されていない場合はエラーメッセージをセッションに保存してメニュー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		Department department = (Department) session.getAttribute("newDeptInput");
		if (department == null) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		session.removeAttribute("newDeptInput");
		req.setAttribute("newDeptCheckViewData", department);
		req.getRequestDispatcher("WEB-INF/jsp/department/insert/departmentinsertcheck.jsp").forward(req, resp);
		return;
	}

	/**
	 * 部門登録確認画面の登録ボタンから実行され、部門登録完了画面にリダイレクト<br>
	 * 同じ部門を再度登録しようとした場合、エラーメッセージをセッションに保存してメニュー画面にリダイレクト<br>
	 * 部門の登録に失敗した場合はエラー画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		Object isRegisted = session.getAttribute("deptRegistedFlg");
		if (isRegisted == null || (boolean) isRegisted) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		Department department = getInputParameterDepartment(req);

		try {
			new InsertDepartmentService().createDepartment(department);
		} catch (Exception e) {
			resp.sendRedirect("error");
			return;
		}

		session.setAttribute("newDeptComplete", department);
		resp.sendRedirect("deptregistcomp");
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
