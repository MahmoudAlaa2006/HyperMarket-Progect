package data;

import models.Product;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Repository<Product>{

    private final String FILE_PATH = "Products.txt";


    @Override
    public List<Product> load() {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return products;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                Product product = new Product(
                        data[0],                          // id
                        data[1],                          // name
                        Double.parseDouble(data[2]),      // price
                        Integer.parseInt(data[3]),        // quantity
                        LocalDate.parse(data[4]),         // expiry date
                        Integer.parseInt(data[5]),        // days warning
                        Integer.parseInt(data[6]),        // low stock threshold
                        Boolean.parseBoolean(data[7])     // damaged
                );

                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }


    @Override
    public void save(List<Product> products) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Product p : products) {
                pw.println(
                        p.getId() + "|" +
                                p.getName() + "|" +
                                p.getPrice() + "|" +
                                p.getQuantity() + "|" +
                                p.getExpiryDate() + "|" +
                                p.getDaysBeforeExpiryWarning() + "|" +
                                p.getLowStockThreshold() + "|" +
                                p.isDamaged()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}