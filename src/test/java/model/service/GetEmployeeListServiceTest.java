// package model.service;

// import static org.junit.jupiter.api.Assertions.*;

// import java.util.List;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// import model.dto.Employee;
// import model.exception.ServiceException;
// import util.TestUtil;

// /**
// * UC01【社員一覧表示】機能のテストクラス<br>
// *
// * @author Fullness, Inc.
// *
// */
// @DisplayName("UC01【社員一覧表示】機能のテスト")
// public class GetEmployeeListServiceTest {

// /**
// * テスト対象
// */
// GetEmployeeListService target;

// /**
// * 後処理
// * @throws Exception
// */
// @AfterAll
// public static void tearDownAfterClass() throws Exception {
// TestUtil.initDB();
// TestUtil.setDS101ToDB();
// TestUtil.setDS001ToDB();
// }

// /**
// * 各テスト前に実行
// * @throws Exception
// */
// @BeforeEach
// public void setUp() throws Exception {
// TestUtil.initDB();
// target = new GetEmployeeListService();
// }

// @Test
// @DisplayName("社員情報とその社員が所属する部門名を複数取得:データあり")
// public void testGetEmpListWithDeptName01() throws Exception {
// TestUtil.setDS101ToDB();
// TestUtil.setDS001ToDB();
// List<Employee> expected = TestUtil.getDS001();
// List<Employee> actual = target.readEmployeeAllWithDeptName();
// assertEquals(expected, actual);
// }

// @Test
// @DisplayName("社員情報とその社員が所属する部門名を複数取得:データなし")
// public void testGetEmpListWithDeptName02() throws Exception {
// TestUtil.setDS101ToDB();
// TestUtil.setDS002ToDB();
// List<Employee> expected = TestUtil.getDS002();
// List<Employee> actual = target.readEmployeeAllWithDeptName();
// assertEquals(expected, actual);
// }

// @Test
// @DisplayName("社員情報とその社員が所属する部門名を複数取得:例外処理（取得失敗）")
// public void testGetEmpListWithDeptName03() throws Exception {
// TestUtil.clearDB();
// assertThrows(ServiceException.class, () ->
// target.readEmployeeAllWithDeptName());
// }

// @Test
// @DisplayName("社員情報とその社員が所属する部門名を複数取得:例外処理（DB処理エラー）")
// public void testGetEmpListWithDeptName04() throws Exception {
// TestUtil.changeDBSetting();
// try {
// assertThrows(ServiceException.class, () ->
// target.readEmployeeAllWithDeptName());
// } finally {
// TestUtil.resetDBSetting();
// }
// }
// }
