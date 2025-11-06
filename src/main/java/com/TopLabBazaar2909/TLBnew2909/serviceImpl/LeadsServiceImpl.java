package com.TopLabBazaar2909.TLBnew2909.serviceImpl;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.LeadsService;
import com.TopLabBazaar2909.TLBnew2909.dto.HistoryDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.LeadsDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.AppUser;
import com.TopLabBazaar2909.TLBnew2909.entity.Leads;
import com.TopLabBazaar2909.TLBnew2909.entity.Position;
import com.TopLabBazaar2909.TLBnew2909.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LeadsServiceImpl implements LeadsService {

    private final LeadsRepository leadsRepository;
    private final UserRepository userRepository;
    private final LabPriceRepository labPriceRepository;
    private final PositionRepository positionRepository;

    public LeadsServiceImpl(
            LeadsRepository leadsRepository,
            UserRepository userRepository,
            LabPriceRepository labPriceRepository,
            PositionRepository positionRepository) {
        this.leadsRepository = leadsRepository;
        this.userRepository = userRepository;
        this.labPriceRepository = labPriceRepository;
        this.positionRepository = positionRepository;
    }

    // ------------------------------------------------------------
    // CREATE LEAD
    // ------------------------------------------------------------
    @Override
    public ResponseEntity<?> createLead(LeadsDTO dto) {
        try {
            if (dto.getUserId() == null)
                return ResponseEntity.badRequest().body(Map.of("message", "User ID is required"));

            Optional<AppUser> userOpt = userRepository.findById(dto.getUserId());
            if (userOpt.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));

            Leads lead = new Leads();
            lead.setUserId(dto.getUserId());
            lead.setUser(userOpt.get());
            lead.setBookingAddress(dto.getBookingAddress());
            lead.setPaymentAmount(dto.getPaymentAmount());
            lead.setBookingStatus(dto.getBookingStatus() != null ? dto.getBookingStatus() : "INTERESTED");
            lead.setBookingCreationDate(LocalDateTime.now());
            lead.setPatientNote(dto.getPatientNote());
            lead.setLastUpdated(LocalDateTime.now());
            lead.setLabs(dto.getLabs());

            // Default history entry
            lead.addHistory("SYSTEM", "LEAD_CREATED", dto.getUserId(),
                    userOpt.get().getUserName(), "SYSTEM", "Lead created", LocalDateTime.now());

            leadsRepository.save(lead);
            return ResponseEntity.status(HttpStatus.CREATED).body(lead);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // ------------------------------------------------------------
    // GET ALL LEADS
    // ------------------------------------------------------------
    @Override
    public List<Leads> getAllLeads() {
        return leadsRepository.findAll();
    }

    // ------------------------------------------------------------
    // GET SINGLE LEAD BY ID
    // ------------------------------------------------------------
    @Override
    public ResponseEntity<?> getLead(String id) {
        return leadsRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Lead not found")));
    }

    // ------------------------------------------------------------
    // GET LEADS BY MOBILE
    // ------------------------------------------------------------
    @Override
    public List<Leads> getLeadsByMobile(String mobile) {
        return leadsRepository.findByUser_MobileNumber(mobile);
    }

    // ------------------------------------------------------------
    // DELETE LEAD
    // ------------------------------------------------------------
    @Override
    public ResponseEntity<?> deleteLead(String id) {
        if (!leadsRepository.existsById(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Lead not found"));
        leadsRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Lead deleted successfully"));
    }

    // ------------------------------------------------------------
    // FILTER LEADS
    // ------------------------------------------------------------
    @Override
    public ResponseEntity<?> filterLeads(Map<String, String> filters) {
        List<Leads> leads = leadsRepository.findAll();
        Stream<Leads> stream = leads.stream();

        if (filters.containsKey("bookingStatus"))
            stream = stream.filter(l -> filters.get("bookingStatus").equalsIgnoreCase(l.getBookingStatus()));
        if (filters.containsKey("careManagerId"))
            stream = stream.filter(l -> filters.get("careManagerId").equalsIgnoreCase(l.getCareManagerId()));
        if (filters.containsKey("userId"))
            stream = stream.filter(l -> filters.get("userId").equalsIgnoreCase(l.getUserId()));

        List<Leads> filtered = stream.toList();
        if (filtered.isEmpty())
            return ResponseEntity.ok(Map.of("message", "No leads found"));
        return ResponseEntity.ok(filtered);
    }

    // ------------------------------------------------------------
    // STATUS COUNTS
    // ------------------------------------------------------------
    @Override
    public ResponseEntity<?> getStatusCounts(Map<String, String> request) {
        List<Leads> leads = leadsRepository.findAll();

        if (request.containsKey("userId")) {
            leads = leads.stream()
                    .filter(l -> request.get("userId").equalsIgnoreCase(l.getUserId()))
                    .toList();
        }

        Map<String, Long> statusCounts = leads.stream()
                .collect(Collectors.groupingBy(
                        l -> l.getBookingStatus() == null ? "UNKNOWN" : l.getBookingStatus(),
                        Collectors.counting()
                ));

        return ResponseEntity.ok(statusCounts);
    }

    // ------------------------------------------------------------
    // UPDATE LEAD
    // ------------------------------------------------------------
    @Override
    public ResponseEntity<?> updateLead(Long phone, String leadId, Map<String, Object> updateData) {
        Optional<Leads> optionalLead = leadsRepository.findById(leadId);
        if (optionalLead.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Lead not found"));

        Leads lead = optionalLead.get();

        updateData.forEach((key, value) -> {
            switch (key) {
                case "bookingStatus" -> lead.setBookingStatus((String) value);
                case "paymentAmount" -> lead.setPaymentAmount(Double.valueOf(value.toString()));
                case "bookingAddress" -> lead.setBookingAddress((String) value);
                case "patientNote" -> lead.setPatientNote((String) value);
            }
        });

        lead.setLastUpdated(LocalDateTime.now());
        leadsRepository.save(lead);

        return ResponseEntity.ok(Map.of("message", "Lead updated successfully", "leadId", lead.getId()));
    }

    // ------------------------------------------------------------
    // CONFIRM LEAD (PATCH /confirm/{id})
    // ------------------------------------------------------------
    @Override
    public ResponseEntity<?> confirmLead(String id) {
        Optional<Leads> optLead = leadsRepository.findById(id);
        if (optLead.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Lead not found"));

        Leads lead = optLead.get();
        lead.setBookingStatus("CONFIRMED");
        lead.setLastUpdated(LocalDateTime.now());
        leadsRepository.save(lead);

        return ResponseEntity.ok(Map.of("message", "Lead confirmed successfully", "leadId", id));
    }

    // ------------------------------------------------------------
    // ASSIGN CARE MANAGER
    // ------------------------------------------------------------
    @Override
    public ResponseEntity<?> assignCareManager(Map<String, Object> body) {
        return assignGenericRole(body, "CARE_MANAGER");
    }

    // ------------------------------------------------------------
    // ASSIGN PHLEBO
    // ------------------------------------------------------------
    @Override
    public ResponseEntity<?> assignPhlebo(Map<String, Object> body) {
        return assignGenericRole(body, "PHLEBO");
    }

    // ------------------------------------------------------------
    // ASSIGN RUNNER
    // ------------------------------------------------------------
    @Override
    public ResponseEntity<?> assignRunner(Map<String, Object> body) {
        return assignGenericRole(body, "RUNNER");
    }

    // ------------------------------------------------------------
    // GENERIC ASSIGNMENT HELPER
    // ------------------------------------------------------------
    private ResponseEntity<?> assignGenericRole(Map<String, Object> body, String roleType) {
        try {
            String leadId = (String) body.get("leadId");
            String assignedId = (String) body.get("assignedId");
            if (leadId == null || assignedId == null)
                return ResponseEntity.badRequest().body(Map.of("message", "leadId and assignedId are required"));

            Optional<Leads> optLead = leadsRepository.findById(leadId);
            if (optLead.isEmpty())
                return ResponseEntity.status(404).body(Map.of("message", "Lead not found"));

            Optional<AppUser> optUser = userRepository.findById(assignedId);
            if (optUser.isEmpty())
                return ResponseEntity.status(404).body(Map.of("message", "User not found"));

            Leads lead = optLead.get();
            AppUser user = optUser.get();

            switch (roleType) {
                case "CARE_MANAGER" -> {
                    lead.setCareManagerId(user.getId());
                    lead.setCareManagerName(user.getUserName());
                    lead.setCareManagerStatus("ASSIGNED");
                }
                case "PHLEBO" -> {
                    lead.setPhleboId(user.getId());
                    lead.setPhleboName(user.getUserName());
                    lead.setPhleboStatus("ASSIGNED");
                }
                case "RUNNER" -> {
                    lead.setRunnerId(user.getId());
                    lead.setRunnerName(user.getUserName());
                    lead.setRunnerStatus("ASSIGNED");
                }
            }

            lead.setLastUpdated(LocalDateTime.now());
            leadsRepository.save(lead);

            return ResponseEntity.ok(Map.of(
                    "message", roleType + " assigned successfully",
                    "leadId", lead.getId(),
                    "assignedTo", user.getUserName(),
                    "updatedAt", lead.getLastUpdated()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }


    }
    // ------------------------------------------------------------
// UPDATE PHLEBO LOCATION
// ------------------------------------------------------------
    @Override
    public ResponseEntity<?> updatePhleboLocation(String id, Map<String, Object> bookingDTO) {
        try {
            Optional<Leads> optLead = leadsRepository.findById(id);
            if (optLead.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Lead not found"));
            }

            Leads lead = optLead.get();

            // Try several common keys for coordinates
            Double lat = null;
            Double lng = null;

            // 1) phleboLat / phleboLng (preferred)
            if (bookingDTO.containsKey("phleboLat") || bookingDTO.containsKey("phleboLng")) {
                Object latObj = bookingDTO.get("phleboLat");
                Object lngObj = bookingDTO.get("phleboLng");
                if (latObj != null) lat = Double.parseDouble(latObj.toString());
                if (lngObj != null) lng = Double.parseDouble(lngObj.toString());
            }

            // 2) fallback to lat / lng
            if ((lat == null || lng == null) && (bookingDTO.containsKey("lat") || bookingDTO.containsKey("lng"))) {
                Object latObj = bookingDTO.get("lat");
                Object lngObj = bookingDTO.get("lng");
                if (latObj != null) lat = Double.parseDouble(latObj.toString());
                if (lngObj != null) lng = Double.parseDouble(lngObj.toString());
            }

            if (lat == null || lng == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Latitude and longitude required (phleboLat/phleboLng or lat/lng)"));
            }

            // Update the lead. Your Leads entity previously used setLat/setLng — reuse those.
            lead.setLat(lat);
            lead.setLng(lng);

            // Optionally update phlebo status if provided
            if (bookingDTO.containsKey("phleboStatus")) {
                Object statusObj = bookingDTO.get("phleboStatus");
                if (statusObj != null) lead.setPhleboStatus(statusObj.toString());
            }

            // Add a history entry if possible (you have addHistory helper in Leads)
            String updatedBy = (String) bookingDTO.getOrDefault("updatedBy", "PHLEBO_SYSTEM");
            String reason = (String) bookingDTO.getOrDefault("reason", "Phlebo location update");
            String assignedId = (String) bookingDTO.getOrDefault("phleboId", lead.getPhleboId());
            String assignedName = (String) bookingDTO.getOrDefault("phleboName", lead.getPhleboName());

            // Use current time if not provided
            LocalDateTime when = bookingDTO.containsKey("timestamp") ?
                    LocalDateTime.parse(bookingDTO.get("timestamp").toString()) : LocalDateTime.now();

            // history role/action names follow your pattern earlier
            lead.addHistory("PHLEBO", "UPDATE_LOCATION", assignedId, assignedName, updatedBy, reason, when);

            lead.setLastUpdated(LocalDateTime.now());

            leadsRepository.save(lead);

            return ResponseEntity.ok(Map.of(
                    "message", "Phlebo location updated successfully",
                    "leadId", lead.getId(),
                    "lat", lat,
                    "lng", lng,
                    "updatedAt", lead.getLastUpdated()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

}
