package data;

import models.Product;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static final String FILE_NAME = "products.txt";

    // Load products from text file
    public List<Product> load() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    Product p = new Product(
                            parts[0],                          // id
                            parts[1],                          // name
                            Double.parseDouble(parts[2]),      // price
                            Integer.parseInt(parts[3]),        // quantity
                            LocalDate.parse(parts[4]),         // expiryDate
                            Integer.parseInt(parts[5]),        // daysBeforeExpiryWarning
                            Integer.parseInt(parts[6]),        // lowStockThreshold
                            Boolean.parseBoolean(parts[7])     // isDamaged
                    );
                    products.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Save products to text file
    public void save(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Product p : products) {
                writer.write(String.join(",",
                        p.getId(),
                        p.getName(),
                        String.valueOf(p.getPrice()),
                        String.valueOf(p.getQuantity()),
                        p.getExpiryDate().toString(),
                        String.valueOf(p.getDaysBeforeExpiryWarning()),
                        String.valueOf(p.getLowStockThreshold()),
                        String.valueOf(p.getIsdamaged())
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get product by ID
    public Product getProductById(String id) {
        List<Product> products = load();
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null; // not found
    }
}