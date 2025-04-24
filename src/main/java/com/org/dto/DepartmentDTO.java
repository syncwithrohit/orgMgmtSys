package com.org.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data @Builder
public class DepartmentDTO {

    private String name;
    private String description;
    private String type;
    private int numOfEmps;

    private List<String> projects;
}
