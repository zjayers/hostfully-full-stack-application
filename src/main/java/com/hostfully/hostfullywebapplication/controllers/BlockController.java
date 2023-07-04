package com.hostfully.hostfullywebapplication.controllers;

import com.hostfully.hostfullywebapplication.exceptions.ResourceNotFoundException;
import com.hostfully.hostfullywebapplication.models.Block;
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
public class BlockController {
  private final BlockRepository blockRepository;
  private final BookingRepository bookingRepository;

  @PostMapping("/blocks")
  public Block createBlock(@RequestBody Block block) {

    // Check for overlapping bookings
    boolean hasBookingOverlap = bookingRepository.existsByStartDateBeforeAndEndDateAfter(block.getEndDate(), block.getStartDate());
    if (hasBookingOverlap) {
      throw new IllegalArgumentException("Booking overlaps with an existing booking.");
    }

    boolean hasBlockOverlap = blockRepository.existsByStartDateBeforeAndEndDateAfter(block.getEndDate(), block.getStartDate());
    if (hasBlockOverlap) {
      throw new IllegalArgumentException("Booking overlaps with an existing block.");
    }

    return blockRepository.save(block);
  }

  @GetMapping("/blocks")
  public List<Block> getAllBlocks() {
    return blockRepository.findAll();
  }

  @GetMapping("/blocks/{id}")
  public ResponseEntity<Block> getBlockById(@PathVariable Long id) {
    Block block = blockRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Block does not exist with id :" + id));
    return ResponseEntity.ok(block);
  }

  @PutMapping("/blocks/{id}")
  public ResponseEntity<Block> updateBlock(@PathVariable Long id, @RequestBody Block blockDetails) {
    Block block = blockRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Block does not exist with id :" + id));

    block.setStartDate(blockDetails.getStartDate());
    block.setEndDate(blockDetails.getEndDate());

    Block updatedBlock = blockRepository.save(block);
    return ResponseEntity.ok(updatedBlock);
  }

  @DeleteMapping("/blocks/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteBlock(@PathVariable Long id) {
    Block block = blockRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Block does not exist with id :" + id));

    blockRepository.delete(block);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
