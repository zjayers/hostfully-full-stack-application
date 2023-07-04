package com.hostfully.hostfullywebapplication.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hostfully.hostfullywebapplication.models.Booking;
import com.hostfully.hostfullywebapplication.repositories.BlockRepository;
import com.hostfully.hostfullywebapplication.repositories.BookingRepository;
import com.hostfully.hostfullywebapplication.utilities.OverlapChecker;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookingRepository bookingRepository;

  @MockBean
  private BlockRepository blockRepository;

  @MockBean
  private OverlapChecker overlapChecker;

  @BeforeEach
  public void setup() {
    when(overlapChecker.hasOverlap(any(LocalDate.class), any(LocalDate.class)))
        .thenReturn(false);
  }

  @Test
  public void testCreateBooking() throws Exception {
    // Arrange
    Booking newBooking = new Booking();
    newBooking.setStartDate(LocalDate.of(2022, 3, 1));
    newBooking.setEndDate(LocalDate.of(2022, 3, 7));

    when(bookingRepository.save(any(Booking.class))).thenReturn(newBooking);

    // Act and Assert
    mockMvc.perform(post("/api/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"startDate\": \"2022-03-01\", \"endDate\": \"2022-03-07\" }"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.startDate").value("2022-03-01"))
        .andExpect(jsonPath("$.endDate").value("2022-03-07"));
  }

  @Test
  public void testGetAllBookings() throws Exception {
    // Act and Assert
    mockMvc.perform(get("/api/bookings"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetBookingById() throws Exception {
    // Arrange
    Booking booking = new Booking();
    booking.setId(1L);
    booking.setStartDate(LocalDate.of(2022, 3, 1));
    booking.setEndDate(LocalDate.of(2022, 3, 7));

    when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

    // Act and Assert
    mockMvc.perform(get("/api/bookings/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.startDate").value("2022-03-01"))
        .andExpect(jsonPath("$.endDate").value("2022-03-07"));
  }

  @Test
  public void testUpdateBooking() throws Exception {
    // Arrange
    Booking existingBooking = new Booking();
    existingBooking.setId(1L);
    existingBooking.setStartDate(LocalDate.of(2022, 3, 1));
    existingBooking.setEndDate(LocalDate.of(2022, 3, 7));

    Booking updatedBooking = new Booking();
    updatedBooking.setId(1L);
    updatedBooking.setStartDate(LocalDate.of(2022, 4, 1));
    updatedBooking.setEndDate(LocalDate.of(2022, 4, 7));

    when(bookingRepository.findById(1L)).thenReturn(Optional.of(existingBooking));
    when(bookingRepository.save(any(Booking.class))).thenReturn(updatedBooking);

    // Act and Assert
    mockMvc.perform(put("/api/bookings/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"startDate\": \"2022-04-01\", \"endDate\": \"2022-04-07\" }"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.startDate").value("2022-04-01"))
        .andExpect(jsonPath("$.endDate").value("2022-04-07"));
  }

  @Test
  public void testDeleteBooking() throws Exception {
    // Arrange
    Booking booking = new Booking();
    booking.setId(1L);
    booking.setStartDate(LocalDate.of(2022, 3, 1));
    booking.setEndDate(LocalDate.of(2022, 3, 7));

    when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

    // Act and Assert
    mockMvc.perform(delete("/api/bookings/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.deleted").value(true));
  }
}
