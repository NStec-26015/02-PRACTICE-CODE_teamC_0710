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
 * P013【社員更新確認画面】再入力ボタン用 コントローラー<br>
 * URL: /empupdatereinput
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/empupdatereinput")
public class EmployeeUpdateReInputButtonServlet extends HttpServlet{
	/**
	 * 社員更新確認画面の再入力ボタンから実行され、パラメータを取得してセッションに保存し、入力画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		Object isRegisted = session.getAttribute("empUpdatedFlg");
		if (isRegisted == null||(boolean)isRegisted) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}

		Employee employee = getInputParameterEmployee(req);
		session.setAttribute("updEmpInput", employee);
		resp.sendRedirect("empupdateinput");
		return;
	}

	/**
	 * 入力パラメータを取得し新しい社員情報として返却
	 * @param req HTTPリクエスト
	 * @return 入力パラメータの社員情報
	 */
	private Employee getInputParameterEmployee(HttpServletRequest req) {
		int empId = Integer.parseInt(req.getParameter("empId"));
		String name = req.getParameter("name");
		int deptId = Integer.parseInt(req.getParameter("deptId"));
		String phone = req.getParameter("phone");
		String mailAddress = req.getParameter("mailAddress");
		Employee employee = new Employee();
		employee.setEmpId(empId);
		employee.setEmpName(name);
		employee.setDeptId(deptId);
		employee.setPhone(phone);
		employee.setMailAddress(mailAddress);

		return employee;
	}
}
