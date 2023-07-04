package com.hostfully.hostfullywebapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

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
