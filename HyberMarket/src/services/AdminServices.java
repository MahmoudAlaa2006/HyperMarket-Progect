package services;

import models.User;
import models.UserRole;

import java.util.List;

public class AdminServices extends UserServices {

    public User addEmployee(String name, String email, UserRole role){
        // set unique id , password, type
    }

    public void deleteEmployee(String employeeId){

    }

    public void updateEmployee(User employee){

    }

    public List<User> listAllEmployees(){

    }

    public User searchEmployee(String searchTerm){

    }
}
