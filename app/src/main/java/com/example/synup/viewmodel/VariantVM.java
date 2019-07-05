package com.example.synup.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.synup.models.Variants;
import com.example.synup.repository.VariantRepo;

public class VariantVM extends ViewModel {
    private VariantRepo mRepository;
    private LiveData<Variants> variantsLiveData;

    public void init() {
        if(variantsLiveData != null)
            return;

        mRepository = VariantRepo.getInstance();
        getData();
    }

    public LiveData<Variants> getVariants() { return variantsLiveData; }

    public void getData() {
        variantsLiveData = mRepository.getVariants();
    }
}
