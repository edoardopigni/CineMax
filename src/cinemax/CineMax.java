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
                    String user = scanner.nextLine();
                    System.out.print("Password: ");
                    String pass = scanner.nextLine();
                    // TODO: Implementare verifica credenziali scansionando db.listaUtenti
                    System.out.println("Verifica in corso... (Funzionalità in costruzione)");
                    break;
                    
                case "3":
                    System.out.println("\n--- REGISTRAZIONE CLIENTE ---");
                    // TODO (Persona A): Chiedere dati in input, creare oggetto Utente e chiamare db.salvaUtenti()
                    System.out.println("Funzionalità in costruzione...");
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