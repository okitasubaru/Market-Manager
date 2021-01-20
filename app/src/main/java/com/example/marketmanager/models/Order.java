package com.example.marketmanager.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Order extends CartItems implements Serializable {
    private ArrayList<CartItems> Item;
    private String OrderId;
    private String Time;
    private double Total;

    public Order(ArrayList<CartItems> item, String orderId, String time, double total) {
        Item = item;
        OrderId = orderId;
        Time = time;
        Total = total;
    }

    public Order() {
    }

    public ArrayList<CartItems> getItem() {
        return Item;
    }

    public void setItem(ArrayList<CartItems> item) {
        Item = item;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }
}