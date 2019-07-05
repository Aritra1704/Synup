package com.example.synup.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VariantGroups {
    private String group_id = "";
    private String name = "";

    @SerializedName("variations")
    private ArrayList<Variations> arrVariation = new ArrayList<>();

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Variations> getArrVariation() {
        return arrVariation;
    }

    public void setArrVariation(ArrayList<Variations> arrVariation) {
        this.arrVariation = arrVariation;
    }

    @Override
    public String toString() {
        return "VariantGroups{" +
                "group_id='" + group_id + '\'' +
                ", name='" + name + '\'' +
                ", arrVariation=" + arrVariation +
                '}';
    }
}
