package com.example.aws;

public class Order {
    int orderId;
    String product;
    int quantity;

    public int getOrderId(){
        return orderId;
    }

    public void setOrderId(){
        this.orderId = orderId;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(){
        this.quantity = quantity;
    }

    public String getProduct(){
        return product;
    }

    public String setProduct(){
        this.product = product;
    }
}
