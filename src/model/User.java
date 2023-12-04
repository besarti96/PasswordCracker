package model;

public class User {
    private String name;
    private String pin;
    private String message;

    public User(String name, String pin, String message) {
        this.name = name;
        this.pin = pin;
        this.message = message;
    }

    // Getter-Methoden
    public String getPin() {
        return pin;
    }

    public String getMessage() {
        return message;
    }
}