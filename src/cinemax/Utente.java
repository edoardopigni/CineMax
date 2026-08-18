package cinemax;

import java.io.Serializable;

/**
 * Rappresenta un utente registrato nel sistema CineMax.
 * Può essere un Cliente, un Proiezionista o un Bigliettaio.
 */
public class Utente implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String dataNascita; // Resa Stringa per semplicità (opzionale)
    private String luogoDomicilio;
    private String ruolo; 
    
    /**
     * Costruttore completo per la creazione di un nuovo Utente.
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoDomicilio() {
        return luogoDomicilio;
    }

    public void setLuogoDomicilio(String luogoDomicilio) {
        this.luogoDomicilio = luogoDomicilio;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return "Utente [" + username + "] - Ruolo: " + ruolo;
    }
}