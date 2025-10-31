# Mobile Banking Demo - Caso di Studio

**Autore:** Marco Morelli  
**Matricola:** 837680

---

## Descrizione del progetto

Questa applicazione simula un sistema di mobile banking. Permette agli utenti di registrarsi, visualizzare il saldo, effettuare bonifici e consultare le proprie transazioni. Il progetto è strutturato con architettura frontend-backend basata su API REST, con attenzione alla sicurezza e protezione dei dati sensibili.

---

## Architettura

- **Frontend:** React.js
- **Backend:** Spring Boot con Spring Data JPA
- **Database:** MySQL (`bank`) tramite XAMPP
- **Comunicazione:** API REST tramite Axios

---

## Requisiti

### Backend
- Java 17+
- Spring Boot 3.x
- Spring Security
- MySQL (XAMPP)
- Maven

### Frontend
- Node.js 18+
- React.js 18+
- Axios
- React Router DOM

---

## Installazione

### 1. Preparazione del database
1. Avvia XAMPP e assicurati che MySQL sia attivo
2. Crea il database `bank`
3. Aggiorna `application.properties` nel backend con le credenziali:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

```
### 2. Clonare il progetto


git clone https://github.com/Marcolino2003/MobileBankingDemo.git
```
cd frontend
npm start
```
```
cd ../backend
mvn clean install
```

### 3. Avvio del backend
```
cd backend
mvn spring-boot:run
```
Il backend sarà disponibile su http://localhost:8080.

### 4. Avvio del frontend
```
cd frontend
npm start
```
Il frontend sarà disponibile su http://localhost:3000.
Funzionalità principali:
- Registrazione utente con controllo età (>= 18 anni) e generazione IBAN
- Login e gestione sessione tramite localStorage
- Visualizzazione saldo e IBAN utente
- Effettuazione di bonifici tra utenti con verifica saldo disponibile
- Visualizzazione lista transazioni con dettagli (data, descrizione, importo)
- Sicurezza Autenticazione e autorizzazione degli utenti tramite Spring Security
- Input validati lato backend e frontend
- Comunicazioni API protette tramite CORS configurato per http://localhost:3000
- Possibilità di estendere con HTTPS/TLS per cifratura delle comunicazioni

Database Tabella utenti (User):
`id, nome, cognome, username, password, iban, saldo, paese, dataNascita`

Tabella transazioni (Transaction):
`id, amount, date, description, iban, user_id`
