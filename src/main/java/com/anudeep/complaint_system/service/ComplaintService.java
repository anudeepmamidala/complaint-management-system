package com.anudeep.complaint_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anudeep.complaint_system.model.Complaint;
import com.anudeep.complaint_system.model.ComplaintStatus;
import com.anudeep.complaint_system.repository.ComplaintRepository;

@Service
public class ComplaintService {

    private final ComplaintRepository repository;

    @Autowired
    public ComplaintService(ComplaintRepository repository) {
        this.repository = repository;
    }

    public Complaint save(Complaint complaint) {
        return repository.save(complaint);
    }

    public List<Complaint> getAll() {
        return repository.findAll();
    }

    public List<Complaint> getByStatus(ComplaintStatus status) {
        return repository.findByStatus(status);
    }

    public List<Complaint> getByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    public Optional<Complaint> updateStatus(Long id, ComplaintStatus newStatus) {
        Optional<Complaint> complaintOpt = repository.findById(id);
        if (complaintOpt.isPresent()) {
            Complaint complaint = complaintOpt.get();
            complaint.setStatus(newStatus);
            repository.save(complaint);
        }
        return complaintOpt;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
