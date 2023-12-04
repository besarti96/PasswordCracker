import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Geben Sie das Zielwort ein: ");
        String inputWord = scanner.nextLine();

        EfficientWordGuesser guesser = new EfficientWordGuesser();
        guesser.setTargetWord(inputWord);
        guesser.guessWord();

        scanner.close();
    }
}