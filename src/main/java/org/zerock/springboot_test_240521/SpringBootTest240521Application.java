package org.zerock.springboot_test_240521;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootTest240521Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTest240521Application.class, args);
    }

}
