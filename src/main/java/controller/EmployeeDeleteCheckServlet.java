package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dto.Department;
import model.dto.Employee;
import model.exception.ServiceException;
import model.service.DeleteEmployeeService;

/**
 * P010【社員削除確認画面】用 コントローラー<br>
 * URL: /empdeletecheck
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/empdeletecheck")
public class EmployeeDeleteCheckServlet extends HttpServlet {
	/**
	 * 社員削除確認画面を表示するメソッド<br>
	 * セッションに社員の値が保存されていない場合はエラーメッセージをセッションに保存してメニュー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		Employee employee = (Employee) session.getAttribute("delEmpInput");
		if (employee == null) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		session.removeAttribute("delEmpInput");

		@SuppressWarnings("unchecked")
		List<String> errMsgs = (List<String>)session.getAttribute("EmpDelInputErrMsgs");
		if(errMsgs!=null&&!errMsgs.isEmpty()) {
			session.removeAttribute("EmpDelInputErrMsgs");
			req.setAttribute("errMsgs", errMsgs);
		}

		req.setAttribute("delEmpCheckViewData", employee);
		req.getRequestDispatcher("WEB-INF/jsp/employee/delete/employeedeletecheck.jsp").forward(req, resp);
		return;
	}

	/**
	 * 社員削除確認画面の削除ボタンから実行され、社員削除完了画面にリダイレクト
	 * 同じ社員を再度削除しようとした場合、エラーメッセージをセッションに保存してメニュー画面にリダイレクト<br>
	 * 社員の削除に失敗した場合はエラー画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		Object isDeleted = session.getAttribute("empDeletedFlg");
		if (isDeleted == null||(boolean)isDeleted) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}

		Employee employee = getInputParameterEmp(req);

		try {
			new DeleteEmployeeService().deleteEmployee(employee);
		} catch (ServiceException e) {
			resp.sendRedirect("error");
			return;
		}

		session.setAttribute("delEmpComplete", employee);
		resp.sendRedirect("empdeletecomp");
		return;
	}

	/**
	 * 入力パラメータを取得し削除対象の社員情報として返却
	 * @param req HTTPリクエスト
	 * @return 入力パラメータの社員情報
	 */
	private Employee getInputParameterEmp(HttpServletRequest req) {
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
