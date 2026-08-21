package cinemax;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utility per la persistenza dei dati (CSV e file binari).
 */
public class GestoreDati {
    
    private static final String FILE_UTENTI = "data/utenti.dat";
    private static final String FILE_PRENOTAZIONI = "data/prenotazioni.dat";
    private static final String FILE_PROIEZIONI_CSV = "data/proiezioni.csv";

    // Liste in memoria rese PRIVATE per l'incapsulamento
    private List<Utente> listaUtenti = new ArrayList<>();
    private List<Prenotazione> listaPrenotazioni = new ArrayList<>();
    private List<Proiezione> listaProiezioni = new ArrayList<>();

    // --- GETTER PER LE LISTE ---
    public List<Proiezione> getListaProiezioni() {
        return listaProiezioni;
    }

    public List<Prenotazione> getListaPrenotazioni() {
        return listaPrenotazioni;
    }

    // --- METODI CONTROLLATI PER AGGIUNGERE ELEMENTI ---
    public void aggiungiUtente(Utente u) {
        if (u != null) {
            listaUtenti.add(u);
        }
    }

    public void aggiungiPrenotazione(Prenotazione p) {
        if (p != null) {
            listaPrenotazioni.add(p);
        }
    }

    /**
     * Metodo da richiamare all'avvio dell'applicazione.
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
     * Legge il file CSV gestendo correttamente i campi racchiusi tra virgolette.
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

    public void salvaUtenti() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_UTENTI))) {
            oos.writeObject(listaUtenti);
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio utenti: " + e.getMessage());
        }
    }

    // --- CARICAMENTO E SALVATAGGIO PRENOTAZIONI ---
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

    public void salvaPrenotazioni() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PRENOTAZIONI))) {
            oos.writeObject(listaPrenotazioni);
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio prenotazioni: " + e.getMessage());
        }
    }

    /**
     * Cerca le proiezioni filtrando per titolo, genere o regista.
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
     * Verifica se uno username è già presente nella lista utenti.
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
     * Verifica le credenziali di accesso.
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
     * Calcola i posti ancora disponibili per una proiezione.
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

    public void salvaProiezioniSuCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PROIEZIONI_CSV))) {
            pw.println("data_ora,titolo,genere,regista,anno,durata,eta_minima,prezzo");
            for (Proiezione p : listaProiezioni) {
                pw.printf("\"%s\",\"%s\",\"%s\",\"%s\",%d,%d,%d,%.2f%n",
                        p.getDataOraProiezione().format(formatter),
                        p.getTitoloFilm(),
                        p.getGenere(),
                        p.getRegista(),
                        p.getAnno(),
                        p.getDurataMinuti(),
                        p.getEtaMinima(),
                        p.getPrezzoBiglietto()
                );
            }
            System.out.println("File CSV delle proiezioni aggiornato con successo!");
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio del CSV proiezioni: " + e.getMessage());
        }
    }

    public boolean haPrenotazioniEsistenti(Proiezione p) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataOraTarget = p.getDataOraProiezione().format(formatter);
        for (Prenotazione pren : listaPrenotazioni) {
            if (pren.getTitoloFilm().equalsIgnoreCase(p.getTitoloFilm()) &&
                    pren.getDataOraStringa().equals(dataOraTarget)) {
                return true;
            }
        }
        return false;
    }


}