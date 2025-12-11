package services;
import data.EmployeeRepository;
import models.User;
import java.time.LocalDateTime;
import java.util.List;
public class AuthServices {
    private EmployeeRepository repo;
    private List<User> userList;
    public AuthServices() {
        this.repo = new EmployeeRepository();
        this.userList = repo.load();        
        System.out.println("System loaded " + userList.size() + " users from file.");
    }
    public User login(String email, String password) {
        for (User u : userList) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)){u.setLastLogin(LocalDateTime.now());
                repo.save(userList); 
                return u;
            }
        }
        return null;
    }
    public void logout(User user) {
        if(user != null) {
            System.out.println("User logged out: " + user.getName());
        }
    }
}
