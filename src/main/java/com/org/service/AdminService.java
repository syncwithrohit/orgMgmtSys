package com.org.service;

import com.org.dto.DepartmentDTO;
import com.org.dto.EmpDeptRemovalReqDTO;
import com.org.dto.EmployeeDTO;
import com.org.entity.Department;
import com.org.entity.EmpDeptRemovalReq;
import com.org.entity.Employee;

import java.util.List;

public interface AdminService {

    Employee addEmployee (EmployeeDTO empDTO);

    Department createDepartment (DepartmentDTO deptDTO);

    void assignDepts (Long empId, List<Long> deptIds);

    List<Employee> findEmployeeByName (String name);

    List<Employee> findEmployeesByDepartment (Long deptId);

    List<EmpDeptRemovalReqDTO> getReqsByStatus (EmpDeptRemovalReq.ReqStatus status);

    void changeRequestStatus (Long reqId, EmpDeptRemovalReq.ReqStatus status);

}
