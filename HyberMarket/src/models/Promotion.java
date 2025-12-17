package models;

import java.time.LocalDate;
import java.util.List;

public class Promotion {
    private final String id;
    private String description;
    private double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Product> applicableProducts;

    public Promotion(String id, String description, double discountPercentage,
                     List<Product> applicableProducts,
                     LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.applicableProducts = applicableProducts;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public double getDiscountPercentage() { return discountPercentage; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public List<Product> getApplicableProducts() { return applicableProducts; }

    public void setDescription(String description) { this.description = description; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }
}
