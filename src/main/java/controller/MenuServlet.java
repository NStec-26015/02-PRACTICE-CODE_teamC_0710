package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * P001【メニュー画面】用 コントローラー<br>
 * URL: /menu
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

	/**
	 * メニュー画面を表示
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		String illegalOperationMsg = (String) session.getAttribute("illegalOperationMsg");
		if (illegalOperationMsg != null) {
			req.setAttribute("illegalOperationMsg", illegalOperationMsg);
			session.removeAttribute("illegalOperationMsg");
		}
		req.getRequestDispatcher("WEB-INF/jsp/menu.jsp").forward(req, resp);
	}
}