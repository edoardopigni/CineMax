package cinemax;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principale contenente il metodo main per l'avvio dell'applicazione.
 */
public class CineMax {

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("   BENVENUTO IN CINEMAX 2026");
        System.out.println("=================================");
        
        // Inizializza il "database" caricando proiezioni e utenti
        GestoreDati db = new GestoreDati();
        db.inizializzaSistema();
        
        Scanner scanner = new Scanner(System.in);
        boolean inEsecuzione = true;

        // Loop del menu iniziale come da specifiche
        while (inEsecuzione) {
            System.out.println("\nSeleziona un'operazione:");
            System.out.println("1. Continua come Guest (Cerca proiezioni)");
            System.out.println("2. Accedi alla piattaforma (Login)");
            System.out.println("3. Registrati come nuovo Cliente");
            System.out.println("0. Esci dal programma");
            System.out.print("> ");
            
            String scelta = scanner.nextLine();
            
            switch (scelta) {
            case "1":
                System.out.println("\n--- NAVIGAZIONE GUEST ---");
                System.out.print("Inserisci un titolo, un genere o un regista da cercare: ");
                String ricerca = scanner.nextLine();
                
                // Richiamiamo il metodo creato nel GestoreDati (che si chiama 'db')
                List<Proiezione> risultati = db.ricercaProiezioni(ricerca);
                
                if (risultati.isEmpty()) {
                    System.out.println("Nessun film trovato con la parola: '" + ricerca + "'");
                } else {
                    System.out.println("\n--- RISULTATI TROVATI (" + risultati.size() + ") ---");
                    for (Proiezione p : risultati) {
                        // Sfruttiamo il metodo toString() già presente nella tua classe Proiezione
                        System.out.println(p.toString());
                    }
                }
                System.out.println("-------------------------\n");
                break;
                    
            case "2":
                System.out.println("\n--- LOGIN ---");
                System.out.print("Username: ");
                String user = scanner.nextLine().trim();
                System.out.print("Password: ");
                String pass = scanner.nextLine().trim();
                
                Utente utenteLoggato = db.verificaCredenziali(user, pass);
                
                if (utenteLoggato != null) {
                    System.out.println("\nLogin effettuato con successo! Bentornato, " + utenteLoggato.getNome() + ".");
                    System.out.println("Accesso eseguito come: " + utenteLoggato.getRuolo());
                    
                    // TODO: Qui più avanti metteremo uno switch per aprire il 
                    // menu specifico (Cliente, Proiezionista o Bigliettaio)
                    
                } else {
                    System.out.println("\nErrore: Username o Password non corretti. Riprova.");
                }
                System.out.println("-------------\n");
                break;
                    
                case "3":
                    System.out.println("\n--- REGISTRAZIONE NUOVO CLIENTE ---");
                    
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine().trim();
                    
                    System.out.print("Cognome: ");
                    String cognome = scanner.nextLine().trim();
                    
                    String username;
                    while (true) {
                        System.out.print("Username: ");
                        username = scanner.nextLine().trim();
                        if (username.isEmpty()) {
                            System.out.println("L'username non può essere vuoto.");
                        } else if (db.usernameGiaEsistente(username)) {
                            System.out.println("Username già in uso! Scegline un altro.");
                        } else {
                            break;
                        }
                    }
                    
                    String password;
                    while (true) {
                        System.out.print("Password: ");
                        password = scanner.nextLine().trim();
                        if (password.isEmpty()) {
                            System.out.println("La password non può essere vuota.");
                        } else {
                            break;
                        }
                    }
                    
                    System.out.print("Data di Nascita (es. GG/MM/AAAA): ");
                    String dataNascita = scanner.nextLine().trim();
                    
                    System.out.print("Luogo di Domicilio: ");
                    String domicilio = scanner.nextLine().trim();
                    
                    // Creazione del nuovo utente con ruolo CLIENTE
                    Utente nuovoCliente = new Utente(nome, cognome, username, password, dataNascita, domicilio, "CLIENTE");
                    
                    // Aggiunta alla lista e salvataggio su file
                    db.listaUtenti.add(nuovoCliente);
                    db.salvaUtenti();
                    
                    System.out.println("\nRegistrazione completata con successo! Benvenuto, " + username + ".");
                    System.out.println("-----------------------------------\n");
                    break;
                    
                case "0":
                    System.out.println("Uscita in corso. Grazie per aver usato CineMax!");
                    inEsecuzione = false;
                    break;
                    
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
        
        scanner.close();
    }
}