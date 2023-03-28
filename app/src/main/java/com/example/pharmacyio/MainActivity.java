package com.example.pharmacyio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.SearchEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.example.pharmacyio.Adaptor.category_adaptor;
import com.example.pharmacyio.Adaptor.product_adaptor;
import com.example.pharmacyio.domain.category_domain;
import com.example.pharmacyio.domain.products_domain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView_CategoryList, recyclerView_ProductsList;
    Button order_now;

    private FirebaseUser user;
    private String userId;
    private DatabaseReference databaseReference;
    private SearchView searchView;
    private List<products_domain> productsDomains;

    TextView user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();        //remove the cursor from searchview
        user_name = (TextView) findViewById(R.id.user_name);
        order_now =findViewById(R.id.order_now);
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

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");


        databaseReference.child(String.valueOf(userId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){

                    String name = userProfile.userName;

                    user_name.setText(name);

                }
            }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });


        Category();
        Products();
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