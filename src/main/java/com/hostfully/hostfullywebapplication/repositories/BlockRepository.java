package com.hostfully.hostfullywebapplication.repositories;

import com.hostfully.hostfullywebapplication.models.Block;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
  boolean existsByStartDateBeforeAndEndDateAfter(LocalDate endDate, LocalDate startDate);
}


