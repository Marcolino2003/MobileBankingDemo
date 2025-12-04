# Mobile Banking Demo - Caso di Studio

**Autore:** Marco Morelli  
**Matricola:** 837680

---

## Scenario
L’applicazione sviluppata simula un sistema di mobile banking progettato per la gestione sicura delle operazioni legate a un conto corrente bancario.
L’obiettivo è ricreare uno scenario realistico di interazione tra un utente e un servizio bancario moderno, evidenziando le principali sfide di sicurezza che caratterizzano il settore finanziario.

Nel sistema, l’utente può registrarsi, autenticarsi, visualizzare il proprio saldo, consultare lo storico delle transazioni ed effettuare bonifici verso altri conti. Tutte le operazioni vengono eseguite attraverso un’architettura frontend-backend che comunica tramite API REST, simulando il comportamento di una reale piattaforma di mobile banking.

Il caso di studio si concentra in particolare sugli aspetti di protezione dei dati sensibili e sulla messa in sicurezza delle comunicazioni tra client e server, affrontando problematiche come:

-accesso non autorizzato alle informazioni bancarie,

-integrità dei dati durante le transazioni,

protezione del canale di comunicazione,

-rischi dovuti a input non validati o attacchi di injection,

-necessità di implementare un modello di autenticazione e autorizzazione robusto.

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
## Seed del Database (DataInitializer)

L’applicazione include un componente di inizializzazione chiamato DataInitializer, utilizzato per popolare automaticamente il database con un utente di prova al primo avvio del backend.

Questo meccanismo permette di accedere subito all’app senza dover creare manualmente un account, ed è utile per testare rapidamente:

-login

-visualizzazione del saldo

-visualizzazione delle transazioni

-esecuzione di bonifici

-verifica delle funzionalità di sicurezza

Utente di prova creato automaticamente

Il DataInitializer crea (solo se non già presente nel database) un utente con:

Username: mariaaa

Password: password123

Nome: Maria Morelli

Paese: IT

IBAN: generato per i test

Saldo iniziale: 50 €

Ruolo: USER

Quando viene eseguito:

-Il seed viene eseguito automaticamente all’avvio del backend grazie al meccanismo di inizializzazione di Spring Boot.

-Utilità per il test

Questo utente permette di:

-accedere subito all’app e verificarne il funzionamento,

-effettuare bonifici senza dover inserire dati a mano,

-visualizzare saldo e transazioni di esempio,

-eseguire test di sicurezza (login, accessi non autorizzati, ecc.).


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


git clone --recursive https://github.com/Marcolino2003/MobileBankingDemo.git
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



