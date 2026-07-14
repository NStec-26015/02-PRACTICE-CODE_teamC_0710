package model.service;

import java.sql.Connection;
import java.sql.SQLException;

import model.dao.EmployeeDAO;
import model.dto.Employee;
import model.exception.ServiceException;
import model.util.ConnectionManager;

/**
 * UC05【社員情報削除】機能のクラス<br>
 * ユーザがシステムに登録されている社員情報を削除できるように、削除する社員の指定と削除を行う為の機能
 *
 * @author Fullness, Inc.
 */
public class DeleteEmployeeService {

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
				throw new ServiceException("取得失敗",e);
			}
		} catch (SQLException e) {
			throw new ServiceException("DB処理エラー",e);
		}
		return employee;
	}

	/**
	 * 指定された社員情報をリソースから削除。削除件数を返却
	 *
	 * @param employee 社員情報
	 * @throws ServiceException リソースの接続に失敗、情報の削除に失敗、又は、削除対象の特定に失敗
	 */
	public void deleteEmployee(Employee employee) throws ServiceException {
		ConnectionManager c = new ConnectionManager();
		try(Connection connection = c.getConnection();) {
			try {
				EmployeeDAO dao = new EmployeeDAO(connection);
				int deleteNum = dao.delete(employee);
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
}
