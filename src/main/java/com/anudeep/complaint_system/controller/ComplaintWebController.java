package com.anudeep.complaint_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anudeep.complaint_system.model.Complaint;
import com.anudeep.complaint_system.model.ComplaintStatus;
import com.anudeep.complaint_system.service.ComplaintService;

@Controller
@RequestMapping("/complaints")
public class ComplaintWebController {

    private final ComplaintService service;

    @Autowired
    public ComplaintWebController(ComplaintService service) {
        this.service = service;
    }

    @GetMapping({"", "/"})
    public String home() {
        return "home";
    }

    @GetMapping("/submit")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public String showForm(Model model) {
        model.addAttribute("complaint", new Complaint());
        return "complaint-form";
    }

    @PostMapping("/submit")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public String submitComplaint(@ModelAttribute Complaint complaint) {
        service.save(complaint);
        return "redirect:/complaints/view";
    }

    @GetMapping("/view")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public String viewComplaints(Model model) {
        model.addAttribute("complaints", service.getAll());
        return "complaints";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            ComplaintStatus complaintStatus = ComplaintStatus.valueOf(status.toUpperCase());
            service.updateStatus(id, complaintStatus);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status");
        }
        return "redirect:/complaints/view";
    }
}
