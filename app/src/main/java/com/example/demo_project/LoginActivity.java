package com.example.demo_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button login, register;
    RequestQueue requestQueue;
    private ProgressDialog progress;
    StringRequest request;
    MaterialEditText username, password;
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username=(MaterialEditText)findViewById(R.id.username);
        password=(MaterialEditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);
        progress = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(Intent);
            }
        });
    }

    public void validator() {
        //Toast.makeText(LoginActivity.this, "inside validator", Toast.LENGTH_SHORT).show();
        progress.setMessage("Please Wait......");
        progress.show();
        request = new StringRequest(Request.Method.POST, UrlClass.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(LoginActivity.this, "resp="+response, Toast.LENGTH_SHORT).show();
                try {
                    Toast.makeText(LoginActivity.this, "resp="+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.names().get(0).equals("success")){

                        token = username.getText().toString();

                        Toast.makeText(LoginActivity.this,"login successful ",Toast.LENGTH_SHORT).show();

                        progress.dismiss();
                        Intent myIntent2 = new Intent(LoginActivity.this, MainActivity.class);
                        myIntent2.putExtra("username",username.getText().toString() );
                        myIntent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(myIntent2);

                    }
                    else if(jsonObject.names().get(0).equals("failure")){

                        Toast.makeText(LoginActivity.this,"login not successful ",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }

        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
