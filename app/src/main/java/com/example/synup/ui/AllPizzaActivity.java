package com.example.synup.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.synup.models.PizzaDetail;
import com.example.synup.models.VariantGroups;
import com.example.synup.viewmodel.PizzaVM;
import com.example.synup.viewmodel.VariantVM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.synup.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllPizzaActivity extends BaseActivity {

    private View vAllPizzaActivity;
    private PizzaAdapter adapter;

    @BindView(R.id.tvNoPizza)
    protected TextView tvNoPizza;

    @BindView(R.id.fab)
    protected FloatingActionButton fab;

    @BindView(R.id.rvPizza)
    protected RecyclerView rvPizza;

    private PizzaVM pizzaVM;
    private ArrayList<PizzaDetail> listPizza = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vAllPizzaActivity = inflaterBase.inflate(R.layout.activity_all_pizza, null);
        llBase.addView(vAllPizzaActivity, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        pizzaVM = ViewModelProviders.of(this).get(PizzaVM.class);
        pizzaVM.getInstance();
        pizzaVM.getPizzas().observe(this, pizzaDetail -> {
            listPizza.add(pizzaDetail);

            if(listPizza != null && listPizza.size() > 0) {
                adapter.refresh(listPizza);
                showNoPizza(false);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllPizzaActivity.this, CreateActivity.class));
            }
        });

        setAdapter();
    }

    private void showNoPizza(boolean shouldShow) {
        if(shouldShow)
            tvNoPizza.setVisibility(View.VISIBLE);
        else
            tvNoPizza.setVisibility(View.GONE);
    }

    private void setAdapter() {
        adapter = new PizzaAdapter(this, new ArrayList<PizzaDetail>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPizza.setLayoutManager(mLayoutManager);
        rvPizza.setItemAnimator(new DefaultItemAnimator());
        rvPizza.setAdapter(adapter);

        showNoPizza(true);
    }
}
