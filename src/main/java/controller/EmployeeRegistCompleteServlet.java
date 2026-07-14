package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dto.Employee;

/**
 * P006【社員登録完了画面】用 コントローラー<br>
 * URL: /empregistcomp
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/empregistcomp")
public class EmployeeRegistCompleteServlet extends HttpServlet {

	/**
	 * 社員登録完了画面を表示<br>
	 * セッションに社員の値が保存されていない場合、エラーメッセージをセッションに保存してメニュー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(true);

		Employee employee = (Employee) session.getAttribute("newEmpComplete");
		if (employee == null) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		session.setAttribute("empRegistedFlg", true);

		session.removeAttribute("newEmpComplete");
		req.setAttribute("newEmpCompleteViewData", employee);
		req.getRequestDispatcher("WEB-INF/jsp/employee/insert/employeeinsertcomplete.jsp").forward(req, resp);
		return;
	}
}
