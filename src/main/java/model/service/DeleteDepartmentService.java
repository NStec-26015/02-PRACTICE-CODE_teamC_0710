package model.service;

import java.sql.Connection;
import java.sql.SQLException;

import model.dao.DepartmentDAO;
import model.dao.EmployeeDAO;
import model.dto.Department;
import model.exception.ServiceException;
import model.util.ConnectionManager;

/**
 * UC06【部門情報削除】機能のクラス<br>
 * ユーザがシステムに登録されている部門情報を削除できるように、削除する部門の指定と削除を行う為の機能
 *
 * @author Fullness, Inc.
 */
public class DeleteDepartmentService {

	/**
	 * 指定された部門IDの部門に所属している従業員がいるかどうかを返却
	 *
	 * @param deptId 部門ID
	 * @return true:従業員がいる false:従業員がいない
	 * @throws ServiceException リソースの接続に失敗、又は、情報の取得に失敗
	 */
	public boolean isEmployeeExist(int deptId) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		int resultCount = 0;
		try (Connection connection = c.getConnection();) {
			try {
				EmployeeDAO dao = new EmployeeDAO(connection);
				resultCount = dao.countByDeptId(deptId);
			} catch (SQLException e) {
				throw new ServiceException("取得失敗", e);
			}
		} catch (SQLException e) {
			throw new ServiceException("DB処理エラー", e);
		}
		return resultCount != 0;
	}

	/**
	 * 指定された部門情報をリソースから削除。削除件数を返却
	 *
	 * @param department 部門情報
	 * @throws ServiceException リソースの接続に失敗した場合、情報の削除に失敗した場合、又は、削除対象の特定に失敗
	 */
	public void deleteDepartment(Department department) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		try (Connection connection = c.getConnection();) {
			try {
				DepartmentDAO dao = new DepartmentDAO(connection);
				int deleteNum = dao.delete(department);
				if (deleteNum == 0) {
					c.rollback();
					throw new ServiceException("削除対象の特定に失敗しました");
				}
				c.commit();
			} catch (SQLException e) {
				c.rollback();
				throw new ServiceException("更新失敗。ロールバックしました", e);
			}
		} catch (SQLException e) {
			throw new ServiceException("DB処理エラー", e);
		}
	}

	/**
	 * 渡された部門IDの部門情報を取得する
	 *
	 * @param deptId 部門ID
	 * @return 渡された部門IDの部門情報
	 * @throws ServiceException リソースの接続に失敗、又は、情報の取得に失敗
	 */
	public Department readDepartmentByDeptId(int deptId) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		Department department;

		try (Connection connection = c.getConnection();) {
			try {
				DepartmentDAO dao = new DepartmentDAO(connection);
				department = dao.selectByDeptId(deptId);
				if (department == null) {
					throw new ServiceException("取得件数が0件でした");
				}
			} catch (SQLException e) {
				throw new ServiceException("取得失敗", e);
			}
		} catch (SQLException e) {
			throw new ServiceException("DB処理エラー", e);
		}
		return department;
	}
}

