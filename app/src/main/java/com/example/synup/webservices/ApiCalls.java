package com.example.synup.webservices;

import com.example.synup.models.BaseResponse;
import com.example.synup.models.Variants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCalls {

    @GET("19u0sf")
    Call<BaseResponse> getVariants();

}
