package com.hostfully.hostfullywebapplication.controllers;

import com.hostfully.hostfullywebapplication.exceptions.ResourceNotFoundException;
import com.hostfully.hostfullywebapplication.models.Booking;
import com.hostfully.hostfullywebapplication.repositories.BlockRepository;
import com.hostfully.hostfullywebapplication.repositories.BookingRepository;
import com.hostfully.hostfullywebapplication.utilities.OverlapChecker;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The BookingController class handles the HTTP requests related to bookings.
 * It provides endpoints for creating, retrieving, updating, and deleting bookings.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {
  private final BookingRepository bookingRepository;
  private final OverlapChecker overlapChecker;

  /**
   * Creates a new booking.
   *
   * @param booking The booking object to be created.
   * @return The created booking object.
   */
  @PostMapping("/bookings")
  public Booking createBooking(@RequestBody Booking booking) {
    overlapChecker.checkForOverlaps(booking.getStartDate(), booking.getEndDate());
    return bookingRepository.save(booking);
  }

  /**
   * Retrieves all bookings.
   *
   * @return A list of all bookings.
   */
  @GetMapping("/bookings")
  public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
  }

  /**
   * Retrieves a booking by its ID.
   *
   * @param id The ID of the booking to be retrieved.
   * @return The booking object with the specified ID.
   * @throws ResourceNotFoundException if the booking does not exist.
   */
  @GetMapping("/bookings/{id}")
  public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
    Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Booking does not exist with id: " + id));
    return ResponseEntity.ok(booking);
  }

  /**
   * Updates a booking with the specified ID.
   *
   * @param id              The ID of the booking to be updated.
   * @param bookingDetails The updated booking object.
   * @return The updated booking object.
   * @throws ResourceNotFoundException if the booking does not exist.
   */
  @PutMapping("/bookings/{id}")
  public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails) {
    Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Booking does not exist with id: " + id));

    booking.setStartDate(bookingDetails.getStartDate());
    booking.setEndDate(bookingDetails.getEndDate());
    overlapChecker.checkForOverlaps(booking.getStartDate(), booking.getEndDate());

    Booking updatedBooking = bookingRepository.save(booking);
    return ResponseEntity.ok(updatedBooking);
  }

  /**
   * Deletes a booking with the specified ID.
   *
   * @param id The ID of the booking to be deleted.
   * @return A response indicating the success of the deletion operation.
   * @throws ResourceNotFoundException if the booking does not exist.
   */
  @DeleteMapping("/bookings/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteBooking(@PathVariable Long id) {
    Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Booking does not exist with id: " + id));

    bookingRepository.delete(booking);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
