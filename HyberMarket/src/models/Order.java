package models;

import utils.IDGenerator;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private final String id;
    private final User seller;
    private final List<OrderItem> items;
    private final LocalDate orderDate;
    private OrderStatus status;
    private double totalPrice;

    public Order(User seller, List<OrderItem> items) {
        this.id = IDGenerator.generateOrderId();
        this.seller = seller;
        this.items = items;
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.COMPLETED;
        this.totalPrice = calculatePrice();
    }

    public Order(String id, User seller, List<OrderItem> items, double totalPrice) {
        this.id = id;
        this.seller = seller;
        this.items = items;
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.COMPLETED;
        this.totalPrice = totalPrice;
    }

    private double calculatePrice() {
        double sum = 0;
        for (OrderItem item : items) {
            sum += item.getProduct().getPrice() * item.getQuantity();
        }
        return sum;
    }

    public String getId() { return id; }
    public User getSeller() { return seller; }
    public List<OrderItem> getItems() { return items; }
    public LocalDate getOrderDate() { return orderDate; }
    public OrderStatus getStatus() { return status; }
    public double getTotalPrice() { return totalPrice; }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
