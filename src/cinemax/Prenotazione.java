package cinemax;

import java.io.Serializable;
import java.util.UUID;

/**
 * Rappresenta la prenotazione di biglietti effettuata da un Cliente per una specifica Proiezione.
 */
public class Prenotazione implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String codicePrenotazione; 
    private String usernameCliente; 
    private String titoloFilm; 
    private String dataOraStringa; // Per semplicità teniamo un riferimento testuale all'orario
    private int numeroPosti;
    private double costoTotale;

    /**
     * Costruttore completo. Genera automaticamente un codice univoco.
     */
    public Prenotazione(String usernameCliente, String titoloFilm, String dataOraStringa, int numeroPosti, double costoSingolo) {
        // Genera un codice alfanumerico univoco di 8 caratteri
        this.codicePrenotazione = UUID.randomUUID().toString().substring(0, 8).toUpperCase(); 
        this.usernameCliente = usernameCliente;
        this.titoloFilm = titoloFilm;
        this.dataOraStringa = dataOraStringa;
        this.numeroPosti = numeroPosti;
        this.costoTotale = numeroPosti * costoSingolo;
    }

    // --- GETTER E SETTER ---

    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public void setCodicePrenotazione(String codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    public String getUsernameCliente() {
        return usernameCliente;
    }

    public void setUsernameCliente(String usernameCliente) {
        this.usernameCliente = usernameCliente;
    }

    public String getTitoloFilm() {
        return titoloFilm;
    }

    public void setTitoloFilm(String titoloFilm) {
        this.titoloFilm = titoloFilm;
    }

    public String getDataOraStringa() {
        return dataOraStringa;
    }

    public void setDataOraStringa(String dataOraStringa) {
        this.dataOraStringa = dataOraStringa;
    }

    public int getNumeroPosti() {
        return numeroPosti;
    }

    public void setNumeroPosti(int numeroPosti) {
        this.numeroPosti = numeroPosti;
        // Aggiorniamo il costo se i posti cambiano (richiede di passare di nuovo il costo singolo)
    }

    public double getCostoTotale() {
        return costoTotale;
    }

    public void setCostoTotale(double costoTotale) {
        this.costoTotale = costoTotale;
    }

    @Override
    public String toString() {
        return "Prenotazione " + codicePrenotazione + ": " + titoloFilm + " il " + dataOraStringa + " - Posti: " + numeroPosti + " (" + costoTotale + "€)";
    }
}