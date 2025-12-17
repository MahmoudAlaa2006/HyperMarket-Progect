package data;

import models.User;
import models.UserRole;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<User>{

    private final String FILE_PATH = "employees.txt";

    @Override
    public List<User> load() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return users;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                User user = new User(
                        data[0],                       // id
                        data[1],                       // name
                        data[2],                       // email
                        data[3],                       // password
                        UserRole.valueOf(data[4])      // role
                );

                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void save(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                pw.println(
                        u.getId() + "|" +
                                u.getName() + "|" +
                                u.getEmail() + "|" +
                                u.getPassword() + "|" +
                                u.getUserRole()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
