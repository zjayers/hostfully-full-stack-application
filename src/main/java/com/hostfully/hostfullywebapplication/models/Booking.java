package com.hostfully.hostfullywebapplication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Data;

/**
 * The Booking class represents a booking entity in the application.
 */
@Entity
@Data
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /**
   * The start date of the booking.
   */
  private LocalDate startDate;

  /**
   * The end date of the booking.
   */
  private LocalDate endDate;
}
