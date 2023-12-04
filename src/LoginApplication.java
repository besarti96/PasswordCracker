import java.util.Scanner;

public class LoginApplication {
    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase("src/users.csv");
        LoginService loginService = new LoginService(userDatabase);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bitte geben Sie Ihren Namen ein:");
        String name = scanner.nextLine();
        boolean loginSuccess = false;

        while (!loginSuccess) {
            System.out.println("Bitte geben Sie Ihren PIN ein:");
            String pin = scanner.nextLine();
            loginSuccess = loginService.login(name, pin);
            if (!loginSuccess) {
                System.out.println("Falscher PIN. Bitte versuchen Sie es erneut.");
            }
        }

        System.out.println("Anmeldung erfolgreich!");
        scanner.close();
    }
}
