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
 * P009【部門登録完了画面】用 コントローラー<br>
 * URL: /deptregistcomp
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/deptregistcomp")
public class DepartmentRegistCompleteServlet extends HttpServlet {
	/**
	 * 部門登録完了画面を表示<br>
	 * セッションに部門の値が保存されていない場合はエラーメッセージをセッションに保存してメニュー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		Department department = (Department) session.getAttribute("newDeptComplete");
		if (department == null) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		session.setAttribute("deptRegistedFlg", true);

		session.removeAttribute("newDeptComplete");
		req.setAttribute("newDeptCompleteViewData", department);
		req.getRequestDispatcher("WEB-INF/jsp/department/insert/departmentinsertcomplete.jsp").forward(req, resp);
		return;
	}
}
