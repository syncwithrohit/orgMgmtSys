package com.org.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpDeptRemovalReq {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ReqStatus status = ReqStatus.PENDING;
    private String reason;
    private LocalDate reqDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee emp;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department dept;

    public enum ReqStatus {
        PENDING , APPROVED , REJECTED
    }
}
