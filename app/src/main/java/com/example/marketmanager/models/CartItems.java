package com.example.marketmanager.models;

public class CartItems {
    private String Name;
    private float Price;
    private String Productid;
    private int Quantity = 1;


    public CartItems(String Name, float Price,String Productid, int Quantity) {
        this.Name = Name;
        this.Price = Price;
        this.Productid = Productid;
        this.Quantity = Quantity;
    }

    public CartItems(){

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


    public String getProductid() {
        return Productid;
    }

    public void setProductid(String productid) {
        Productid = productid;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
