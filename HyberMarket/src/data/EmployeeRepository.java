package data;
import models.User;
import models.UserRole;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class EmployeeRepository {
    private final String FILE_PATH = "employees.txt";
    public List<User> load() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Warning: " + FILE_PATH + " not found. Please create it.");
            return users;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length >= 5) { 
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String email = data[2].trim();
                    String password = data[3].trim();
                    UserRole role = UserRole.valueOf(data[4].trim());
                    LocalDateTime lastLogin = null;
                    if (data.length > 5 && !data[5].trim().equals("null")) {
                        try {
                            lastLogin = LocalDateTime.parse(data[5].trim());
                        } catch (Exception e) {
                            System.out.println("Could not parse date for user: " + name);
                        }
                    }
                    users.add(new User(id, name, email, password, role, lastLogin));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing Enum (Check your text file for typos in Roles): " + e.getMessage());
        }
        return users;
    }
    public void save(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                String dateStr = (u.getLastLogin() == null) ? "null" : u.getLastLogin().toString();               
                String line = String.join(",",u.getId(),u.getName(),u.getEmail(),u.getPassword(),u.getRole().toString(),dateStr);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public User getUserById(String id){

    }
}
