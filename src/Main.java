import EigenKlasse.BruteForceAttacke;
import controller.LoginService;
import model.User;
import model.UserDatabase;

import java.util.Scanner;

public class Main {

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

    // Methode für den Prozess des Passwort-Knackens.
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
        boolean loginSuccess = false;

        // Führt den Brute-Force-Angriff durch, bis der Login erfolgreich ist.
        while (!loginSuccess) {
            String guessedPin = guesser.guessWord();
            loginSuccess = loginService.login(name, guessedPin);
        }

        System.out.println("Passwort-Knacker erfolgreich!");
    }
}
