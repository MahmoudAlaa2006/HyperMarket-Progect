package models;

import java.time.LocalDate;
import java.util.Date;

public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private Date expiryDate;
    private int lowStockThreshold;
    private boolean isDamaged;
}