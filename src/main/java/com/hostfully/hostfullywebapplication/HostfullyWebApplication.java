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

@SpringBootApplication
public class HostfullyWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(HostfullyWebApplication.class, args);
  }

  @Configuration
  public class SpringFoxConfig {
    @Bean
    @Profile("dev")
    public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.any())
          .paths(PathSelectors.any())
          .build();
    }

    @Bean
    @Profile("dev")
    public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**") // for all endpoints
              .allowedOrigins("http://localhost:3000") // allowed origin
              .allowedMethods("*") // allow all methods
              .allowedHeaders("*"); // allow all headers
        }
      };
    }
  }
}
