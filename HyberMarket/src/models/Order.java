package models;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private String id;
    private User seller;
    private List<OrderItem> items;
    private LocalDate orderDate;
    private OrderStatus status;
    private double totalPrice;
}