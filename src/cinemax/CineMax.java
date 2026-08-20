package cinemax;

import java.time.format.DateTimeFormatter;
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
            
            String scelta = scanner.nextLine().trim();
            
            switch (scelta) {
            case "1":
                System.out.println("\n--- NAVIGAZIONE GUEST ---");
                System.out.print("Inserisci un titolo, un genere o un regista da cercare: ");
                String ricerca = scanner.nextLine().trim();
                
                List<Proiezione> risultati = db.ricercaProiezioni(ricerca);
                
                if (risultati.isEmpty()) {
                    System.out.println("Nessun film trovato con la parola: '" + ricerca + "'");
                } else {
                    System.out.println("\n--- RISULTATI TROVATI (" + risultati.size() + ") ---");
                    for (Proiezione p : risultati) {
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
                    
                    switch (utenteLoggato.getRuolo().toUpperCase()) {
                        case "CLIENTE":
                            menuCliente(scanner, db, utenteLoggato);
                            break;
                        case "PROIEZIONISTA":
                            menuProiezionista(scanner, db, utenteLoggato);
                            break;
                        case "BIGLIETTAIO":
                            menuBigliettaio(scanner, db, utenteLoggato);
                            break;
                        default:
                            System.out.println("Errore: Ruolo non riconosciuto nel sistema.");
                    }
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
                    
                    Utente nuovoCliente = new Utente(nome, cognome, username, password, dataNascita, domicilio, "CLIENTE");
                    
                    // REFACTORING: Uso del metodo sicuro per l'aggiunta
                    db.aggiungiUtente(nuovoCliente);
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

    /**
     * Menu dedicato alle operazioni del Cliente
     */
    private static void menuCliente(Scanner scanner, GestoreDati db, Utente cliente) {
        boolean inAreaRiservata = true;
        
        while (inAreaRiservata) {
            System.out.println("\n=== AREA CLIENTE: " + cliente.getUsername() + " ===");
            System.out.println("1. Visualizza il palinsesto e prenota");
            System.out.println("2. I miei biglietti");
            System.out.println("0. Logout");
            System.out.print("Scegli un'opzione: ");
            
            String scelta = scanner.nextLine().trim();
            
            switch (scelta) {
            case "1":
                System.out.println("\n--- PALINSESTO E PRENOTAZIONE ---");
                // REFACTORING: Uso del getter
                if (db.getListaProiezioni().isEmpty()) {
                    System.out.println("Nessuna proiezione disponibile al momento.");
                    break;
                }

                // REFACTORING: Uso del getter
                for (int i = 0; i < db.getListaProiezioni().size(); i++) {
                    Proiezione p = db.getListaProiezioni().get(i);
                    int postiDisp = db.calcolaPostiDisponibili(p);
                    System.out.println((i + 1) + ". " + p.toString() + " [Posti disponibili: " + postiDisp + "]");
                }

                System.out.print("\nInserisci il numero del film che vuoi prenotare (0 per annullare): ");
                int sceltaFilm = 0;
                try {
                    sceltaFilm = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Errore: devi inserire un numero intero.");
                    break;
                }

                if (sceltaFilm == 0) break;
                
                // REFACTORING: Uso del getter
                if (sceltaFilm < 1 || sceltaFilm > db.getListaProiezioni().size()) {
                    System.out.println("Scelta non valida.");
                    break;
                }

                // REFACTORING: Uso del getter
                Proiezione filmScelto = db.getListaProiezioni().get(sceltaFilm - 1);
                int postiDisponibili = db.calcolaPostiDisponibili(filmScelto);

                System.out.print("Quanti posti vuoi prenotare? ");
                int numPosti = 0;
                try {
                    numPosti = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Errore: devi inserire un numero intero.");
                    break;
                }

                if (numPosti <= 0) {
                    System.out.println("Devi prenotare almeno un posto.");
                } else if (numPosti > postiDisponibili) {
                    System.out.println("Spiacenti, per questo film ci sono solo " + postiDisponibili + " posti disponibili.");
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    String dataOraStr = filmScelto.getDataOraProiezione().format(formatter);
                    
                    Prenotazione nuovaPren = new Prenotazione(
                            cliente.getUsername(), 
                            filmScelto.getTitoloFilm(), 
                            dataOraStr, 
                            numPosti, 
                            filmScelto.getPrezzoBiglietto()
                    );
                    
                    // REFACTORING: Uso dei metodi sicuri e salvataggio sbloccato
                    db.aggiungiPrenotazione(nuovaPren);
                    db.salvaPrenotazioni(); 
                    
                    System.out.println("\n✅ Prenotazione confermata con successo!");
                    System.out.println("Riepilogo: " + nuovaPren.toString());
                }
                System.out.println("---------------------------------\n");
                break;
            case "2":
                System.out.println("\n--- I MIEI BIGLIETTI ---");
                boolean trovati = false;
                
                // REFACTORING: Uso del getter
                for (Prenotazione p : db.getListaPrenotazioni()) {
                    if (p.getUsernameCliente().equals(cliente.getUsername())) {
                        System.out.println(p.toString());
                        trovati = true;
                    }
                }
                
                if (!trovati) {
                    System.out.println("Non hai ancora effettuato nessuna prenotazione.");
                }
                System.out.println("------------------------\n");
                break;
                case "0":
                    System.out.println("\nLogout effettuato con successo.");
                    inAreaRiservata = false; 
                    break;
                default:
                    System.out.println("\nScelta non valida. Riprova.");
            }
        }
    }
    
    /**
     * Menu dedicato al Proiezionista (Task assegnato ad Anes)
     */
    private static void menuProiezionista(Scanner scanner, GestoreDati db, Utente proiezionista) {
        System.out.println("\n--- AREA PROIEZIONISTA ---");
        System.out.println("Accesso consentito a: " + proiezionista.getNome());
        System.out.println("Backend in fase di sviluppo. Ritorno al menu principale...");
    }
    
    /**
     * Menu dedicato al Bigliettaio (Task assegnato ad Anes)
     */
    private static void menuBigliettaio(Scanner scanner, GestoreDati db, Utente bigliettaio) {
        System.out.println("\n--- AREA BIGLIETTAIO ---");
        System.out.println("Accesso consentito a: " + bigliettaio.getNome());
        System.out.println("Backend in fase di sviluppo. Ritorno al menu principale...");
    }
}