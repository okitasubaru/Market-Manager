package com.example.marketmanager.models;

public class Product {
    private String productId;
    private String name;
    private int price;
    private int amount;

    public Product() { }

    public Product(String productId, String name, int price, int amount) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
