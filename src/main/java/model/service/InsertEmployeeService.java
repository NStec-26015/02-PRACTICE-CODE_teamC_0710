package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.dao.DepartmentDAO;
import model.dao.EmployeeDAO;
import model.dto.Department;
import model.dto.Employee;
import model.exception.ServiceException;
import model.util.ConnectionManager;

/**
 * UC03【社員情報登録】機能のクラス<br>
 * ユーザがシステムに社員情報を新規に登録できるように、社員情報の入力と登録を行う為の機能
 *
 * @author Fullness, Inc.
 *
 */
public class InsertEmployeeService {

	/**
	 * リソースに登録されている全ての部門情報を取得
	 *
	 * @return リソースに登録されている全ての部門情報のリスト
	 * @throws ServiceException リソースの接続に失敗、又は、情報の取得に失敗
	 */
	public List<Department> readDepartmentAll() throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		List<Department> deptList;
		try (Connection connection = c.getConnection();) {
			try {
				DepartmentDAO dao = new DepartmentDAO(connection);
				deptList = dao.selectAll();
				if (deptList.isEmpty()) {
					throw new ServiceException("該当するデータはありません");
				}
			} catch (SQLException e) {
				throw new ServiceException("取得失敗", e);
			}
		} catch (SQLException e) {
			throw new ServiceException("DB処理エラー", e);
		}
		return deptList;
	}

	/**
	 * メールアドレスがリソースに登録済のものと被っているかどうかを検証し返却
	 *
	 * @param mailAddress 検証するメールアドレス
	 * @return true:被っている false:被っていない
	 * @throws ServiceException リソースの接続に失敗、又は、情報の取得に失敗
	 */
	public boolean isDuplicateMailAddress(String mailAddress) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		Employee employee;
		try (Connection connection = c.getConnection();) {
			try {
				EmployeeDAO dao = new EmployeeDAO(connection);
				employee = dao.selectByMailAddress(mailAddress);
				return employee != null;
			} catch (SQLException e) {
				throw new ServiceException("取得失敗", e);
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

	/**
	 * 社員IDを新規に発行し引数の社員情報と合わせてリソースに登録。
	 *
	 * @param employee 社員情報
	 * @throws ServiceException リソースの接続に失敗、又は、情報の更新に失敗
	 */
	public void createEmployee(Employee employee) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		try (Connection connection = c.getConnection();) {
			try {
				EmployeeDAO dao = new EmployeeDAO(connection);
				dao.insertEmp(employee);
				c.commit();
			} catch (SQLException e) {
				c.rollback();
				throw new ServiceException("更新失敗。ロールバックしました", e);
			}
		} catch (SQLException e) {
			throw new ServiceException("DB処理エラー", e);
		}
	}
}
