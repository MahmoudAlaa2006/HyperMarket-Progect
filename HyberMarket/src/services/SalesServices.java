package services;

import data.OrderRepository;
import data.ProductRepository;
import models.*;
import utils.IDGenerator;

import java.util.ArrayList;
import java.util.List;

public class SalesServices extends BaseServices{

    private final OrderRepository orderRepo = new OrderRepository();
    private final ProductRepository productRepo = new ProductRepository();
    private final InventoryServices inventoryServices = new InventoryServices();


    public Order createOrder(User seller, List<OrderItem> items) {
        authorize(seller, UserRole.SELLER);

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        String orderId = IDGenerator.generateOrderId();
        Order newOrder = new Order(orderId, seller, items);

        // Reduce inventory quantities
        for (OrderItem item : items){
            inventoryServices.updateQuantitySys(item.getProduct().getId(), -item.getQuantity());
        }

        List<Order> orders = orderRepo.load();
        orders.add(newOrder);
        orderRepo.save(orders);

        return newOrder;
    }

    public void cancelOrder(User seller, String orderId) {
        authorize(seller, UserRole.SELLER);

        List<Order> orders = orderRepo.load();
        for (Order order : orders){
            if (order.getId().equals(orderId)){
                if (order.getStatus() == OrderStatus.CANCELLED) {
                    return;
                }

                order.setStatus(OrderStatus.CANCELLED);
                // Return items to inventory
                for (OrderItem item: order.getItems()){
                    inventoryServices.updateQuantitySys(item.getProduct().getId(), item.getQuantity());
                }

                orderRepo.save(orders);
            }
        }
    }

    public List<Product> searchProducts(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return new ArrayList<>();
        }

        List<Product> Products = productRepo.load();
        List<Product> results = new ArrayList<>();

        for (Product product : Products) {
            if (product.getName().equalsIgnoreCase(searchTerm)
                    || product.getName().toLowerCase().startsWith(searchTerm.toLowerCase())
                    || product.getName().toLowerCase().endsWith(searchTerm.toLowerCase())) {
                results.add(product);
            }
        }
        return results;
    }

    public List<Product> listAllProducts() {
        return productRepo.load();
    }
}