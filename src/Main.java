import EigenKlasse.BruteForceAttacke;
import controller.LoginService;
import model.User;
import model.UserDatabase;

import java.util.Scanner;

public class Main {

    /**
     * Hauptmethode, die den Startpunkt der Anwendung darstellt.
     * Initialisiert die Datenbank, den Login-Service und verarbeitet die Benutzerinteraktion.
     *
     * @param args Argumente der Kommandozeile, die nicht verwendet werden.
     */
    public static void main(String[] args) {
        // Initialisierung der User-Datenbank und des Login-Services.
        UserDatabase userDatabase = new UserDatabase("src/users.csv");
        LoginService loginService = new LoginService(userDatabase);
        Scanner sc = new Scanner(System.in);

        // Auswahlmenü für den Benutzer.
        System.out.println("Wählen Sie eine Option: \n1. Normaler Login\n2. Passwort-Knacker starten");
        int choice = sc.nextInt();
        sc.nextLine(); // Verarbeitung des Zeilenumbruchs nach der Eingabe.

        // Auswahlverarbeitung: Normaler Login oder Passwort-Knacker.
        if (choice == 1) {
            normalLoginProcess(sc, loginService);
        } else if (choice == 2) {
            passwordCrackerProcess(sc, userDatabase, loginService);
        } else {
            System.out.println("Ungültige Auswahl.");
        }

        // Schließt den Scanner.
        sc.close();
    }

    /**
     * Methode zur Durchführung des normalen Login-Prozesses.
     * Fragt den Benutzer nach Benutzername und PIN und führt eine Login-Überprüfung durch.
     *
     * @param sc Der Scanner zur Erfassung der Benutzereingaben.
     * @param loginService Der Service, der die Login-Funktionalität bereitstellt.
     */

    // Methode für den normalen Login-Prozess.
    private static void normalLoginProcess(Scanner sc, LoginService loginService) {
        System.out.println("Bitte geben Sie Ihren Namen ein:");
        String name = sc.nextLine();

        System.out.println("Bitte geben Sie Ihren PIN ein:");
        String pin = sc.nextLine();

        // Überprüft die Anmeldedaten.
        if (loginService.login(name, pin)) {
            System.out.println("Anmeldung erfolgreich!");
        } else {
            System.out.println("Anmeldung fehlgeschlagen.");
        }
    }

    /**
     * Methode für den Prozess des Passwort-Knackens.
     * Leitet einen Brute-Force-Angriff ein und zeigt die Ergebnisse an.
     *
     * @param sc Der Scanner zur Erfassung der Benutzereingaben.
     * @param userDatabase Die Datenbank mit Benutzerinformationen.
     * @param loginService Der Service, der die Login-Funktionalität bereitstellt.
     */
    private static void passwordCrackerProcess(Scanner sc, UserDatabase userDatabase, LoginService loginService) {
        System.out.println("Bitte geben Sie den Namen des zu knackenden Benutzers ein:");
        String name = sc.nextLine();

        // Holt den Benutzer aus der Datenbank.
        User user = userDatabase.getUser(name);
        if (user == null) {
            System.out.println("Benutzer nicht gefunden.");
            return;
        }

        // Initialisiert den Brute-Force-Angriff mit der PIN des Benutzers.
        BruteForceAttacke guesser = new BruteForceAttacke(user.getPin());
        String result = guesser.guessWord();

        // Trenne die Rückgabeinformationen
        String[] parts = result.split(";");
        String guessedPin = parts[0];
        long elapsedTime = Long.parseLong(parts[1]);
        int attempts = Integer.parseInt(parts[2]);

        // Zeige die Statistiken an
        System.out.println("Erratene PIN: " + guessedPin);
        printStatistics(elapsedTime, attempts);
    }

    /**
     * Zeigt Statistiken über den Erratungsprozess an.
     *
     * @param elapsedTime Die verstrichene Zeit in Millisekunden.
     * @param attempts    Die Anzahl der Versuche.
     */
    private static void printStatistics(long elapsedTime, int attempts) {
        long seconds = (elapsedTime / 1000) % 60;
        long minutes = (elapsedTime / (1000 * 60)) % 60;

        System.out.println("Anzahl der Versuche: " + attempts);
        System.out.println("Verstrichene Zeit: " + minutes + " Minuten und " + seconds + " Sekunden");
    }
    }
