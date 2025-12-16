package services;

import data.EmployeeRepository;
import models.User;
import models.UserRole;

import java.util.List;

import static utils.IDGenerator.generateUserId;
import static utils.PasswordGenerator.generatePassword;

public class AdminServices extends UserServices {
    
    private EmployeeRepository employeeRepository;

    public AdminServices() {
        this.employeeRepository = new EmployeeRepository();
    }

    public User addEmployee(String name, String email, UserRole role) {
        String id = generateUserId();
        String password = generatePassword();
        
        // Note: User is abstract, so you'll need a concrete implementation
        // For now, assuming you have a concrete User class or will create one
        User newUser = new User(id, name, email, password, role);
        
        List<User> employees = employeeRepository.load();
        employees.add(newUser);
        employeeRepository.save(employees);
        
        return newUser;
    }

    public void deleteEmployee(String employeeId) {
        List<User> employees = employeeRepository.load();
        employees.removeIf(user -> user.getId().equals(employeeId));
        employeeRepository.save(employees);
    }

    public void updateEmployee(User employee) {
        List<User> employees = employeeRepository.load();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(employee.getId())) {
                employees.set(i, employee);
                break;
            }
        }
        employeeRepository.save(employees);
    }

    public List<User> listAllEmployees() {
        return employeeRepository.load();
    }

    public User searchEmployee(String searchTerm) {
        List<User> employees = employeeRepository.load();
        for (User user : employees) {
            if (user.getName().equalsIgnoreCase(searchTerm) || 
                user.getEmail().equalsIgnoreCase(searchTerm) || 
                user.getId().equals(searchTerm)) {
                return user;
            }
        }
        return null;
    }
}