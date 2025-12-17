package services;

import models.User;
import models.UserRole;

public abstract class BaseServices {
    protected void authorize(User user, UserRole role){
        if (user.getUserRole() != role){
            throw new SecurityException("Access denied");
        }
    }
}
