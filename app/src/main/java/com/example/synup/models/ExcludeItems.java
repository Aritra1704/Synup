package com.example.synup.models;

public class ExcludeItems {
    private String group_id = "";
    private String variation_id = "";

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(String variation_id) {
        this.variation_id = variation_id;
    }

    @Override
    public String toString() {
        return "ExcludeItems{" +
                "group_id='" + group_id + '\'' +
                ", variation_id='" + variation_id + '\'' +
                '}';
    }
}
