import controller.EfficientWordGuesser;
import controller.LoginService;
import model.User;
import model.UserDatabase;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase("src/users.csv");
        LoginService loginService = new LoginService(userDatabase);
        Scanner sc = new Scanner(System.in);

        System.out.println("Bitte geben Sie Ihren Namen ein:");
        String name = sc.nextLine();

        User user = userDatabase.getUser(name);
        if (user == null) {
            System.out.println("Benutzer nicht gefunden.");
            return;
        }

        // Die Zeile, die guesser.setTargetWord(user.getPin()) verwendet hat, wird entfernt
        // und der Konstruktor von EfficientWordGuesser wird nun mit dem Zielwort aufgerufen.
        EfficientWordGuesser guesser = new EfficientWordGuesser(user.getPin());

        boolean loginSuccess = false;

        while (!loginSuccess) {
            String guessedPin = guesser.guessWord();
            loginSuccess = loginService.login(name, guessedPin);
        }

        System.out.println("Anmeldung erfolgreich!");
        sc.close();
    }
}
