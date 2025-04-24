package com.org.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UpdatedEmployeeDTO {

    private String email;
    private String mobile;
    private String emergencyContact;
    private List<AddressDTO> addresses;

}
