package services;

import data.EmployeeRepository;
import models.User;

import java.util.List;

public class UserServices {

    private final EmployeeRepository repo = new EmployeeRepository();

    public void changeName(String id, String newName){
        List<User> users = repo.load();
        for (User user : users){
            if (user.getId().equals(id)){
                user.setName(newName);
                repo.save(List<users>);
                return;
            }
        }
    }

    public void changePassword(String id, String newPassword){
        List<User> users = repo.load();
        for (User user : users){
            if (user.getId().equals(id)){
                user.setPassword(newPassword);
                repo.save(List<users>);
                return;
            }
        }
    }

    public void changeEmail(String id, String newEmail){
        List<User> users = repo.load();
        for (User user : users){
            if (user.getId().equals(id)){
                user.setEmail(newEmail);
                repo.save(List<users>);
            }
        }
    }
}