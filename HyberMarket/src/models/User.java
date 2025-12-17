package models;


public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private UserRole role;


    public User(){

    }

    public User(String id, String name, String email, String password, UserRole role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getId(){
        return id;
    }


    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String newEmail){
        email = newEmail;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String newPassword){
        password = newPassword;
    }

    public UserRole getUserRole(){
        return role;
    }

//    public void setUserRole(UserRole newRole){
//        role = newRole;
//    }
}