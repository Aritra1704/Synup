package com.example.synup.models;

import com.google.gson.JsonObject;

public class BaseResponse {

    JsonObject variants;

    public Object getResponse() {
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
