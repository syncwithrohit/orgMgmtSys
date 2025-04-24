package com.org.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EmpValidateDTO {

    private String empCode;
    private LocalDate dob;
}
