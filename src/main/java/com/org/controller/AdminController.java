package com.org.controller;

import com.org.dto.DepartmentDTO;
import com.org.dto.EmpDeptRemovalReqDTO;
import com.org.dto.EmployeeDTO;
import com.org.entity.Department;
import com.org.entity.EmpDeptRemovalReq;
import com.org.entity.Employee;
import com.org.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/reg-emp")
    public ResponseEntity<Employee> regEmployee (@RequestBody EmployeeDTO empDTO) {
        return ResponseEntity.ok( adminService.addEmployee(empDTO) );
    }

    @PostMapping("/add-dept")
    public ResponseEntity<Department> addDepartment (@RequestBody DepartmentDTO deptDTO) {
        return ResponseEntity.ok(adminService.createDepartment(deptDTO));
    }

    @PostMapping("/emps/{empId}/assign-depts")
    public ResponseEntity<String> assignDepartmentsToEmployee (@PathVariable Long empId, @RequestBody List<Long> deptIds) {
        try {
            adminService.assignDepts( empId , deptIds );
            return ResponseEntity.ok("Departments assigned successfully to Employee Id : " + empId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/emps/search")
    public ResponseEntity<List<Employee>> searchEmpsByName (@RequestParam String name) {
        return ResponseEntity.ok( adminService.findEmployeeByName(name) );
    }

    @GetMapping("/depts/{deptId}/emps")
    public ResponseEntity<List<Employee>> getEmpsByDept (@PathVariable Long deptId) {
        return ResponseEntity.ok( adminService.findEmployeesByDepartment(deptId) );
    }

    @GetMapping("/reqs")
    public ResponseEntity<List<EmpDeptRemovalReqDTO>> getReqsByStatus (@RequestParam String status) {
        EmpDeptRemovalReq.ReqStatus reqStatus = EmpDeptRemovalReq.ReqStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(adminService.getReqsByStatus(reqStatus));
    }

    @PutMapping("/reqs/{reqId}/process")
    public ResponseEntity<String> handleReqAction (@PathVariable Long reqId, @RequestParam String status) {
        EmpDeptRemovalReq.ReqStatus reqStatus = EmpDeptRemovalReq.ReqStatus.valueOf(status.toUpperCase());
        adminService.changeRequestStatus(reqId, reqStatus);
        return ResponseEntity.ok("Request status updated to : " + status + " for Request Id : " + reqId);
    }
}
