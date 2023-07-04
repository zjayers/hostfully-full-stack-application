package com.hostfully.hostfullywebapplication.repositories;

import com.hostfully.hostfullywebapplication.models.Booking;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  boolean existsByStartDateBeforeAndEndDateAfter(LocalDate endDate, LocalDate startDate);
}


