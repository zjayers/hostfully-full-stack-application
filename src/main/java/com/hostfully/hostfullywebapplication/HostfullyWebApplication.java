package com.hostfully.hostfullywebapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The HostfullyWebApplication class is the main entry point for the application.
 */
@SpringBootApplication
public class HostfullyWebApplication {

  /**
   * The main method that starts the application.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(HostfullyWebApplication.class, args);
  }
}
