package com.org.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.dto.DepartmentDTO;
import com.org.dto.EmpDeptRemovalReqDTO;
import com.org.dto.UpdatedEmployeeDTO;
import com.org.entity.Address;
import com.org.entity.Department;
import com.org.entity.EmpDeptRemovalReq;
import com.org.entity.Employee;
import com.org.repository.DepartmentRepo;
import com.org.repository.DeptRemovalReqRepo;
import com.org.repository.EmployeeRepo;
import com.org.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo empRepo;
    private final DepartmentRepo deptRepo;
    private final DeptRemovalReqRepo deptRemovalReqRepo;
    private final ObjectMapper om;

    public EmployeeServiceImpl(EmployeeRepo empRepo, DepartmentRepo deptRepo,
                            DeptRemovalReqRepo deptRemovalReqRepo, ObjectMapper om) {
        this.empRepo = empRepo;
        this.deptRepo = deptRepo;
        this.deptRemovalReqRepo = deptRemovalReqRepo;
        this.om = om;
    }

    @Override
    public void updateEmployee(String empCode, UpdatedEmployeeDTO updEmpDTO) {
        Employee emp =  empRepo.findByEmpCode(empCode).orElseThrow(() -> new RuntimeException("Employee not found"));

        emp.setEmail(updEmpDTO.getEmail());
        emp.setMobile(updEmpDTO.getMobile());
        emp.setEmergencyContact(updEmpDTO.getEmergencyContact());

        List<Address> updAddr = updEmpDTO.getAddresses().stream().map( dto ->
                Address.builder()
                        .type(dto.getType())
                        .street(dto.getStreet())
                        .city(dto.getCity())
                        .state(dto.getState())
                        .zipcode(dto.getZipcode())
                        .emp(emp)
                        .build()).collect(Collectors.toList());

        emp.setAddressesList(updAddr);

        empRepo.save(emp);

    }

    @Override
    public List<DepartmentDTO> getDepartmentsForEmp(String empCode) {
        Employee emp = empRepo.findByEmpCode(empCode).orElseThrow(() -> new RuntimeException("Employee not found"));

        return emp.getDepartmentsSet().stream().map(d -> om.convertValue(d,DepartmentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void submitDeptRemovalReq(String empCode, EmpDeptRemovalReqDTO empDTO) {
        Employee emp = empRepo.findByEmpCode(empCode).orElseThrow(() -> new RuntimeException("Employee not found"));
        Department dept = deptRepo.findById(empDTO.getDeptId()).orElseThrow(() -> new RuntimeException("Department not found"));

        EmpDeptRemovalReq req = EmpDeptRemovalReq.builder().emp(emp).dept(dept).reqDate(LocalDate.now())
                .reason(empDTO.getReason()).status(EmpDeptRemovalReq.ReqStatus.PENDING).build();

        deptRemovalReqRepo.save(req);
    }

    @Override
    public Employee validateEmployee(String empCode, LocalDate dob) {
        return empRepo.findByEmpCodeAndDob(empCode,dob).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Employee Validation Failed."));
    }
}
