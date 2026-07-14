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
import model.exception.ServiceException;
import model.service.DeleteDepartmentService;

/**
 * P015【部門削除確認画面】用 コントローラー<br>
 * URL: /deptdeletecheck
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/deptdeletecheck")
public class DepartmentDeleteCheckServlet extends HttpServlet {

	/**
	 * 部門削除確認画面を表示するメソッド<br>
	 * セッションに部門の値が保存されていない場合はエラーメッセージをセッションに保存してメニュー画面にリダイレクト
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		Department department = (Department) session.getAttribute("delDeptInput");
		if (department == null) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		session.removeAttribute("delDeptInput");

		@SuppressWarnings("unchecked")
		List<String> errMsgs = (List<String>)session.getAttribute("DeptDelInputErrMsgs");
		if(errMsgs!=null&&!errMsgs.isEmpty()) {
			session.removeAttribute("DeptDelInputErrMsgs");
			req.setAttribute("errMsgs", errMsgs);
		}

		req.setAttribute("delDeptCheckViewData", department);
		req.getRequestDispatcher("WEB-INF/jsp/department/delete/departmentdeletecheck.jsp").forward(req, resp);
		return;
	}

	/**
	 * 部門削除確認画面の削除ボタンから実行され、部門削除完了画面にリダイレクト<br>
	 * 削除不可な部門（社員が登録されているなど）を削除しようとした場合、エラーメッセージをセッションに保存し、部門削除確認画面にリダイレクト<br>
	 * 同じ部門を再度削除しようとした場合、エラーメッセージをセッションに保存してメニュー画面にリダイレクト<br>
	 * 部門の削除に失敗した場合はエラー画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		Object isDeleted = session.getAttribute("deptDeletedFlg");
		if (isDeleted == null||(boolean)isDeleted) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}

		Department department = getInputParameterDept(req);

		List<String> errMsgs;
		try {
			errMsgs = checkInputData(department);
		} catch (ServiceException e) {
			resp.sendRedirect("error");
			return;
		}

		if (!errMsgs.isEmpty()) {
			session.setAttribute("DeptDelInputErrMsgs", errMsgs);
			resp.sendRedirect("deptdeletecheck");
			return;
		}

		try {
			new DeleteDepartmentService().deleteDepartment(department);
		} catch (ServiceException e) {
			resp.sendRedirect("error");
			return;
		}

		session.setAttribute("delDeptComplete", department);
		resp.sendRedirect("deptdeletecomp");
		return;
	}

	/**
	 * 入力パラメータを取得し新しい部門情報として返却
	 * @param req HTTPリクエスト
	 * @return 入力パラメータの部門情報
	 */
	private Department getInputParameterDept(HttpServletRequest req) {
		int deptId = Integer.parseInt(req.getParameter("deptId"));
		String deptName = req.getParameter("name");

		Department department = new Department();
		department.setDeptId(deptId);
		department.setDeptName(deptName);

		return department;
	}

	/**
	 * 入力された部門が削除可能かチェックし、問題がある場合はエラーメッセージのリストを返却<br>
	 * 問題がない場合は空のリストを返却<br>
	 * リソースからデータの取得に失敗した場合はServiceExceptionをスロー
	 *
	 * @param department 入力された部門情報
	 * @return エラーメッセージのリスト
	 * @throws ServiceException リソースからデータの取得に失敗
	 */
	private List<String> checkInputData(Department department) throws ServiceException {
		List<String> errMsgs = new ArrayList<String>();

		if (new DeleteDepartmentService().isEmployeeExist(department.getDeptId())) {
			errMsgs.add("社員が所属しているため、削除できません");
		}

		return errMsgs;
	}
}
