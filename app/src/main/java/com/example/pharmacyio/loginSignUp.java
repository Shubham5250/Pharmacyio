package com.example.pharmacyio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class loginSignUp extends AppCompatActivity {

    Button register;
    TextView signup, login, login_btnuser, signup_btn, sign_up_btn_register, donthaveacc, login_user_btn;
    EditText signup_username, signup_userphone, signup_useremail, signup_userpassword, signup_userconfirmpassword, login_email, login_password;

    LinearLayout signUp_userLayout, login_userLayout;

    ProgressBar progressBar;

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREF_NAME = "MyPrefFile";

    SharedPreferences sharedPreferences;

    String name, email, password, emaill, passwordl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        register = findViewById(R.id.register);

        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        login_btnuser = findViewById(R.id.login_btnuser);
        signup_btn = findViewById(R.id.signup_btn);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);

        signUp_userLayout = findViewById(R.id.signupuser_layout);
        login_userLayout = findViewById(R.id.loginuser_layout);

        login_user_btn = findViewById(R.id.login_user_btn);
        signup_username = findViewById(R.id.signup_username);
        signup_useremail = findViewById(R.id.signup_useremail);
        signup_userpassword = findViewById(R.id.signup_userpassword);

        sign_up_btn_register = findViewById(R.id.signup_btn_register);

        progressBar = findViewById(R.id.SHOW_PROGRESS);

        if (sharedPreferences.getString("logged", "false").equals("true")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        login_btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp_userLayout.setVisibility(View.GONE);
                login_userLayout.setVisibility(View.VISIBLE);
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp_userLayout.setVisibility(View.VISIBLE);
                login_userLayout.setVisibility(View.GONE);
            }
        });

        login_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){


                passwordl = String.valueOf(login_password.getText());
                emaill = String.valueOf(login_email.getText());
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String login_url ="http://192.168.0.100/login_registration/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    if(status.equals("success")){
                                        name = jsonObject.getString("name");
                                        email = jsonObject.getString("email");
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("logged", "true");
                                        editor.putString("name", name);
                                        editor.putString("email", email);
                                        editor.apply();
                                        Toast.makeText(loginSignUp.this, "Hii", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(loginSignUp.this, "Error Server: "+error, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", emaill);
                        paramV.put("password", passwordl);
                        return paramV;
                    }
                };

                queue.add(stringRequest);
            }
        });

        sign_up_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                sign_up_btn_register.setVisibility(View.GONE);
                name = String.valueOf(signup_username.getText());
                password = String.valueOf(signup_userpassword.getText());
                email = String.valueOf(signup_useremail.getText());
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String register_url ="http://192.168.123.197/login_registration/register.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                if(response.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);

                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext() , "Error: "+response, Toast.LENGTH_SHORT).show();
                                    sign_up_btn_register.setVisibility(View.VISIBLE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        sign_up_btn_register.setVisibility(View.VISIBLE);
                        Toast.makeText(loginSignUp.this, "Error Server", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("name", name);
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };

                queue.add(stringRequest);
            }
        });

    }


}