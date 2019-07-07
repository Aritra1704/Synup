package com.example.synup.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arpaul.utilitieslib.StringUtils;
import com.example.synup.models.ExcludeItems;
import com.example.synup.models.PizzaDetail;
import com.example.synup.models.Variants;
import com.example.synup.models.Variations;
import com.example.synup.repository.VariantRepo;

import java.util.ArrayList;
import java.util.HashMap;

public class PizzaVM extends ViewModel {
    private static MutableLiveData<PizzaDetail> pizzaLiveData;

    public MutableLiveData<PizzaDetail> getInstance() {
        if(pizzaLiveData == null)
            pizzaLiveData = new MutableLiveData<>();;

        return pizzaLiveData;
    }
    public LiveData<PizzaDetail> getPizzas() {
        return pizzaLiveData;
    }

    public void setPizza(PizzaDetail pizzaDetail) {
        pizzaLiveData.setValue(pizzaDetail);
    }

}
