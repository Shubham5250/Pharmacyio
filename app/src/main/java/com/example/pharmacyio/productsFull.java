package com.example.pharmacyio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pharmacyio.Adaptor.productFull_adapter;
import com.example.pharmacyio.Adaptor.product_adaptor;
import com.example.pharmacyio.domain.products_domain;

import java.util.ArrayList;

public class productsFull extends AppCompatActivity {


    private RecyclerView product_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_full);

        product_list = findViewById(R.id.products_list_full);
        Products();
    }

    private void Products(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(productsFull.this, LinearLayoutManager.VERTICAL,false);

        product_list = findViewById(R.id.products_list_full);

        product_list.setLayoutManager(linearLayoutManager);

        ArrayList<products_domain> productDomains = new ArrayList<>();

        productDomains.add(new products_domain("Multivitamin", "multivitamin", "149"));
        productDomains.add(new products_domain("Azithromycin", "azithro", "75"));
        productDomains.add(new products_domain("Vitamin D3", "vitamind3", "251"));
        productDomains.add(new products_domain("Cipladine", "cipladine", "109"));
        productDomains.add(new products_domain("Omeprazole", "omeprazole", "175"));
        productDomains.add(new products_domain("Levothryoxine", "levothyroxine", "49"));
        productDomains.add(new products_domain("Paracetamol", "paracetamol", "29"));


        RecyclerView.Adapter adapter_products = new productFull_adapter(productDomains);
        product_list.setAdapter(adapter_products);
    }
}