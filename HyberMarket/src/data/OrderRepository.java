package data;

import models.Order;
import models.OrderStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private static final String FILE_NAME = "orders.txt";

    // Load all orders from file
    public List<Order> load() {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: orderId|status
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    String orderId = parts[0];
                    OrderStatus status = OrderStatus.valueOf(parts[1]);
                    orders.add(new Order(orderId, null, null, status));
                }
            }
        } catch (IOException e) {
            // File may not exist yet, ignore
        }
        return orders;
    }

    // Save all orders back to file
    public void save(List<Order> orders) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Order o : orders) {
                bw.write(o.getId() + "|" + o.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Find order by ID
    public Order getOrderById(String id) {
        return load().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update or add order in file
    public void update(Order order) {
        List<Order> orders = load();
        boolean found = false;

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(order.getId())) {
                orders.set(i, order);
                found = true;
                break;
            }
        }

        if (!found) {
            orders.add(order); // add new if not found
        }

        save(orders);
    }
}