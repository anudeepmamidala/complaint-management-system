package com.anudeep.complaint_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anudeep.complaint_system.model.Complaint;
import com.anudeep.complaint_system.model.ComplaintStatus;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStatus(ComplaintStatus status);
    List<Complaint> findByDepartment(String department);
}
