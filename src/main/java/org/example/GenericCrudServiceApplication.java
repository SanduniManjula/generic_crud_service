package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.example.entity")
public class GenericCrudServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericCrudServiceApplication.class, args);
    }
}
