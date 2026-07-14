package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dto.Department;
import model.dto.Employee;

/**
 * P005【社員登録確認画面】再入力ボタン用 コントローラー<br>
 * URL: /empregistreinput
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/empregistreinput")
public class EmployeeRegistReInputButtonServlet extends HttpServlet {

	/**
	 * 社員登録入力画面の再入力ボタンから実行され、パラメータを取得してセッションに保存し、入力画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(true);
		Object isRegisted = session.getAttribute("empRegistedFlg");
		if (isRegisted == null||(boolean)isRegisted) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}

		Employee employee = getInputParameterEmployee(req);
		session.setAttribute("reNewEmpInput", employee);
		resp.sendRedirect("empregistinput");
		return;
	}

	/**
	 * 入力パラメータを取得し新しい社員情報として返却
	 * @param req HTTPリクエスト
	 * @return 入力パラメータの社員情報
	 */
	private Employee getInputParameterEmployee(HttpServletRequest req) {
		String name = req.getParameter("name");
		int deptId = Integer.parseInt(req.getParameter("deptId"));
		String phone = req.getParameter("phone");
		String mailAddress = req.getParameter("mailAddress");
		String deptName = req.getParameter("deptName");
		Employee employee = new Employee();
		employee.setEmpName(name);
		employee.setDeptId(deptId);
		employee.setPhone(phone);
		employee.setMailAddress(mailAddress);
		Department department = new Department();
		department.setDeptId(deptId);
		department.setDeptName(deptName);
		employee.setDepartment(department);

		return employee;
	}
}

