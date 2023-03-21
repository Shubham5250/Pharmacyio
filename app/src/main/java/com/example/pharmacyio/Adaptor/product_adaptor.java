package com.example.pharmacyio.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pharmacyio.R;

import com.example.pharmacyio.domain.products_domain;

import java.util.ArrayList;

public class product_adaptor extends RecyclerView.Adapter<product_adaptor.ViewHolder> {
    ArrayList<products_domain> productDomains;

    public product_adaptor(ArrayList<products_domain> productDomains  ) {
        this.productDomains = productDomains;
    }

    @NonNull
    @Override
    public product_adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_products,parent,false);
        return new product_adaptor.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull product_adaptor.ViewHolder holder, int position) {
        holder.productName.setText(productDomains.get(position).getProduct_name());
        holder.productFee.setText(productDomains.get(position).getProduct_fee());

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(productDomains.get(position).getProduct_pic(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.productPic);
    }

    @Override
    public int getItemCount() {
        return productDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productFee, productName;
        ImageView productPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productFee = itemView.findViewById(R.id.product_fee);
            productName = itemView.findViewById(R.id.product_title);
            productPic = itemView.findViewById(R.id.product_pic);
        }
    }
}
