package models;

public class OrderItem {
    private Product product;
    private int quantity;


    public OrderItem(){

    }

    public OrderItem(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct(){
        return product;
    }

    public void setProduct(Product newProduct){
        product = newProduct;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int newQuantity){
        quantity = newQuantity;
    }
}