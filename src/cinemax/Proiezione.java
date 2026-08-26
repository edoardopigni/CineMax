package cinemax;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Rappresenta una singola proiezione cinematografica programmata nel palinsesto del cinema.
 * <p>
 * La classe mantiene le informazioni relative all'orario di programmazione, ai dettagli
 * descrittivi dell'opera cinematografica (titolo, genere, regista, anno di produzione, durata),
 * ai vincoli di accesso per età e al costo unitario del biglietto.
 * Implementa {@link Serializable} per permettere la persistenza e il trasferimento dello stato.
 * </p>
 * 
 * @author Daniele Paoli
 * @author Edoardo Pigni
 * @author Anes Khaia
 * @version 1.0
 */
public class Proiezione implements Serializable {
    
    /**
     * Identificativo univoco di versione per la serializzazione degli oggetti.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Capienza massima standard di posti a sedere per la sala di proiezione.
     */
    public static final int POSTI_TOTALI = 200; 
    
    /**
     * Data e ora esatta in cui è programmata la proiezione dello spettacolo.
     */
    private LocalDateTime dataOraProiezione;

    /**
     * Titolo dell'opera cinematografica proiettata.
     */
    private String titoloFilm;

    /**
     * Categoria tematica o genere di appartenenza del film (es. Azione, Drammatico, Commedia).
     */
    private String genere;

    /**
     * Nome e cognome del regista dell'opera cinematografica.
     */
    private String regista;

    /**
     * Anno solare di produzione e uscita del film.
     */
    private int anno;

    /**
     * Durata complessiva della proiezione espressa in minuti.
     */
    private int durataMinuti;

    /**
     * Età minima anagrafica raccomandata o vincolante per la visione dello spettacolo.
     */
    private int etaMinima;

    /**
     * Tariffa base in Euro (€) per l'acquisto del singolo biglietto.
     */
    private double prezzoBiglietto;

    /**
     * Costruttore predefinito privo di argomenti.
     * <p>
     * Utilizzato principalmente per l'istanziazione generica o per procedure di popolamento progressivo.
     * </p>
     */
    public Proiezione() {
    }

    /**
     * Costruttore completo per la creazione e inizializzazione di una proiezione cinematografica.
     *
     * @param dataOraProiezione la data e l'orario di programmazione dello spettacolo
     * @param titoloFilm        il titolo del film
     * @param genere            il genere cinematografico
     * @param regista           il regista dell'opera
     * @param anno              l'anno di produzione del film
     * @param durataMinuti      la durata complessiva in minuti
     * @param etaMinima         l'età minima richiesta per la visione
     * @param prezzoBiglietto   il costo in Euro per singolo posto
     */
    public Proiezione(LocalDateTime dataOraProiezione, String titoloFilm, String genere, String regista, 
                      int anno, int durataMinuti, int etaMinima, double prezzoBiglietto) {
        this.dataOraProiezione = dataOraProiezione;
        this.titoloFilm = titoloFilm;
        this.genere = genere;
        this.regista = regista;
        this.anno = anno;
        this.durataMinuti = durataMinuti;
        this.etaMinima = etaMinima;
        this.prezzoBiglietto = prezzoBiglietto;
    }

    // --- GETTER E SETTER ---

    /**
     * Restituisce la data e l'ora fissate per la proiezione.
     * 
     * @return l'oggetto {@link LocalDateTime} dell'evento
     */
    public LocalDateTime getDataOraProiezione() {
        return dataOraProiezione;
    }

    /**
     * Imposta la data e l'ora fissate per la proiezione.
     * 
     * @param dataOraProiezione la nuova data e ora di programmazione
     */
    public void setDataOraProiezione(LocalDateTime dataOraProiezione) {
        this.dataOraProiezione = dataOraProiezione;
    }

    /**
     * Restituisce il titolo del film.
     * 
     * @return una stringa contenente il titolo dell'opera
     */
    public String getTitoloFilm() {
        return titoloFilm;
    }

    /**
     * Aggiorna il titolo del film in programmazione.
     * 
     * @param titoloFilm il nuovo titolo da assegnare
     */
    public void setTitoloFilm(String titoloFilm) {
        this.titoloFilm = titoloFilm;
    }

    /**
     * Restituisce il genere del film.
     * 
     * @return la stringa rappresentante il genere cinematografico
     */
    public String getGenere() {
        return genere;
    }

    /**
     * Aggiorna il genere del film.
     * 
     * @param genere il nuovo genere da impostare
     */
    public void setGenere(String genere) {
        this.genere = genere;
    }

    /**
     * Restituisce il regista del film.
     * 
     * @return il nome del regista
     */
    public String getRegista() {
        return regista;
    }

    /**
     * Aggiorna il regista dell'opera cinematografica.
     * 
     * @param regista il nome del regista da impostare
     */
    public void setRegista(String regista) {
        this.regista = regista;
    }

    /**
     * Restituisce l'anno di produzione del film.
     * 
     * @return l'anno espresso come valore intero
     */
    public int getAnno() {
        return anno;
    }

    /**
     * Imposta l'anno di uscita del film.
     * 
     * @param anno il nuovo anno di rilascio
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }

    /**
     * Restituisce la durata complessiva della proiezione.
     * 
     * @return la durata espressa in minuti
     */
    public int getDurataMinuti() {
        return durataMinuti;
    }

    /**
     * Aggiorna la durata in minuti della proiezione.
     * 
     * @param durataMinuti il numero di minuti totali
     */
    public void setDurataMinuti(int durataMinuti) {
        this.durataMinuti = durataMinuti;
    }

    /**
     * Restituisce la soglia di età minima raccomandata per assistere allo spettacolo.
     * 
     * @return l'età minima richiesta
     */
    public int getEtaMinima() {
        return etaMinima;
    }

    /**
     * Imposta la soglia di età minima per la visione del film.
     * 
     * @param etaMinima l'età minima espressa in anni
     */
    public void setEtaMinima(int etaMinima) {
        this.etaMinima = etaMinima;
    }

    /**
     * Restituisce la tariffa unitaria del biglietto per questa proiezione.
     * 
     * @return il prezzo del biglietto espresso in Euro
     */
    public double getPrezzoBiglietto() {
        return prezzoBiglietto;
    }

    /**
     * Imposta la tariffa unitaria del biglietto per questa proiezione.
     * 
     * @param prezzoBiglietto il nuovo costo del biglietto
     */
    public void setPrezzoBiglietto(double prezzoBiglietto) {
        this.prezzoBiglietto = prezzoBiglietto;
    }
    
    /**
     * Restituisce una stringa formattata con i dettagli essenziali della proiezione (data/ora, titolo, anno e costo).
     * 
     * @return la descrizione formattata nel pattern {@code "GG/MM/AAAA HH:mm | Titolo (Anno) - Prezzo€"}
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataOraProiezione.format(formatter) + " | " + titoloFilm + " (" + anno + ") - " + prezzoBiglietto + "€";
    }
}