package cinemax;

import java.io.Serializable;

/**
 * Rappresenta un utente registrato all'interno della piattaforma CineMax.
 * <p>
 * La classe modella le informazioni anagrafiche, le credenziali di autenticazione
 * e il livello di autorizzazione (ruolo) all'interno dell'applicazione.
 * Implementa l'interfaccia {@link Serializable} per consentire la persistenza su file binario.
 * </p>
 * 
 * @author Daniele Paoli
 * @author Edoardo Pigni
 * @author Anes Khaia
 * @version 1.0
 */
public class Utente implements Serializable {
    
    /**
     * Identificativo univoco di versione per la serializzazione degli oggetti.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Nome anagrafico dell'utente.
     */
    private String nome;

    /**
     * Cognome anagrafico dell'utente.
     */
    private String cognome;

    /**
     * Identificativo univoco (username) utilizzato per l'accesso al sistema.
     */
    private String username;

    /**
     * Password associata all'account per l'autenticazione.
     */
    private String password;

    /**
     * Data di nascita dell'utente espressa in formato testuale (es. GG/MM/AAAA).
     */
    private String dataNascita;

    /**
     * Luogo o comune di residenza/domicilio dell'utente.
     */
    private String luogoDomicilio;

    /**
     * Ruolo applicativo assegnato (es. {@code "CLIENTE"}, {@code "PROIEZIONISTA"}, {@code "BIGLIETTAIO"}).
     */
    private String ruolo; 
    
    /**
     * Costruisce una nuova istanza di {@code Utente} specificando tutte le informazioni anagrafiche,
     * le credenziali di accesso e il profilo operativo.
     *
     * @param nome           il nome dell'utente
     * @param cognome        il cognome dell'utente
     * @param username       l'identificativo univoco di login
     * @param password       la chiave di accesso per il login
     * @param dataNascita    la data di nascita in formato stringa
     * @param luogoDomicilio il comune di domicilio
     * @param ruolo          il profilo o ruolo nel sistema (Cliente, Proiezionista o Bigliettaio)
     */
    public Utente(String nome, String cognome, String username, String password, String dataNascita, String luogoDomicilio, String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.dataNascita = dataNascita;
        this.luogoDomicilio = luogoDomicilio;
        this.ruolo = ruolo;
    }

    // --- GETTER E SETTER ---

    /**
     * Restituisce il nome dell'utente.
     * 
     * @return il nome anagrafico
     */
    public String getNome() {
        return nome;
    }

    /**
     * Aggiorna il nome dell'utente.
     * 
     * @param nome il nuovo nome da impostare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome dell'utente.
     * 
     * @return il cognome anagrafico
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Aggiorna il cognome dell'utente.
     * 
     * @param cognome il nuovo cognome da impostare
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce lo username dell'account.
     * 
     * @return lo username univoco
     */
    public String getUsername() {
        return username;
    }

    /**
     * Aggiorna lo username dell'account.
     * 
     * @param username il nuovo username da impostare
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Restituisce la password associata all'account.
     * 
     * @return la stringa contenente la password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Aggiorna la password dell'account.
     * 
     * @param password la nuova password da impostare
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce la data di nascita dell'utente.
     * 
     * @return la data di nascita formattata
     */
    public String getDataNascita() {
        return dataNascita;
    }

    /**
     * Aggiorna la data di nascita dell'utente.
     * 
     * @param dataNascita la nuova data di nascita in formato stringa
     */
    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     * Restituisce il comune di domicilio dell'utente.
     * 
     * @return il luogo di domicilio
     */
    public String getLuogoDomicilio() {
        return luogoDomicilio;
    }

    /**
     * Aggiorna il luogo di domicilio dell'utente.
     * 
     * @param luogoDomicilio il nuovo domicilio da impostare
     */
    public void setLuogoDomicilio(String luogoDomicilio) {
        this.luogoDomicilio = luogoDomicilio;
    }

    /**
     * Restituisce il ruolo applicativo dell'utente.
     * 
     * @return il ruolo assegnato (es. CLIENTE, PROIEZIONISTA, BIGLIETTAIO)
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il ruolo applicativo dell'utente.
     * 
     * @param ruolo il nuovo ruolo da assegnare
     */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    /**
     * Restituisce una rappresentazione testuale sintetica dell'utente comprensiva di username e ruolo.
     * 
     * @return una stringa nel formato {@code "Utente [username] - Ruolo: [ruolo]"}
     */
    @Override
    public String toString() {
        return "Utente [" + username + "] - Ruolo: " + ruolo;
    }
}