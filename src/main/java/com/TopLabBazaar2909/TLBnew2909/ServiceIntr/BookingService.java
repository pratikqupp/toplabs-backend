package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.dto.BookingDTO;

import java.util.List;
import java.util.Map;

public interface BookingService {
    List<BookingDTO> getAllBookings();

    Map<String, Object> filterBookings(Map<String, String> filters);

    Map<String, Object> getStatusCounts(Map<String, String> body);

    BookingDTO getBookingById(String id);

    List<BookingDTO> getBookingsByMobile(String mobile);


    BookingDTO createBooking(BookingDTO bookingDTO);



    BookingDTO updateBooking(String phone, String id, Map<String, Object> updates);

    BookingDTO patchBooking(String id, Map<String, Object> updates);

    BookingDTO updatePhleboLocation(String id, Map<String, Object> location);

    BookingDTO updateRunnerLocation(String id, Map<String, Object> location);

    Map<String, String> deleteBooking(String id);

    Map<String, Object> assignCareManager(Map<String, Object> body);

    Map<String, Object> assignPhlebo(Map<String, Object> body);

    Map<String, Object> assignRunner(Map<String, Object> body);



    Map<String, Object> unassignCareManager(Map<String, Object> body);

    Object confirmBooking(String leadId);
}
