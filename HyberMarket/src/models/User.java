package models;

import java.time.LocalDateTime;

public abstract class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private LocalDateTime lastLogin;
}