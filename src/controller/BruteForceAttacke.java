package controller;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Diese Klasse ist dafür verantwortlich, ein Password (in diesem Fall eine PIN) zu erraten.
 * Sie verwendet eine einfache Zufallslogik, um die PIN basierend auf einem vorgegebenen Alphabet zu generieren.
 * Die Klasse hält auch die Anzahl der Versuche und die Zeit, die für das Erraten benötigt wurde.
 */
public class BruteForceAttacke {
    // Konstante für das verwendete Alphabet. Da es als 'final' deklariert ist, ist es unveränderlich nach der Zuweisung.
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";
    // Zielwort, das erraten werden soll. Es wird durch den Konstruktor final gesetzt und kann nicht geändert werden.
    private final String targetWord;
    // Random-Objekt für die Erzeugung zufälliger Werte. Es ist final, weil es nach der Instanziierung der Klasse nicht mehr geändert wird.
    private final Random random = new Random();
    // Eine Warteschlange, um die jüngsten Vermutungen zu speichern. Es ist final, da es nicht mehr geändert werden soll.
    private final Queue<String> recentGuesses = new LinkedList<>();
    // Felder, die die Laufzeit und Anzahl der Versuche berechnen. Nicht final, da sie während der Laufzeit geändert werden.
    private long startTime;
    private int attemptCount;

    /**
     * Konstruktor, der das Zielwort initialisiert und die Startzeit setzt.
     *
     * @param targetWord Das zu erratende Zielwort.
     */
    public BruteForceAttacke(String targetWord) {
        this.targetWord = targetWord;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Diese Methode generiert eine zufällige Zeichenfolge, die als Vermutung für das Zielwort verwendet wird.
     * Es wird auch die Anzahl der Versuche verfolgt und die verstrichene Zeit berechnet.
     *
     * @return Die generierte Zeichenfolge.
     */
    public String guessWord() {
        int minPinLength = 4;
        int maxPinLength = 6;

        while (true) {
            int pinLength = random.nextInt(maxPinLength - minPinLength + 1) + minPinLength;
            StringBuilder generatedString = new StringBuilder();

            for (int i = 0; i < pinLength; i++) {
                char randomChar = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
                generatedString.append(randomChar);
            }

            recentGuesses.add(generatedString.toString());

            if (recentGuesses.size() > 10) {
                recentGuesses.poll();
            }

            attemptCount++; // Zählt die Anzahl der Versuche.

            clearConsole();
            System.out.print("\rAktueller Versuch: " + generatedString);

            // Beendet die Schleife, wenn das Zielwort erraten wurde
            if (generatedString.toString().equals(targetWord)) {
                long endTime = System.currentTimeMillis();
                printStatistics(endTime - startTime, attemptCount);
                return generatedString.toString();
            }
        }
    }

    /**
     * Löscht die Konsole für eine saubere Ausgabe.
     */
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Druckt Statistiken über den Erratungsprozess.
     *
     * @param elapsedTime Die verstrichene Zeit in Millisekunden.
     * @param attempts    Die Anzahl der Versuche.
     */
    private void printStatistics(long elapsedTime, int attempts) {
        int seconds = (int) (elapsedTime / 1000) % 60 ;
        int minutes = (int) ((elapsedTime / (1000*60)) % 60);

        System.out.println("\nAnzahl der Versuche: " + attempts);
        System.out.println("Verstrichene Zeit: " + minutes + " Minuten und " + seconds + " Sekunden");
    }
}
