package com.hostfully.hostfullywebapplication.utilities;

import com.hostfully.hostfullywebapplication.repositories.BlockRepository;
import com.hostfully.hostfullywebapplication.repositories.BookingRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OverlapChecker {
  private final BookingRepository bookingRepository;
  private final BlockRepository blockRepository;

  public OverlapChecker(BookingRepository bookingRepository, BlockRepository blockRepository) {
    this.bookingRepository = bookingRepository;
    this.blockRepository = blockRepository;
  }

  public boolean hasOverlap(LocalDate startDate, LocalDate endDate) {
    return hasBookingOverlap(startDate, endDate) || hasBlockOverlap(startDate, endDate);
  }

  private boolean hasBookingOverlap(LocalDate startDate, LocalDate endDate) {
    return bookingRepository.existsByStartDateBeforeAndEndDateAfter(endDate, startDate);
  }

  private boolean hasBlockOverlap(LocalDate startDate, LocalDate endDate) {
    return blockRepository.existsByStartDateBeforeAndEndDateAfter(endDate, startDate);
  }

  public void checkForOverlaps(LocalDate startDate, LocalDate endDate) {
    boolean hasOverlap = hasOverlap(startDate, endDate);
    if (hasOverlap) {
      throw new IllegalArgumentException("Booking overlaps with an existing booking or block.");
    }
  }
}
