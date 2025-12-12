package models;

import java.util.List;

public class Seller extends User {
    public Seller(String id, String name, String email, String password){
        super(id, name, email, password, UserRole.SELLER);
    }
}
