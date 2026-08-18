package cinemax;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Rappresenta una singola proiezione in sala.
 * La capienza massima di default è di 200 posti.
 */
public class Proiezione implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final int POSTI_TOTALI = 200; 
    
    private LocalDateTime dataOraProiezione;
    private String titoloFilm;
    private String genere;
    private String regista;
    private int anno;
    private int durataMinuti;
    private int etaMinima;
    private double prezzoBiglietto;

    /**
     * Costruttore vuoto, utile quando si caricano i dati da CSV.
     */
    public Proiezione() {
    }

    /**
     * Costruttore completo.
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

    public LocalDateTime getDataOraProiezione() {
        return dataOraProiezione;
    }

    public void setDataOraProiezione(LocalDateTime dataOraProiezione) {
        this.dataOraProiezione = dataOraProiezione;
    }

    public String getTitoloFilm() {
        return titoloFilm;
    }

    public void setTitoloFilm(String titoloFilm) {
        this.titoloFilm = titoloFilm;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getRegista() {
        return regista;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getDurataMinuti() {
        return durataMinuti;
    }

    public void setDurataMinuti(int durataMinuti) {
        this.durataMinuti = durataMinuti;
    }

    public int getEtaMinima() {
        return etaMinima;
    }

    public void setEtaMinima(int etaMinima) {
        this.etaMinima = etaMinima;
    }

    public double getPrezzoBiglietto() {
        return prezzoBiglietto;
    }

    public void setPrezzoBiglietto(double prezzoBiglietto) {
        this.prezzoBiglietto = prezzoBiglietto;
    }
    
    // Metodo helper per stampare facilmente la proiezione
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataOraProiezione.format(formatter) + " | " + titoloFilm + " (" + anno + ") - " + prezzoBiglietto + "€";
    }
}