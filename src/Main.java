import controller.LoginService;
import controller.PasswordGuesserMultithread;
import model.User;
import model.UserDatabase;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        // Initialisierung der Benutzerdatenbank und des Anmeldedienstes.
        UserDatabase userDatabase = new UserDatabase("src/users.csv");
        LoginService loginService = new LoginService(userDatabase);
        Scanner sc = new Scanner(System.in);

        // Eingabeaufforderung für den Benutzernamen.
        System.out.println("Bitte geben Sie Ihren Namen ein:");
        String name = sc.nextLine();

        // Abrufen des Benutzerobjekts aus der Datenbank.
        User user = userDatabase.getUser(name);
        if (user == null) {
            System.out.println("Benutzer nicht gefunden.");
            sc.close();
            return;
        }

        // Verwenden von AtomicBoolean, um den Erfolg des Passworterratens zwischen Threads zu synchronisieren.
        AtomicBoolean success = new AtomicBoolean(false);
        // Ermitteln der Anzahl der zu verwendenden Threads basierend auf verfügbaren Prozessorkernen.
        int numberOfThreads = Runtime.getRuntime().availableProcessors();

        // Erstellen und Starten eines Passwort-Rateverfahrens in mehreren Threads.
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new PasswordGuesserMultithread(user.getPin(), loginService, name, success));
            thread.start();
        }

        // Warten auf den Erfolg eines der Rate-Threads.
        while (!success.get()) {
            try {
                // Reduzieren der CPU-Auslastung, indem das Hauptprogramm regelmäßig schläft.
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Falls das Hauptthread unterbrochen wird, setzen wir das Unterbrechungszeichen und geben eine Meldung aus.
                Thread.currentThread().interrupt();
                System.out.println("Thread wurde unterbrochen");
            }
        }

        // Nach erfolgreicher Anmeldung wird eine Nachricht ausgegeben und der Scanner geschlossen.
        System.out.println("Anmeldung in einem der Threads erfolgreich!");
        sc.close();
    }
}