package com.example.pharmacyio;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pharmacyio.Adaptor.category_adaptor;
import com.example.pharmacyio.Adaptor.product_adaptor;
import com.example.pharmacyio.domain.category_domain;
import com.example.pharmacyio.domain.products_domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView_CategoryList, recyclerView_ProductsList;
    Button order_now;

    private String userId;
    private SearchView searchView;
    private List<products_domain> productsDomains;

    TextView view_products, view_category;
    SharedPreferences sharedPreferences;
    TextView user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        view_products = findViewById(R.id.view_products);
        view_category = findViewById(R.id.view_category);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();        //remove the cursor from searchview
        order_now =findViewById(R.id.order_now);

        if (sharedPreferences.getString("logged", "false").equals("true")) {
            Intent intent = new Intent(getApplicationContext(), loginSignUp.class);
            startActivity(intent);
            finish();
        }
        order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, loginSignUp.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        view_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, productsFull.class);
                startActivity(intent);
            }
        });
        Category();
        Products();
        fetchUser();

    }

    private void fetchUser(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String login_url ="http://192.168.0.100/login_registration/logout.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            if(response.equals("success")){

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("logged", "true");
                                editor.putString("name", "");
                                editor.putString("email", "");
                                editor.apply();
                                Toast.makeText(getApplicationContext(), "Hii", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();

                            }
                        }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error Server: "+error, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("email", sharedPreferences.getString("email", ""));
                return paramV;
            }
        };

        queue.add(stringRequest);
    }


    private void Category(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false);

        recyclerView_CategoryList = findViewById(R.id.recyclerView_CategoryList);

        recyclerView_CategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<category_domain> categoryDomains = new ArrayList<>();

        categoryDomains.add(new category_domain("Medicines", "category_medicines"));
        categoryDomains.add(new category_domain("lat tests", "labtest_category"));
        categoryDomains.add(new category_domain("Ask doctor", "ask_doctor"));
        categoryDomains.add(new category_domain("Consult Store", "consult_category"));


        RecyclerView.Adapter adapter_category = new category_adaptor(categoryDomains);
        recyclerView_CategoryList.setAdapter(adapter_category);
    }

    private void Products(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false);

        recyclerView_ProductsList = findViewById(R.id.recycylerView_ProductsList);

        recyclerView_ProductsList.setLayoutManager(linearLayoutManager);

        ArrayList<products_domain> productDomains = new ArrayList<>();

        productDomains.add(new products_domain("Multivitamin", "multivitamin", "149"));
        productDomains.add(new products_domain("Azithromycin", "azithro", "75"));
        productDomains.add(new products_domain("Vitamin D3", "vitamind3", "251"));
        productDomains.add(new products_domain("Cipladine", "cipladine", "109"));
        productDomains.add(new products_domain("Omeprazole", "omeprazole", "175"));
        productDomains.add(new products_domain("Levothryoxine", "levothyroxine", "49"));
        productDomains.add(new products_domain("Paracetamol", "paracetamol", "29"));


        RecyclerView.Adapter adapter_products = new product_adaptor(productDomains);
        recyclerView_ProductsList.setAdapter(adapter_products);
    }

}