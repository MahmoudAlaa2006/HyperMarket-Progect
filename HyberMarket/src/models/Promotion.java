package models;

import java.time.LocalDate;
import java.util.List;

public class Promotion {
    private String id;
    private String description;
    private double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Product> applicableProuducts;
}