package com.example.bank.service;

import com.example.bank.model.User;
import com.example.bank.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrazione utente
    public User registerUser(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username già esistente");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("USER");
        return userRepository.save(user);
    }

    // Trova utente per username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Validazione login (confronta password)
    public boolean validateLogin(String username, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) return false;
        User user = userOpt.get();
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
