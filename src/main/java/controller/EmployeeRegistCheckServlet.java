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
import model.service.InsertEmployeeService;

/**
 * P005【社員登録確認画面】用 コントローラー<br>
 * URL: /empregistcheck
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/empregistcheck")
public class EmployeeRegistCheckServlet extends HttpServlet {

	/**
	 * 社員登録確認画面を表示するメソッド<br>
	 * セッションに社員の値が保存されていない場合はエラーメッセージをセッションに保存してメニュー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(true);

		Employee employee = (Employee) session.getAttribute("newEmpInput");
		if (employee == null) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}

		session.removeAttribute("newEmpInput");
		req.setAttribute("newEmpCheckViewData", employee);
		req.getRequestDispatcher("WEB-INF/jsp/employee/insert/employeeinsertcheck.jsp").forward(req, resp);
		return;
	}

	/**
	 * 社員登録確認画面の登録ボタンから実行され、社員登録完了画面にリダイレクト<br>
	 * 同じ社員を再度登録しようとした場合、エラーメッセージをセッションに保存してメニュー画面にリダイレクト<br>
	 * 社員の登録に失敗した場合、エラー画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		Employee employee = getInputParameterEmployee(req);
		InsertEmployeeService insertEmployeeService = new InsertEmployeeService();

		try {
			insertEmployeeService.createEmployee(employee);
		} catch (Exception e) {
			resp.sendRedirect("error");
			return;
		}

		// DAOがない
		try {
			new InsertEmployeeService().createEmployee(employee);
		} catch (Exception e) {
			resp.sendRedirect("error");
			return;
		}

		session.setAttribute("newEmpComplete", employee);
		resp.sendRedirect("empregistcomp");
		return;
	}

	/**
	 * 入力パラメータを取得し新しい社員情報として返却
	 * 
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
