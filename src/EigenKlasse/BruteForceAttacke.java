package EigenKlasse;

import java.util.Random;

/**
 * Klasse zur Durchführung einer Brute-Force-Attacke zum Erraten eines Zielwortes.
 * Verwendet zufällig generierte Zeichenfolgen, um das Zielwort zu erraten.
 */
public class BruteForceAttacke {
    /* Zeichen fuer die Generierung der Zeichenfolgen. */
    private static final String Zeichen = "abcdefghijklmnopqrstuvwxyz0123456789";

    // Das zu erratende Zielwort.
    private final String targetWord;

    // Hilfsobjekt zur Erzeugung zufälliger Werte wird im Konstruktor initialisiert.
    private final Random random;

    // Startzeit der Attacke für die Zeitmessung.
    private final long startTime;

    // Zähler für die Anzahl der Versuche.++
    private int attemptCount;

    /**
     * Konstruktor zur Initialisierung der Brute-Force-Attacke.
     * Setzt das Zielwort Startzeit und das hilfsobjekt random.
     *
     * @param targetWord Das zu erratende Zielwort.
     */
    public BruteForceAttacke(String targetWord) {
        this.targetWord = targetWord;
        this.startTime = System.currentTimeMillis();
        this.random = new Random(); // Initialisiert den Random-Generator.

    }

    /**
     * Versucht das Zielwort durch Erzeugen zufälliger Zeichenfolgen zu erraten.
     * Zählt Versuche und misst die benötigte Zeit.
     *
     * @return Die erratene Zeichenfolge, falls erfolgreich.
     */

    //methode:
    public String guessWord() {
        // Definiert minimale und maximale Länge der zu generierenden Zeichenfolgen.
        final int minPinLength = 4;
        final int maxPinLength = 6;

        while (true) {
            // Erzeugt eine zufällige Zeichenfolge der definierten Länge.
            final int pinLength = random.nextInt(maxPinLength - minPinLength + 1) + minPinLength;
            //StringBuilder für den Aufbau der PIN.
            StringBuilder generatedString = new StringBuilder();

            //Eine Schleife, die für jede Stelle der PIN ein zufälliges Zeichen aus dem ALPHABET wählt und an den StringBuilder anhängt.
            for (int i = 0; i < pinLength; i++) {
                final char randomChar = Zeichen.charAt(random.nextInt(Zeichen.length()));
                generatedString.append(randomChar);
            }

            // Zählt den aktuellen Versuch.
            attemptCount++;

            // Aktualisiert die Konsole mit dem aktuellen Versuch.
            clearConsole();

            System.out.print("\rAktueller Versuch: " + generatedString);

            // Überprüft, ob das Zielwort erraten wurde.
            if (generatedString.toString().equals(targetWord)) {
                long endTime = System.currentTimeMillis();
                return generatedString + ";" + (endTime - startTime) + ";" + attemptCount;
            }
            }
        }


    //Methode 2 um die Konsole zzu Löschen cleanConsole();
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
