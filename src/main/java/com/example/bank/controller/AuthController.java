package com.example.bank.controller;

import com.example.bank.model.User;
import com.example.bank.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // permette a React di chiamare il backend
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 REGISTRAZIONE
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Controllo username
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("❌ Username già esistente");
        }

        // Controllo età >= 18
        try {
            LocalDate nascita = LocalDate.parse(user.getDataNascita());
            Period eta = Period.between(nascita, LocalDate.now());
            if (eta.getYears() < 18) {
                return ResponseEntity.badRequest().body("❌ Devi avere almeno 18 anni");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Data di nascita non valida");
        }

        // Criptazione password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        // Generazione IBAN in base al paese scelto (IT/ES/FR)
        String prefix = switch (user.getPaese().toUpperCase()) {
            case "IT" -> "IT";
            case "ES" -> "ES";
            case "FR" -> "FR";
            default -> "XX";
        };
        String iban = prefix + randomString(25); // totale 27 caratteri
        user.setIban(iban);

        // Salvataggio utente
        userRepository.save(user);

        // Non inviare password al frontend
        user.setPassword(null);

        return ResponseEntity.ok(user);
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
        if (userOpt.isEmpty() ||
                !passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body("❌ Credenziali non valide");
        }

        User user = userOpt.get();
        user.setPassword(null); // non inviare la password al frontend

        return ResponseEntity.ok(user);
    }

    // 🔹 Metodo helper per generare stringa casuale IBAN
    private String randomString(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int idx = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }
}
