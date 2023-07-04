package com.hostfully.hostfullywebapplication.utilities;

import com.hostfully.hostfullywebapplication.repositories.BlockRepository;
import com.hostfully.hostfullywebapplication.repositories.BookingRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * The OverlapChecker class provides methods for checking overlaps between bookings and blocks.
 */
@Component
public class OverlapChecker {
  private final BookingRepository bookingRepository;
  private final BlockRepository blockRepository;

  /**
   * Constructs an OverlapChecker with the provided repositories.
   *
   * @param bookingRepository The repository for booking data.
   * @param blockRepository   The repository for block data.
   */
  public OverlapChecker(BookingRepository bookingRepository, BlockRepository blockRepository) {
    this.bookingRepository = bookingRepository;
    this.blockRepository = blockRepository;
  }

  /**
   * Checks if there is an overlap between the specified start and end dates.
   *
   * @param startDate The start date of the booking.
   * @param endDate   The end date of the booking.
   * @return true if there is an overlap, false otherwise.
   */
  public boolean hasOverlap(LocalDate startDate, LocalDate endDate) {
    return hasBookingOverlap(startDate, endDate) || hasBlockOverlap(startDate, endDate);
  }

  private boolean hasBookingOverlap(LocalDate startDate, LocalDate endDate) {
    return bookingRepository.existsByStartDateBeforeAndEndDateAfter(endDate, startDate);
  }

  private boolean hasBlockOverlap(LocalDate startDate, LocalDate endDate) {
    return blockRepository.existsByStartDateBeforeAndEndDateAfter(endDate, startDate);
  }

  /**
   * Checks for overlaps between the specified start and end dates.
   *
   * @param startDate The start date of the booking.
   * @param endDate   The end date of the booking.
   * @throws IllegalArgumentException if there is an overlap with an existing booking or block.
   */
  public void checkForOverlaps(LocalDate startDate, LocalDate endDate) {
    boolean hasOverlap = hasOverlap(startDate, endDate);
    if (hasOverlap) {
      throw new IllegalArgumentException("Booking overlaps with an existing booking or block.");
    }
  }
}
