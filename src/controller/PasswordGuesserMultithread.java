package controller;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Eine Runnable-Implementierung, die für das Erraten des Passworts in einem separaten Thread verwendet wird.
 */
public class PasswordGuesserMultithread implements Runnable {
    // Felder, die für das Passwort-Rateverfahren benötigt werden.
    private final BruteForceAttacke guesser;
    private final LoginService loginService;
    private final String name;
    private final AtomicBoolean success;

    /**
     * Konstruktor, der die benötigten Felder initialisiert.
     *
     * @param pin          Die zu erratende PIN.
     * @param loginService Der Dienst, der die Logik für die Anmeldung enthält.
     * @param name         Der Name des Benutzers.
     * @param success      Eine AtomicBoolean-Referenz, um den Erfolg des Rateverfahrens zu verfolgen.
     */
    public PasswordGuesserMultithread(String pin, LoginService loginService, String name, AtomicBoolean success) {
        this.guesser = new BruteForceAttacke(pin);
        this.loginService = loginService;
        this.name = name;
        this.success = success;
    }

    /**
     * Die run-Methode, die von jedem Thread ausgeführt wird, um das Passwort zu erraten.
     */
    @Override
    public void run() {
        // Solange das Passwort nicht erraten wurde, versucht der Thread weiterhin, das Passwort zu erraten.
        while (!success.get()) {
            String guessedPin = guesser.guessWord();
            // Wenn der Anmeldeversuch erfolgreich ist, wird success auf true gesetzt.
            if (loginService.login(name, guessedPin)) {
                success.set(true);
                // Eine Erfolgsmeldung wird ausgegeben.
                System.out.println(Thread.currentThread().getName() + " hat die Anmeldung erfolgreich!");
                break;
            }
        }
    }
}