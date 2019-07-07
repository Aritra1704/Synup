package com.example.synup.models;

public class PizzaDetail {
    String crust;
    String size;
    String sauce;
    float price;

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PizzaDetail{" +
                "crust='" + crust + '\'' +
                ", size='" + size + '\'' +
                ", sauce='" + sauce + '\'' +
                ", price=" + price +
                '}';
    }
}
