package com.toplabs.bazaar.ServiceIntr;

import com.toplabs.bazaar.dto.LeadsDTO;
import com.toplabs.bazaar.entity.Leads;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LeadsService {

    // ------------------------------------------------------------
    // GET ALL LEADS
    // ------------------------------------------------------------
    List<Leads> getAllLeads();

    // ------------------------------------------------------------
    // GET SINGLE LEAD BY ID
    // ------------------------------------------------------------
    ResponseEntity<?> getLead(String id);

    // ------------------------------------------------------------
    // FILTER LEADS
    // ------------------------------------------------------------
    ResponseEntity<?> filterLeads(Map<String, String> request);

    // ------------------------------------------------------------
    // STATUS COUNTS
    // ------------------------------------------------------------
    ResponseEntity<?> getStatusCounts(Map<String, String> request);

    // ------------------------------------------------------------
    // GET LEADS BY MOBILE NUMBER
    // ------------------------------------------------------------
    List<Leads> getLeadsByMobile(String mobile);

    // ------------------------------------------------------------
    // CREATE LEAD
    // ------------------------------------------------------------
    ResponseEntity<?> createLead(LeadsDTO dto);

    // ------------------------------------------------------------
    // UPDATE LEAD
    // ------------------------------------------------------------
    ResponseEntity<?> updateLead(Long phone, String leadId, Map<String, Object> updateData);

    // ------------------------------------------------------------
    // DELETE LEAD
    // ------------------------------------------------------------
    ResponseEntity<?> deleteLead(String id);

    // ------------------------------------------------------------
    // CONFIRM LEAD (PATCH /confirm/{id})
    // ------------------------------------------------------------
    ResponseEntity<?> confirmLead(String id);

    // ------------------------------------------------------------
    // ASSIGN CARE MANAGER
    // ------------------------------------------------------------
    ResponseEntity<?> assignCareManager(Map<String, Object> body);

    // ------------------------------------------------------------
    // ASSIGN PHLEBO
    // ------------------------------------------------------------
    ResponseEntity<?> assignPhlebo(Map<String, Object> body);

    // ------------------------------------------------------------
    // ASSIGN RUNNER
    // ------------------------------------------------------------
    ResponseEntity<?> assignRunner(Map<String, Object> body);

    // ------------------------------------------------------------
    // UPDATE PHLEBO LOCATION
    // ------------------------------------------------------------
    ResponseEntity<?> updatePhleboLocation(String id, Map<String, Object> bookingDTO);

    ResponseEntity<?> unassignCareManager(Map<String, Object> request);

    ResponseEntity<?> confirmBooking(Map<String, Object> request);


}
