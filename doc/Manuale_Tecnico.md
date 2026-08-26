# CineMax – Manuale Tecnico e Architetturale

---


|                        |                                                             |
| ---------------------- | ----------------------------------------------------------- |
| **Titolo**             | CineMax — Sistema di prenotazione biglietti cinematografici |
| **Autori**             | Daniele Paoli, Edoardo Pigni, Anes Khaia                    |
| **Data**               | 23 agosto 2026                                              |
| **Versione documento** | 1.0                                                         |


---

## Indice

1. [Installazione](#1-installazione)
  1.1. [Requisiti di sistema](#11-requisiti-di-sistema)
   1.2. [Setup ambiente](#12-setup-ambiente)
   1.3. [Installazione programma](#13-installazione-programma)
2. [Esecuzione ed uso](#2-esecuzione-ed-uso)
  2.1. [Setup e lancio del programma](#21-setup-e-lancio-del-programma)
   2.2. [Uso delle funzionalità (Architettura, Dati e Algoritmi)](#22-uso-delle-funzionalit-architettura-dati-e-algoritmi)
3. [Data set di test](#3-data-set-di-test)
4. [Limiti della soluzione sviluppata](#4-limiti-della-soluzione-sviluppata)
5. [Sitografia / Bibliografia](#5-sitografia--bibliografia)

---

## 1. Installazione

### 1.1. Requisiti di sistema

L'applicazione è progettata per essere "Platform Independent" (indipendente dal sistema operativo) grazie all'esecuzione sulla Java Virtual Machine (JVM).

- **Ambiente di Esecuzione (Target Runtime):** Java Development Kit (JDK) versione 8 o superiore.
- **Spazio su disco:** < 10 MB per l'eseguibile e le librerie standard; lo spazio per i dati cresce linearmente in base al numero di prenotazioni effettuate.
- **Memoria RAM:** Il footprint di memoria è minimo (circa 50-100 MB), in quanto le strutture dati in memoria vengono gestite attivamente dal Garbage Collector.

### 1.2. Setup ambiente

Per operare correttamente, l'ambiente di sviluppo (IDE) e il terminale di esecuzione devono essere configurati con **Encoding UTF-8** per prevenire artefatti nella lettura del file sorgente CSV e nella scrittura a console.

### 1.3. Installazione programma

L'architettura del progetto prevede specifica struttura delle directory che deve essere mantenuta integra:

- `src/cinemax/`: Contiene i sorgenti `.java` del package.
- `data/`: Directory adibita allo storage persistente dei file dati (CSV e `.dat`).
- `doc/javadoc/`: Contiene la documentazione tecnica del codice sotto forma di API generata dal tool JavaDoc.

La build del progetto avviene compilando le classi del package `cinemax` tramite direttiva standard `javac`.

---

## 2. Esecuzione ed uso

### 2.1. Setup e lancio del programma

Il punto di ingresso (Entry Point) del software è il metodo `main` localizzato nella classe `CineMax.java`. Al momento dell'avvio del processo, il sistema delega la fase di *bootstrap* alla classe `GestoreDati` invocando il metodo `inizializzaSistema()`. Questa procedura si occupa di ricostruire lo stato dell'applicazione in memoria leggendo i file dal disco e intercettando eventuali anomalie tramite blocchi di `try-catch`.

### 2.2. Uso delle funzionalità (Architettura, Dati e Algoritmi)

**A. Scelte Architetturali e Design Pattern**
Il sistema disaccoppia l'interfaccia utente testuale (CLI) dalla logica applicativa sfruttando una declinazione del **Data Access Object (DAO) Pattern**. 
La classe `GestoreDati` funge da unico layer di accesso alle informazioni: incapsula le liste in memoria mantenendole `private` e le espone alle classi esterne unicamente tramite metodi di servizio sicuri (getter, logiche di inserimento validato). Questa scelta previene modifiche accidentali dello stato dell'applicazione dalla UI.

**B. Strutture Dati Utilizzate**
Per la gestione dello stato a runtime (in memoria), si è optato per la **Java Collections Framework**:

- `List<Utente>`, `List<Prenotazione>`, `List<Proiezione>`: Implementate tramite `ArrayList`. L'utilizzo di array dinamici garantisce un costo computazionale costante O(1) per l'inserimento in coda (append) e supporta la ricerca lineare, che risulta adeguata data la mole contenuta dei record analizzati.
- `LocalDateTime` e `OrarioDoc`: Utilizzate per garantire l'integrità del calcolo temporale, superando le criticità delle stringhe standard per la comparazione delle scadenze di proiezione.

**C. Formato dei File e Persistenza**
Il sistema impiega due flussi di I/O distinti allocati nella cartella relativa `data/`:

1. **File CSV (**`proiezioni.csv`**):** Sola lettura. Viene utilizzato per caricare il palinsesto.
2. **File Binari Serializzati (**`utenti.dat`**,** `prenotazioni.dat`**):** Lettura/Scrittura. Permettono di salvare lo stato degli oggetti complessi sfruttando l'interfaccia marker `java.io.Serializable`. Gli stream `ObjectOutputStream` evitano la necessità di implementare parser complessi per ripristinare le relazioni tra i dati generati dagli utenti.

**D. Scelte Algoritmiche e Flusso Dati**

*Snippet 1: Algoritmo di Parsing CSV con Regex*
Durante la lettura del file testuale, una semplice funzione split(",") genererebbe eccezioni se i campi di testo contenessero virgole interne (es. Il Buono, Il Brutto, Il Cattivo). Il flusso di lettura implementa una Regular Expression di tipo "lookahead" per identificare e isolare solo i separatori esterni alle virgolette.

```java
while ((line = br.readLine()) != null) {
    // Regex algoritmica per il parsing sicuro del CSV
    String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    if (values.length >= 8) {
        // Estrazione e pulizia dei dati
        String dataOra = values[0].replace("\"", "").trim();
        // [...] 
    }
}
```

*Snippet 2: Logica di Calcolo Disponibilità Posti*
Invece di memorizzare un attributo di "posti rimanenti" soggetto a facili desincronizzazioni in memoria, l'algoritmo calcola dinamicamente la disponibilità interrogando a runtime lo storico delle prenotazioni.

```java
public int calcolaPostiDisponibili(Proiezione p) {
    int postiOccupati = 0;
    String dataOraTarget = p.getDataOraProiezione().format(formatter);
    
    for (Prenotazione pren : listaPrenotazioni) {
        if (pren.getTitoloFilm().equals(p.getTitoloFilm()) && 
            pren.getDataOraStringa().equals(dataOraTarget)) {
            postiOccupati += pren.getNumeroPosti();
        }
    }
    return Proiezione.POSTI_TOTALI - postiOccupati;
}
```

**E. Documentazione JavaDoc**
Tutto il codice sorgente è corredato da commenti formattati secondo lo standard JavaDoc. La documentazione tecnica dettagliata delle singole classi, dei metodi e delle firme delle funzioni è consultabile nel relativo sito web HTML generato all'interno del percorso `doc/javadoc/index.html`.

---

## 3. Data set di test

Per agevolare il testing e validare le logiche architetturali, l'applicazione è dotata di routine di auto-popolamento (seeding). 
Nel caso in cui i file di persistenza `.dat` risultino assenti o vuoti al primo avvio, il metodo `inizializzaSistema()` inietta nel database in memoria un dataset di test obbligatorio da specifiche, composto da:

- 2 Utenti con ruolo *Proiezionista* (es. `proiezionista1`, `proiezionista2`).
- 5 Utenti con ruolo *Bigliettaio*.

Le anagrafiche Cliente e le transazioni di Prenotazione vengono invece simulate a runtime dall'operatore.

---

## 4. Limiti della soluzione sviluppata

- **Limiti di Concorrenza:** Il salvataggio dei dati in file `.dat` locali tramite serializzazione pura impedisce gli accessi concorrenti da parte di più thread o terminali simultanei senza l'innesco di condizioni di race condition o lock sui file. 
- **Scalabilità della Memoria:** Il caricamento di interi set di dati (tutte le prenotazioni e tutti gli utenti) all'interno degli `ArrayList` ad ogni avvio ha una complessità spaziale pari a O(N). Se il database superasse centinaia di migliaia di record, si incorrerebbe in rallentamenti o crash (OutOfMemoryError). Per un uso in produzione risulterebbe necessaria l'implementazione di un Relational Database Management System (RDBMS) come MySQL o PostgreSQL.

---

## 5. Sitografia / Bibliografia

- Oracle Corporation. *Java Platform, Standard Edition 17 API Specification*. Consultabile online su: [https://docs.oracle.com/en/java/javase/17/docs/api/](https://docs.oracle.com/en/java/javase/17/docs/api/)
- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley (Riferimenti al Data Access Object Pattern per la strutturazione della classe `GestoreDati`).

---

*Fine del Manuale Tecnico — CineMax v1.0*