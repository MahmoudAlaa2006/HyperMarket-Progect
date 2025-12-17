package services;

import data.ProductRepository;
import models.*;
import utils.IDGenerator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class InventoryServices extends BaseServices{

    private final ProductRepository productRepo = new ProductRepository();
    private final NotificationServices notificationServices = new NotificationServices();


    public void addProduct(User user, Product product) {
        authorize(user, UserRole.INVENTORY);

        List<Product> products = productRepo.load();
        products.add(product);
        productRepo.save(products);
    }

    public void deleteProduct(User user, String productId) {
        authorize(user, UserRole.INVENTORY);

        List<Product> products = productRepo.load();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                products.remove(i);
                productRepo.save(products);
                return;
            }
        }
        System.out.println("Product not found, enter the right ID");
    }

    public void updatePrice(User user, String id, double newPrice) {
        authorize(user, UserRole.INVENTORY);

        List<Product> products = productRepo.load();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setPrice(newPrice);
                productRepo.save(products);
                return;
            }
        }
        System.out.println("Product not found, enter the right ID");
    }

    public void updateQuantity(User user, String id, int value, boolean add) {
        authorize(user, UserRole.INVENTORY);

        List<Product> products = productRepo.load();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                int newQuantity = add ? product.getQuantity() + value : product.getQuantity() - value;
                if (newQuantity < 0) {
                    throw new IllegalArgumentException("Not enough stock");
                }
                product.setQuantity(newQuantity);

                if (newQuantity <= product.getLowStockThreshold()) {
                    sendLowStockThreshold(product);
                }
                productRepo.save(products);
                return;
            }
        }
    }

    public void updateQuantitySys(String id, int change){
        List<Product> products = productRepo.load();

        for(Product product : products){
            if (product.getId().equals(id)){
                product.setQuantity(product.getQuantity() + change);

                if (product.getQuantity() <= product.getLowStockThreshold()){
                    notificationServices.send(new Notification(
                            IDGenerator.generateNotificationId(),
                            NotificationType.LOW_STOCK,
                            "Low stock for product: " + product.getName()
                    ));
                }
                break;
            }
        }
        productRepo.save(products);
    }

    public void checkExpiry() {
        List<Product> products = productRepo.load();
        List<String> expiredProducts = new ArrayList<>();
        for (Product product : products) {
            long differance = ChronoUnit.DAYS.between(LocalDate.now(), product.getExpiryDate());
            if (differance > 0) {
                if (differance <= product.getDaysBeforeExpiryWarning()) {
                    sendExpiryWarning(product);
                }
            }
            else {
                expiredProducts.add(product.getId());
            }
        }
        if (!expiredProducts.isEmpty()) {
            manageExpiry(expiredProducts);
        }
    }

    private void manageExpiry(List<String> ids) {
        List<Product> products = productRepo.load();
        products.removeIf(product -> ids.contains(product.getId()));
        productRepo.save(products);
    }

    public void markAsDamaged(User user, String id){
        authorize(user, UserRole.INVENTORY);
        List<Product> products = productRepo.load();

        for (Product product : products){
            if (product.getId().equals(id)){
                product.setIsDamaged(true);

                notificationServices.send(new Notification(IDGenerator.generateNotificationId(),
                        NotificationType.DAMAGE_ITEM, "Product " + product.getName() + " marked as damaged"));

                productRepo.save(products);
                return;
            }
        }
    }

    public void handleSalesReturn(User seller, String id, int quantity){
        List<Product> products = productRepo.load();

        for(Product product : products){
            if (product.getId().equals(id)){
                product.setQuantity(product.getQuantity() + quantity);

                notificationServices.send(new Notification(IDGenerator.generateNotificationId(),
                        NotificationType.SALES_RETURN, quantity + " items returned for " + product.getName()));

                productRepo.save(products);
                return;
            }
        }
    }

    public List<Product> listAllProduct() {
        return productRepo.load();
    }

    public Product searchProduct(String searchTerm) {
        for (Product product : productRepo.load()) {
            if (product.getId().equalsIgnoreCase(searchTerm) || product.getName().equalsIgnoreCase(searchTerm)) {
                return product;
            }
        }
        System.out.println("Product not found");
        return null;
    }

    private void sendLowStockThreshold(Product product) {
        notificationServices.send(new Notification(IDGenerator.generateNotificationId(),
                NotificationType.LOW_STOCK, "Low stock for product: " + product.getName()));
    }

    private void sendExpiryWarning(Product product) {
        notificationServices.send(new Notification(IDGenerator.generateNotificationId(),
                NotificationType.EXPIRY_WARNING, "Product near expiry: " + product.getName()));
    }
}

