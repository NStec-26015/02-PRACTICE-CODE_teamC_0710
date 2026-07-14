package controller.error;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


/**
 * P099【システムエラー画面】用 コントローラー<br>
 * URL: /error
 *
 * @author Fullness, Inc.
 */
@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
	}
}
