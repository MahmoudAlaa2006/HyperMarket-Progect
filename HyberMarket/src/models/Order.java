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



    public Order(){

    }

    public Order(String id, User seller, List<OrderItem> items, OrderStatus status){
        this.id = id;
        this.seller = seller;
        this.items = items;
        this.status = status;
        this.orderDate = LocalDate.now();
        this.totalPrice = calculatePrice();
    }

    public double calculatePrice() {
        double sum = 0;
        for (OrderItem item : items) {
            sum +=  item.getQuantity() * item.getProduct().getPrice();
        }
        return sum;
    }

    public String getId(){
        return id;
    }

    public void setId(String newId){
        id = newId;
    }

    public User getSeller(){
        return seller;
    }

    public void setSeller(User newSeller){
        seller = newSeller;
    }

    public List<OrderItem> getItems(){
        return items;
    }

    public void setItems(List<OrderItem> newItems){
        items = newItems;
    }

    public OrderStatus getStatus(){
        return status;
    }

    public void setStatus(OrderStatus newStatus){
        status = newStatus;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public void setTotalPrice(){
        totalPrice = calculatePrice();
    }

    public LocalDate getOrderDate(){
        return orderDate;
    }

    public void setOrderDate(){
        orderDate = LocalDate.now();
    }
}

