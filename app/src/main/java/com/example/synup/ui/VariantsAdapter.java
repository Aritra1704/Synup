package com.example.synup.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.synup.R;
import com.example.synup.models.VariantGroups;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VariantsAdapter extends RecyclerView.Adapter<VariantsAdapter.VariantHolder> {
    private Context context;
    private List<VariantGroups> variantGroups;

    public class VariantHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvVariantName)
        TextView tvVariantName;

        public VariantHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    public void refresh(List<VariantGroups> users) {
        this.variantGroups = users;
        notifyDataSetChanged();
    }


    public VariantsAdapter(Context context, List<VariantGroups> users) {
        this.context = context;
        this.variantGroups = users;
    }

    @Override
    public VariantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.variant_parent_item, parent, false);

        return new VariantHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VariantHolder holder, int position) {
        holder.tvVariantName.setText(variantGroups.get(position).getName());
//        holder.tvPasscode.setText(variantGroups.get(position).getPasscode() + "");
    }

    @Override
    public int getItemCount() {
        return variantGroups.size();
    }
}
