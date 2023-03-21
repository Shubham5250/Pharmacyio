package com.example.pharmacyio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginSignUp extends AppCompatActivity {

    TextView signup, login, hasaccounttxt, sign_up_btn_register, donthaveacc, login_user_btn;
    EditText signup_username, signup_userphone, signup_useremail, signup_userpassword, signup_userconfirmpassword;

    LinearLayout signUp_userLayout, login_userLayout;

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREF_NAME = "MyPrefFile";

    FirebaseAuth mAuth;
    FirebaseAuth mAuth1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mAuth1 = FirebaseAuth.getInstance();

        signUp_userLayout = findViewById(R.id.signupuser_layout);
        login_userLayout = findViewById(R.id.loginuser_layout);
        login = findViewById(R.id.login_btnuser);
        signup = findViewById(R.id.signup_btn);
        login_user_btn = findViewById(R.id.login_user_btn);
        hasaccounttxt = findViewById(R.id.hasaccounttxt);
        donthaveacc = findViewById(R.id.donthaveacc);
        signup_username = findViewById(R.id.signup_username);
        signup_userphone = findViewById(R.id.signup_userphone);
        signup_useremail = findViewById(R.id.signup_useremail);
        hasaccounttxt = findViewById(R.id.hasaccounttxt);
        signup_userpassword = findViewById(R.id.signup_userpassword);
        signup_userconfirmpassword = findViewById(R.id.signup_userconfirmpassword);

        sign_up_btn_register = findViewById(R.id.signup_btn_register);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_userLayout.setVisibility(View.GONE);
                signUp_userLayout.setVisibility(View.VISIBLE);
                donthaveacc.setVisibility(View.GONE);
                hasaccounttxt.setVisibility(View.VISIBLE);
                signup.setTextColor(getResources().getColor(R.color.theme_color));
                login.setTextColor(getResources().getColor(R.color.default_color));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_userLayout.setVisibility(View.VISIBLE);
                signUp_userLayout.setVisibility(View.GONE);
                hasaccounttxt.setVisibility(View.GONE);
                donthaveacc.setVisibility(View.VISIBLE);
                login.setTextColor(getResources().getColor(R.color.theme_color));
                signup.setTextColor(getResources().getColor(R.color.default_color));
            }
        });

        login_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("hasLoggedIn", true);
                editor.apply();

            }
        });

        sign_up_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("hasRegistered", true);
                editor.apply();
                registerUser();

            }
        });

    }

    private void registerUser(){
        String user_name = signup_username.getText().toString();
        String user_email = signup_useremail.getText().toString();
        String user_phone = signup_userphone.getText().toString();
        String user_password = signup_userpassword.getText().toString();
        String conf_password = signup_userconfirmpassword.getText().toString();

        if(user_name.isEmpty()){
            signup_username.setError("Name is required");
            signup_username.requestFocus();
            return;
        }
        else if(user_email.isEmpty()){
            signup_useremail.setError("Email is required");
            signup_useremail.requestFocus();
            return;
        }
        else if(user_phone.isEmpty()){
            signup_userphone.setError("Phone number is required");
            signup_userphone.requestFocus();
            return;
        }
        else if(user_password.isEmpty()){
            signup_userpassword.setError("Password is required");
            signup_userpassword.requestFocus();
            return;
        }
        else if(user_password.length()<6){
            signup_userpassword.setError("Password should be atleast ");
            signup_userpassword.requestFocus();
            return;
        }
        if(!user_password.matches(conf_password)){
            signup_userconfirmpassword.setError("Password should match with the above");
            signup_userconfirmpassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(user_email,user_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(user_name, user_email, user_phone);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())      //we get the registered users id and set it here, so that we can correspond this object(i.e user) to the registered user
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task2) {

                                            if(task2.isSuccessful()){

                                                sendUserToNextActivity();
                                            }
                                            else{


                                                Toast.makeText(loginSignUp.this, ""+task2.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(loginSignUp.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void sendUserToNextActivity() {
        Intent i = new Intent(loginSignUp.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}