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
 * UC07【社員情報更新】機能のクラス<br>
 * ユーザがシステムに登録されている社員情報を更新できるように、更新する社員の指定と更新情報の入力と更新を行う為の機能
 *
 * @author Fullness, Inc.
 */
public class UpdateEmployeeService {

	/**
	 * メールアドレスがリソースに登録済のもの（更新前の自身のものは含まない）と被っているかどうかを検証し返却
	 *
	 * @param empId       更新する社員のid
	 * @param mailAddress 検証するメールアドレス
	 * @return true:被っている false:被っていない
	 * @throws ServiceException リソースの接続に失敗、又は、情報の取得に失敗
	 */
	public boolean isDuplicateMailAddress(int empId, String mailAddress) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		Employee employee;
		try (Connection connection = c.getConnection();) {
			try {
				EmployeeDAO dao = new EmployeeDAO(connection);
				employee = dao.selectByMailAddress(mailAddress);
			} catch (SQLException e) {
				throw new ServiceException("取得失敗", e);
			}
		} catch (SQLException e) {
			throw new ServiceException("DB処理エラー", e);
		}
		if (employee == null) {
			return false;
		}
		return true;
	}

	/**
	 * 指定された社員IDに該当する社員情報を取得
	 *
	 * @param empId 社員ID
	 * @return 社員IDに該当する社員情報
	 * @throws ServiceException リソースの接続に失敗、又は、情報の取得に失敗
	 */
	public Employee readEmployeeByEmpId(int empId) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		Employee employee = null;
		try (Connection connection = c.getConnection();) {
			try {
				EmployeeDAO dao = new EmployeeDAO(connection);
				employee = dao.selectByIdWithDepartment(empId);
				if (employee == null) {
					throw new ServiceException("取得件数が0件でした");
				}
			} catch (SQLException e) {
				throw new ServiceException("取得失敗", e);
			}
		} catch (SQLException e) {
			throw new ServiceException("DB処理エラー", e);
		}
		return employee;
	}

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
	 * 渡された部門IDの部門情報をリソースから取得する
	 *
	 * @param deptId 部門ID
	 * @return 渡された部門IDの部門情報
	 * @throws Exception リソースの接続に失敗、又は、情報の取得に失敗
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
	 * リソースにある社員情報を指定された値に更新。更新件数を返却
	 *
	 * @param employee 社員情報
	 * @throws Exception リソースの接続に失敗、又は、情報の更新に失敗
	 */
	public void updateEmployee(Employee employee) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		try (Connection connection = c.getConnection();) {
			try {
				EmployeeDAO dao = new EmployeeDAO(connection);
				int updateNum = dao.update(employee);
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
}
