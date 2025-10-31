package com.example.bank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;
    private String nome;
    private String cognome;
    private String dataNascita;
    private String paese;
    private String iban;   // campo IBAN

    private BigDecimal saldo = BigDecimal.ZERO;

    // ===== GETTER e SETTER =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getDataNascita() { return dataNascita; }
    public void setDataNascita(String dataNascita) { this.dataNascita = dataNascita; }

    public String getPaese() { return paese; }
    public void setPaese(String paese) { this.paese = paese; }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }  // ✅ Aggiungi questo setter

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    private String phone;
    private boolean verified = false;


}
