package com.TopLabBazaar2909.TLBnew2909.controller;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.LeadsService;
import com.TopLabBazaar2909.TLBnew2909.dto.BookingDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.LeadsDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.Leads;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leads")
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
    @PostMapping("/create")
    public ResponseEntity<?> createLead(@RequestBody LeadsDTO dto) {
        return leadsService.createLead(dto);
    }

    // ------------------------------------------------------------
    // 2️ GET ALL LEADS
    // ------------------------------------------------------------
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/getall")
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
    @GetMapping("/mobile/{mobilenumber}")
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
    @PutMapping("/update/{id}")
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
    @PostMapping("/assignCareManager")
    public ResponseEntity<?> assignCareManager(@RequestBody Map<String, Object> body) {
        return leadsService.assignCareManager(body);
    }

    // ------------------------------------------------------------
    // UNASSIGN CARE MANAGER  (NEW)
    // ------------------------------------------------------------
    @PostMapping("/unassign-care-manager")
    public ResponseEntity<?> unassignCareManager(@RequestBody Map<String, Object> request) {
        return leadsService.unassignCareManager(request);
    }

    // ------------------------------------------------------------
    // CONFIRM BOOKING (Lead → Booking)  (NEW)
    // ------------------------------------------------------------
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmBooking(@RequestBody Map<String, Object> request) {
        return leadsService.confirmBooking(request);
    }

    @PostMapping("/assign-runner")
    public ResponseEntity<String> assignRunner(@RequestBody List<String> ids) {
        leadsService.assignRunner((Map<String, Object>) ids);
        return ResponseEntity.ok("Runner assigned successfully");
    }

    @PostMapping("/assign-phlebo")
    public ResponseEntity<String> assignPhlebo(@RequestBody List<String> ids) {
        leadsService.assignPhlebo((Map<String, Object>) ids);
        return ResponseEntity.ok("Phlebo assigned successfully");
    }

    @PutMapping("/{id}/phlebo/location")
    public ResponseEntity<ResponseEntity<?>> updatePhleboLocation(
            @PathVariable("id") String id,
            @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(leadsService.updatePhleboLocation(id, (Map<String, Object>) bookingDTO));
    }


}
