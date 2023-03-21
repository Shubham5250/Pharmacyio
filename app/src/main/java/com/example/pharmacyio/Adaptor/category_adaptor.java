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
import com.example.pharmacyio.domain.category_domain;

import java.util.ArrayList;

public class category_adaptor extends RecyclerView.Adapter<category_adaptor.ViewHolder> {

    ArrayList<category_domain> categoryDomains;

    public category_adaptor(ArrayList<category_domain> categoryDomains  ) {
        this.categoryDomains = categoryDomains;
    }

    @NonNull
    @Override
    public category_adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull category_adaptor.ViewHolder holder, int position) {
        holder.title.setText(categoryDomains.get(position).getTitle());

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(categoryDomains.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.category_name);
            pic = itemView.findViewById(R.id.category_image);
        }
    }
}
