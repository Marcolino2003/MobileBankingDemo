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
# Architettura MVC del Progetto Bancario

## V - VIEW (La Vista / Frontend)

È la parte che l'utente **vede** e con cui **interagisce**.  
Nel progetto, la **View è completamente gestita dal client**.

- **Tecnologie:** React.js, React Router DOM  
- **Ruolo:**  
  - Mostra il saldo  
  - Visualizza il form di login  
  - Elenca le transazioni  


**Nel codice:**
- Componenti React 
- Ricevono dati JSON dal backend


---

## C - CONTROLLER (Il Gestore / Backend)

È il **cervello** dell’applicazione: riceve le richieste e decide cosa fare.

- **Tecnologie:** Spring Boot (`@RestController`), Spring Security  
- **Ruolo:**  
  - Riceve richieste HTTP dal frontend (Axios)  
  - Gestisce l’autenticazione (JWT / Session)  
  - Valida gli input (es. *età > 18*)  
  - Decide quale servizio invocare  

**Nel codice:**
- Classi Java annotate con `@RestController`
- Endpoint REST (es. `POST /api/bonifico`)

---

## M - MODEL (Il Modello / Dati)

Rappresenta la **struttura dei dati** e le **regole di business fondamentali**.

- **Tecnologie:** Spring Data JPA, MySQL (XAMPP)  
- **Ruolo:**  
  - Gestisce lo stato dell’applicazione  
  - Mantiene saldo, utenti e storico transazioni  

**Nel codice:**
- **Entities Java:**
  - `User` (id, nome, saldo, iban)
  - `Transaction`
- **Database MySQL:**
  - Tabelle `users`
  - Tabelle `transaction`

---
## MOTIVAZIONE DELLA SCELTA DI QUESTO TIPO DI ARCHITETTURA

- Ho scelto di separare nettamente il Frontend (React) dal Backend (Spring Boot) per tre motivi fondamentali:

- Migliore User Experience (La View): Utilizzando React, l'applicazione è una Single Page Application (SPA). Come descritto nel punto "View", l'utente interagisce senza dover ricaricare l'intera pagina a ogni click. Se avessi usato un MVC classico (JSP), ogni azione avrebbe comportato un ricaricamento pagina, rendendo l'esperienza lenta e "vecchia".

- Riusabilità del Backend (Il Controller): Il mio Controller espone API REST che restituiscono JSON, non HTML. Questo è fondamentale perché rende il backend "universale": lo stesso identico codice Java potrebbe servire domani un'app mobile (Android/iOS) senza dover essere modificato. Un'architettura monolitica non avrebbe permesso questa flessibilità.

- Sicurezza e Pulizia (Il Model): Tenendo la logica di business (calcolo saldo, regole bonifico) strettamente nel Backend e nel Database (Model), garantisco che nessun dato sensibile venga manipolato dal browser. Il Frontend riceve solo il risultato finale, garantendo che le regole di sicurezza ("Business Logic") siano inaccessibili all'utente finale.

---

## Esempio Pratico: Flusso di un Bonifico

### 1️ View (Frontend)
L’utente **Maria Morelli** compila il form del bonifico e preme **"Invia"**.  
React intercetta l’evento.

### 2️ Axios
Viene inviata una richiesta:
```http
POST /api/bonifico


{
  "importo": 100,
  "ibanDestinatario": "IT60X0542811101000000123456"
}
```
### 3️ Controller (Backend)

Spring Boot riceve la richiesta tramite TransactionController:

Verifica autenticazione

Valida i dati

Passa la richiesta al service

### 4️ Model / Business Logic

Controlla se il saldo di Maria è sufficiente

Se sì:

Aggiorna la tabella users

Sottrae l’importo a Maria

Aggiunge l’importo al destinatario

Inserisce una nuova riga in transaction

### 5️ Ritorno della Risposta

Il backend risponde con:

200 OK

### 6️ Aggiornamento della View

Axios riceve la risposta positiva
React aggiorna il saldo mostrato senza ricaricare la pagina



<img width="732" height="1011" alt="MVC PROGETTO SAOS drawio" src="https://github.com/user-attachments/assets/3b78774b-a506-41e4-b057-42bfcb78f6f1" />


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



2. Timezone impostato nella URL JDBC  
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
| Campo        | Descrizione     |
|--------------|-----------------|
| id           | Identificativo  |
| nome         | Nome utente     |
| cognome      | Cognome         |
| username     | Username        |
| password     | Password hash   |
| iban         | Codice IBAN     |
| saldo        | Saldo attuale   |
| paese        | Paese           |
| dataNascita  | Data di nascita |

## Tabella transaction
| Campo       | Descrizione            |
|-------------|------------------------|
| id          | Identificativo         |
| amount      | Importo                |
| date        | Data transazione       |
| description | Descrizione            |
| iban        | IBAN destinazione      |
| user_id     | Riferimento utente     |


##  Istruzioni di Download e Setup — Requisiti

Per avviare l'applicazione sono necessari i seguenti ambienti di runtime e gestori di pacchetti.

---

## 1️ Java e Spring Boot (Backend)

Il progetto utilizza **Java 17** e **Maven** per la gestione delle dipendenze di Spring Boot.

###  Java Development Kit (JDK 17)

**Download:**  
Scarica e installa Java 17 (o versione successiva) dal seguente  provider:

- Oracle JDK  


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

## 6 SCHERMATE DEL PROGETTO

### REGISTRAZIONE

<img width="1912" height="1007" alt="REGISTRAZIONE" src="https://github.com/user-attachments/assets/f091f9c8-b0ef-4d09-b532-0552deb21406" />

### REGISTRAZIONE FALLITA

<img width="1911" height="1002" alt="Registrazione_Fallita" src="https://github.com/user-attachments/assets/594600c0-746c-437d-b342-91de46dada25" />

### LOGIN

<img width="1917" height="1007" alt="login" src="https://github.com/user-attachments/assets/97f4e374-09b1-4d96-ab29-cff360fff578" />

### LOGIN FALLITO

<img width="1918" height="951" alt="CREDENZIALI_NON_VALIDE" src="https://github.com/user-attachments/assets/67f19f08-8650-4a2e-8914-f0ce560af468" />


### HOMEPAGE UTENTE NORMALE

<img width="1903" height="1017" alt="HOMEPAGE_USERNORMALE" src="https://github.com/user-attachments/assets/3b312318-110b-495b-b492-96960a97da5f" />

### HOMEPAGE UTENTE TESTER

<img width="1917" height="1016" alt="HOMEPAGE_USERTESTER" src="https://github.com/user-attachments/assets/0a24ac2b-8b06-4036-aa59-e212d225e123" />

### ERRORE BONIFICO

<img width="1917" height="1017" alt="ERRORE_BONIFICO" src="https://github.com/user-attachments/assets/13cdcceb-6417-4024-b280-35eb875afb90" />

### ERRORE SALDO INSUFFICIENTE 

<img width="1915" height="1011" alt="SALDO_INSUFFICENTE" src="https://github.com/user-attachments/assets/0c15650e-531c-4e9f-a662-7eff8ce0e63e" />

### BONIFICO EFFETTUATO CON SUCCESSO

<img width="1917" height="1012" alt="SUCCESSO_BONIFICO" src="https://github.com/user-attachments/assets/f99ce7f6-d582-46d0-bfe0-ccf46d8952a8" />

### HOMEPAGE DOPO AVER FATTO BONIFICO UTENTE TEST

<img width="1917" height="1005" alt="HOME_DOPOBONIFICO_UTENTETEST" src="https://github.com/user-attachments/assets/c179f229-96b8-4ef8-9400-1a6e4671d2dc" />

### HOMEPAGE DOPO AVER RICEVUTO BONIFICO

<img width="1918" height="1016" alt="HOMEPAGE_DOPO_BONIFICO_DI_UTENTE_NORMALE" src="https://github.com/user-attachments/assets/3f60245c-5a9b-4050-94f1-f08afe417742" />


