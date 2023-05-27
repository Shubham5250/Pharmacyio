package com.example.pharmacyio.Adaptor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pharmacyio.R;

import com.example.pharmacyio.domain.products_domain;
import com.example.pharmacyio.urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class productFull_adapter extends RecyclerView.Adapter<productFull_adapter.ViewHolder> {
    ArrayList<products_domain> productDomains;

    public productFull_adapter(ArrayList<products_domain> productDomains  ) {
        this.productDomains = productDomains;
    }

    @NonNull
    @Override
    public productFull_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_products_full,parent,false);
        return new productFull_adapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull productFull_adapter.ViewHolder holder, int position) {
        holder.productName.setText(productDomains.get(position).getProduct_name());
        holder.productFee.setText(productDomains.get(position).getProduct_fee());

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View editLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.edit_product, null);
                EditText productName = editLayout.findViewById(R.id.productName);
                EditText productUserEmail = editLayout.findViewById(R.id.productUserEmail);
                EditText productPrice = editLayout.findViewById(R.id.productPrice);
                productName.setText(productDomains.get(position).getProduct_name());
                productPrice.setText(productDomains.get(position).getProduct_fee());
                AlertDialog.Builder builder = new AlertDialog.Builder(editLayout.getContext());
                builder.setTitle("Edit");
                builder.setView(editLayout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = productName.getText().toString();
                        String email = productUserEmail.getText().toString();
                        String price = productPrice.getText().toString();
                        if (name.isEmpty() || email.isEmpty() || price.isEmpty()) {
                            Toast.makeText(editLayout.getContext(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                        } else {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.EDIT_USER_URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(editLayout.getContext(), response, Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(editLayout.getContext(), ""+error, Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("productName", name);
                                    params.put("productUserEmail", email);
                                    params.put("productPrice", price);
                                    return params;


                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                            requestQueue.add(stringRequest);

                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
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
        Button Edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productFee = itemView.findViewById(R.id.product_fee);
            productName = itemView.findViewById(R.id.product_title);
            productPic = itemView.findViewById(R.id.product_pic);
            Edit = itemView.findViewById(R.id.edit);
        }
    }
}
