package models;

import utils.IDGenerator;

import java.time.LocalDate;

public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private LocalDate expiryDate;
    private int daysBeforeExpiryWarning;
    private int lowStockThreshold;
    private boolean isDamaged;


    public Product(){

    }

    public Product(String name, double price, int quantity, LocalDate expiryDate,
                   int daysBeforeExpiryWarning, int lowStockThreshold, boolean isDamaged){
        this.id = IDGenerator.generateProductId();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.daysBeforeExpiryWarning = daysBeforeExpiryWarning;
        this.lowStockThreshold = lowStockThreshold;
        this.isDamaged = isDamaged;
        this.expiryDate = expiryDate;
    }

    public Product(String id, String name, double price, int quantity, LocalDate expiryDate,
                   int daysBeforeExpiryWarning, int lowStockThreshold, boolean isDamaged){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.daysBeforeExpiryWarning = daysBeforeExpiryWarning;
        this.lowStockThreshold = lowStockThreshold;
        this.isDamaged = isDamaged;
        this.expiryDate = expiryDate;
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

    public double getPrice(){
        return price;
    }

    public void setPrice(double newPrice){
        price = newPrice;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int newQuantity){
        quantity = newQuantity;
    }

    public LocalDate getExpiryDate(){
        return expiryDate;
    }

    public void setExpiryDate(LocalDate newDate){
        expiryDate = newDate;
    }

    public int getDaysBeforeExpiryWarning(){
        return daysBeforeExpiryWarning;
    }

    public void setDaysBeforeExpiryWarning(int newDaysBeforeExpiryWarning){
        daysBeforeExpiryWarning = newDaysBeforeExpiryWarning;
    }

    public int getLowStockThreshold(){
        return lowStockThreshold;
    }

    public void setLowStockThreshold(int newLowStockThreshold){
        lowStockThreshold = newLowStockThreshold;
    }

    public boolean isDamaged(){
        return isDamaged;
    }

    public void setIsDamaged(boolean newIsDamage){
        isDamaged = newIsDamage;
    }
}

