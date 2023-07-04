package com.hostfully.hostfullywebapplication.controllers;

import com.hostfully.hostfullywebapplication.exceptions.ResourceNotFoundException;
import com.hostfully.hostfullywebapplication.models.Booking;
import com.hostfully.hostfullywebapplication.repositories.BlockRepository;
import com.hostfully.hostfullywebapplication.repositories.BookingRepository;
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

  @PostMapping("/bookings")
  public Booking createBooking(@RequestBody Booking booking) {
    // Check for overlapping bookings
    boolean hasBookingOverlap = bookingRepository.existsByStartDateBeforeAndEndDateAfter(booking.getEndDate(), booking.getStartDate());
    if (hasBookingOverlap) {
      throw new IllegalArgumentException("Booking overlaps with an existing booking.");
    }

    // Check for overlapping blocks
    boolean hasBlockOverlap = blockRepository.existsByStartDateBeforeAndEndDateAfter(booking.getEndDate(), booking.getStartDate());
    if (hasBlockOverlap) {
      throw new IllegalArgumentException("Booking overlaps with an existing block.");
    }

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

    // Check for overlapping blocks
    boolean hasBlockOverlap = blockRepository.existsByStartDateBeforeAndEndDateAfter(booking.getEndDate(), booking.getStartDate());
    if (hasBlockOverlap) {
      throw new IllegalArgumentException("Booking overlaps with an existing block.");
    }

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
