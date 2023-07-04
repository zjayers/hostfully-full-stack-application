package com.hostfully.hostfullywebapplication.repositories;

import com.hostfully.hostfullywebapplication.models.Block;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The BlockRepository interface provides methods for accessing and manipulating Block entities in the database.
 */
public interface BlockRepository extends JpaRepository<Block, Long> {

  /**
   * Checks if a block with a start date before the specified end date and an end date after the specified start date exists.
   *
   * @param endDate   The end date to check for overlaps.
   * @param startDate The start date to check for overlaps.
   * @return true if a block with the specified overlap exists, false otherwise.
   */
  boolean existsByStartDateBeforeAndEndDateAfter(LocalDate endDate, LocalDate startDate);
}
