package com.org.repository;

import com.org.entity.EmpDeptRemovalReq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeptRemovalReqRepo extends JpaRepository < EmpDeptRemovalReq , Long > {
    List<EmpDeptRemovalReq> findByStatus (EmpDeptRemovalReq.ReqStatus status);
}
