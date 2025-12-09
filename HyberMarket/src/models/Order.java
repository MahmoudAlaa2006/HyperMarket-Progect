package models;

import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private User seller;
    private List<OrderItem> items;
    private Date orderDate;
    private OrderStatus status;
    private double totalPrice;
}