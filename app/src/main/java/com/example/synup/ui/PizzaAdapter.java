package com.example.synup.ui;

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
import com.example.synup.models.PizzaDetail;
import com.example.synup.viewmodel.VariantVM;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaHolder> {
    private Context context;
    private List<PizzaDetail> pizzaDetails;

    private VariantVM variantVM;

    public class PizzaHolder extends RecyclerView.ViewHolder {

        View myView;
        @BindView(R.id.tvPizzaCrust)
        TextView tvPizzaCrust;
        @BindView(R.id.tvPizzaSize)
        TextView tvPizzaSize;
        @BindView(R.id.tvPizzaSauce)
        TextView tvPizzaSauce;
        @BindView(R.id.tvPizzaPrice)
        TextView tvPizzaPrice;

        public PizzaHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

            myView = view;
        }
    }

    public void refresh(List<PizzaDetail> users) {
        this.pizzaDetails = users;
        notifyDataSetChanged();
    }


    public PizzaAdapter(Context context, List<PizzaDetail> users) {
        this.context = context;
        this.pizzaDetails = users;
    }

    @Override
    public PizzaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_detail_item, parent, false);

        return new PizzaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PizzaHolder holder, int position) {
        PizzaDetail pizza = pizzaDetails.get(position);
        holder.tvPizzaCrust.setText(pizza.getCrust());
        holder.tvPizzaSize.setText(pizza.getSize());
        holder.tvPizzaSauce.setText(pizza.getSauce());
        holder.tvPizzaPrice.setText(pizza.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return pizzaDetails.size();
    }
}
