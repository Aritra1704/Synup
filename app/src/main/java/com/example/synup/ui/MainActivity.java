package com.example.synup.ui;

import android.os.Bundle;

import com.example.synup.R;
import com.example.synup.common.AppInstance;
import com.example.synup.models.VariantGroups;
import com.example.synup.models.Variants;
import com.example.synup.viewmodel.VariantVM;
import com.example.synup.webservices.ApiCalls;
import com.example.synup.webservices.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvPizza)
    protected RecyclerView rvPizza;

    @BindView(R.id.tvNoData)
    protected TextView tvNoData;

    @BindView(R.id.fab)
    protected FloatingActionButton fab;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    private VariantVM variantVM;

    private VariantsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        variantVM = ViewModelProviders.of(this).get(VariantVM.class);
        variantVM.init();
//        variantVM.getVariants().observe(this, variantResponse -> {
//            ArrayList<VariantGroups> listVariants = variantResponse.getArrVariantGroups();
//            if(listVariants != null) {
//                adapter.refresh(listVariants);
//                isUserAvailable(true);
//            } else
//                isUserAvailable(false);
//        });

        variantVM.getVariants().observe(this, new Observer<Variants>() {
            @Override
            public void onChanged(Variants variants) {
                ArrayList<VariantGroups> listVariants = variants.getArrVariantGroups();
                Log.d("getVariants", listVariants.size() + "");
                if(listVariants != null) {
                    adapter.refresh(listVariants);
                    isUserAvailable(true);
                } else
                    isUserAvailable(false);
            }
        });
        setAdapter();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                variantVM.getData();
                Snackbar.make(view, "Calling API again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setAdapter() {
        adapter = new VariantsAdapter(this, new ArrayList<VariantGroups>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPizza.setLayoutManager(mLayoutManager);
        rvPizza.setItemAnimator(new DefaultItemAnimator());
        rvPizza.setAdapter(adapter);

        isUserAvailable(false);
    }

    private void isUserAvailable(boolean isData) {
        if(!isData)
            tvNoData.setVisibility(View.GONE);
        else
            tvNoData.setVisibility(View.VISIBLE);
    }
}
