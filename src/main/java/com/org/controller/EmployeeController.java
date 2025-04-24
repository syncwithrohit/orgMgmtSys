package com.org.controller;

import com.org.dto.DepartmentDTO;
import com.org.dto.EmpDeptRemovalReqDTO;
import com.org.dto.EmpValidateDTO;
import com.org.dto.UpdatedEmployeeDTO;
import com.org.entity.Employee;
import com.org.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp/{empCode}")
public class EmployeeController {

    @Autowired
    private EmployeeService empService;

    @PostMapping("/update-profile")
    public ResponseEntity<String> updEmp (@PathVariable String empCode, @RequestBody UpdatedEmployeeDTO updEmpDTO) {
        empService.updateEmployee( empCode, updEmpDTO );
        return ResponseEntity.ok("Details updated Successfully for Employee Code : " + empCode );
    }

    @GetMapping("/depts")
    public ResponseEntity<List<DepartmentDTO>> getDeptsForEmp (@PathVariable String empCode) {
        return ResponseEntity.ok( empService.getDepartmentsForEmp(empCode) );
    }

    @PostMapping("/req-dept-removal")
    public ResponseEntity<String> reqDeptRemoval (@PathVariable String empCode, @RequestBody EmpDeptRemovalReqDTO empDTO) {
        empService.submitDeptRemovalReq( empCode , empDTO );
        return ResponseEntity.ok("Your Department Removal Request has been submitted successfully for review.");
    }

    @PostMapping("/validate")
    public ResponseEntity<Employee> validateEmp (@RequestBody EmpValidateDTO empValidateDTO) {
        return ResponseEntity.ok(empService.validateEmployee(empValidateDTO.getEmpCode(), empValidateDTO.getDob()));
    }

}
