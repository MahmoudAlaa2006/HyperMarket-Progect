package services;

import data.EmployeeRepository;
import models.User;

public class AuthServices {
    private final EmployeeRepository repo = new EmployeeRepository();

    public User login(String email, String password) {
        for (User u : repo.load()) {
            if (u.getEmail().equalsIgnoreCase(email)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void logout(User user) {
        System.out.println("User logged out: " + user.getName());
    }
}
