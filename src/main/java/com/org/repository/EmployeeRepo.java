package com.org.repository;

import com.org.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

//    List<Employee> findByDepartmentsId(String deptId);
    List<Employee> findByNameContainingIgnoreCase(String empName);

    Optional<Employee> findByEmpCode(String empCode);
    Optional<Employee> findByEmpCodeAndDob(String empCode, LocalDate dob);
}
