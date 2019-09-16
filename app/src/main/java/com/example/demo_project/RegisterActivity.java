package com.example.demo_project;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText Rname, Rusername, Rpassword;
    Button register;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.Rtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Rname=(MaterialEditText)findViewById(R.id.name);
        Rusername=(MaterialEditText)findViewById(R.id.username);
        Rpassword=(MaterialEditText)findViewById(R.id.password);
        register = (Button)findViewById(R.id.register);
        progress = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();
            }
        });
    }

    void registration() {
        progress.setMessage("Please Wait......");
        progress.show();


        final String name=Rname.getText().toString();
        final String username=Rusername.getText().toString();
        final String password=Rpassword.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.registration,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")){

                                //String user=jsonObject.getString("donor");
                                progress.dismiss();
                                Toast.makeText(RegisterActivity.this,"Registration Successful "+username,Toast.LENGTH_SHORT).show();
                            }
                            else if(jsonObject.names().get(0).equals("notsuccess")){
                                progress.dismiss();
                                Toast.makeText(RegisterActivity.this,"Email Already present ",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("name", name);
                params.put("username", username);
                params.put("password",password );

                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
}
