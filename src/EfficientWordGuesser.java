import java.util.Random;

public class EfficientWordGuesser {
    private static final String ALPHABET = "0123456789"; // Angenommen, der PIN besteht aus Buchstaben und Zahlen
    private String targetWord;
    private Random random;

    public EfficientWordGuesser() {
        this.random = new Random();
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public String guessWord() {
        StringBuilder generatedString = new StringBuilder();
        boolean wordFound = false;

        while (!wordFound) {
            char randomChar = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
            generatedString.append(randomChar);

            if (generatedString.length() > targetWord.length()) {
                generatedString.delete(0, generatedString.length() - targetWord.length());
            }

            System.out.print("\r" + generatedString); // Zeigt den aktuellen Versuch an

            if (generatedString.toString().equals(targetWord)) {
                wordFound = true;
            }
        }

        System.out.println("\nPIN gefunden: " + targetWord);
        return generatedString.toString();
    }
}
