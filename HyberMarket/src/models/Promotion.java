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


    public Promotion(){

    }

    public Promotion(String id, String descreption, double discountPercentage, List<Product> applicableProducts,
                     LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.description = descreption;
        this.discountPercentage = discountPercentage;
        this.applicableProuducts = applicableProducts;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId(){
        return id;
    }

    public void setId(String newId){
        id = newId;
    }

    public String descreption(){
        return description;
    }

    public void setDescreption(String newDescreption){
        description = newDescreption;
    }

    public double getDiscountPecentage(){
        return discountPercentage;
    }

    public void setDiscountPercentage(double newDiscountPercentage){
        discountPercentage = newDiscountPercentage;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public void setStartDate(){
        startDate = LocalDate.now();
    }

    public LocalDate getEndDate(){
        return endDate;
    }

    public void setEndDate(){
        endDate = LocalDate.now();
    }

    public List<Product> getApplicableProuducts(){
        return applicableProuducts;
    }

    public void setApplicableProuducts(List<Product> newApplicableProuducts){
        applicableProuducts = newApplicableProuducts;
    }
}

