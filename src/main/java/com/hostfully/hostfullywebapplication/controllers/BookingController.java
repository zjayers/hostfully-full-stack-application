package com.hostfully.hostfullywebapplication.controllers;

import com.hostfully.hostfullywebapplication.exceptions.ResourceNotFoundException;
import com.hostfully.hostfullywebapplication.models.Booking;
import com.hostfully.hostfullywebapplication.repositories.BlockRepository;
import com.hostfully.hostfullywebapplication.repositories.BookingRepository;
import com.hostfully.hostfullywebapplication.utilities.OverlapChecker;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {
  private final BookingRepository bookingRepository;
  private final BlockRepository blockRepository;
  private final OverlapChecker overlapChecker;

  @PostMapping("/bookings")
  public Booking createBooking(@RequestBody Booking booking) {
    overlapChecker.checkForOverlaps(booking.getStartDate(), booking.getEndDate());
    return bookingRepository.save(booking);
  }

  @GetMapping("/bookings")
  public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
  }

  @GetMapping("/bookings/{id}")
  public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
    Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Booking does not exist with id :" + id));
    return ResponseEntity.ok(booking);
  }

  @PutMapping("/bookings/{id}")
  public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails) {
    Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Booking does not exist with id :" + id));

    booking.setStartDate(bookingDetails.getStartDate());
    booking.setEndDate(bookingDetails.getEndDate());
    overlapChecker.checkForOverlaps(booking.getStartDate(), booking.getEndDate());

    Booking updatedBooking = bookingRepository.save(booking);
    return ResponseEntity.ok(updatedBooking);
  }

  @DeleteMapping("/bookings/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteBooking(@PathVariable Long id) {
    Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Booking does not exist with id :" + id));

    bookingRepository.delete(booking);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
