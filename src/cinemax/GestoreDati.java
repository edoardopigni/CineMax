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

    // Liste in memoria che fungono da database durante l'esecuzione
    public List<Utente> listaUtenti = new ArrayList<>();
    public List<Prenotazione> listaPrenotazioni = new ArrayList<>();
    public List<Proiezione> listaProiezioni = new ArrayList<>();

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
     * Legge il file CSV e popola la lista delle proiezioni.
     */
    public void caricaProiezioniDalCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        File csvFile = new File(FILE_PROIEZIONI_CSV);
        
        if (!csvFile.exists()) {
            System.err.println("Attenzione: file " + FILE_PROIEZIONI_CSV + " non trovato nella cartella data!");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine(); // Salta la prima riga (intestazione)
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8) {
                    Proiezione p = new Proiezione(
                        LocalDateTime.parse(values[0].trim(), formatter),
                        values[1].trim(),
                        values[2].trim(),
                        values[3].trim(),
                        Integer.parseInt(values[4].trim()),
                        Integer.parseInt(values[5].trim()),
                        Integer.parseInt(values[6].trim()),
                        Double.parseDouble(values[7].trim())
                    );
                    listaProiezioni.add(p);
                }
            }
            System.out.println("Caricate " + listaProiezioni.size() + " proiezioni dal CSV.");
        } catch (Exception e) {
            System.err.println("Errore durante la lettura del CSV: " + e.getMessage());
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
}