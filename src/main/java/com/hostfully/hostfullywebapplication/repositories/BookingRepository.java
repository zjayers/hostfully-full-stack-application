package com.hostfully.hostfullywebapplication.repositories;

import com.hostfully.hostfullywebapplication.models.Booking;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The BookingRepository interface provides methods for accessing and manipulating Booking entities in the database.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

  /**
   * Checks if a booking with a start date before the specified end date and an end date after the specified start date exists.
   *
   * @param endDate   The end date to check for overlaps.
   * @param startDate The start date to check for overlaps.
   * @return true if a booking with the specified overlap exists, false otherwise.
   */
  boolean existsByStartDateBeforeAndEndDateAfter(LocalDate endDate, LocalDate startDate);
}
