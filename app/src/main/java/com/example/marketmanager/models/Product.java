package com.example.marketmanager.models;

public class Product {
    private String Productid;
    private String Name;
    private float Price;
    private String Barcode;

    // Contructor - Getter - Setter


    public Product(String productid, String name, float price, String barcode) {
        Productid = productid;
        Name = name;
        Price = price;
        Barcode = barcode;
    }

    public Product() {
    }

    public String getProductid() {
        return Productid;
    }

    public void setProductid(String productid) {
        Productid = productid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }
}