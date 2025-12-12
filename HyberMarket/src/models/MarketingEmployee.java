package models;

import java.util.List;

public class MarketingEmployee extends User {
    public MarketingEmployee(String id, String name, String email, String password){
        super(id, name, email, password, UserRole.MARKETING);
    }
}