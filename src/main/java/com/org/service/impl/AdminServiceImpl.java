package com.org.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.dto.DepartmentDTO;
import com.org.dto.EmpDeptRemovalReqDTO;
import com.org.dto.EmployeeDTO;
import com.org.entity.Address;
import com.org.entity.Department;
import com.org.entity.EmpDeptRemovalReq;
import com.org.entity.Employee;
import com.org.repository.AddressRepo;
import com.org.repository.DepartmentRepo;
import com.org.repository.DeptRemovalReqRepo;
import com.org.repository.EmployeeRepo;
import com.org.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private EmployeeRepo empRepo;
    @Autowired
    private AddressRepo addRepo;
    @Autowired
    private DepartmentRepo deptRepo;
    @Autowired
    private DeptRemovalReqRepo deptRemovalReqRepo;
    @Autowired
    private ObjectMapper om;

//    private final String COMPANY_NAME = "${org.mgmt.company.name}";
//    private final String COMPANY_NAME = "org.mgmt.company.name";
    private final String COMPANY_NAME = ResourceBundle.getBundle("application").getString("org.mgmt.company.name");

    @Transactional
    @Override
    public Employee addEmployee(EmployeeDTO empDTO) {
        Employee e = om.convertValue( empDTO , Employee.class);
        e.setEmpCode(generateEmpCode());
        Employee emp = empRepo.save(e);

        List<Address> addrList = new ArrayList<>();
        empDTO.getAddressList().forEach( addrDTO -> {
            Address address = om.convertValue( addrDTO , Address.class);
            address.setEmp(emp);
            addrList.add(address);
        });

        addRepo.saveAll(addrList);
        emp.setAddressesList(addrList);

        return empRepo.save(emp);
    }

    @Override
    public Department createDepartment(DepartmentDTO deptDTO) {
        return deptRepo.save(om.convertValue( deptDTO , Department.class));
    }

    @Override
    public void assignDepts(Long empId, List<Long> deptIds) {
        Employee emp = empRepo.findById( empId ).orElseThrow( () -> new RuntimeException("Employee not found"));
        List<Department> depts = deptRepo.findAllById( deptIds );

        emp.getDepartmentsSet().addAll( depts );
        depts.forEach( d -> d.setNumOfEmps( d.getNumOfEmps() + 1 ));

        deptRepo.saveAll(depts);
        empRepo.save(emp);
    }

    @Override
    public List<Employee> findEmployeeByName(String name) {
        return empRepo.findByNameContainingIgnoreCase( name );
    }

    @Override
    public List<Employee> findEmployeesByDepartment(Long deptId) {
        return new ArrayList<>( ( deptRepo.findById( deptId ).orElseThrow(
                () -> new RuntimeException("Department doesn't exist")) ).getEmployeeSet() );
    }

    @Override
    public List<EmpDeptRemovalReqDTO> getReqsByStatus(EmpDeptRemovalReq.ReqStatus status) {
        return deptRemovalReqRepo.findByStatus(status).stream().map(req -> om.convertValue(req, EmpDeptRemovalReqDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void changeRequestStatus(Long reqId, EmpDeptRemovalReq.ReqStatus status) {
        EmpDeptRemovalReq req = deptRemovalReqRepo.findById(reqId).orElseThrow(()->new RuntimeException("Request not found"));

        req.setStatus(status);
        deptRemovalReqRepo.save(req);

        if (status == EmpDeptRemovalReq.ReqStatus.APPROVED) {
            Employee emp = req.getEmp();
            emp.getDepartmentsSet().remove(req.getDept());
            empRepo.save(emp);
        }
    }

    public String generateEmpCode(){
        return COMPANY_NAME.concat(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .concat(String.valueOf(empRepo.count()+1));
    }

}
