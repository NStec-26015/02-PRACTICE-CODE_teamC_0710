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
 * P019【部門更新完了画面】用 コントローラー<br>
 * URL: /deptupdatecomp
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/deptupdatecomp")
public class DepartmentUpdateCompleteServlet extends HttpServlet {
	/**
	 * 部門更新完了画面を表示<br>
	 * セッションに部門の値が保存されていない場合はエラーメッセージをセッションに保存してメニュー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		Department department = (Department) session.getAttribute("updDeptComplete");
		if (department == null) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		session.setAttribute("deptUpdatedFlg", true);

		session.removeAttribute("updDeptComplete");
		req.setAttribute("updDeptCompleteViewData", department);
		req.getRequestDispatcher("WEB-INF/jsp/department/update/departmentupdatecomplete.jsp").forward(req, resp);
		return;
	}
}
