// src/main/java/com/example/bank/config/DataInitializer.java
package com.example.bank.config;

import com.example.bank.model.User;
import com.example.bank.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se l'utente già esiste
            if (userRepository.findByUsername("marco").isEmpty()) {
                User u = new User();
                u.setUsername("marcolinoo");
                u.setPassword(passwordEncoder.encode("password123")); // password sicura
                u.setNome("Maria");
                u.setCognome("Morelli");
                u.setPaese("IT");
                u.setIban("IT" + "1234567890123456789012345"); // IBAN fittizio 27 caratteri
                u.setSaldo(java.math.BigDecimal.valueOf(50.00));
                u.setRole("USER");

                userRepository.save(u);
                System.out.println("Utente di prova 'marco' creato con saldo 50€.");
            }
        };
    }
}
