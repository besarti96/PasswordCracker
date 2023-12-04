public class LoginService {
    private UserDatabase userDatabase;

    public LoginService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public boolean login(String name, String pin) {
        User user = userDatabase.getUser(name);
        if (user != null && user.getPin().equals(pin)) {
            System.out.println("Anmeldung erfolgreich! Ihre Nachricht: " + user.getMessage());
            return true;
        } else {
            System.out.println("Falscher Name oder PIN.");
            return false;
        }
    }
}
