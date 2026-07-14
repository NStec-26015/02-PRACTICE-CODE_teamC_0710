package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.DepartmentDAO;
import model.dto.Department;
import model.exception.ServiceException;
import model.util.ConnectionManager;

/**
 * UC02【部門一覧表示】機能のクラス<br>
 * ユーザがシステムに部門情報を新規に登録できるように、部門情報の入力と登録を行う為の機能
 *
 * @author Fullness, Inc.
 *
 */
public class GetDepartmentListService {
	/**
	 * リソースに登録されている全ての部門情報を取得
	 *
	 * @return リソースに登録されている全ての部門情報
	 * @throws ServiceException リソースの接続に失敗、又は、情報の取得に失敗
	 */
	public List<Department> readDepartmentAll() throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		List<Department> deptList = new ArrayList<Department>();
		try (Connection connection = c.getConnection();) {
			try {
				DepartmentDAO dao = new DepartmentDAO(connection);
				deptList = dao.selectAll();
			} catch (SQLException e) {
				throw new ServiceException("取得失敗", e);
			}
		} catch (SQLException e) {
			throw new ServiceException("DB処理エラー", e);
		}
		return deptList;
	}
}
