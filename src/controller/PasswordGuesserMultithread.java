package controller;

import java.util.concurrent.atomic.AtomicBoolean;

public class PasswordGuesserMultithread implements Runnable {
    private final BruteForceAttacke guesser;
    private final LoginService loginService;
    private final String name;
    private final AtomicBoolean success;

    public PasswordGuesserMultithread(String pin, LoginService loginService, String name, AtomicBoolean success) {
        this.guesser = new BruteForceAttacke(pin);
        this.loginService = loginService;
        this.name = name;
        this.success = success;
    }

    @Override
    public void run() {
        while (!success.get()) {
            String guessedPin = guesser.guessWord();
            if (loginService.login(name, guessedPin)) {
                success.set(true);
                System.out.println("Passwort erfolgreich erraten von " + Thread.currentThread().getName() + ": " + guessedPin);
                System.out.println("Aktueller Versuch: " + guessedPin);
                for (Thread t : Thread.getAllStackTraces().keySet()) {
                    if (t != Thread.currentThread() && t.getState() == Thread.State.RUNNABLE)
                        t.interrupt();
                }
                break;
            }
            if (Thread.interrupted()) {
                // Reinigung, falls nötig.
                return;
            }
        }
    }
}
