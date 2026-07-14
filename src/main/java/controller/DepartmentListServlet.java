package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.exception.ServiceException;
import model.service.GetDepartmentListService;

/**
 * P003【部門一覧画面】用コントローラー<br>
 * URL: /deptlist
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/deptlist")
public class DepartmentListServlet extends HttpServlet {

	/**
	 * 部門一覧画面を表示<br>
	 * 画面に表示する部門のリストの取得に失敗した場合はエラー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setAttribute("deptAllList", new GetDepartmentListService().readDepartmentAll());
		} catch (ServiceException e) {
			resp.sendRedirect("error");
			return;
		}
		req.getRequestDispatcher("WEB-INF/jsp/department/select/departmentselectlist.jsp").forward(req, resp);
		return;
	}
}
