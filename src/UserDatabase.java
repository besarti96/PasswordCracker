import java.io.*;
import java.util.*;

public class UserDatabase {
    private Map<String, User> users = new HashMap<>();

    public UserDatabase(String filePath) {
        loadUsersFromCSV(filePath);
    }

    private void loadUsersFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                users.put(values[0], new User(values[0], values[2], values[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String name) {
        return users.get(name);
    }
}