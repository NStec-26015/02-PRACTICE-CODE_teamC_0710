package util.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.dto.Department;
import model.dto.Employee;

/**
 * テスト用DAOクラス<br>
 *
 * @author Fullness, Inc.
 *
 */
public class ForTestDAO {
	/**
	 * コネクション
	 */
	private Connection connection;
	/**
	 * コンストラクタ
	 * @param connection
	 */
	public ForTestDAO(Connection connection) {
		this.connection = connection;
	}
	/** 登録：社員SQL */
	private static final String INSERT_EMP_SQL = "INSERT INTO employee(emp_id, emp_name, phone_number, email_address, dept_id) VALUES (nextval('seq_empno'), ?, ?, ?, ?)";
	/** 登録：部門SQL */
	private static final String INSERT_DEPT_SQL = "INSERT INTO department(dept_id, dept_name) VALUES (nextval('seq_deptno'), ?)";

	/**
	 * テスト用に社員情報を複数登録する
	 * @param employees
	 */
	public void insertEmps(List<Employee> employees) {
		for (Employee employee : employees) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMP_SQL);) {
				preparedStatement.setString(1, employee.getEmpName());
				preparedStatement.setString(2, employee.getPhone());
				preparedStatement.setString(3, employee.getMailAddress());
				preparedStatement.setInt(4, employee.getDeptId());
				preparedStatement.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * テスト用に部門情報を複数登録する
	 * @param departments
	 */
	public void insertDepts(List<Department> departments) {
		for (Department department : departments) {
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = connection.prepareStatement(INSERT_DEPT_SQL);
				preparedStatement.setString(1, department.getDeptName());
				preparedStatement.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				if (preparedStatement != null) {
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	/** シーケンス（社員）削除SQL */
	private static final String DROP_EMP_SQL = "drop sequence if exists seq_empno";
	/** シーケンス（部門）削除SQL */
	private static final String DROP_DEPT_SQL = "drop sequence if exists seq_deptno";
	/** テーブル（社員）削除SQL */
	private static final String DROP_EMPNO_SEQ_SQL = "drop table if exists employee";
	/** テーブル（部門）削除SQL */
	private static final String DROP_DEPTNO_SEQ_SQL = "drop table if exists department";

	/**
	 * シーケンスとテーブルをドロップする
	 */
	public void dropAll() {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(DROP_EMP_SQL);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = connection.prepareStatement(DROP_DEPT_SQL);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = connection.prepareStatement(DROP_EMPNO_SEQ_SQL);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = connection.prepareStatement(DROP_DEPTNO_SEQ_SQL);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/** テーブル（社員）作成SQL */
	private static final String CREATE_EMP_SQL = "create table employee( emp_id integer, emp_name varchar(100) not null, phone_number varchar(20), email_address varchar(100), dept_id integer, primary key(emp_id), foreign key(dept_id) REFERENCES department(dept_id) )";
	/** テーブル（部門）作成SQL */
	private static final String CREATE_DEPT_SQL = "create table department(dept_id integer ,dept_name varchar(100) not null,primary key(dept_id))";
	/** シーケンス（社員）作成SQL */
	private static final String CREATE_EMPNO_SEQ_SQL = "create sequence seq_empno"
			+ " start with 1001"
			+ " increment by 1"
			+ " maxvalue 9999";
	/** シーケンス（部門）作成SQL */
	private static final String CREATE_DEPTNO_SEQ_SQL = "create sequence seq_deptno"
			+ " start with 101"
			+ " increment by 1"
			+ " maxvalue 999";

	/**
	 * シーケンスとテーブルを作成する
	 */
	public void createAll() {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(CREATE_DEPT_SQL);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = connection.prepareStatement(CREATE_EMP_SQL);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = connection.prepareStatement(CREATE_DEPTNO_SEQ_SQL);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = connection.prepareStatement(CREATE_EMPNO_SEQ_SQL);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/** パスワード変更用SQL(リセット) */
	private static final String RESET_PASSWORD = "ALTER USER postgres WITH PASSWORD 'postgres'";
	/** パスワード変更用SQL(変更) */
	private static final String CHANGE_PASSWORD = "ALTER USER postgres WITH PASSWORD 'new'";

	/**
	 * パスワードをリセットする
	 */
	public void resetPassword() {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(RESET_PASSWORD);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * パスワードを変更する
	 */
	public void changePassword() {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(CHANGE_PASSWORD);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}

