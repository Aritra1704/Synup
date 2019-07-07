package com.example.synup.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Variants {

    @SerializedName("variant_groups")
    ArrayList<VariantGroups> arrVariantGroups = new ArrayList<>();

    @SerializedName("exclude_list")
    ArrayList<ArrayList<ExcludeItems>> arrExclude = new ArrayList<>();

    public ArrayList<VariantGroups> getArrVariantGroups() {
        return arrVariantGroups;
    }

    public void setArrVariantGroups(ArrayList<VariantGroups> arrVariantGroups) {
        this.arrVariantGroups = arrVariantGroups;
    }

    public ArrayList<ArrayList<ExcludeItems>> getArrExclude() {
        return arrExclude;
    }

    public void setArrExclude(ArrayList<ArrayList<ExcludeItems>> arrExclude) {
        this.arrExclude = arrExclude;
    }

    @Override
    public String toString() {
        return "Variants{" +
                "arrVariantGroups=" + arrVariantGroups +
                ", arrExclude=" + arrExclude +
                '}';
    }
}
