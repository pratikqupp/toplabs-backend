package com.TopLabBazaar2909.TLBnew2909.serviceImpl;

import com.TopLabBazaar2909.TLBnew2909.Enum.BookingStatus;
import com.TopLabBazaar2909.TLBnew2909.Enum.PositionStatus;
import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.LeadsService;
import com.TopLabBazaar2909.TLBnew2909.dto.HistoryDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.LeadsDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.AppUser;
import com.TopLabBazaar2909.TLBnew2909.entity.LabPrice;
import com.TopLabBazaar2909.TLBnew2909.entity.Leads;
import com.TopLabBazaar2909.TLBnew2909.entity.Position;
import com.TopLabBazaar2909.TLBnew2909.repository.LabPriceRepository;
import com.TopLabBazaar2909.TLBnew2909.repository.LeadsRepository;
import com.TopLabBazaar2909.TLBnew2909.repository.PositionRepository;
import com.TopLabBazaar2909.TLBnew2909.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LeadsServiceImpl implements LeadsService {
    private final LeadsRepository leadsRepository;
    private final UserRepository userRepository;
    private final LabPriceRepository labPriceRepository;
    private final PositionRepository positionRepository;

    public LeadsServiceImpl(LeadsRepository leadsRepository, UserRepository userRepository, LabPriceRepository labPriceRepository, PositionRepository positionRepository) {
        this.leadsRepository = leadsRepository;
        this.userRepository = userRepository;
        this.labPriceRepository = labPriceRepository;
        this.positionRepository = positionRepository;
    }

    // ------------------------------------------
    // CREATE LEAD
    // ------------------------------------------
    @Override
    public ResponseEntity<?> createLead(LeadsDTO dto) {
        try {
            // 1️⃣ Validate input
            if (dto.getId() == null || dto.getId().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Lead ID (id) is required"));
            }

            if (dto.getUserId() == null || dto.getUserId().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "userId is required"));
            }

            // 2️⃣ Find user by string ID
            Optional<AppUser> optionalUser = userRepository.findById(dto.getUserId());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "User not found"));
            }
            AppUser user = optionalUser.get();

            // 3️⃣ Create and populate Lead entity
            Leads lead = new Leads();
            lead.setId(dto.getId());
            lead.setUser(user);
            lead.setUserId(dto.getUserId());
            lead.setPatientNote(dto.getPatientNote());
            lead.setBookingAddress(dto.getBookingAddress());
            lead.setPaymentAmount(dto.getPaymentAmount());
            lead.setBookingCreationDate(LocalDateTime.now());
            lead.setBookingStatus(dto.getBookingStatus() != null ? dto.getBookingStatus() : "INTERESTED");

            // 4️⃣ Labs
            if (dto.getLabs() != null && !dto.getLabs().isEmpty()) {
                lead.setLabs(dto.getLabs());
            }

            // 5️⃣ Map all role-related fields (manual assignment from DTO)
            lead.setCareHeadId(dto.getCareHeadId());
            lead.setCareHeadName(dto.getCareHeadName());
            lead.setCareHeadStatus(dto.getCareHeadStatus());

            lead.setCareManagerId(dto.getCareManagerId());
            lead.setCareManagerName(dto.getCareManagerName());
            lead.setCareManagerStatus(dto.getCareManagerStatus());

            lead.setLabHeadId(dto.getLabHeadId());
            lead.setLabHeadName(dto.getLabHeadName());
            lead.setLabHeadStatus(dto.getLabHeadStatus());

            lead.setPhleboId(dto.getPhleboId());
            lead.setPhleboName(dto.getPhleboName());
            lead.setPhleboStatus(dto.getPhleboStatus());

            lead.setRunnerId(dto.getRunnerId());
            lead.setRunnerName(dto.getRunnerName());
            lead.setRunnerStatus(dto.getRunnerStatus());

            // 6️⃣ Geo fields and timestamp
            lead.setLastUpdated(LocalDateTime.now());

            // Optional: use values from DTO if they exist, otherwise set defaults
            lead.setLat(dto.getLat() != null ? dto.getLat() : 0.0);
            lead.setLng(dto.getLng() != null ? dto.getLng() : 0.0);

            // 7️⃣ Add History
            if (dto.getHistory() != null && !dto.getHistory().isEmpty()) {
                for (HistoryDTO h : dto.getHistory()) {
                    lead.addHistory(
                            h.getRole(),
                            h.getAction(),
                            h.getAssignedId(),
                            h.getAssignedName(),
                            h.getUpdatedBy(),
                            h.getReason(),
                            h.getTimestamp() != null ? h.getTimestamp() : LocalDateTime.now()
                    );
                }
            } else {
                lead.addHistory(
                        "SYSTEM",
                        "LEAD_CREATED",
                        dto.getUserId(),
                        user.getUserName(),
                        "SYSTEM",
                        "Lead created automatically",
                        LocalDateTime.now()
                );
            }

            // 8️⃣ Save lead
            leadsRepository.save(lead);

            return ResponseEntity.status(201).body(lead);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }


    private void assignRoleDetails(Leads lead, String roleId, String roleType) {
        Position position = positionRepository.findFirstByRoleIdAndStatus(roleId, PositionStatus.ACTIVE);

        if (position != null && position.getCurrentUser() != null) {
            String id = String.valueOf(position.getId());
            String name = position.getCurrentUser().getUserName();
            String status = String.valueOf(position.getCurrentUser().getStatus());

            switch (roleType) {
                case "careHead" -> {
                    lead.setCareHeadId(id);
                    lead.setCareHeadName(name);
                    lead.setCareHeadStatus(status);
                }
                case "careManager" -> {
                    lead.setCareManagerId(id);
                    lead.setCareManagerName(name);
                    lead.setCareManagerStatus(status);
                }
                case "labHead" -> {
                    lead.setLabHeadId(id);
                    lead.setLabHeadName(name);
                    lead.setLabHeadStatus(status);
                }
                case "phlebo" -> {
                    lead.setPhleboId(id);
                    lead.setPhleboName(name);
                    lead.setPhleboStatus(status);
                }
                case "runner" -> {
                    lead.setRunnerId(id);
                    lead.setRunnerName(name);
                    lead.setRunnerStatus(status);
                }
            }
        }
    }



    // ------------------------------------------
    // GET ALL LEADS
    // ------------------------------------------
    @Override
    public List<Leads> getAllLeads() {
        return leadsRepository.findAll();
    }

    // ------------------------------------------
    // GET SINGLE LEAD
    // ------------------------------------------
    @Override
    public ResponseEntity<?> getLead(String id) {
        return leadsRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body(Map.of("message", "Lead not found")));
    }

    // ------------------------------------------
    // GET LEADS BY MOBILE
    // ------------------------------------------
    @Override
    public List<Leads> getLeadsByMobile(String mobile) {
        try {
            return leadsRepository.findByUser_MobileNumber((mobile));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid mobile number");
        }
    }

    // ------------------------------------------
    // DELETE LEAD
    // ------------------------------------------
    @Override
    public ResponseEntity<?> deleteLead(String id) {
        if (!leadsRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Lead not found"));
        }
        leadsRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Lead deleted successfully"));
    }

    // ------------------------------------------
    // UNUSED (you can extend these later)
    // ------------------------------------------
    @Override
    public ResponseEntity<?> filterLeads(Map<String, String> filters) {
        try {
            // 1️⃣ Fetch all leads
            List<Leads> allLeads = leadsRepository.findAll();

            // 2️⃣ Start stream for filtering
            Stream<Leads> stream = allLeads.stream();

            // 3️⃣ Apply filters dynamically
            if (filters.containsKey("bookingStatus")) {
                String bookingStatus = filters.get("bookingStatus");
                stream = stream.filter(l -> bookingStatus.equalsIgnoreCase(l.getBookingStatus()));
            }

            if (filters.containsKey("careManagerName")) {
                String careManagerName = filters.get("careManagerName");
                stream = stream.filter(l -> careManagerName.equalsIgnoreCase(l.getCareManagerName()));
            }

            if (filters.containsKey("runnerStatus")) {
                String runnerStatus = filters.get("runnerStatus");
                stream = stream.filter(l -> runnerStatus.equalsIgnoreCase(l.getRunnerStatus()));
            }

            if (filters.containsKey("phleboStatus")) {
                String phleboStatus = filters.get("phleboStatus");
                stream = stream.filter(l -> phleboStatus.equalsIgnoreCase(l.getPhleboStatus()));
            }

            if (filters.containsKey("careHeadStatus")) {
                String careHeadStatus = filters.get("careHeadStatus");
                stream = stream.filter(l -> careHeadStatus.equalsIgnoreCase(l.getCareHeadStatus()));
            }

            if (filters.containsKey("careManagerStatus")) {
                String careManagerStatus = filters.get("careManagerStatus");
                stream = stream.filter(l -> careManagerStatus.equalsIgnoreCase(l.getCareManagerStatus()));
            }

            if (filters.containsKey("labHeadStatus")) {
                String labHeadStatus = filters.get("labHeadStatus");
                stream = stream.filter(l -> labHeadStatus.equalsIgnoreCase(l.getLabHeadStatus()));
            }

            if (filters.containsKey("userId")) {
                String userId = filters.get("userId");
                stream = stream.filter(l -> userId.equalsIgnoreCase(l.getUserId()));
            }

            // 4️⃣ Collect results
            List<Leads> filteredLeads = stream.toList();

            // 5️⃣ Return response
            if (filteredLeads.isEmpty()) {
                return ResponseEntity.ok(Map.of("message", "No leads found for the given filters"));
            }

            return ResponseEntity.ok(filteredLeads);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<?> getStatusCounts(Map<String, String> request) {
        // 1️⃣ Fetch all leads
        List<Leads> leads = leadsRepository.findAll();

        // 2️⃣ Apply optional filters
        if (request.containsKey("userId")) {
            String userId = request.get("userId");
            leads = leads.stream()
                    .filter(l -> l.getUserId() != null && l.getUserId().equalsIgnoreCase(userId))
                    .toList();
        }

        if (request.containsKey("careManagerId")) {
            String careManagerId = request.get("careManagerId");
            leads = leads.stream()
                    .filter(l -> l.getCareManagerId() != null && l.getCareManagerId().equalsIgnoreCase(careManagerId))
                    .toList();
        }

        if (request.containsKey("bookingStatus")) {
            String bookingStatus = request.get("bookingStatus");
            leads = leads.stream()
                    .filter(l -> l.getBookingStatus() != null && l.getBookingStatus().equalsIgnoreCase(bookingStatus))
                    .toList();
        }

        // 3️⃣ Group by bookingStatus and count
        Map<String, Long> statusCounts = leads.stream()
                .collect(Collectors.groupingBy(
                        l -> l.getBookingStatus() == null ? "UNKNOWN" : l.getBookingStatus(),
                        Collectors.counting()
                ));

        // 4️⃣ Return as JSON response
        return ResponseEntity.ok(statusCounts);
    }


    @Override
    public ResponseEntity<?> updateLead(Long phone, String leadId, Map<String, Object> updateData) {
        try {
            // 1️⃣ Find the lead by ID
            Optional<Leads> optionalLead = leadsRepository.findById(leadId);
            if (optionalLead.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Lead not found with ID: " + leadId));
            }

            Leads lead = optionalLead.get();

            // 2️⃣ Optional: Verify phone belongs to this lead's user
            if (phone != null) {
                AppUser user = lead.getUser();
                if (user != null && user.getMobileNumber() != null) {
                    if (!String.valueOf(user.getMobileNumber()).equals(String.valueOf(phone))) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(Map.of("message", "Phone number not authorized to update this lead"));
                    }
                }
            }

            // 3️⃣ Dynamically update fields from request body
            updateData.forEach((key, value) -> {
                switch (key) {
                    case "bookingStatus" -> lead.setBookingStatus((String) value);
                    case "careManagerStatus" -> lead.setCareManagerStatus((String) value);
                    case "phleboStatus" -> lead.setPhleboStatus((String) value);
                    case "runnerStatus" -> lead.setRunnerStatus((String) value);
                    case "paymentAmount" -> lead.setPaymentAmount(Double.parseDouble(value.toString()));
                    case "bookingAddress" -> lead.setBookingAddress((String) value);
                    case "patientNote" -> lead.setPatientNote((String) value);
                    case "careHeadStatus" -> lead.setCareHeadStatus((String) value);
                    case "labHeadStatus" -> lead.setLabHeadStatus((String) value);
                    default -> System.out.println("⚠️ Ignored unknown field: " + key);
                }
            });

            // 4️⃣ Update timestamp
            lead.setLastUpdated(LocalDateTime.now());

            // 5️⃣ Save lead
            leadsRepository.save(lead);

            return ResponseEntity.ok(Map.of(
                    "message", "Lead updated successfully",
                    "leadId", lead.getId(),
                    "updatedAt", lead.getLastUpdated()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to update lead", "details", e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<?> assignCareManager(Map<String, Object> body) {
        try {
            // 1️⃣ Extract required fields from request body
            String leadId = (String) body.get("leadId");
            String careManagerId = (String) body.get("careManagerId");
            String updatedBy = (String) body.getOrDefault("updatedBy", "SYSTEM");
            String reason = (String) body.getOrDefault("reason", "Assigned via API");

            // 2️⃣ Validate input
            if (leadId == null || careManagerId == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "message", "leadId and careManagerId are required"
                ));
            }

            // 3️⃣ Find Lead
            Optional<Leads> optionalLead = leadsRepository.findById(leadId);
            if (optionalLead.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("message", "Lead not found"));
            }

            Leads lead = optionalLead.get();

            // 4️⃣ Find Care Manager (AppUser)
            Optional<AppUser> optionalManager = userRepository.findById(careManagerId);
            if (optionalManager.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("message", "Care Manager not found"));
            }

            AppUser manager = optionalManager.get();

            // 5️⃣ Assign care manager details to lead
            lead.setCareManagerId(manager.getId());
            lead.setCareManagerName(manager.getFirstName() + " " + manager.getLastName());
            lead.setCareManagerStatus("ASSIGNED");
            lead.setLastUpdated(LocalDateTime.now());

//            // 6️⃣ Optionally: add history (if your Leads entity has a history list)
//            if (lead.getHistory() != null) {
//                lead.getHistory().add("Assigned to " + lead.getCareManagerName() + " by " + updatedBy);
//            }

            // 7️⃣ Save changes
            leadsRepository.save(lead);

            // 8️⃣ Response
            return ResponseEntity.ok(Map.of(
                    "message", "Care Manager assigned successfully",
                    "leadId", lead.getId(),
                    "careManagerId", manager.getId(),
                    "careManagerName", lead.getCareManagerName(),
                    "updatedAt", lead.getLastUpdated()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

}
