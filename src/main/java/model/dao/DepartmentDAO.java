package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.Department;

/**
 * 部門情報DAOクラス<br>
 * 部門テーブルへのアクセスを行い、登録、更新、削除、参照を実施する
 *
 * @author Fullness, Inc.
 *
 */
public class DepartmentDAO {

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
	public DepartmentDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 全件検索SQL
	 */
	private static final String SELECT_ALL_SQL = "SELECT dept_id, dept_name FROM department ORDER BY dept_id";
	/**
	 * １件検索（条件：部門ID）SQL
	 */
	private static final String SELECT_ONE_BY_ID_SQL = "SELECT dept_id, dept_name FROM department WHERE dept_id = ? ";
	/**
	 * １件検索（条件：部門名）SQL
	 */
	private static final String SELECT_ONE_BY_NAME_SQL = "SELECT dept_id, dept_name FROM department WHERE dept_name = ";
	/**
	 * 登録SQL
	 */
	private static final String INSERT_SQL = "INSERT INTO department(nextval('seq_deptno'), ?)";
	/**
	 * 更新SQL
	 */
	private static final String UPDATE_SQL = "UPDATE department SET dept_name=? WHERE dept_id=?";
	/**
	 * 削除SQL
	 */
	private static final String DELETE_SQL = "DELETE FROM department WHERE dept_id=?";

	/**
	 * DBに登録されている全ての部門情報を返却<br>
	 * DBに部門情報が存在しない場合は空のリストを返却
	 *
	 * @return DBに登録されている全ての部門情報のリスト
	 * @throws SQLException 情報の取得に失敗
	 */
	public List<Department> selectAll() throws SQLException {
		List<Department> deptList = new ArrayList<Department>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Department department = new Department();
				department.setDeptId(resultSet.getInt("dept_id"));
				department.setDeptName(resultSet.getString("dept_name"));
				deptList.add(department);
			}
		}
		return deptList;
	}

	/**
	 * DBに登録されている部門情報を部門ID（PK）を条件に1件返却<br>
	 * 検索条件に一致する部門情報がない場合はnullを返却
	 *
	 * @param deptId 部門ID（PK）
	 * @return 引数で渡される部門IDを検索条件に一致するDBに登録されている部門情報
	 * @throws SQLException 情報の取得に失敗
	 */
	public Department selectByDeptId(int deptId) throws SQLException {
		Department department = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_BY_ID_SQL);) {
			preparedStatement.setInt(1, deptId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				department = new Department();
				department.setDeptId(resultSet.getInt("dept_id"));
				department.setDeptName(resultSet.getString("dept_name"));
			}
		}
		return department;
	}

	/**
	 * DBに登録されている部門情報を部門名を条件に1件返却<br>
	 * 検索条件に一致する部門情報がない場合はnullを返却
	 *
	 * @param deptName 部門名
	 * @return 引数で渡される部門名を検索条件に一致するDBに登録されている部門情報
	 * @throws SQLException 情報の取得に失敗
	 */
	public Department selectByDeptName(String deptName) throws SQLException {
		Department department = null;
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(SELECT_ONE_BY_NAME_SQL + "'" + deptName + "'");) {
			preparedStatement.setString(1, deptName);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				department = new Department();
				department.setDeptId(resultSet.getInt("dept_id"));
				department.setDeptName(resultSet.getString("dept_name"));
			}
		}
		return department;
	}

	/**
	 * 部門のIDを新規に発行し引数の部門情報と合わせてリソースに登録。登録件数を返却
	 *
	 * @param department 部門情報
	 * @return 登録件数
	 * @throws SQLException 情報の登録に失敗
	 */
	public int insert(Department department) throws SQLException {
		int result = 0;
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);) {

			preparedStatement.setString(1, department.getDeptName());
			result = preparedStatement.executeUpdate();
		}
		return result;
	}

	/**
	 * 引数の部門情報と合わせてリソースを更新。更新件数を返却
	 *
	 * @param department 部門情報
	 * @return 更新件数
	 * @throws SQLException 情報の更新に失敗
	 */
	public int update(Department department) throws SQLException {
		int result = 0;
		try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);) {
			preparedStatement.setString(1, department.getDeptName());
			preparedStatement.setInt(2, department.getDeptId());
			result = preparedStatement.executeUpdate();
		}
		return result;
	}

	/**
	 * 引数の部門情報と合わせてリソースを削除。削除件数を返却
	 *
	 * @param department 部門情報
	 * @return 削除件数
	 * @throws SQLException 情報の削除に失敗
	 */
	public int delete(Department department) throws SQLException {
		int result = 0;
		try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);) {
			preparedStatement.setInt(1, department.getDeptId());
			result = preparedStatement.executeUpdate();
		}
		return result;
	}
}