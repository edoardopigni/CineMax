package cinemax;

import java.io.Serializable;
import java.util.UUID;

/**
 * Rappresenta la prenotazione di biglietti effettuata da un Cliente per una specifica Proiezione.
 * <p>
 * Questa classe traccia l'associazione tra un utente, uno spettacolo cinematografico e i posti riservati,
 * calcolando il costo totale e generando un codice identificativo univoco per il ritiro dei biglietti.
 * Implementa {@link Serializable} per il salvataggio persistente dello storico prenotazioni.
 * </p>
 * 
 * @author Daniele Paoli
 * @author Edoardo Pigni
 * @author Anes Khaia
 * @version 1.0
 */
public class Prenotazione implements Serializable {
    
    /**
     * Identificativo univoco di versione per la serializzazione degli oggetti.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Codice identificativo alfanumerico univoco della prenotazione.
     */
    private String codicePrenotazione; 

    /**
     * L'identificativo (username) del cliente che ha effettuato l'acquisto.
     */
    private String usernameCliente; 

    /**
     * Il titolo del film per il quale sono stati prenotati i posti.
     */
    private String titoloFilm; 

    /**
     * La data e l'ora della proiezione, memorizzata in formato testuale per semplicità di visualizzazione.
     */
    private String dataOraStringa; 

    /**
     * Il quantitativo di posti riservati in questa transazione.
     */
    private int numeroPosti;

    /**
     * Il prezzo complessivo della prenotazione calcolato al momento della creazione.
     */
    private double costoTotale;

    /**
     * Costruttore per creare una nuova prenotazione e calcolarne il costo totale.
     * <p>
     * Durante l'inizializzazione, il sistema genera in automatico un codice alfanumerico
     * univoco di 8 caratteri utilizzando la classe {@link UUID}.
     * </p>
     *
     * @param usernameCliente l'identificativo dell'utente che prenota
     * @param titoloFilm      il titolo dello spettacolo scelto
     * @param dataOraStringa  l'orario della proiezione in formato stringa
     * @param numeroPosti     la quantità di biglietti richiesti
     * @param costoSingolo    il prezzo unitario del singolo biglietto per quella proiezione
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

    /**
     * Restituisce il codice univoco della prenotazione.
     * 
     * @return la stringa contenente il codice alfanumerico a 8 caratteri
     */
    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    /**
     * Imposta o sovrascrive il codice univoco della prenotazione.
     * 
     * @param codicePrenotazione il nuovo codice da assegnare
     */
    public void setCodicePrenotazione(String codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    /**
     * Restituisce lo username del cliente titolare della prenotazione.
     * 
     * @return l'identificativo del cliente
     */
    public String getUsernameCliente() {
        return usernameCliente;
    }

    /**
     * Imposta lo username del cliente titolare della prenotazione.
     * 
     * @param usernameCliente il nuovo identificativo del cliente
     */
    public void setUsernameCliente(String usernameCliente) {
        this.usernameCliente = usernameCliente;
    }

    /**
     * Restituisce il titolo del film prenotato.
     * 
     * @return il titolo dello spettacolo
     */
    public String getTitoloFilm() {
        return titoloFilm;
    }

    /**
     * Imposta il titolo del film prenotato.
     * 
     * @param titoloFilm il nuovo titolo dello spettacolo
     */
    public void setTitoloFilm(String titoloFilm) {
        this.titoloFilm = titoloFilm;
    }

    /**
     * Restituisce la data e l'ora della proiezione in formato testuale.
     * 
     * @return la stringa rappresentante data e ora
     */
    public String getDataOraStringa() {
        return dataOraStringa;
    }

    /**
     * Imposta la data e l'ora della proiezione.
     * 
     * @param dataOraStringa la nuova data e ora in formato testuale
     */
    public void setDataOraStringa(String dataOraStringa) {
        this.dataOraStringa = dataOraStringa;
    }

    /**
     * Restituisce il numero totale di posti riservati.
     * 
     * @return la quantità di biglietti associati alla prenotazione
     */
    public int getNumeroPosti() {
        return numeroPosti;
    }

    /**
     * Aggiorna il numero di posti riservati.
     * <p>
     * Attenzione: questa operazione non ricalcola automaticamente il costo totale.
     * </p>
     * 
     * @param numeroPosti la nuova quantità di biglietti
     */
    public void setNumeroPosti(int numeroPosti) {
        this.numeroPosti = numeroPosti;
        // Aggiorniamo il costo se i posti cambiano (richiede di passare di nuovo il costo singolo)
    }

    /**
     * Restituisce l'importo totale della transazione.
     * 
     * @return il costo complessivo in Euro
     */
    public double getCostoTotale() {
        return costoTotale;
    }

    /**
     * Imposta l'importo totale della transazione.
     * 
     * @param costoTotale il nuovo costo complessivo
     */
    public void setCostoTotale(double costoTotale) {
        this.costoTotale = costoTotale;
    }

    /**
     * Genera una rappresentazione testuale formattata della prenotazione.
     * 
     * @return una stringa riassuntiva nel formato 
     *         {@code "Prenotazione [Codice]: [Titolo] il [DataOra] - Posti: [Num] ([Costo]€)"}
     */
    @Override
    public String toString() {
        return "Prenotazione " + codicePrenotazione + ": " + titoloFilm + " il " + dataOraStringa + " - Posti: " + numeroPosti + " (" + costoTotale + "€)";
    }
}