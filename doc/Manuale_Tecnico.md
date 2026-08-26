# CineMax - Manuale Tecnico e Architetturale

## Frontespizio

| | |
|---|---|
| **Progetto** | CineMax – Sistema di Gestione e Prenotazione Cinematografica (CLI) |
| **Documento** | Manuale Tecnico e Documentazione Architetturale |
| **Autori** | Team di Sviluppo CineMax |
| **Ruolo Redazione** | Document & Quality Manager |
| **Versione** | 1.0 Definitive |
| **Target Runtime** | Java Standard Edition (JDK 17+) |
| **Data Rilascio** | 23 Agosto 2026 |

---

## Indice
1. [Introduzione e Obiettivi del Documento](#1-introduzione-e-obiettivi-del-documento)
2. [Scelte Architetturali e Design Pattern](#2-scelte-architetturali-e-design-pattern)
3. [Analisi Strutturale delle Classi (Package cinemax)](#3-analisi-strutturale-delle-classi-package-cinemax)
   - 3.1. [Classe CineMax (Controller CLI)](#31-classe-cinemax)
   - 3.2. [Classe GestoreDati (Data Access Layer & Business Logic)](#32-classe-gestoredati)
   - 3.3. [Classe Utente (Modello di Dominio)](#33-classe-utente)
   - 3.4. [Classe Proiezione (Modello di Dominio)](#34-classe-proiezione)
   - 3.5. [Classe Prenotazione (Modello Transazionale)](#35-classe-prenotazione)
   - 3.6. [Classe OrarioDoc (Utility Temporale)](#36-classe-orariodoc)
4. [Strutture Dati e Scelte Algoritmiche](#4-strutture-dati-e-scelte-algoritmiche)
   - 4.1. [Strutture Dati in Memoria](#41-strutture-dati-in-memoria)
   - 4.2. [Algoritmo di Parsing CSV con Regular Expression Avanzata](#42-algoritmo-di-parsing-csv-con-regular-expression-avanzata)
   - 4.3. [Algoritmo di Ricerca Multi-Parametrica](#43-algoritmo-di-ricerca-multi-parametrica)
   - 4.4. [Algoritmo per il Calcolo della Disponibilità Posti](#44-algoritmo-per-il-calcolo-della-disponibilit-posti)
   - 4.5. [Generazione Univoca dei Codici Prenotazione](#45-generazione-univoca-dei-codici-prenotazione)
5. [Persistenza e Formato dei File](#5-persistenza-e-formato-dei-file)
   - 5.1. [Organizzazione Directory e Percorsi Relativi](#51-organizzazione-directory-e-percorsi-relativi)
   - 5.2. [Formato Tabellare: data/proiezioni.csv](#52-formato-tabellare-dataproiezionicsv)
   - 5.3. [Persistenza Binaria a Oggetti: data/utenti.dat e data/prenotazioni.dat](#53-persistenza-binaria-a-oggetti-datautentidat-e-dataprenotazionidat)
   - 5.4. [Meccanismo di Auto-Inizializzazione (Seeding)](#54-meccanismo-di-auto-inizializzazione-seeding)
6. [Gestione degli Errori, Validazione e Robustezza](#6-gestione-degli-errori-validazione-e-robustezza)
7. [Snippet di Codice Significativo](#7-snippet-di-codice-significativo)
8. [Conformità JavaDoc e Standard di Codifica](#8-conformit-javadoc-e-standard-di-codifica)

---

## 1. Introduzione e Obiettivi del Documento
Il presente manuale tecnico illustra l'architettura software, i criteri progettuali, le strutture dati e le implementazioni algoritmiche alla base dell'applicazione **CineMax**.

Il sistema è concepito come un'applicazione monolitica e modulare a riga di comando (CLI), progettata per garantire affidabilità nella gestione delle sessioni multi-ruolo, efficienza nelle operazioni di ricerca e integrità nella persistenza dei dati relativi a palinsesti cinematografici, anagrafiche utenti e transazioni di prenotazione.

---

## 2. Scelte Architetturali e Design Pattern

Il sistema adotta una variante architetturale a tre livelli (**Layered Architecture**), finalizzata a garantire separazione delle responsabilità (Separation of Concerns), incapsulamento e facilità di manutenzione: