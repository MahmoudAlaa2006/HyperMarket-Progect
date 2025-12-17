package services;

import data.EmployeeRepository;
import models.User;
import models.UserRole;
import utils.IDGenerator;
import utils.PasswordGenerator;

import java.util.List;

public class AdminServices extends BaseServices{

    private final EmployeeRepository repo = new EmployeeRepository();

    public User addEmployee(User admin, String name, String email, UserRole role) {
        authorize(admin, UserRole.ADMIN);

        User user = new User(
                IDGenerator.generateUserId(),
                name,
                email,
                PasswordGenerator.generatePassword(),
                role
        );

        List<User> users = repo.load();
        users.add(user);
        repo.save(users);
        return user;
    }

    public void deleteEmployee(User admin, String employeeId) {
        authorize(admin, UserRole.ADMIN);
        List<User> users = repo.load();
        for (User user : users){
            if (user.getId().equals(employeeId)){
                users.remove(user);
                repo.save(users);
                return;
            }
        }
    }



    public List<User> listAllEmployees(User admin) {
        authorize(admin, UserRole.ADMIN);
        return repo.load();
    }

    public User searchEmployee(User admin, String searchTerm) {
        authorize(admin, UserRole.ADMIN);

        List<User> users = repo.load();
        for (User user : users){
            if (user.getId().equalsIgnoreCase(searchTerm) || user.getName().equalsIgnoreCase(searchTerm)
                    || user.getEmail().equalsIgnoreCase(searchTerm)){
                return user;
            }
        }
        return null;
    }
}