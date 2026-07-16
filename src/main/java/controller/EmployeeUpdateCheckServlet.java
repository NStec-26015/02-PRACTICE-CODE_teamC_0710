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
import model.exception.ServiceException;
import model.service.UpdateEmployeeService;

/**
 * P013【社員更新確認画面】用 コントローラー<br>
 * URL: /empupdatecheck
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/empupdatecheck")
public class EmployeeUpdateCheckServlet extends HttpServlet {
	/**
	 * 社員更新確認画面を表示するメソッド<br>
	 * セッションに部門の値が保存されていない場合はエラーメッセージをセッションに保存してメニュー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		Employee employee = (Employee) session.getAttribute("updEmpInput");
		if (employee == null) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}

		session.removeAttribute("updEmpInput");
		req.setAttribute("updEmpCheckViewData", employee);
		req.getRequestDispatcher("WEB-INF/jsp/employee/update/employeeupdatecheck.jsp").forward(req, resp);
		return;
	}

	/**
	 * 社員更新確認画面の更新ボタンから実行され、社員更新完了画面にリダイレクト<br>
	 * 同じ社員を再度更新しようとした場合、エラーメッセージをセッションに保存してメニュー画面にリダイレクト<br>
	 * 社員の更新に失敗した場合はエラー画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		Object isRegisted = session.getAttribute("empUpdatedFlg");
		if (isRegisted == null || (boolean) isRegisted) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		Employee employee = getInputParameterEmployee(req);

		try {
			new UpdateEmployeeService().updateEmployee(employee);
		} catch (ServiceException e) {
			resp.sendRedirect("error");
			return;
		}

		session.setAttribute("updEmpComplete", employee);
		resp.sendRedirect("empupdatecomp");
		return;
	}

	/**
	 * 入力パラメータを取得し新しい社員情報として返却
	 * 
	 * @param req HTTPリクエスト
	 * @return 入力パラメータの社員情報
	 */
	private Employee getInputParameterEmployee(HttpServletRequest req) {
		int empId = Integer.parseInt(req.getParameter("empId"));
		String name = req.getParameter("name");
		int deptId = Integer.parseInt(req.getParameter("deptId"));
		String phone = req.getParameter("phone");
		String mailAddress = req.getParameter("mailAddress");
		String deptName = req.getParameter("deptName");
		Employee employee = new Employee();
		employee.setEmpId(empId);
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