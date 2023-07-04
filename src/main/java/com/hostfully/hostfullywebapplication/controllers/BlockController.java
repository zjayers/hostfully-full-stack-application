package com.hostfully.hostfullywebapplication.controllers;

import com.hostfully.hostfullywebapplication.exceptions.ResourceNotFoundException;
import com.hostfully.hostfullywebapplication.models.Block;
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
 * The BlockController class handles the HTTP requests related to blocks.
 * It provides endpoints for creating, retrieving, updating, and deleting blocks.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlockController {

  private final BlockRepository blockRepository;

  /**
   * Creates a new block.
   *
   * @param block The block object to be created.
   * @return The created block object.
   */
  @PostMapping("/blocks")
  public Block createBlock(@RequestBody Block block) {
    return blockRepository.save(block);
  }

  /**
   * Retrieves all blocks.
   *
   * @return A list of all blocks.
   */
  @GetMapping("/blocks")
  public List<Block> getAllBlocks() {
    return blockRepository.findAll();
  }

  /**
   * Retrieves a block by its ID.
   *
   * @param id The ID of the block to be retrieved.
   * @return The block object with the specified ID.
   * @throws ResourceNotFoundException if the block does not exist.
   */
  @GetMapping("/blocks/{id}")
  public ResponseEntity<Block> getBlockById(@PathVariable Long id) {
    Block block = blockRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Block does not exist with id: " + id));
    return ResponseEntity.ok(block);
  }

  /**
   * Updates a block with the specified ID.
   *
   * @param id           The ID of the block to be updated.
   * @param blockDetails The updated block object.
   * @return The updated block object.
   * @throws ResourceNotFoundException if the block does not exist.
   */
  @PutMapping("/blocks/{id}")
  public ResponseEntity<Block> updateBlock(@PathVariable Long id, @RequestBody Block blockDetails) {
    Block block = blockRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Block does not exist with id: " + id));

    block.setStartDate(blockDetails.getStartDate());
    block.setEndDate(blockDetails.getEndDate());

    Block updatedBlock = blockRepository.save(block);
    return ResponseEntity.ok(updatedBlock);
  }

  /**
   * Deletes a block with the specified ID.
   *
   * @param id The ID of the block to be deleted.
   * @return A response indicating the success of the deletion operation.
   * @throws ResourceNotFoundException if the block does not exist.
   */
  @DeleteMapping("/blocks/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteBlock(@PathVariable Long id) {
    Block block = blockRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Block does not exist with id: " + id));

    blockRepository.delete(block);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
