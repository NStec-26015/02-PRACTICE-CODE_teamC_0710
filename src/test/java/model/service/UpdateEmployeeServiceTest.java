package model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.dto.Department;
import model.dto.Employee;
import model.exception.ServiceException;
import util.TestUtil;

public class UpdateEmployeeServiceTest {

    UpdateEmployeeService target;

    /**
     * 後処理
     * 
     * @throws Exception
     */
    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        TestUtil.initDB();
        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();
    }

    /**
     * 各テスト前に実行
     * 
     * @throws Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        TestUtil.initDB();
        target = new UpdateEmployeeService();
    }

    @Test
    @DisplayName("メールアドレス重複チェック:データあり")
    public void testIsDuplicateMailAddressAll01() throws Exception {
        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();
        assertTrue(target.isDuplicateMailAddress(TestUtil.emp1001.getEmpId(), TestUtil.emp1001.getMailAddress()));
    }

    @Test
    @DisplayName("メールアドレス重複チェック:データなし")
    public void testIsDuplicateMailAddressAll02() throws Exception {
        TestUtil.setDS101ToDB();
        assertFalse(target.isDuplicateMailAddress(1001, "unknown@foo.bar.baz"));
    }

    @Test
    @DisplayName("メールアドレス重複チェック:例外処理（DB処理エラー）")
    public void testIsDuplicateMailAddress03() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class,
                    () -> target.isDuplicateMailAddress(TestUtil.emp1001.getEmpId(),
                            TestUtil.emp1001.getMailAddress()));
        } finally {
            TestUtil.resetDBSetting();
        }
    }

    @Test
    @DisplayName("社員IDに該当する社員情報のチェック:データあり")
    public void testReadEmployeeByEmpIdAll01() throws Exception {
        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();
        Employee expected = TestUtil.emp1001;
        Employee actual = target.readEmployeeByEmpId(TestUtil.emp1001.getEmpId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("社員IDに該当する社員情報のチェック:データなし")
    public void testReadEmployeeByEmpIdAll02() throws Exception {
        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();
        assertThrows(ServiceException.class,
                () -> target.readEmployeeByEmpId(1000));
    }

    @Test
    @DisplayName("部門情報を取得:データあり")
    public void testReadDepartmentAll01() throws Exception {
        TestUtil.setDS101ToDB();
        List<Department> expected = TestUtil.getDS101();
        List<Department> actual = target.readDepartmentAll();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("部門情報を取得:データなし")
    public void testReadDepartmentAll02() throws Exception {
        assertThrows(ServiceException.class, () -> target.readDepartmentAll());
    }

    @Test
    @DisplayName("部門情報を取得:例外処理（DB処理エラー）")
    public void testReadDepartmentAll03() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class, () -> target.readDepartmentAll());
        } finally {
            TestUtil.resetDBSetting();
        }
    }

    @Test
    @DisplayName("部門情報を部門IDで取得")
    public void testReadDepartmentByDeptId01() throws Exception {
        TestUtil.setDS101ToDB();
        Department expected = TestUtil.dept101;
        Department actual = target.readDepartmentByDeptId(expected.getDeptId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("部門情報を部門IDで取得:データなし")
    public void testReadDepartmentByDeptId02() throws Exception {
        Department expected = TestUtil.dept102;
        assertThrows(ServiceException.class, () -> target.readDepartmentByDeptId(expected.getDeptId()));
    }

    @Test
    @DisplayName("社員情報を更新する")
    public void testUpdateEmployee01() throws Exception {
        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();

        Employee updated = new Employee();
        updated.setEmpId(TestUtil.emp1003_upd.getEmpId());
        updated.setEmpName(TestUtil.emp1003_upd.getEmpName());
        updated.setPhone(TestUtil.emp1003_upd.getPhone());
        updated.setMailAddress(TestUtil.emp1003_upd.getMailAddress());
        updated.setDeptId(TestUtil.emp1003_upd.getDeptId());

        target.updateEmployee(updated);

        Employee actual = target.readEmployeeByEmpId(updated.getEmpId());
        assertEquals(updated.getEmpName(), actual.getEmpName());
        assertEquals(updated.getPhone(), actual.getPhone());
        assertEquals(updated.getMailAddress(), actual.getMailAddress());
        assertEquals(TestUtil.dept103, actual.getDepartment());
    }

    @Test
    @DisplayName("社員情報を更新する:例外処理（DB処理エラー）")
    public void testUpdateEmployee02() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class, () -> target.updateEmployee(TestUtil.emp1003_upd));
        } finally {
            TestUtil.resetDBSetting();
        }
    }

}
