package com.example.synup.models;

import com.google.gson.JsonObject;

import org.json.JSONObject;

public class BaseResponse {

    JsonObject variants;

    public JsonObject getResponse() {
        return variants;
    }

    public void setResponse(JsonObject response) {
        this.variants = response;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "variants=" + variants +
                '}';
    }
}
