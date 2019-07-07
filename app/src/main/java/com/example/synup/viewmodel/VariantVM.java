package com.example.synup.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.arpaul.utilitieslib.StringUtils;
import com.example.synup.R;
import com.example.synup.models.ExcludeItems;
import com.example.synup.models.Variants;
import com.example.synup.models.Variations;
import com.example.synup.repository.VariantRepo;
import com.example.synup.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

    public ArrayList<String> variantsToStringList(ArrayList<Variations> arrVariations) {
        ArrayList<String> list = new ArrayList<String>();
        for (Variations variation : arrVariations) {
            list.add(variation.getName());
        }
        return list;
    }

    public boolean isExclusion(ArrayList<String> selectedCombination, ArrayList<ArrayList<ExcludeItems>> listExclude) {
        boolean isExcluded = false;
        for (int i = 0; i < listExclude.size(); i++) {
            boolean doesMatch[] = new boolean[listExclude.get(i).size()];
            for (int j = 0; j < listExclude.get(i).size(); j++) {
                String groupId = listExclude.get(i).get(j).getGroup_id();
                String variationId = listExclude.get(i).get(j).getVariation_id();
                String exclude = getFormattedVariant(StringUtils.getInt(groupId), StringUtils.getInt(variationId));

                if(selectedCombination.contains(exclude)) {
                    doesMatch[j] = true;
                }
            }
            isExcluded = areAllTrue(doesMatch);//Arrays.asList(doesMatch).contains(false);
            if(isExcluded)
                break;
        }


        return isExcluded;
    }

    public String getFormattedVariant(int groupId, int variationId) {
        String selected = "G" + groupId + "V" + variationId;
        return selected;
    }

    public float calculatePrice(HashMap<String, Variations> hashSelected) {
        float totalPrice = 0f;
        for (String key : hashSelected.keySet()) {
            totalPrice += hashSelected.get(key).getPrice();
        }
        return totalPrice;
    }

    public boolean areAllTrue(boolean[] array) {
        for(boolean b : array) if(!b) return false;
        return true;
    }
}
