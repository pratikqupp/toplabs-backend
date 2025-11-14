package com.TopLabBazaar2909.TLBnew2909.serviceImpl;

import com.TopLabBazaar2909.TLBnew2909.Enum.BookingStatus;
import com.TopLabBazaar2909.TLBnew2909.Enum.CareHeadStatus;
import com.TopLabBazaar2909.TLBnew2909.Enum.PaymentStatus;
import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.BookingService;
import com.TopLabBazaar2909.TLBnew2909.dto.BookingDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.LocationDTO;
import com.TopLabBazaar2909.TLBnew2909.embedded.Location;
import com.TopLabBazaar2909.TLBnew2909.entity.Booking;
import com.TopLabBazaar2909.TLBnew2909.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookingRepository bookingRepository;
    @Override
    public List<BookingDTO> getAllBookings() {
        // Example placeholder logic
        return Collections.emptyList();
    }

    @Override
    public Map<String, Object> filterBookings(Map<String, String> filters) {
        // role + position + status filter
        return Map.of("message", "Filter logic not implemented yet", "filters", filters);
    }

    @Override
    public Map<String, Object> getStatusCounts(Map<String, String> body) {
        // return counts grouped by status
        return Map.of("pending", 0, "completed", 0);
    }

    @Override
    public BookingDTO getBookingById(String id) {
        Booking booking = bookingRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingDTO dto = modelMapper.map(booking, BookingDTO.class);
        return dto;
    }
    @Override
    public List<BookingDTO> getBookingsByMobile(String mobile) {
        List<Booking> bookings = bookingRepository.findByMobile(mobile);

        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .toList();
    }
    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();

        booking.setUserId(bookingDTO.getUserId());
        booking.setMobile(bookingDTO.getMobile());
        booking.setBookingAddress(bookingDTO.getBookingAddress());
        booking.setBookingCreationDate(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.CREATED);

        booking.setCareHeadId(bookingDTO.getCareHeadId());
        booking.setCareHeadName(bookingDTO.getCareHeadName());

        // Phlebo location
        if (bookingDTO.getPhleboLocation() != null) {
            Location phleboLoc = new Location();
            phleboLoc.setLat(bookingDTO.getPhleboLocation().getLat());
            phleboLoc.setLng(bookingDTO.getPhleboLocation().getLng());
            phleboLoc.setLastUpdated(LocalDateTime.now());
            booking.setPhleboLocation(phleboLoc);
        }

        // Runner location
        if (bookingDTO.getRunnerLocation() != null) {
            Location runnerLoc = new Location();
            runnerLoc.setLat(bookingDTO.getRunnerLocation().getLat());
            runnerLoc.setLng(bookingDTO.getRunnerLocation().getLng());
            runnerLoc.setLastUpdated(LocalDateTime.now());
            booking.setRunnerLocation(runnerLoc);
        }

        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingDTO.class);
    }

    @Override
    public BookingDTO updateBooking(String phone, String id, Map<String, Object> updates) {
        Booking booking = bookingRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Update fields from the map
        if (updates.containsKey("bookingAddress")) {
            booking.setBookingAddress((String) updates.get("bookingAddress"));
        }
        if (updates.containsKey("patientNote")) {
            booking.setPatientNote((String) updates.get("patientNote"));
        }
        if (updates.containsKey("bookingStatus")) {
            booking.setBookingStatus(BookingStatus.valueOf((String) updates.get("bookingStatus")));
        }
        if (updates.containsKey("paymentStatus")) {
            booking.setPaymentStatus(PaymentStatus.valueOf((String) updates.get("paymentStatus")));
        }
        if (updates.containsKey("paymentAmount")) {
            booking.setPaymentAmount(Double.parseDouble(updates.get("paymentAmount").toString()));
        }
        if (updates.containsKey("paymentDate")) {
            booking.setPaymentDate(LocalDateTime.parse((String) updates.get("paymentDate")));
        }
        if (updates.containsKey("careManagerName")) {
            booking.setCareManagerName((String) updates.get("careManagerName"));
        }

        // Save updated booking
        Booking updatedBooking = bookingRepository.save(booking);

        // Use ModelMapper to map entity to DTO
        BookingDTO bookingDTO = modelMapper.map(updatedBooking, BookingDTO.class);

        return bookingDTO;
    }


    @Override
    public BookingDTO patchBooking(String id, Map<String, Object> updates) {
        Booking booking = bookingRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Update fields dynamically from the map
        updates.forEach((key, value) -> {
            switch (key) {
                case "bookingStatus" -> booking.setBookingStatus(BookingStatus.valueOf((String) value));
                case "paymentStatus" -> booking.setPaymentStatus(PaymentStatus.valueOf((String) value));
                case "patientNote" -> booking.setPatientNote((String) value);
                case "careHeadStatus" -> booking.setCareHeadStatus(CareHeadStatus.valueOf((String) value));
                default -> System.out.println("Ignored unknown field: " + key);
            }
        });

        // Save updated booking
        Booking updated = bookingRepository.save(booking);

        // Map entity to DTO using ModelMapper
        return modelMapper.map(updated, BookingDTO.class);
    }


    @Override
    public BookingDTO updatePhleboLocation(String id, Map<String, Object> location) {
        Booking booking = bookingRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Map the location map to LocationDTO
        Location phleboLocDTO = modelMapper.map(location, Location.class);

        // Set DTO directly
        booking.setPhleboLocation(phleboLocDTO);

        Booking updated = bookingRepository.save(booking);

        // Convert updated Booking entity to DTO
        return modelMapper.map(updated, BookingDTO.class);
    }




    @Override
    public BookingDTO updateRunnerLocation(String id, Map<String, Object> location) {
        Booking booking = bookingRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Map the location map to LocationDTO using ModelMapper
        LocationDTO runnerLocDTO = modelMapper.map(location, LocationDTO.class);

        // Map DTO to entity if needed
        Location runnerLoc = modelMapper.map(runnerLocDTO, Location.class);
        booking.setRunnerLocation(runnerLoc);

        // Save the updated booking
        Booking updated = bookingRepository.save(booking);

        // Convert the updated Booking entity to BookingDTO using ModelMapper
        return modelMapper.map(updated, BookingDTO.class);
    }



    @Override
    public Map<String, String> deleteBooking(String id) {
        Long bookingId = Long.parseLong(id);

        // Check if booking exists
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        // Delete the booking
        bookingRepository.delete(booking);

        // Return confirmation message
        return Map.of("message", "Booking deleted successfully with ID: " + id);
    }

    @Override
    public Map<String, Object> assignCareManager(Map<String, Object> body) {
        try {
            List<String> bookingIds = (List<String>) body.get("bookingIds");
            String careManagerId = (String) body.get("careManagerId");

            if (bookingIds == null || careManagerId == null) {
                return Map.of("error", "Missing bookingIds or careManagerId");
            }

            List<Booking> bookings = bookingRepository.findAllById(
                    bookingIds.stream().map(Long::parseLong).collect(Collectors.toList())
            );

            for (Booking booking : bookings) {
                booking.setCareManagerId(careManagerId);
                booking.setUpdatedAt(LocalDateTime.now());

            }

            bookingRepository.saveAll(bookings);

            return Map.of(
                    "message", "Care Manager assigned successfully",
                    "assignedCount", bookings.size()
            );
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }

    @Override
    public Map<String, Object> assignPhlebo(Map<String, Object> body) {
        try {
            List<String> bookingIds = (List<String>) body.get("bookingIds");
            String phleboId = (String) body.get("phleboId");

            if (bookingIds == null || phleboId == null) {
                return Map.of("error", "Missing bookingIds or phleboId");
            }

            List<Booking> bookings = bookingRepository.findAllById(
                    bookingIds.stream().map(Long::parseLong).collect(Collectors.toList())
            );

            for (Booking booking : bookings) {
                booking.setPhleboId(phleboId);
                booking.setUpdatedAt(LocalDateTime.now());

            }

            bookingRepository.saveAll(bookings);

            return Map.of(
                    "message", "Phlebo assigned successfully",
                    "assignedCount", bookings.size()
            );
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }

    @Override
    public Map<String, Object> assignRunner(Map<String, Object> body) {
        try {
            List<String> bookingIds = (List<String>) body.get("bookingIds");
            String runnerId = (String) body.get("runnerId");

            if (bookingIds == null || runnerId == null) {
                return Map.of("error", "Missing bookingIds or runnerId");
            }

            List<Booking> bookings = bookingRepository.findAllById(
                    bookingIds.stream().map(Long::parseLong).collect(Collectors.toList())
            );

            for (Booking booking : bookings) {
                booking.setRunnerId(runnerId);
                booking.setUpdatedAt(LocalDateTime.now());

            }

            bookingRepository.saveAll(bookings);

            return Map.of(
                    "message", "Runner assigned successfully",
                    "assignedCount", bookings.size()
            );
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }

    @Override
    public Map<String, Object> unassignCareManager(Map<String, Object> body) {
        try {
            List<String> bookingIds = (List<String>) body.get("bookingIds");

            if (bookingIds == null) {
                return Map.of("error", "Missing bookingIds");
            }

            List<Booking> bookings = bookingRepository.findAllById(
                    bookingIds.stream().map(Long::parseLong).collect(Collectors.toList())
            );

            for (Booking booking : bookings) {
                booking.setCareManagerId(null);
                booking.setUpdatedAt(LocalDateTime.now());

            }

            bookingRepository.saveAll(bookings);

            return Map.of(
                    "message", "Care Manager unassigned successfully",
                    "unassignedCount", bookings.size()
            );
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }

    @Override
    public Map<String, Object> confirmBooking(String leadId) {
        try {
            // TODO: Replace this with actual LeadService + LeadRepository
            // Fetch lead data using FEIGN or repository
            // Example:
            // Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new RuntimeException("Lead not found"));

            // For now, assume lead exists — replace after you implement Lead module mapping
            Booking booking = new Booking();
            booking.setLeadId(leadId);

            // Default booking details
            booking.setBookingCreationDate(LocalDateTime.now());
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            booking.setPaymentStatus(PaymentStatus.PENDING);
            booking.setCareHeadStatus(CareHeadStatus.NONE);
            booking.setUpdatedAt(LocalDateTime.now());

            Booking saved = bookingRepository.save(booking);

            return Map.of(
                    "message", "Booking created from lead successfully",
                    "booking", modelMapper.map(saved, BookingDTO.class)
            );

        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }

}
