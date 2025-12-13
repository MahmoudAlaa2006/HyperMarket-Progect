package services;

import models.User;
import models.UserRole;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

import static utils.IDGenerator.generateUserId;
import static utils.PasswordGenerator.generatePassword;

public class AdminServices extends UserServices {

    public User addEmployee(String name, String email, UserRole role){
        String id = generateUserId();
        String password = generatePassword();
        User newUser = new User(id, name, email, password, role);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("employees.txt", true))) {
            String line = id + "," + name + "," + email + "," + password + "," + role.toString();
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUser;
    }

    public void deleteEmployee(String employeeId){
        List<User> employees = listAllEmployees();
        employees.removeIf(user -> user.getId().equals(employeeId));
        saveEmployees(employees);
    }

    public void updateEmployee(User employee){
        List<User> employees = listAllEmployees();
        for(int i = 0; i < employees.size(); i++){
            if(employees.get(i).getId().equals(employee.getId())){
                employees.set(i, employee);
                break;
            }
        }
        saveEmployees(employees);
    }

    public List<User> listAllEmployees(){
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
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
                    users.add(new User(id, name, email, password, role));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User searchEmployee(String searchTerm){
        List<User> employees = listAllEmployees();
        for(User user : employees){
            if(user.getName().equalsIgnoreCase(searchTerm) || user.getEmail().equalsIgnoreCase(searchTerm) || user.getId().equals(searchTerm)){
                return user;
            }
        }
        return null;
    }

    private void saveEmployees(List<User> users){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("employees.txt"))) {
            for (User u : users) {
                String line = u.getId() + "," + u.getName() + "," + u.getEmail() + "," + u.getPassword() + "," + u.getRole().toString();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
