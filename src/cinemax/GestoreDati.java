package cinemax;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che funge da Data Access Object (DAO) e gestore della logica di business.
 * <p>
 * Centralizza l'accesso ai dati, gestendo il caricamento e il salvataggio persistente
 * tramite file CSV e file binari serializzati. Mantiene in memoria lo stato dell'applicazione
 * attraverso liste incapsulate e fornisce metodi sicuri per la ricerca, la validazione 
 * e il calcolo delle disponibilità.
 * </p>
 * 
 * @author Daniele Paoli
 * @author Edoardo Pigni
 * @author Anes Khaia
 * @version 1.0
 */
public class GestoreDati {
    
    /**
     * Percorso relativo del file binario utilizzato per la persistenza degli utenti.
     */
    private static final String FILE_UTENTI = "data/utenti.dat";

    /**
     * Percorso relativo del file binario utilizzato per la persistenza delle prenotazioni.
     */
    private static final String FILE_PRENOTAZIONI = "data/prenotazioni.dat";

    /**
     * Percorso relativo del file testuale CSV contenente il palinsesto delle proiezioni.
     */
    private static final String FILE_PROIEZIONI_CSV = "data/proiezioni.csv";

    /**
     * Lista in memoria contenente tutti gli utenti registrati nel sistema.
     */
    private List<Utente> listaUtenti = new ArrayList<>();

    /**
     * Lista in memoria contenente tutte le prenotazioni effettuate.
     */
    private List<Prenotazione> listaPrenotazioni = new ArrayList<>();

    /**
     * Lista in memoria contenente il palinsesto delle proiezioni disponibili.
     */
    private List<Proiezione> listaProiezioni = new ArrayList<>();

    // --- GETTER PER LE LISTE ---
    
    /**
     * Restituisce la lista di tutte le proiezioni attualmente caricate a sistema.
     * 
     * @return una lista di oggetti {@link Proiezione}
     */
    public List<Proiezione> getListaProiezioni() {
        return listaProiezioni;
    }

    /**
     * Restituisce la lista di tutte le prenotazioni effettuate dai clienti.
     * 
     * @return una lista di oggetti {@link Prenotazione}
     */
    public List<Prenotazione> getListaPrenotazioni() {
        return listaPrenotazioni;
    }

    // --- METODI CONTROLLATI PER AGGIUNGERE ELEMENTI ---
    
    /**
     * Aggiunge in modo sicuro un nuovo utente alla lista in memoria.
     * 
     * @param u l'oggetto {@link Utente} da inserire a sistema
     */
    public void aggiungiUtente(Utente u) {
        if (u != null) {
            listaUtenti.add(u);
        }
    }

    /**
     * Aggiunge in modo sicuro una nuova prenotazione alla lista in memoria.
     * 
     * @param p l'oggetto {@link Prenotazione} da inserire a sistema
     */
    public void aggiungiPrenotazione(Prenotazione p) {
        if (p != null) {
            listaPrenotazioni.add(p);
        }
    }

    /**
     * Inizializza il sistema caricando tutti i dati salvati su disco.
     * <p>
     * Legge le proiezioni dal file CSV e deserializza utenti e prenotazioni dai file `.dat`.
     * Se la lista utenti risulta vuota al primo avvio, genera automaticamente una configurazione
     * di default inserendo 2 account Proiezionista e 5 account Bigliettaio.
     * </p>
     */
    public void inizializzaSistema() {
        caricaProiezioniDalCSV();
        caricaUtenti();
        caricaPrenotazioni();
        
        // Specifica: Il file dovrà già contenere 2 proiezionisti e 5 bigliettai.
        if (listaUtenti.isEmpty()) {
            System.out.println("Inizializzazione utenti di default in corso...");
            listaUtenti.add(new Utente("Mario", "Rossi", "proiezionista1", "pass123", "01/01/1980", "Varese", "Proiezionista"));
            listaUtenti.add(new Utente("Luigi", "Verdi", "proiezionista2", "pass123", "02/02/1985", "Como", "Proiezionista"));
            
            for (int i = 1; i <= 5; i++) {
                listaUtenti.add(new Utente("Bigliettaio", "Num" + i, "bigliettaio" + i, "pass123", "01/01/1990", "Milano", "Bigliettaio"));
            }
            salvaUtenti();
        }
    }

    /**
     * Legge il palinsesto delle proiezioni dal file CSV e popola la lista in memoria.
     * <p>
     * Utilizza una Regular Expression (Regex) avanzata per splittare correttamente i campi
     * separati da virgola, ignorando le virgole interne a blocchi di testo racchiusi tra virgolette.
     * </p>
     */
    public void caricaProiezioniDalCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        File csvFile = new File(FILE_PROIEZIONI_CSV);
        
        if (!csvFile.exists()) {
            System.err.println("Attenzione: file " + FILE_PROIEZIONI_CSV + " non trovato nella cartella data!");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine(); // Salta l'intestazione
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                
                if (values.length >= 8) {
                    String dataOra = values[0].replace("\"", "").trim();
                    String titolo = values[1].replace("\"", "").trim();
                    String genere = values[2].replace("\"", "").trim();
                    String regista = values[3].replace("\"", "").trim();
                    int anno = Integer.parseInt(values[4].replace("\"", "").trim());
                    int durata = Integer.parseInt(values[5].replace("\"", "").trim());
                    int etaMin = Integer.parseInt(values[6].replace("\"", "").trim());
                    double prezzo = Double.parseDouble(values[7].replace("\"", "").trim());

                    Proiezione p = new Proiezione(
                        LocalDateTime.parse(dataOra, formatter),
                        titolo,
                        genere,
                        regista,
                        anno,
                        durata,
                        etaMin,
                        prezzo
                    );
                    listaProiezioni.add(p);
                }
            }
            System.out.println("Caricate " + listaProiezioni.size() + " proiezioni dal CSV con successo!");
        } catch (Exception e) {
            System.err.println("Errore durante la lettura del CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- CARICAMENTO E SALVATAGGIO UTENTI ---
    
    /**
     * Deserializza la lista degli utenti dal file binario preposto.
     * Gestisce silenziosamente l'assenza del file al primo avvio.
     */
    @SuppressWarnings("unchecked")
    public void caricaUtenti() {
        File f = new File(FILE_UTENTI);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                listaUtenti = (List<Utente>) ois.readObject();
            } catch (Exception e) {
                System.err.println("Errore nel caricamento utenti: " + e.getMessage());
            }
        }
    }

    /**
     * Serializza la lista corrente degli utenti salvandola su file binario.
     */
    public void salvaUtenti() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_UTENTI))) {
            oos.writeObject(listaUtenti);
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio utenti: " + e.getMessage());
        }
    }

    // --- CARICAMENTO E SALVATAGGIO PRENOTAZIONI ---
    
    /**
     * Deserializza la lista delle prenotazioni dal file binario preposto.
     * Gestisce silenziosamente l'assenza del file in caso di archivio vuoto.
     */
    @SuppressWarnings("unchecked")
    public void caricaPrenotazioni() {
        File f = new File(FILE_PRENOTAZIONI);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                listaPrenotazioni = (List<Prenotazione>) ois.readObject();
            } catch (Exception e) {
                System.err.println("Errore nel caricamento prenotazioni: " + e.getMessage());
            }
        }
    }

    /**
     * Serializza la lista corrente delle prenotazioni salvandola su file binario.
     */
    public void salvaPrenotazioni() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PRENOTAZIONI))) {
            oos.writeObject(listaPrenotazioni);
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio prenotazioni: " + e.getMessage());
        }
    }

    /**
     * Esegue una ricerca lineare all'interno del palinsesto.
     * <p>
     * Il matching avviene in modalità case-insensitive controllando se la chiave 
     * di ricerca è contenuta nel titolo, nel genere o nel nome del regista.
     * </p>
     * 
     * @param chiaveDiRicerca la stringa immessa dall'utente per filtrare i film
     * @return una lista contenente le proiezioni che soddisfano i criteri di ricerca
     */
    public List<Proiezione> ricercaProiezioni(String chiaveDiRicerca) {
        List<Proiezione> risultati = new ArrayList<>();
        String query = chiaveDiRicerca.toLowerCase().trim();

        for (Proiezione p : listaProiezioni) {
            if (p.getTitoloFilm().toLowerCase().contains(query) ||
                p.getGenere().toLowerCase().contains(query) ||
                p.getRegista().toLowerCase().contains(query)) {
                
                risultati.add(p);
            }
        }
        return risultati;
    }

    /**
     * Verifica la disponibilità di uno username all'interno dell'anagrafica utenti.
     * Viene utilizzato in fase di registrazione per prevenire duplicati.
     * 
     * @param username l'identificativo da verificare
     * @return {@code true} se lo username è già registrato, {@code false} altrimenti
     */
    public boolean usernameGiaEsistente(String username) {
        for (Utente u : listaUtenti) {
            if (u.getUsername().equalsIgnoreCase(username.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Autentica un utente verificandone la corrispondenza esatta di username e password.
     * 
     * @param username lo username inserito
     * @param password la password inserita
     * @return l'oggetto {@link Utente} se le credenziali sono corrette, {@code null} in caso contrario
     */
    public Utente verificaCredenziali(String username, String password) {
        for (Utente u : listaUtenti) {
            if (u.getUsername().equals(username.trim()) && u.getPassword().equals(password.trim())) {
                return u; 
            }
        }
        return null; 
    }

    /**
     * Calcola dinamicamente il numero di posti ancora disponibili per un dato spettacolo.
     * <p>
     * Il calcolo viene effettuato sottraendo alla capienza totale della sala ({@code Proiezione.POSTI_TOTALI})
     * la somma dei posti già allocati nelle prenotazioni attive per quello specifico film e orario.
     * </p>
     * 
     * @param p la proiezione di cui verificare la disponibilità residua
     * @return il numero intero di posti liberi e prenotabili
     */
    public int calcolaPostiDisponibili(Proiezione p) {
        int postiOccupati = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataOraTarget = p.getDataOraProiezione().format(formatter);
        
        for (Prenotazione pren : listaPrenotazioni) {
            if (pren.getTitoloFilm().equals(p.getTitoloFilm()) && 
                pren.getDataOraStringa().equals(dataOraTarget)) {
                postiOccupati += pren.getNumeroPosti();
            }
        }
        return Proiezione.POSTI_TOTALI - postiOccupati;
    }
}