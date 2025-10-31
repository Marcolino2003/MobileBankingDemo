package com.example.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;  // ✅ era double → ora BigDecimal

    private LocalDateTime date;

    private String description;

    private String iban;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ✅ Costruttore personalizzato (utile per il TransactionController)
    public Transaction(BigDecimal amount, LocalDateTime date, String description, String iban, User user) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.iban = iban;
        this.user = user;
    }
}
