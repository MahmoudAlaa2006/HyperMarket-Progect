package models;

public class InventoryEmployee extends User {
    public InventoryEmployee(String id, String name, String email, String password){
        super(id, name, email, password, UserRole.INVENTORY);
    }

}