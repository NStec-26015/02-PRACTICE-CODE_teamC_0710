package model.service;

import java.sql.Connection;
import java.sql.SQLException;

import model.dao.DepartmentDAO;
import model.dto.Department;
import model.exception.ServiceException;
import model.util.ConnectionManager;

/**
 * UC08【部門情報更新】機能のクラス<br>
 * ユーザがシステムに登録されている部門情報を更新できるように、更新する部門の指定と更新情報の入力と更新を行う為の機能
 *
 * @author Fullness, Inc.
 *
 */
public class UpdateDepartmentService {

	/**
	 * 部門名がリソースに登録済のもの（更新前の自身のものは含まない）と被っているかどうかを検証し返却
	 *
	 * @param deptId 更新する部門ID
	 * @param deptName 検証する部門名
	 * @return true:被っている false:被っていない
	 * @throws ServiceException リソースの接続に失敗、又は、情報の取得に失敗
	 */
	public boolean isDuplicateDeptName(int deptId, String deptName) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		Department department = null;
		try (Connection connection = c.getConnection();) {
			try {
				DepartmentDAO dao = new DepartmentDAO(connection);
				department = dao.selectByDeptName(deptName);
			} catch (SQLException e) {
				throw new ServiceException("取得失敗", e);
			}
		} catch (SQLException e) {
			throw new ServiceException("DB処理エラー", e);
		}
		if (department == null || department.getDeptId() == deptId) {
			return false;
		}
		return true;
	}

	/**
	 * リソースにある部門情報を指定された値に更新。更新件数を返却
	 *
	 * @param department 部門情報
	 * @throws ServiceException リソースの接続に失敗、又は、情報の更新に失敗
	 */
	public void updateDepartment(Department department) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		try (Connection connection = c.getConnection();) {
			try {
				DepartmentDAO dao = new DepartmentDAO(connection);
				int updateNum = dao.update(department);
				if (updateNum == 0) {
					c.rollback();
					throw new ServiceException("更新対象の特定に失敗しました");
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
