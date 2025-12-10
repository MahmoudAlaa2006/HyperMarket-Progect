package models;

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
}