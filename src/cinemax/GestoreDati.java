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
                // Usiamo un'espressione regolare per dividere la riga considerando levirgolette
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                
                if (values.length >= 8) {
                    // Puliamo eventuali virgolette residue dai campi stringa
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
 * 
 * @param chiaveDiRicerca La parola o frase da cercare
 * @return Una lista di proiezioni che corrispondono ai criteri
 */
public List<Proiezione> ricercaProiezioni(String chiaveDiRicerca) {
    List<Proiezione> risultati = new ArrayList<>();
    // Trasformiamo la ricerca in minuscolo per non avere problemi di Case Sensitivity
    String query = chiaveDiRicerca.toLowerCase().trim();

    for (Proiezione p : listaProiezioni) {
    	// Controlla se la query è contenuta nel titolo, genere o regista
        if (p.getTitoloFilm().toLowerCase().contains(query) ||
            p.getGenere().toLowerCase().contains(query) ||
            p.getRegista().toLowerCase().contains(query)) {
            
            risultati.add(p);
        }
    }
    return risultati;
	}
}