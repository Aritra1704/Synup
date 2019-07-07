package com.example.synup.models;

import com.google.gson.annotations.SerializedName;

public class Variations {
    private String name = "";
    private int price = 0;

    @SerializedName("default")
    private int default_Variation = 0;
    private String id = "";
    private int intStock = 0;

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

    public int getDefault_Variation() {
        return default_Variation;
    }

    public void setDefault_Variation(int default_Variation) {
        this.default_Variation = default_Variation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIntStock() {
        return intStock;
    }

    public void setIntStock(int intStock) {
        this.intStock = intStock;
    }

    @Override
    public String toString() {
        return "Variations{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", default_Variation=" + default_Variation +
                ", id='" + id + '\'' +
                ", intStock=" + intStock +
                '}';
    }
}
