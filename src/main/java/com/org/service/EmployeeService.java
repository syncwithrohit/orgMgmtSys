package com.org.service;

import com.org.dto.DepartmentDTO;
import com.org.dto.EmpDeptRemovalReqDTO;
import com.org.dto.UpdatedEmployeeDTO;
import com.org.entity.Department;
import com.org.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    void updateEmployee (String empCode, UpdatedEmployeeDTO updEmpDTO);
    List<DepartmentDTO> getDepartmentsForEmp (String empCode);
    void submitDeptRemovalReq (String empCode, EmpDeptRemovalReqDTO empDTO);
    Employee validateEmployee (String empCode, LocalDate dob);
}
