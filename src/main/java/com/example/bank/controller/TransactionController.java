package com.example.bank.controller;

import com.example.bank.model.Transaction;
import com.example.bank.model.User;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionController(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // 📘 Recupera tutte le transazioni di un utente tramite IBAN
    @GetMapping("/{iban}")
    public ResponseEntity<?> getTransactionsByIban(@PathVariable String iban) {
        Optional<User> userOpt = userRepository.findByIban(iban);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("IBAN non trovato.");
        }

        List<Transaction> transactions = transactionRepository.findByUserIbanOrderByDateDesc(iban);
        return ResponseEntity.ok(transactions);
    }

    // 💸 Bonifico
    @PostMapping("/transfer")
    public ResponseEntity<?> makeTransfer(@RequestBody TransferRequest req) {
        Optional<User> senderOpt = userRepository.findByIban(req.getSenderIban());
        if (senderOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("IBAN mittente non valido.");
        }

        User sender = senderOpt.get();

        Optional<User> receiverOpt = userRepository.findByIban(req.getReceiverIban());
        if (receiverOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("IBAN beneficiario non trovato.");
        }

        User receiver = receiverOpt.get();

        // verifica nome e cognome beneficiario
        if (!receiver.getNome().equalsIgnoreCase(req.getReceiverName()) ||
                !receiver.getCognome().equalsIgnoreCase(req.getReceiverSurname())) {
            return ResponseEntity.badRequest().body("Nome o cognome beneficiario non corrispondono all’IBAN.");
        }

        BigDecimal importo = BigDecimal.valueOf(req.getAmount());

        // verifica saldo sufficiente
        if (sender.getSaldo().compareTo(importo) < 0) {
            return ResponseEntity.badRequest().body("Saldo insufficiente per completare il bonifico.");
        }

        // aggiorna saldi
        sender.setSaldo(sender.getSaldo().subtract(importo));
        receiver.setSaldo(receiver.getSaldo().add(importo));

        // salva le transazioni
        Transaction t1 = new Transaction(importo.negate(), LocalDateTime.now(),
                "Bonifico a " + receiver.getNome() + " " + receiver.getCognome() + " - " + req.getDescription(),
                sender.getIban(), sender);

        Transaction t2 = new Transaction(importo, LocalDateTime.now(),
                "Bonifico da " + sender.getNome() + " " + sender.getCognome() + " - " + req.getDescription(),
                receiver.getIban(), receiver);

        transactionRepository.save(t1);
        transactionRepository.save(t2);

        userRepository.save(sender);
        userRepository.save(receiver);

        return ResponseEntity.ok("✅ Bonifico completato con successo.");
    }
}
