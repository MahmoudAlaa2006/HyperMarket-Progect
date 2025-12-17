
package data;

import models.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements Repository<Order>{

    private final String FILE_PATH = "orders.txt";

    @Override
    public List<Order> load() {
        List<Order> orders = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return orders;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] main = line.split("\\|");

                String orderId = main[0];
                User seller = new User(main[1], null, null, null, UserRole.SELLER);
                LocalDate date = LocalDate.parse(main[2]);
                OrderStatus status = OrderStatus.valueOf(main[3]);

                String[] itemData = main[5].split(";");
                List<OrderItem> items = new ArrayList<>();

                for (String item : itemData) {
                    String[] parts = item.split(",");
                    Product p = new Product();
                    p.setName(parts[0]); // productId placeholder
                    items.add(new OrderItem(p, Integer.parseInt(parts[1])));
                }

                Order order = new Order(orderId, seller, items);
                order.setStatus(status);

                orders.add(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }


    @Override
    public void save(List<Order> orders) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Order o : orders) {
                StringBuilder items = new StringBuilder();

                for (OrderItem item : o.getItems()) {
                    items.append(item.getProduct().getId())
                            .append(",")
                            .append(item.getQuantity())
                            .append(";");
                }

                pw.println(
                        o.getId() + "|" +
                                o.getSeller().getId() + "|" +
                                o.getOrderDate() + "|" +
                                o.getStatus() + "|" +
                                o.getTotalPrice() + "|" +
                                items.toString()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
