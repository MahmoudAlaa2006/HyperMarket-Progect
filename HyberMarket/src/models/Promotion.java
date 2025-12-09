package models;

import java.util.Date;

public class Promotion {
    private String id;
    private String description;
    private double discountPercentage;
    private Date startDate;
    private Date endDate;
    private List<Product> applicableProuducts;
}