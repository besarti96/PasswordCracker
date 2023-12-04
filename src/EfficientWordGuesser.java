import java.util.Random;

public class EfficientWordGuesser {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz ";
    private String targetWord;
    private Random random;

    public EfficientWordGuesser() {
        this.random = new Random();
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public void guessWord() {
        long startTime = System.currentTimeMillis();
        StringBuilder generatedString = new StringBuilder();
        boolean wordFound = false;
        int charCount = 0;
        final int displayLength = 10; // Maximale Anzeigelänge

        while (!wordFound) {
            char randomChar = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
            generatedString.append(randomChar);
            charCount++;

            if (generatedString.length() > displayLength) {
                generatedString.delete(0, generatedString.length() - displayLength);
            }

            System.out.print("\r" + generatedString); // Zeigt die letzten 10 Zeichen an

            if (generatedString.length() >= targetWord.length() &&
                    generatedString.substring(generatedString.length() - targetWord.length()).equals(targetWord)) {
                wordFound = true;
            }
        }

        long endTime = System.currentTimeMillis();
        long durationMillis = endTime - startTime;
        long durationSeconds = durationMillis / 1000;
        long minutes = durationSeconds / 60;
        long seconds = durationSeconds % 60;

        System.out.println("\nWORT GEFUNDEN: " + targetWord);
        System.out.println("ZEIT: " + minutes + " Minuten und " + seconds + " Sekunden");
        System.out.println("ZEICHENANZAHL: " + charCount);
    }
}