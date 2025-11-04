package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.dto.LeadsDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.Leads;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LeadsService {
    // GET ALL LEADS
    List<Leads> getAllLeads();

    // GET SINGLE LEAD
    ResponseEntity<?> getLead(String id);

    // FILTER LEADS
    ResponseEntity<?> filterLeads(Map<String, String> request);

    // STATUS COUNTS
    ResponseEntity<?> getStatusCounts(Map<String, String> request);

    // GET LEADS BY USER MOBILE
    List<Leads> getLeadsByMobile(String mobile);

    // CREATE LEAD (Using DTO)
    ResponseEntity<?> createLead(LeadsDTO dto);

    // UPDATE LEAD
    ResponseEntity<?> updateLead(Long phone, String leadId, Map<String, Object> updateData);

    // DELETE LEAD
    ResponseEntity<?> deleteLead(String id);

    // ASSIGN CARE MANAGER
    ResponseEntity<?> assignCareManager(Map<String, Object> body);
}
