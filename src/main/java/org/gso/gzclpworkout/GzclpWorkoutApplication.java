package org.gso.gzclpworkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GzclpWorkoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(GzclpWorkoutApplication.class, args);
    }
}
