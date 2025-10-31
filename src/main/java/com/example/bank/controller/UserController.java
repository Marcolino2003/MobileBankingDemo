// src/main/java/com/example/bank/controller/UserController.java
package com.example.bank.controller;

import com.example.bank.model.User;
import com.example.bank.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Aggiorna profilo utente
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User updatedData) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Utente non trovato");
        }

        User user = userOpt.get();
        // aggiorniamo solo i campi modificabili
        user.setNome(updatedData.getNome());
        user.setCognome(updatedData.getCognome());
        user.setDataNascita(updatedData.getDataNascita());
        user.setPaese(updatedData.getPaese());

        userRepository.save(user);

        // rimuovi la password prima di inviare al frontend
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
