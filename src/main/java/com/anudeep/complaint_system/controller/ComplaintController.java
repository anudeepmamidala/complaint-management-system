package com.anudeep.complaint_system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.anudeep.complaint_system.model.Complaint;
import com.anudeep.complaint_system.model.ComplaintStatus;
import com.anudeep.complaint_system.service.ComplaintService;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService service;

    @Autowired
    public ComplaintController(ComplaintService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public Complaint create(@RequestBody Complaint complaint) {
        return service.save(complaint);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public List<Complaint> getAll() {
        return service.getAll();
    }

    @GetMapping(params = "status")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public List<Complaint> getByStatus(@RequestParam String status) {
        try {
            ComplaintStatus statusEnum = ComplaintStatus.valueOf(status.toUpperCase());
            return service.getByStatus(statusEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Use: PENDING, IN_PROGRESS, or RESOLVED");
        }
    }

    @GetMapping(params = "department")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public List<Complaint> getByDept(@RequestParam String department) {
        return service.getByDepartment(department);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Complaint> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            ComplaintStatus statusEnum = ComplaintStatus.valueOf(status.toUpperCase());
            return service.updateStatus(id, statusEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Use: PENDING, IN_PROGRESS, or RESOLVED");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
