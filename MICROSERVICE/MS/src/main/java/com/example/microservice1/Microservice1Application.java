package com.example.microservice1;

import com.example.microservice1.Entity.Candidat;
import com.example.microservice1.Repo.ICandidateRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
@EnableEurekaClient
public class Microservice1Application {

    public static void main(String[] args) {
        SpringApplication.run(Microservice1Application.class, args);
    }

    @Bean
    ApplicationRunner start(ICandidateRepository repo) {
        return args -> {
            Stream.of(Candidat.builder()
                            .nom("firas")
                            .prenom("belhadj")
                            .email("bilhadj@gmail.com")
                            .build(),
                    Candidat.builder()
                            .nom("Rihab")
                            .prenom("Idoudi")
                            .build())
                    .forEach(candidat -> {
                repo.save(candidat);
            });
            repo.findAll().forEach(System.out::println);
        };
    }
}
