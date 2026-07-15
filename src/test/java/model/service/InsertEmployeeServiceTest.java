package model.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.dto.Department;
import model.dto.Employee;
import model.exception.ServiceException;
import util.TestUtil;

/**
 * UC03【社員情報登録】機能のテストクラス<br>
 *
 * @author Fullness, Inc.
 *
 */
@DisplayName("UC03【社員情報登録】機能のテスト")
public class InsertEmployeeServiceTest {

    /**
     * テスト対象
     */
    InsertEmployeeService target;

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
        target = new InsertEmployeeService();
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
    @DisplayName("メールアドレス重複検証")
    public void testIsDuplicateMailAddress02() throws Exception {
        TestUtil.setDS101ToDB();
        assertFalse(target.isDuplicateMailAddress("newuser@foo.bar"));
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
    @DisplayName("社員情報を登録する")
    public void testCreateEmployee01() throws Exception {
        TestUtil.setDS101ToDB();
        Employee employee = new Employee();
        employee.setEmpName("佐藤太郎");
        employee.setDeptId(TestUtil.dept101.getDeptId());
        employee.setPhone("090-1234-5678");
        employee.setMailAddress("sato@foo.bar");

        target.createEmployee(employee);

        List<Employee> actual = new GetEmployeeListService().readEmployeeAllWithDeptName();
        assertEquals(1, actual.size());
        assertEquals("佐藤太郎", actual.get(0).getEmpName());
        assertEquals("sato@foo.bar", actual.get(0).getMailAddress());
        assertEquals(TestUtil.dept101, actual.get(0).getDepartment());
    }
}
