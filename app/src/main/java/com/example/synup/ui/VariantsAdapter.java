package com.example.synup.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.arpaul.utilitieslib.StringUtils;
import com.example.synup.R;
import com.example.synup.models.VariantGroups;
import com.example.synup.viewmodel.VariantVM;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VariantsAdapter extends RecyclerView.Adapter<VariantsAdapter.VariantHolder> {
    private Context context;
    private List<VariantGroups> variantGroups;

    private VariantVM variantVM;

    public class VariantHolder extends RecyclerView.ViewHolder {

        View myView;

        @BindView(R.id.tvVariantHeader)
        TextView tvVariantHeader;

        @BindView(R.id.tvVariantName)
        TextView tvVariantName;

        public VariantHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

            myView = view;
        }
    }

    public void refresh(List<VariantGroups> users) {
        this.variantGroups = users;
        notifyDataSetChanged();
    }


    public VariantsAdapter(Context context, List<VariantGroups> users) {
        this.context = context;
        this.variantGroups = users;

        variantVM = ViewModelProviders.of((AppCompatActivity) context).get(VariantVM.class);
    }

    @Override
    public VariantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.variant_parent_item, parent, false);

        return new VariantHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VariantHolder holder, int position) {
        VariantGroups variant = variantGroups.get(position);
        holder.tvVariantHeader.setText(variant.getName() + "    ??");
        holder.myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvVariantName.performLongClick();
            }
        });
        holder.tvVariantName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CreateActivity)context).openChildDialog(variant.getName(), variant.getArrVariation(),
                        StringUtils.getInt(variantGroups.get(position).getGroup_id()),
                        new selectedVariant() {
                    @Override
                    public void selected(String selected) {
                        holder.tvVariantName.setText(selected);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return variantGroups.size();
    }

    public interface selectedVariant {
        public void selected(String selected);
    }
}
