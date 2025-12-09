package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private LocalDateTime lastLogin;
}