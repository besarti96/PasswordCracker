import java.util.Scanner;

public class LoginApplication {
    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase("src/users.csv");  // Pfad anpassen
        LoginService loginService = new LoginService(userDatabase);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bitte geben Sie Ihren Namen ein:");
        String name = scanner.nextLine();

        System.out.println("Bitte geben Sie Ihren PIN ein:");
        String pin = scanner.nextLine();

        loginService.login(name, pin);
    }
}