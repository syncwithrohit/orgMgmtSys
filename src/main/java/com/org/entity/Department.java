package com.org.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String type;
    private int numOfEmps;

    private List<String> projects;

    @ManyToMany(mappedBy = "departmentsSet")
    private Set<Employee> employeeSet = new HashSet<Employee>();

    public void setNumOfEmps(Integer numOfEmps) {
        this.numOfEmps = ( numOfEmps != null ) ? numOfEmps : 0 ;
    }
}
