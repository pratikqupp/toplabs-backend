package com.TopLabBazaar2909.TLBnew2909.controller;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.LeadsService;
import com.TopLabBazaar2909.TLBnew2909.dto.LeadsDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.Leads;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins = "*")
public class LeadsController {
    private final LeadsService leadsService;

    @Autowired
    public LeadsController(LeadsService leadsService) {
        this.leadsService = leadsService;
    }

    // ------------------------------------------------------------
    // 1️CREATE LEAD
    // ------------------------------------------------------------
   //@PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> createLead(@RequestBody LeadsDTO dto) {
        return leadsService.createLead(dto);
    }

    // ------------------------------------------------------------
    // 2️ GET ALL LEADS
    // ------------------------------------------------------------
   //@PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<Leads> getAllLeads() {
        return leadsService.getAllLeads();
    }

    // ------------------------------------------------------------
    // 3️ GET SINGLE LEAD BY ID
    // ------------------------------------------------------------
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getLead(@PathVariable String id) {
        return leadsService.getLead(id);
    }

    // ------------------------------------------------------------
    // 4️ GET LEADS BY USER MOBILE NUMBER
    // ------------------------------------------------------------
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/mobile/{mobile}")
    public List<Leads> getLeadsByMobile(@PathVariable String mobile) {
        return leadsService.getLeadsByMobile(mobile);
    }

    // ------------------------------------------------------------
    // DELETE LEAD BY ID
    // ------------------------------------------------------------
   //@PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLead(@PathVariable String id) {
        return leadsService.deleteLead(id);
    }

    // ------------------------------------------------------------
    // FILTER LEADS (role + positionId + status)
    // ------------------------------------------------------------
   //@PreAuthorize("isAuthenticated()")
    @PostMapping("/filter")
    public ResponseEntity<?> filterLeads(@RequestBody Map<String, String> request) {
        return leadsService.filterLeads(request);
    }

    // ------------------------------------------------------------
    //  STATUS COUNTS
    // ------------------------------------------------------------
   //@PreAuthorize("isAuthenticated()")
    @PostMapping("/status-counts")
    public ResponseEntity<?> getStatusCounts(@RequestBody Map<String, String> request) {
        return leadsService.getStatusCounts(request);
    }

    // ------------------------------------------------------------
    //  UPDATE LEAD
    // ------------------------------------------------------------
   //@PreAuthorize("isAuthenticated()")
    @PutMapping("/{leadId}")
    public ResponseEntity<?> updateLead(
            @RequestParam Long phone,
            @PathVariable String leadId,
            @RequestBody Map<String, Object> updateData
    ) {
        return leadsService.updateLead(phone, leadId, updateData);
    }

    // ------------------------------------------------------------
    //  ASSIGN CARE MANAGER
    // ------------------------------------------------------------
   //@PreAuthorize("isAuthenticated()")
    @PostMapping("/assign-care-manager")
    public ResponseEntity<?> assignCareManager(@RequestBody Map<String, Object> body) {
        return leadsService.assignCareManager(body);
    }
}
