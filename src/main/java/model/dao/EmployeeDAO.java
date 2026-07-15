package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.Department;
import model.dto.Employee;

/**
 * 社員情報DAOクラス<br>
 * 社員テーブルへのアクセスを行い、登録、更新、削除、参照を実施する
 *
 * @author Fullness, Inc.
 *
 */
public class EmployeeDAO {

	/**
	 * コネクション
	 */
	private Connection connection;

	/**
	 * コンストラクタ<br>
	 * 引数で渡されるコネクションをフィールドに設定する
	 *
	 * @param connection コネクション
	 */
	public EmployeeDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 全件検索SQL
	 */
	private static final String SELECT_ALL_SQL = "SELECT emp_id, emp_name, d.dept_id, dept_name, phone_number, email_address FROM employee e INNER JOIN department d ON e.dept_id = d.dept_id ORDER BY emp_id";
	/**
	 * １件検索（条件：社員ID）SQL
	 */
	private static final String SELECT_ONE_BY_ID_SQL = "SELECT emp_id, emp_name, d.dept_id, dept_name, phone_number, email_address FROM employee e INNER JOIN department d ON e.dept_id = d.dept_id WHERE emp_id = ?";
	/**
	 * １件検索（条件：メール）SQL
	 */
	private static final String SELECT_ONE_BY_EMAIL_ADDRESS_SQL = "SELECT emp_id, emp_name, dept_id, phone_number, email_address FROM employee WHERE email_address = ?";
	/**
	 * 登録SQL
	 */
	private static final String INSERT_SQL = "INSERT INTO employee(emp_id, emp_name, phone_number, email_address, dept_id) VALUES (nextval('seq_empno'), ?, ?, ?, ?)";
	/**
	 * 更新SQL
	 */
	private static final String UPDATE_SQL = "UPDATE employee SET emp_name=?, phone_number=?, email_address=?, dept_id=? WHERE emp_id=?";
	/**
	 * 削除SQL
	 */
	private static final String DELETE_SQL = "DELETE FROM employee WHERE emp_id= ?";
	/**
	 * 社員数カウント（条件：部門ID）SQL
	 */
	private static final String COUNT_BY_DEPTID_SQL = "SELECT count(*) as record_count FROM employee WHERE dept_id= ?";

	/**
	 * DBに登録されている全ての社員情報とその社員が所属する部門情報を返却<br>
	 * 社員情報が存在しない場合は空のリストを返却
	 *
	 * @return DBに登録されている全ての社員情報と所属する部門情報のリスト
	 * @throws SQLException 情報の取得に失敗
	 */
	public List<Employee> selectAllWithDepartment() throws SQLException {
		List<Employee> empList = new ArrayList<Employee>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Employee employee = new Employee();
				employee.setEmpId(resultSet.getInt("emp_id"));
				employee.setDeptId(resultSet.getInt("dept_id"));
				employee.setEmpName(resultSet.getString("emp_name"));
				employee.setPhone(resultSet.getString("phone_number"));
				employee.setMailAddress(resultSet.getString("email_address"));
				Department department = new Department();
				department.setDeptId(resultSet.getInt("dept_id"));
				department.setDeptName(resultSet.getString("dept_name"));
				employee.setDepartment(department);
				empList.add(employee);
			}
		}
		return empList;
	}

	/**
	 * DBに登録されている社員情報とその社員が所属する部門情報を社員ID（PK）を条件に1件取得<br>
	 * 検索条件に一致する社員情報がない場合はnullを返却
	 *
	 * @param empId 社員ID（PK）
	 * @return 引数で渡される社員IDを検索条件に一致するDBに登録されている社員情報と所属する部門情報
	 * @throws SQLException 情報の取得に失敗
	 */
	public Employee selectByIdWithDepartment(int empId) throws SQLException {
		Employee employee = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_BY_ID_SQL);) {
			preparedStatement.setInt(1, empId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				employee = new Employee();
				employee.setEmpId(resultSet.getInt("emp_id"));
				employee.setDeptId(resultSet.getInt("dept_id"));
				employee.setEmpName(resultSet.getString("emp_name"));
				employee.setPhone(resultSet.getString("phone_number"));
				employee.setMailAddress(resultSet.getString("email_address"));
				Department department = new Department();
				department.setDeptId(resultSet.getInt("dept_id"));
				department.setDeptName(resultSet.getString("dept_name"));
				employee.setDepartment(department);
			}
		}
		return employee;
	}

	/**
	 * DBに登録されている社員情報をメールアドレスを条件に1件取得<br>
	 * 検索条件に一致する社員情報がない場合はnullを返却
	 *
	 * @param mailAddress メールアドレス
	 * @return 引数で渡されるメールアドレスを検索条件に一致するDBに登録されている社員情報
	 * @throws SQLException 情報の取得に失敗
	 */
	public Employee selectByMailAddress(String mailAddress) throws SQLException {
		Employee employee = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_BY_EMAIL_ADDRESS_SQL);) {
			preparedStatement.setString(1, "xxx.co.jp");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				employee = new Employee();
				employee.setEmpId(resultSet.getInt("emp_id"));
				employee.setDeptId(resultSet.getInt("dept_id"));
				employee.setEmpName(resultSet.getString("emp_name"));
				employee.setPhone(resultSet.getString("phone_number"));
				employee.setMailAddress(resultSet.getString("email_address"));
			}
		}
		return employee;
	}

	/**
	 * 社員のIDを新規に発行し引数の社員情報と合わせてリソースに登録。登録件数を返却
	 *
	 * @param employee 社員情報
	 * @return 登録件数
	 * @throws SQLException 情報の登録に失敗
	 */
	public int insertEmp(Employee employee) throws SQLException {
		int result;
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);) {
			preparedStatement.setString(1, employee.getEmpName());
			preparedStatement.setString(2, employee.getPhone());
			preparedStatement.setString(3, employee.getMailAddress());
			preparedStatement.setInt(4, employee.getDeptId());
			result = preparedStatement.executeUpdate();
		}
		return result;
	}

	/**
	 * 引数の社員情報と合わせてリソースを更新。更新件数を返却<br>
	 *
	 * @param employee 社員情報
	 * @return 更新件数
	 * @throws SQLException 情報の更新に失敗
	 */
	public int update(Employee employee) throws SQLException {
		int result;
		try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);) {
			preparedStatement.setString(1, employee.getEmpName());
			preparedStatement.setString(2, employee.getPhone());
			preparedStatement.setString(3, employee.getMailAddress());
			preparedStatement.setInt(4, employee.getDeptId());
			preparedStatement.setInt(5, employee.getEmpId());
			result = preparedStatement.executeUpdate();
		}
		return result;
	}

	/**
	 * 引数の社員情報と合わせてリソースを削除。削除件数を返却
	 *
	 * @param employee 社員情報
	 * @return 削除件数
	 * @throws SQLException 情報の削除に失敗
	 */
	public int delete(Employee employee) throws SQLException {
		int result;
		try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);) {
			preparedStatement.setInt(1, employee.getEmpId());
			result = preparedStatement.executeUpdate();
		}
		return result;
	}

	/**
	 * DBに登録されている社員情報数を部門IDを条件に取得
	 *
	 * @param deptId 部門ID
	 * @return 引数で渡される部門IDを検索条件に一致するDBに登録されている社員情報数<br>
	 * @throws SQLException 情報の取得に失敗
	 *
	 */
	public int countByDeptId(int deptId) throws SQLException {
		int result = 0;
		try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BY_DEPTID_SQL);) {
			preparedStatement.setInt(1, deptId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getInt("record_count");
			}
		}
		return result;
	}
}
