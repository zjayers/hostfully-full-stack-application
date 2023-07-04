package com.hostfully.hostfullywebapplication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Data;

/**
 * The Block class represents a block entity in the application.
 */
@Entity
@Data
public class Block {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /**
   * The start date of the block.
   */
  private LocalDate startDate;

  /**
   * The end date of the block.
   */
  private LocalDate endDate;
}
