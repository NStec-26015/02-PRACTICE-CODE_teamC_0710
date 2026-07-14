package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dto.Department;
import model.exception.ServiceException;
import model.service.UpdateDepartmentService;

/**
 * P018【部門更新確認画面】用 コントローラー<br>
 * URL: /deptupdatecheck
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/deptupdatecheck")
public class DepartmentUpdateCheckServlet extends HttpServlet {
	/**
	 * 部門更新確認画面を表示するメソッド<br>
	 * セッションに部門の値が保存されていない場合はエラーメッセージをセッションに保存してメニュー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		Department department = (Department) session.getAttribute("updDeptInput");
		if (department == null) {
			throw new ServletException("不正な操作です");
		}
		
		session.removeAttribute("updDeptInput");
		req.setAttribute("updDeptCheckViewData", department);
		req.getRequestDispatcher("WEB-INF/jsp/department/update/departmentupdatecheck.jsp").forward(req, resp);
		return;
	}

	/**
	 * 部門更新確認画面の更新ボタンから実行され、部門更新完了画面にリダイレクト<br>
	 * 同じ部門を再度更新しようとした場合、エラーメッセージをセッションに保存してメニュー画面にリダイレクト<br>
	 * 部門の更新に失敗した場合はエラー画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		Object isRegisted = session.getAttribute("deptUpdatedFlg");
		if (isRegisted == null||(boolean)isRegisted) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		Department department = getInputParameterDept(req);

		try {
			new UpdateDepartmentService().updateDepartment(department);
		} catch (ServiceException e) {
			resp.sendRedirect("error");
			return;
		}

		session.setAttribute("updDeptComplete", department);
		resp.sendRedirect("deptupdatecomp");
		return;
	}

	/**
	 * 入力パラメータを取得し新しい部門情報として返却
	 * @param req HTTPリクエスト
	 * @return 入力パラメータの部門情報
	 */
	private Department getInputParameterDept(HttpServletRequest req) {
		int deptId = Integer.parseInt(req.getParameter("deptId"));
		String deptName = req.getParameter("name");

		Department department = new Department();
		department.setDeptId(deptId);
		department.setDeptName(deptName);

		return department;
	}
}