package services;

import data.OrderRepository;
import data.ProductRepository;
import models.*;
import utils.IDGenerator;

import java.util.ArrayList;
import java.util.List;

public class SalesServices {

    private final OrderRepository orderRepo = new OrderRepository();
    private final ProductRepository productRepo = new ProductRepository();

    // Create and persist a new order
    public Order createOrder(Seller seller, List<OrderItem> items) {
        if (seller == null || items == null) return null;

        String orderId = IDGenerator.generateOrderId();
        Order newOrder = new Order(orderId, seller, items, OrderStatus.PENDING);

        // Save new order to repository
        orderRepo.update(newOrder);

        return newOrder;
    }

    // Cancel an existing order and persist change
    public void cancelOrder(String orderId) {
        Order order = orderRepo.getOrderById(orderId);

        if (order == null) return;

        order.setStatus(OrderStatus.CANCELLED);
        orderRepo.update(order); // persist cancellation
    }

    // Search products by keyword
    public List<Product> searchProducts(String keyword) {
        List<Product> productsSearch = productRepo.load();
        List<Product> products = new ArrayList<>();

        String lowerKeyword = keyword.toLowerCase();
        for (Product product : productsSearch) {
            String name = product.getName();
            if (name.equalsIgnoreCase(keyword)
                    || name.toLowerCase().startsWith(lowerKeyword)
                    || name.toLowerCase().endsWith(lowerKeyword)) {
                products.add(product);
            }
        }

        return products; // return empty list if no matches
    }

    // List all products
    public List<Product> listAllProducts() {
        return productRepo.load();
    }
}