package controller;

import java.io.IOException;

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
 * P003【部門一覧画面】更新ボタン 用コントローラー<br>
 * URL: /deptupdateselectbutton
 *
 * @author Fullness, Inc.
 *
 */
@WebServlet("/deptupdateselectbutton")
public class DepartmentUpdateSelectButtonServlet extends HttpServlet {

	/**
	 * 部門一覧画面の更新ボタンから実行され、入力内容をセッションに保存して確認画面にリダイレクト<br>
	 * 入力内容確認のDB処理に失敗した場合はエラー画面にリダイレクト
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session==null) {
			session=req.getSession(true);
			session.setAttribute("illegalOperationMsg", "不正な操作です");
			resp.sendRedirect("menu");
			return;			
		}

		try {
			int deptId = getInputParameterDeptID(req);
			Department targetDept = new UpdateDepartmentService().readDepartmentByDeptId(deptId);
			session.setAttribute("updDeptInput", targetDept);
		} catch (ServiceException e) {
			resp.sendRedirect("error");
			return;
		}

		session.setAttribute("deptUpdatedFlg", false);
		resp.sendRedirect("deptupdateinput");
		return;
	}

	/**
	 * 入力パラメータを取得し部門IDとして返却
	 * @param req HTTPリクエスト
	 * @return 入力パラメータの部門ID
	 */
	private int getInputParameterDeptID(HttpServletRequest req){
		String deptId = req.getParameter("deptId");
		return Integer.parseInt(deptId);
	}
}

