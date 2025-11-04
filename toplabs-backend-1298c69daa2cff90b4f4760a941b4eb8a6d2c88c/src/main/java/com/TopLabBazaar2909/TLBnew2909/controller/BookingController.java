package com.TopLabBazaar2909.TLBnew2909.controller;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.BookingService;
import com.TopLabBazaar2909.TLBnew2909.dto.BookingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")

public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ------------------------------
    // Booking Routes
    // ------------------------------

    // get all bookings
    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/getAllBookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // filter bookings by role + positionId + status
    //@PreAuthorize("isAuthenticated()")
    @PostMapping("/filter")
    public ResponseEntity<List<BookingDTO>> filterBookings(@RequestBody BookingDTO filterRequest) {
        return ResponseEntity.ok((List<BookingDTO>) bookingService.filterBookings((Map<String, String>) filterRequest));
    }

    //  get booking by ID
   ////@PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable("id") String id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    //  get bookings by user mobile
   ////@PreAuthorize("isAuthenticated()")
    @GetMapping("/getbooking/bymobile/{mobile}")
    public ResponseEntity<List<BookingDTO>> getBookingByMobile(@PathVariable("mobile") String mobile) {
        return ResponseEntity.ok(bookingService.getBookingsByMobile(mobile));
    }

    //  create booking
   ////@PreAuthorize("isAuthenticated()")
    @PostMapping("/createbooking")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO));
    }


    //  update booking by user phone + id
   ////@PreAuthorize("isAuthenticated()")
    @PutMapping("/{phone}/{id}")
    public ResponseEntity<BookingDTO> updateBooking(
            @PathVariable("phone") String phone,
            @PathVariable("id") String id,
            @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updateBooking(phone, id, (Map<String, Object>) bookingDTO));
    }

    //  partial update booking by id
   ////@PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    public ResponseEntity<BookingDTO> patchBooking(
            @PathVariable("id") String id,
            @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.patchBooking(id, (Map<String, Object>) bookingDTO));
    }

    //  update phlebo location
   ////@PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/phlebo/location")
    public ResponseEntity<BookingDTO> updatePhleboLocation(
            @PathVariable("id") String id,
            @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updatePhleboLocation(id, (Map<String, Object>) bookingDTO));
    }

    //  update runner location
    //@PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/runner/location")
    public ResponseEntity<BookingDTO> updateRunnerLocation(
            @PathVariable("id") String id,
            @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updateRunnerLocation(id, (Map<String, Object>) bookingDTO));
    }

    //  delete booking
   ////@PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteBooking(@PathVariable("id") String id) {
        Map<String, String> response = bookingService.deleteBooking(id);
        return ResponseEntity.ok(response);
    }


    //  Bulk assign Care Manager
   ////@PreAuthorize("isAuthenticated()")
    @PostMapping("/assign-caremanager")
    public ResponseEntity<String> assignCareManager(@RequestBody List<String> ids) {
        bookingService.assignCareManager((Map<String, Object>) ids);
        return ResponseEntity.ok("Care Manager assigned successfully");
    }

    //  Bulk assign Phlebo
//@PreAuthorize("isAuthenticated()")
    @PostMapping("/assign-phlebo")
    public ResponseEntity<String> assignPhlebo(@RequestBody List<String> ids) {
        bookingService.assignPhlebo((Map<String, Object>) ids);
        return ResponseEntity.ok("Phlebo assigned successfully");
    }

    //  Bulk assign Runner
   ////@PreAuthorize("isAuthenticated()")
    @PostMapping("/assign-runner")
    public ResponseEntity<String> assignRunner(@RequestBody List<String> ids) {
        bookingService.assignRunner((Map<String, Object>) ids);
        return ResponseEntity.ok("Runner assigned successfully");
    }

    //  get status counts
   ////@PreAuthorize("isAuthenticated()")
    @PostMapping("/status")
    public ResponseEntity<Object> getStatusCounts(@RequestBody BookingDTO filterRequest) {
        return ResponseEntity.ok(bookingService.getStatusCounts((Map<String, String>) filterRequest));
    }
}
