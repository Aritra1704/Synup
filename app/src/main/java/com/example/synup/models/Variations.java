package com.example.synup.models;

import com.google.gson.annotations.SerializedName;

public class Variations {
    private String name = "";
    private int price = 0;

    @SerializedName("default")
    private int default_Variation = 0;
    private String id = "";
    private int intStock = 0;
}
