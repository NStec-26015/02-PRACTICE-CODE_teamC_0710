package model.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社員情報DTOクラス
 *
 * @author Fullness, Inc.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	/** 社員ID */
	private int empId;
	/** 社員名 */
	private String empName;
	/** 電話番号 */
	private String phone;
	/** メールアドレス */
	private String mailAddress;
	/** 部門ID */
	private int deptId;
	/** 部門情報 */
	private Department department;

	@Override
	public int hashCode() {
		return Objects.hash(department, deptId, empId, empName, mailAddress, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(department, other.department) && deptId == other.deptId && empId == other.empId
				&& Objects.equals(empName, other.empName) && Objects.equals(mailAddress, other.mailAddress)
				&& Objects.equals(phone, other.phone);
	}
}
