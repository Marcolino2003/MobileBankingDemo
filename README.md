#  Mobile Banking Demo – Caso di Studio

**Autore:** Marco Morelli  
**Matricola:** 837680  

---

##  Scenario

L’applicazione simula un sistema di mobile banking moderno, progettato per gestire in modo sicuro le operazioni relative a un conto corrente bancario.

L’utente può:

- Registrarsi  
- Autenticarsi  
- Visualizzare saldo e IBAN  
- Consultare lo storico delle transazioni  
- Effettuare bonifici verso altri conti  

Il sistema è basato su un’architettura **frontend–backend** che comunica tramite **API REST**, replicando il comportamento di una piattaforma reale.

---

##  Obiettivi di Sicurezza

Il caso di studio evidenzia le principali sfide del settore bancario:

- Prevenzione dell’accesso non autorizzato  
- Garantire l’integrità dei dati nelle transazioni  
- Proteggere la comunicazione tra client e server  
- Mitigare rischi da input non validati o attacchi injection  
- Applicare un modello solido di autenticazione e autorizzazione  

---

##  Architettura Tecnica

### **Frontend**
- React.js  
- Axios  
- React Router DOM  

### **Backend**
- Spring Boot (3.5.7)  
- Spring Data JPA  
- Spring Security  
- Java 17  
- Maven  

### **Database**
- MySQL tramite XAMPP  
- **Schema:** `bank`

### **Comunicazione**
- API REST  
- Axios lato frontend  

---

##  Requisiti Software (Versioni Precise)

### **Backend**
- Java: **17**  
- Spring Boot: **3.5.7**  
- Spring Security: integrato  
- MySQL: via XAMPP  
- Maven  

### **Frontend**
- Node.js: **18+**  
- React.js: **9.2.0**  
- Axios: **1.13.1**  
- React Router DOM: **7.9.5**  

---

##  Note e Risoluzione Problemi MySQL

###  Uso di XAMPP
Per evitare problemi di compatibilità con MySQL installato standalone, si utilizza **XAMPP**, che garantisce:

- Maggiore stabilità  
- Avvio affidabile del servizio MySQL  

### Verifiche fondamentali

Assicurarsi che:

1. MySQL sia attivo in XAMPP  
   - Porta predefinita: **3306**

2. Dialetto Hibernate corretto  
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect


3. Timezone impostato nella URL JDBC  
spring.datasource.url=jdbc:mysql://localhost:3306/bank?serverTimezone=Europe/Rome



Queste impostazioni permettono il corretto funzionamento del **DataInitializer**.

---

##  Seed del Database (DataInitializer)

Il backend contiene un componente **DataInitializer** che crea automaticamente un utente di test al primo avvio, se non esistente.

###  Utente creato automaticamente

- **Username:** `mariaaa`  
- **Password:** `password123`  
- **Nome:** Maria Morelli  
- **Paese:** IT  
- **IBAN:** generato automaticamente  
- **Saldo iniziale:** 50 €  
- **Ruolo:** `UTENTE`  

###  A cosa serve?

Permette di testare subito:

- Login  
- Saldo e transazioni  
- Bonifici  
- Funzionalità di sicurezza  

---

##  Installazione e Setup

### 1️ Preparazione del Database

Avvia **XAMPP → MySQL → phpMyAdmin**

Assicurati che esista il database `bank`:

```sql
USE bank;
```
### 2️ Clonare il progetto
```
git clone --recursive https://github.com/Marcolino2003/MobileBankingDemo.git
```
### 3️ Avvio Backend
```
cd backend
mvn spring-boot:run
```
Backend disponibile su:
http://localhost:8080

### 4️ Avvio Frontend
```
cd frontend/frontend-bank
npm install
npm start
```

Frontend disponibile su:
 http://localhost:3000

---

## Funzionalità Principali
- Registrazione utente (età minima 18 anni)

- Generazione automatica IBAN

- Login 

- Visualizzazione saldo e IBAN

- Effettuazione bonifici con verifica saldo

- Lista transazioni con data, descrizione e importo
---

## Sicurezza Implementata
- Autenticazione e autorizzazione con Spring Security

- Validazione input lato backend e frontend

- CORS configurato per http://localhost:3000

- Architettura predisposta per HTTPS/TLS

- Password cifrate con BCrypt
---

## Struttura del Database
### Tabella users:
- Campo	Descrizione
- id	Identificativo
- nome	Nome utente
- cognome	Cognome
- username	Username
- password	Password hash
- iban	Codice IBAN
- saldo	Saldo attuale
- paese	Paese
- dataNascita	Data di nascita

## Tabella transaction
- Campo	Descrizione
- id	Identificativo
- amount	Importo
- date	Data transazione
- description	Descrizione
- iban	IBAN destinazione
- user_id	Riferimento utente

##  Istruzioni di Download e Setup — Requisiti

Per avviare l'applicazione sono necessari i seguenti ambienti di runtime e gestori di pacchetti.

---

## 1️ Java e Spring Boot (Backend)

Il progetto utilizza **Java 17** e **Maven** per la gestione delle dipendenze di Spring Boot.

###  Java Development Kit (JDK 17)

**Download:**  
Scarica e installa Java 17 (o versione successiva) da uno dei seguenti provider:

- Oracle JDK  
- Eclipse Temurin  
- Amazon Corretto  
- Adoptium  

**Verifica installazione:**  
Apri il terminale e digita:

```
java -version
```
### Maven

Maven è il gestore di dipendenze del backend.

Molti IDE (come IntelliJ IDEA o Eclipse) includono già Maven.

In caso contrario, installalo dal sito ufficiale.

Verifica installazione:
```
mvn -version
```

Se il comando non viene riconosciuto, assicurati di configurare la variabile d’ambiente PATH seguendo le istruzioni di installazione ufficiali.

## 2️ Node.js e Dipendenze Frontend (React / Axios)

Il frontend React richiede Node.js, che include automaticamente npm (Node Package Manager).

- Node.js (v18+)

Download:
Scarica e installa l’ultima versione LTS di Node.js dal sito ufficiale.

Verifica installazione:
```
node -v
npm -v
```

### Librerie Frontend (React, Axios, React Router DOM)

Le librerie necessarie:

- React.js 19.2.0

- Axios 1.13.1

- React Router DOM 7.9.5

Non richiedono download manuale.

Verranno installate automaticamente quando esegui:
```
npm install
```

nella directory:
```
frontend/frontend-bank
```
## 3 Database — MySQL tramite XAMPP

### XAMPP

Download:
Scarica XAMPP dal sito ufficiale Apache Friends.

Installazione:
Durante l’installazione, assicurati che il modulo MySQL sia selezionato.

Avvio:
Apri il Pannello di Controllo XAMPP → premi Start su:
```
MySQL

https://github.com/user-attachments/assets/b14336b4-e5af-43a5-aedf-157370018655


```
Il backend Spring Boot si collegherà automaticamente a questo servizio.

## 4 DIMOSTRAZIONE DEL PROGETTO 


https://github.com/user-attachments/assets/1631c703-7b7c-4fd4-8954-0c3887770b48


## 5 Diagramma Architetturale con Endpoint (Stile API)
Questo diagramma mostra come il Frontend (Client) interagisce con specifici endpoint del Backend (Server), che a sua volta comunica con il Database.

<img width="797" height="302" alt="Diagramma Architetturale con Endpoint (Stile API) drawio (1)" src="https://github.com/user-attachments/assets/b4378695-6ac4-467b-97f3-6d3e0f621d2f" />


