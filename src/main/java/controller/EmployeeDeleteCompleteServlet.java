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
 * P011【社員削除完了画面】用 コントローラー<br>
 * URL: /empdeletecomp
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/empdeletecomp")
public class EmployeeDeleteCompleteServlet extends HttpServlet{

	/**
	 * 社員削除完了画面を表示<br>
	 * セッションに社員の値が保存されていない場合、エラーメッセージをセッションに保存してメニュー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(true);

		Employee employee = (Employee) session.getAttribute("delEmpComplete");
		if (employee == null) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
		}
		session.setAttribute("empDeletedFlg", true);
		
		session.removeAttribute("delEmpComplete");
		req.setAttribute("delEmpCompleteViewData", employee);
		req.getRequestDispatcher("WEB-INF/jsp/employee/delete/employeedeletecomplete.jsp").forward(req, resp);
		return;
	}
}


