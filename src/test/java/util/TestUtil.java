package util;

import java.util.ArrayList;
import java.util.List;

import model.dto.Department;
import model.dto.Employee;
import util.dbaccess.ForTestConnectionManager;
import util.dbaccess.ForTestDAO;

/**
 * テスト共通クラス<br>
 *
 * @author Fullness, Inc.
 *
 */
public class TestUtil {

	public static final Employee emp1001;
	public static final Employee emp1001_nodept;
	public static final Employee emp1002;
	public static final Employee emp1003;
	public static final Employee emp1003_upd;
	public static final Employee emp1004;
	public static final Department dept101;
	public static final Department dept102;
	public static final Department dept103;
	public static final Department dept103_upd;
	public static final Department dept104;

	static {
		dept101 = new Department();
		dept101.setDeptId(101);
		dept101.setDeptName("人事部");
		dept102 = new Department();
		dept102.setDeptId(102);
		dept102.setDeptName("企画部");
		dept103 = new Department();
		dept103.setDeptId(103);
		dept103.setDeptName("システム開発部");
		dept103_upd = new Department();
		dept103_upd.setDeptId(103);
		dept103_upd.setDeptName("営業部");
		dept104 = new Department();
		dept104.setDeptId(104);
		dept104.setDeptName("営業部");
		;
		emp1001 = new Employee();
		emp1001.setEmpId(1001);
		emp1001.setDeptId(101);
		emp1001.setEmpName("山田太郎");
		emp1001.setPhone("000-1111-2222");
		emp1001.setMailAddress("taro@foo.bar.baz");
		emp1001.setDepartment(dept101);
		emp1001_nodept = new Employee();
		emp1001_nodept.setEmpId(1001);
		emp1001_nodept.setDeptId(101);
		emp1001_nodept.setEmpName("山田太郎");
		emp1001_nodept.setPhone("000-1111-2222");
		emp1001_nodept.setMailAddress("taro@foo.bar.baz");
		emp1002 = new Employee();
		emp1002.setEmpId(1002);
		emp1002.setDeptId(102);
		emp1002.setEmpName("川田次郎");
		emp1002.setPhone("000-2222-3333");
		emp1002.setMailAddress("jiro@foo.bar.baz");
		emp1002.setDepartment(dept102);
		emp1003 = new Employee();
		emp1003.setEmpId(1003);
		emp1003.setDeptId(101);
		emp1003.setEmpName("海田三郎");
		emp1003.setPhone("000-3333-4444");
		emp1003.setMailAddress("saburo@foo.bar.baz");
		emp1003.setDepartment(dept101);
		emp1003_upd = new Employee();
		emp1003_upd.setEmpId(1003);
		emp1003_upd.setDeptId(103);
		emp1003_upd.setEmpName("空田四郎");
		emp1003_upd.setPhone("000-4444-5555");
		emp1003_upd.setMailAddress("shiro@foo.bar.baz");
		emp1003_upd.setDepartment(dept103);
		emp1004 = new Employee();
		emp1004.setEmpId(1004);
		emp1004.setDeptId(103);
		emp1004.setEmpName("空田四郎");
		emp1004.setPhone("000-4444-5555");
		emp1004.setMailAddress("shiro@foo.bar.baz");
		emp1004.setDepartment(dept103);
	}

	/**
	 * DB初期化。テストの前に実施
	 */
	public static void initDB() {
		ForTestConnectionManager c = new ForTestConnectionManager();
		ForTestDAO dao = new ForTestDAO(c.getConnection());
		dao.dropAll();
		dao.createAll();
		c.closeConnection();
	}

	/**
	 * DBの設定を変更。DB接続エラーのテスト時に実施
	 */
	public static void changeDBSetting() {
		ForTestConnectionManager c = new ForTestConnectionManager();
		ForTestDAO dao = new ForTestDAO(c.getConnection());
		dao.changePassword();
		c.closeConnection();
	}

	/**
	 * DBの設定を元にもどす。changeDBSetting実施後に必ず実施する事。
	 */
	public static void resetDBSetting() {
		ForTestConnectionManager c = new ForTestConnectionManager();
		ForTestDAO dao = new ForTestDAO(c.getConnectionForReset());
		dao.resetPassword();
		c.closeConnection();
	}

	/**
	 * DBのデータを削除
	 */
	public static void clearDB() {
		ForTestConnectionManager c = new ForTestConnectionManager();
		ForTestDAO dao = new ForTestDAO(c.getConnection());
		dao.dropAll();
		c.closeConnection();
	}

	/**
	 * 社員リスト作成
	 * 
	 * @return 社員リスト
	 */
	public static List<Employee> getDS001() {
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(emp1001);
		emps.add(emp1002);
		emps.add(emp1003);
		return emps;
	}

	/**
	 * 社員データを複数登録する
	 */
	public static void setDS001ToDB() {
		ForTestConnectionManager c = new ForTestConnectionManager();
		ForTestDAO dao = new ForTestDAO(c.getConnection());
		dao.insertEmps(getDS001());
		c.closeConnection();
	}

	/**
	 * 社員データ用：空のリストを作成する
	 * 
	 * @return 空のリスト
	 */
	public static List<Employee> getDS002() {
		List<Employee> emps = new ArrayList<Employee>();
		return emps;
	}

	/**
	 * 社員データを登録する（データなし）
	 */
	public static void setDS002ToDB() {
		ForTestConnectionManager c = new ForTestConnectionManager();
		ForTestDAO dao = new ForTestDAO(c.getConnection());
		dao.insertEmps(getDS002());
		c.closeConnection();
	}

	/**
	 * 部門リスト作成
	 * 
	 * @return 部門リスト
	 */
	public static List<Department> getDS101() {
		List<Department> depts = new ArrayList<Department>();
		depts.add(dept101);
		depts.add(dept102);
		depts.add(dept103);
		return depts;
	}

	/**
	 * 部門データを複数登録する
	 */
	public static void setDS101ToDB() {
		ForTestConnectionManager c = new ForTestConnectionManager();
		ForTestDAO dao = new ForTestDAO(c.getConnection());
		dao.insertDepts(getDS101());
		c.closeConnection();
	}

	/**
	 * 部門データ用：空のリストを作成する
	 * 
	 * @return 空のリスト
	 */
	public static List<Department> getDS102() {
		List<Department> depts = new ArrayList<Department>();
		return depts;
	}

	/**
	 * 部門データを登録する（データなし）
	 */
	public static void setDS102ToDB() {
		ForTestConnectionManager c = new ForTestConnectionManager();
		ForTestDAO dao = new ForTestDAO(c.getConnection());
		dao.insertDepts(getDS102());
		c.closeConnection();
	}
}
