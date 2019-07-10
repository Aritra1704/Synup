package com.example.synup.ui;

import android.os.Bundle;

import com.arpaul.utilitieslib.NetworkUtility;
import com.arpaul.utilitieslib.StringUtils;
import com.example.synup.R;
import com.example.synup.models.ExcludeItems;
import com.example.synup.models.PizzaDetail;
import com.example.synup.models.VariantGroups;
import com.example.synup.models.Variations;
import com.example.synup.viewmodel.PizzaVM;
import com.example.synup.viewmodel.VariantVM;
import com.google.android.material.snackbar.Snackbar;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateActivity extends BaseActivity {

    @BindView(R.id.tvPrice)
    protected TextView tvPrice;

    @BindView(R.id.btnCreate)
    protected Button btnCreate;

    @BindView(R.id.rvVariants)
    protected RecyclerView rvVariants;

    private View vCreateActivity;

    private VariantVM variantVM;
    private PizzaVM pizzaVM;

    private SingleClickDialog crustDialog;
    private VariantsAdapter adapter;
    private ArrayList<VariantGroups> listVariants;
    private ArrayList<ArrayList<ExcludeItems>> listExclude;
    ArrayList<String> selectedCombination = new ArrayList<>();
    private HashMap<String, Variations> hashSelected = new HashMap<>();
    private HashMap<String, Integer> hashgroupId = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vCreateActivity = inflaterBase.inflate(R.layout.content_main, null);
        llBase.addView(vCreateActivity, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        if(!NetworkUtility.isConnectionAvailable(this)) {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            finish();
        }
        variantVM = ViewModelProviders.of(this).get(VariantVM.class);
        variantVM.init();
        pizzaVM = ViewModelProviders.of(this).get(PizzaVM.class);
        pizzaVM.getInstance();
        variantVM.getVariants().observe(this, variants -> {
            if(variants != null) {
                listVariants = variants.getArrVariantGroups();
                listExclude = variants.getArrExclude();
                Log.d("getVariants", listVariants.size() + "");

                if(listVariants != null && listVariants.size() > 0) {
                    adapter.refresh(listVariants);
                }
            }
        });

        setAdapter();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar;
                if(hashgroupId.size() < 3)
                    snackbar = Snackbar.make(view, "Select all fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                else {
                    selectedCombination.clear();

                    for (String variant: hashgroupId.keySet()) {//G1V11     G2V20   G3V30
                        selectedCombination.add(variantVM.getFormattedVariant(hashgroupId.get(variant) , StringUtils.getInt(hashSelected.get(variant).getId())));
                    }

                    boolean isExcluded = variantVM.isExclusion(selectedCombination, listExclude);
                    if(isExcluded)
                        snackbar = Snackbar.make(view, "This pizza is not available", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                    else {
                        snackbar = Snackbar.make(view, "Your pizza is created!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PizzaDetail pizzaDetail = new PizzaDetail();
                                pizzaDetail.setCrust(hashSelected.get("Crust").getName());
                                pizzaDetail.setSize(hashSelected.get("Size").getName());
                                pizzaDetail.setSauce(hashSelected.get("Sauce").getName());
                                pizzaDetail.setPrice(StringUtils.getFloat(tvPrice.getText().toString()));
                                pizzaVM.setPizza(pizzaDetail);

                                finish();
                            }
                        }, 2000);
                    }
                }

                snackbar.show();
            }
        });
    }

    private void setAdapter() {
        adapter = new VariantsAdapter(this, new ArrayList<VariantGroups>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvVariants.setLayoutManager(mLayoutManager);
        rvVariants.setItemAnimator(new DefaultItemAnimator());
        rvVariants.setAdapter(adapter);
    }

    public void openChildDialog(String name, ArrayList<Variations> variationGroups, int groupPosition, VariantsAdapter.selectedVariant selectedVariant) {
        ArrayList<String> list = variantVM.variantsToStringList(variationGroups);
        crustDialog = createSingleClickDialog(getString(R.string.select_crust), list);
        crustDialog.setOnSelectedListener(new SingleClickDialog.OnSelectedListener() {
            @Override
            public void onSelect(int position) {
                hashSelected.put(name, variationGroups.get(position));
                hashgroupId.put(name, groupPosition);
                selectedVariant.selected(variationGroups.get(position).getName());
                updatePrice();
            }
        });
        crustDialog.show(getSupportFragmentManager(), getString(R.string.select_crust));
    }

    void updatePrice() {
        tvPrice.setText("" + variantVM.calculatePrice(hashSelected));
    }

    private SingleClickDialog createSingleClickDialog(String title, ArrayList<String> list) {
        SingleClickDialog dialog = SingleClickDialog.newInstance(title, list);
        return dialog;
    }
}
