package edu.cornell.PPMFullStack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.cornell.PPMFullStack.services.CustomerUserDetailsService;

@SpringBootApplication
public class PpmFullStackApplication {

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CustomerUserDetailsService customerUserDetailsService() {
        return new CustomerUserDetailsService();
    }

    public static void main(String[] args) {
        SpringApplication.run(PpmFullStackApplication.class, args);
    }

}
