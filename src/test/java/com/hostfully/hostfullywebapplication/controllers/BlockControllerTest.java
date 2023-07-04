package com.hostfully.hostfullywebapplication.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.hostfullywebapplication.models.Block;
import com.hostfully.hostfullywebapplication.repositories.BlockRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BlockControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BlockRepository blockRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    blockRepository.deleteAll();
  }

  @AfterEach
  public void tearDown() {
    blockRepository.deleteAll();
  }

  @Test
  public void testCreateBlock() throws Exception {
    Block block = new Block();
    block.setStartDate(LocalDate.parse("2023-07-01"));
    block.setEndDate(LocalDate.parse("2023-07-05"));

    mockMvc.perform(post("/api/blocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(block)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.startDate").value("2023-07-01"))
        .andExpect(jsonPath("$.endDate").value("2023-07-05"));
  }

  @Test
  public void testGetAllBlocks() throws Exception {
    Block block1 = new Block();
    block1.setStartDate(LocalDate.parse("2023-07-01"));
    block1.setEndDate(LocalDate.parse("2023-07-05"));
    blockRepository.save(block1);

    Block block2 = new Block();
    block2.setStartDate(LocalDate.parse("2023-07-06"));
    block2.setEndDate(LocalDate.parse("2023-07-10"));
    blockRepository.save(block2);

    mockMvc.perform(get("/api/blocks"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").isNotEmpty())
        .andExpect(jsonPath("$[0].startDate").value("2023-07-01"))
        .andExpect(jsonPath("$[0].endDate").value("2023-07-05"))
        .andExpect(jsonPath("$[1].id").isNotEmpty())
        .andExpect(jsonPath("$[1].startDate").value("2023-07-06"))
        .andExpect(jsonPath("$[1].endDate").value("2023-07-10"));
  }

  @Test
  public void testGetBlockById_ExistingId() throws Exception {
    Block block = new Block();
    block.setStartDate(LocalDate.parse("2023-07-01"));
    block.setEndDate(LocalDate.parse("2023-07-05"));
    blockRepository.save(block);

    mockMvc.perform(get("/api/blocks/{id}", block.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(block.getId()))
        .andExpect(jsonPath("$.startDate").value("2023-07-01"))
        .andExpect(jsonPath("$.endDate").value("2023-07-05"));
  }

  @Test
  public void testGetBlockById_NonExistingId() throws Exception {
    mockMvc.perform(get("/api/blocks/{id}", 1))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdateBlock_ExistingId() throws Exception {
    Block block = new Block();
    block.setStartDate(LocalDate.parse("2023-07-01"));
    block.setEndDate(LocalDate.parse("2023-07-05"));
    blockRepository.save(block);

    Block updatedBlock = new Block();
    updatedBlock.setStartDate(LocalDate.parse("2023-07-06"));
    updatedBlock.setEndDate(LocalDate.parse("2023-07-10"));

    mockMvc.perform(put("/api/blocks/{id}", block.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedBlock)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(block.getId()))
        .andExpect(jsonPath("$.startDate").value("2023-07-06"))
        .andExpect(jsonPath("$.endDate").value("2023-07-10"));
  }

  @Test
  public void testUpdateBlock_NonExistingId() throws Exception {
    Block updatedBlock = new Block();
    updatedBlock.setStartDate(LocalDate.parse("2023-07-06"));
    updatedBlock.setEndDate(LocalDate.parse("2023-07-10"));

    mockMvc.perform(put("/api/blocks/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedBlock)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteBlock_ExistingId() throws Exception {
    Block block = new Block();
    block.setStartDate(LocalDate.parse("2023-07-01"));
    block.setEndDate(LocalDate.parse("2023-07-05"));
    blockRepository.save(block);

    mockMvc.perform(delete("/api/blocks/{id}", block.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.deleted").value(true));
  }

  @Test
  public void testDeleteBlock_NonExistingId() throws Exception {
    mockMvc.perform(delete("/api/blocks/{id}", 1))
        .andExpect(status().isNotFound());
  }
}
