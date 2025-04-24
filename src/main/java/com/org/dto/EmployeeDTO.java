package com.org.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class EmployeeDTO {

    private String name;
    private LocalDate dob;
    private String gender;
    private String empCode;
    private List<AddressDTO> addressList;
}
