package com.example.synup.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.synup.models.BaseResponse;
import com.example.synup.models.VariantGroups;
import com.example.synup.models.Variants;
import com.example.synup.webservices.ApiCalls;
import com.example.synup.webservices.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VariantRepo {
    private static VariantRepo repository;

    public static VariantRepo getInstance() {
        if(repository == null) {
            repository = new VariantRepo();
        }

        return repository;
    }

    private ApiCalls apiCalls;

    public VariantRepo() {
        apiCalls = RetrofitService.createService(ApiCalls.class);
    }

    public MutableLiveData<Variants> getVariants() {
        final MutableLiveData<Variants> variantData = new MutableLiveData<>();
        apiCalls.getVariants().enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("onResponse", response.toString());

                if(response.isSuccessful()) {
//                    Variants variants = new Variants();
//                    ArrayList<VariantGroups> arrVariantGroups = new Gson().fromJson(response.body().getResponse().toString(), new TypeToken<List<VariantGroups>>(){}.getType());
//                    variants.setArrVariantGroups(arrVariantGroups);

                    Variants variants = new Gson().fromJson(response.body().getResponse().toString(), new TypeToken<Variants>(){}.getType());
                    variantData.setValue(variants);
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
                variantData.setValue(null);
            }
        });

        return variantData;
    }
}
