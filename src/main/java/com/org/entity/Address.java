package com.org.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String street;
    private String city;
    private String state;
    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee emp;
}
