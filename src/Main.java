import controller.BruteForceAttacke;
import controller.LoginService;
import model.User;
import model.UserDatabase;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase("src/users.csv");
        LoginService loginService = new LoginService(userDatabase);
        Scanner sc = new Scanner(System.in);

        System.out.println("Wählen Sie eine Option: \n1. Normaler Login\n2. Passwort-Knacker starten");
        int choice = sc.nextInt();
        sc.nextLine(); // Zum Erfassen des Zeilenumbruchs nach der Zahleneingabe

        if (choice == 1) {
            normalLoginProcess(sc, loginService);
        } else if (choice == 2) {
            passwordCrackerProcess(sc, userDatabase, loginService);
        } else {
            System.out.println("Ungültige Auswahl.");
        }

        sc.close();
    }

    private static void normalLoginProcess(Scanner sc, LoginService loginService) {
        System.out.println("Bitte geben Sie Ihren Namen ein:");
        String name = sc.nextLine();

        System.out.println("Bitte geben Sie Ihren PIN ein:");
        String pin = sc.nextLine();

        if (loginService.login(name, pin)) {
            System.out.println("Anmeldung erfolgreich!");
        } else {
            System.out.println("Anmeldung fehlgeschlagen.");
        }
    }

    private static void passwordCrackerProcess(Scanner sc, UserDatabase userDatabase, LoginService loginService) {
        System.out.println("Bitte geben Sie den Namen des zu knackenden Benutzers ein:");
        String name = sc.nextLine();

        User user = userDatabase.getUser(name);
        if (user == null) {
            System.out.println("Benutzer nicht gefunden.");
            return;
        }

        BruteForceAttacke guesser = new BruteForceAttacke(user.getPin());
        boolean loginSuccess = false;

        while (!loginSuccess) {
            String guessedPin = guesser.guessWord();
            loginSuccess = loginService.login(name, guessedPin);
        }

        System.out.println("Passwort-Knacker erfolgreich!");
    }
}
