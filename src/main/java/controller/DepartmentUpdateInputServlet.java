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
import model.service.UpdateDepartmentService;

/**
 * P017【部門更新入力画面】用 コントローラー<br>
 * URL: /deptupdateinput
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/deptupdateinput")
public class DepartmentUpdateInputServlet extends HttpServlet {
	/**
	 * 部門更新入力画面を表示するメソッド
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(true);

		Department department = (Department) session.getAttribute("updDeptInput");
		if (department == null) {
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;
		}
		session.removeAttribute("updDeptInput");

		@SuppressWarnings("unchecked")
		List<String> errMsgs = (List<String>) session.getAttribute("DeptUpdateInputErrMsgs");
		if (errMsgs != null && !errMsgs.isEmpty()) {
			session.removeAttribute("DeptUpdateInputErrMsgs");
			req.setAttribute("errMsgs", errMsgs);
		}

		req.setAttribute("updDeptInputViewData", department);
		req.getRequestDispatcher("WEB-INF/jsp/department/update/departmentupdateinput.jsp").forward(req, resp);
	}

	/**
	 * 部門更新入力画面の確認ボタンから実行され、入力内容をセッションに保存して確認画面にリダイレクト<br>
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
		Department department = getInputParameterDepartment(req);

		List<String> errMsgs;
		try {
			errMsgs = checkInputData(department);
		} catch (ServiceException e) {
			resp.sendRedirect("error");
			return;
		}

		if (!errMsgs.isEmpty()) {
			session.setAttribute("updDeptInput", department);
			session.setAttribute("DeptUpdateInputErrMsgs", errMsgs);
			resp.sendRedirect("deptupdateinput");
			return;
		}

		session.setAttribute("updDeptInput", department);
		session.setAttribute("deptUpdatedFlg", false);
		resp.sendRedirect("deptupdatecheck");
		return;
	}

	/**
	 * 入力パラメータを取得し新しい部門情報として返却
	 * 
	 * @param req HTTPリクエスト
	 * @return 入力パラメータの部門情報
	 */
	private Department getInputParameterDepartment(HttpServletRequest req) {
		int deptId = Integer.parseInt(req.getParameter("deptId"));
		String deptName = req.getParameter("name");

		Department department = new Department();
		department.setDeptId(deptId);
		department.setDeptName(deptName);

		return department;
	}

	/**
	 * 入力された部門情報のバリデーションチェックし、問題がある場合はエラーメッセージのリストを返却<br>
	 * 問題がない場合は空のリストを返却<br>
	 * リソースからデータの取得に失敗した場合はServiceExceptionをスロー
	 *
	 * @param department 入力された部門情報
	 * @return エラーメッセージのリスト
	 * @throws ServiceException リソースからデータの取得に失敗
	 */
	private List<String> checkInputData(Department department) throws ServiceException {
		List<String> errMsgs = new ArrayList<String>();

		int id = department.getDeptId();
		String name = department.getDeptName();
		if (name == "") {
			errMsgs.add("部門名を入力して下さい");
		} else if (name.length() > 100) {
			errMsgs.add("部門名は100文字以内で入力して下さい");
		} else if (new UpdateDepartmentService().isDuplicateDeptName(id, name)) {
			errMsgs.add("この部門名は既に登録されています。別の部門名で登録してください。");
		}

		return errMsgs;
	}
}
