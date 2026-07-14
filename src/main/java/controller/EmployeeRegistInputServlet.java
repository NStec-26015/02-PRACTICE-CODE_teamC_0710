package controller;

import java.io.IOException;
import java.util.ArrayList;
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
import model.service.InsertEmployeeService;

/**
 * P004【社員登録入力画面】用 コントローラー<br>
 * URL: /empregistinput
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/empregistinput")
public class EmployeeRegistInputServlet extends HttpServlet {

	/**
	 * 社員登録入力画面を表示するメソッド<br>
	 * 画面に表示する部門の一覧の取得に失敗した場合はエラー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(true);

		@SuppressWarnings("unchecked")
		List<String> errMsgs = (List<String>) session.getAttribute("EmpRegistInputErrMsgs");
		if (errMsgs != null && !errMsgs.isEmpty()) {
			session.removeAttribute("EmpRegistInputErrMsgs");
			req.setAttribute("errMsgs", errMsgs);

			Employee employee = (Employee) session.getAttribute("newEmpInput");
			if (employee != null) {
				session.removeAttribute("newEmpInput");
			}

			try {
				req.setAttribute("deptAllList", new InsertEmployeeService().readDepartmentAll());
			} catch (Exception e) {
				resp.sendRedirect("error");
				return;
			}
			req.setAttribute("newEmpInputViewData", employee);

		}
		req.getRequestDispatcher("WEB-INF/jsp/employee/insert/employeeinsertinput.jsp").forward(req, resp);
		return;

	}

	/**
	 * 社員登録入力画面の確認ボタンから実行され、入力内容をセッションに保存して確認画面にリダイレクト<br>
	 * 入力内容に問題があれば、エラーメッセージをセッションに保存して入力画面にリダイレクト
	 * 入力内容確認のDB処理に失敗した場合はエラー画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session == null) {
			session = req.getSession(true);
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		Employee employee = getInputParameterEmployee(req);

		List<String> errMsgs;
		try {
			errMsgs = checkInputData(employee);
		} catch (ServiceException e) {
			resp.sendRedirect("error");
			return;
		}
		session.setAttribute("newEmpInput", employee);

		if (!errMsgs.isEmpty()) {
			session.setAttribute("EmpRegistInputErrMsgs", errMsgs);
			resp.sendRedirect("empregistinput");
			return;
		}

		session.setAttribute("empRegistedFlg", false);
		resp.sendRedirect("empregistcheck");
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
		Employee employee = new Employee();
		employee.setEmpName(name);
		employee.setDeptId(deptId);
		employee.setMailAddress(mailAddress);

		return employee;
	}

	/**
	 * 入力された社員情報のバリデーションチェックし、部門情報を格納<br>
	 * 問題がある場合はエラーメッセージのリストを返却<br>
	 * 問題がない場合は空のリストを返却<br>
	 * リソースからデータの取得に失敗した場合はServiceExceptionをスロー
	 *
	 * @param emp 入力された社員情報
	 * @return エラーメッセージのリスト
	 * @throws ServiceException リソースからデータの取得に失敗
	 */
	private List<String> checkInputData(Employee emp) throws ServiceException {
		List<String> errMsgs = new ArrayList<String>();

		String name = emp.getEmpName();
		if (name == null || name == "") {
			errMsgs.add("名前を入力して下さい");
		} else if (name.length() > 100) {
			errMsgs.add("名前は100文字以内で入力して下さい");
		}

		String phoneNumber = emp.getPhone();
		if (phoneNumber == null || phoneNumber == "") {
			errMsgs.add("電話番号を入力して下さい");
		} else if (!phoneNumber.matches("[0-9]{2,4}-[0-9]{3,4}-[0-9]{4}")) {
			errMsgs.add("電話番号はxxxx－yyyy－zzzzの形式で入力して下さい");
		}

		String mailAddress = emp.getMailAddress();
		if (mailAddress == null || mailAddress == "") {
			errMsgs.add("メールアドレスを入力して下さい");
		} else if (mailAddress.length() > 100) {
			errMsgs.add("メールアドレスは100文字以内で入力して下さい");
		} else if (!mailAddress.matches(
				"^(([0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*)|(\"[^\"]*\"))@[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*$")) {
			errMsgs.add("メールアドレスはxxxx@yyyyの形式で入力して下さい");
		} else if (new InsertEmployeeService().isDuplicateMailAddress(mailAddress)) {
			errMsgs.add("このアドレスは既に登録されています。別のアドレスで登録してください。");
		}

		int deptId = emp.getDeptId();
		try {
			Department department = new InsertEmployeeService().readDepartmentByDeptId(deptId);
			emp.setDepartment(department);
		} catch (ServiceException e) {
			errMsgs.add("部門情報を取得できません。別の部門を選択してください。");
		}

		return errMsgs;
	}

}
